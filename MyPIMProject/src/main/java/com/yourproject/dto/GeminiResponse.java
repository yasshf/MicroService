package com.yourproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GeminiResponse {
    @JsonProperty("candidates")
    private List<Candidate> candidates;

    // For debugging
    @JsonProperty("promptFeedback")
    private Object promptFeedback;

    public String getText() {
        if (candidates == null || candidates.isEmpty()) {
            System.out.println("No candidates in response");
            return "I don't have specific information about this course.";
        }

        // Log the number of candidates for debugging
        System.out.println("Number of candidates: " + candidates.size());

        Candidate candidate = candidates.get(0);
        if (candidate == null) {
            System.out.println("First candidate is null");
            return "I don't have specific information about this course.";
        }

        if (candidate.content == null) {
            System.out.println("Candidate content is null");
            return "I don't have specific information about this course.";
        }

        if (candidate.content.parts == null || candidate.content.parts.isEmpty()) {
            System.out.println("Candidate parts are null or empty");
            return "I don't have specific information about this course.";
        }

        // Log all parts for debugging
        for (int i = 0; i < candidate.content.parts.size(); i++) {
            Part part = candidate.content.parts.get(i);
            System.out.println("Part " + i + ": " + (part != null ? part.getText() : "null"));
        }

        // Combine all text parts to get complete response
        StringBuilder fullResponse = new StringBuilder();
        for (Part part : candidate.content.parts) {
            if (part != null && part.getText() != null && !part.getText().isEmpty()) {
                fullResponse.append(part.getText()).append(" ");
            }
        }

        String result = fullResponse.toString().trim();

        if (result.isEmpty()) {
            System.out.println("All parts were empty or null");
            return "I don't have specific information about this course.";
        }

        return result;
    }

    // For debugging purposes
    @Override
    public String toString() {
        return "GeminiResponse{" +
                "candidates=" + (candidates != null ? candidates.size() : "null") +
                ", promptFeedback=" + promptFeedback +
                '}';
    }

    public static class Candidate {
        @JsonProperty("content")
        private Content content;

        @JsonProperty("finishReason")
        private String finishReason;

        // Safety ratings and other fields might be present

        public Content getContent() {
            return content;
        }
    }

    public static class Content {
        @JsonProperty("parts")
        private List<Part> parts;

        @JsonProperty("role")
        private String role;

        public List<Part> getParts() {
            return parts;
        }

        public String getRole() {
            return role;
        }
    }

    public static class Part {
        @JsonProperty("text")
        private String text;

        public String getText() {
            return text;
        }
    }
}