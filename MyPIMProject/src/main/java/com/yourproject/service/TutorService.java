package com.yourproject.service;

import com.yourproject.dto.GeminiResponse;
import com.yourproject.model.Course;
import com.yourproject.model.CourseChunk;
import com.yourproject.repository.CourseChunkRepository;
import com.yourproject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TutorService {

    private final RestTemplate restTemplate;
    private final CourseChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;
    private final CourseRepository courseRepository;

    @Value("${google.ai.key}")
    private String apiKey;

    @Value("${google.ai.model}")
    private String model; // This should be set to "gemini-2.0-flash" without "models/" prefix

    private final Map<String, StringBuilder> sessionContext = new HashMap<>();

    public TutorService(RestTemplate restTemplate,
                        CourseChunkRepository chunkRepository,
                        EmbeddingService embeddingService,
                        CourseRepository courseRepository) {
        this.restTemplate = restTemplate;
        this.chunkRepository = chunkRepository;
        this.embeddingService = embeddingService;
        this.courseRepository = courseRepository;
    }

    public String answerQuestion(Long categoryId, Long courseId, String question) {
        // Retrieve the course by ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Ensure the course belongs to the correct category
        if (!course.getCategory().getId().equals(categoryId)) {
            throw new RuntimeException("Course does not belong to category");
        }

        // Generate embeddings for the question
        List<Double> questionEmbedding;
        try {
            questionEmbedding = embeddingService.generateEmbedding(question);
            System.out.println("Question Embedding: " + questionEmbedding);
        } catch (Exception e) {
            System.out.println("Error generating embeddings: " + e.getMessage());
            throw new RuntimeException("Failed to generate embeddings for the question.");
        }

        // Retrieve relevant course chunks based on the question embedding
        List<CourseChunk> relevantChunks = chunkRepository.findRelevantChunks(courseId, questionEmbedding);

        // Log relevant chunks
        if (relevantChunks.isEmpty()) {
            System.out.println("No relevant chunks found.");
        } else {
            relevantChunks.forEach(chunk -> System.out.println("Relevant chunk: " + chunk.getContent()));
        }

        // Combine the relevant course chunks into a single context string
        String context = relevantChunks.stream()
                .map(CourseChunk::getContent)
                .collect(Collectors.joining("\n\n"));

        // Generate the AI answer based on the context and question
        return generateAnswer(question, context);
    }
    private String generateAnswer(String question, String context) {
        // Check if context is empty
        boolean hasContent = context != null && !context.trim().isEmpty();

        String prompt;

        if (hasContent) {
            // Normal prompt when we have content
            prompt = """
        You are a helpful course assistant. Answer the question based on the following course content.
        Provide detailed explanations and examples when possible.
        
        Course Content:
        %s
        
        Question: %s
        """.formatted(context, question);
        } else {
            // Special prompt for when we don't have content
            prompt = """
        You are a helpful course assistant. 
        The specific course content related to this question is not available in my database.
        
        However, please provide a general response about what "%s" might refer to in an educational context.
        Suggest what topics this course might cover based on common curriculum standards.
        Also suggest what specific topics the student might want to look for in their course materials.
        
        Be helpful but make it clear you don't have the specific course details.
        """.formatted(question);
        }

        // Log the prompt that is sent to the AI model
        System.out.println("Prompt sent to AI: " + prompt);

        // Send request to AI model
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body with generation config for better responses
        Map<String, Object> requestBody = new HashMap<>();

        // Add contents
        requestBody.put("contents", List.of(
                Map.of("role", "user",
                        "parts", List.of(
                                Map.of("text", prompt)
                        )
                )
        ));

        // Add generation config for more helpful responses
        requestBody.put("generationConfig", Map.of(
                "temperature", 0.7,
                "topK", 40,
                "topP", 0.95,
                "maxOutputTokens", 800
        ));

        try {
            // Ensure the URL is constructed correctly
            ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                    String.format("https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s", model, apiKey),
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody, headers),
                    GeminiResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                // Log the full response body for debugging
                System.out.println("Full response: " + response.getBody());

                String answer = response.getBody().getText();

                // Check if answer is still "I don't know" or empty
                if (answer == null || answer.trim().isEmpty() || answer.equalsIgnoreCase("I don't know.")) {
                    if (hasContent) {
                        answer = "Based on the available course content, I can't provide a specific answer to your question. Please try asking about another topic or check your course materials for more information.";
                    } else {
                        answer = "I don't have access to the specific content of this course. However, based on the topic, this course might cover common subjects like [relevant subjects]. I recommend checking your syllabus or course materials for the exact topics covered.";
                    }
                }

                // Log AI response
                System.out.println("AI Answer: " + answer);
                return answer;
            } else {
                System.out.println("Error: Received non-OK response from AI API.");
                throw new RuntimeException("Failed to generate AI answer: Non-OK response from AI API.");
            }
        } catch (Exception e) {
            // Handle all errors
            System.out.println("Error generating AI answer: " + e.getMessage());
            e.printStackTrace();
            return "I'm sorry, I encountered a technical issue while trying to answer your question. Please try again or contact support if the problem persists.";
        }
    }


}