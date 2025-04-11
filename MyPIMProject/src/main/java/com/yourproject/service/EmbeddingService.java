package com.yourproject.service;

import com.yourproject.dto.GoogleEmbeddingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate;

    @Value("${google.ai.key}")
    private String apiKey;

    @Value("${google.ai.embedding.model}")
    private String model; // Should be set to "text-embedding-004" without "models/" prefix

    public EmbeddingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Double> generateEmbedding(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Simplified request body format
        Map<String, Object> requestBody = Map.of(
                "model", model,
                "content", Map.of(
                        "parts", List.of(
                                Map.of("text", text)
                        )
                )
        );

        // Correct URL format for the embedding endpoint
        String url = String.format(
                "https://generativelanguage.googleapis.com/v1beta/models/%s:embedContent?key=%s",
                model,
                apiKey
        );

        try {
            GoogleEmbeddingResponse response = restTemplate.postForObject(
                    url,
                    new HttpEntity<>(requestBody, headers),
                    GoogleEmbeddingResponse.class
            );

            // Improved error handling
            if (response == null || response.getEmbedding() == null || response.getEmbedding().isEmpty()) {
                System.out.println("Warning: Embedding response was null or empty");
                return List.of(); // Return empty list as fallback
            }
            return response.getEmbedding();
        } catch (Exception e) {
            System.out.println("Error in embedding service: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list as fallback
        }
    }
}