package com.yourproject.dto;

import java.util.List;

public class GoogleEmbeddingResponse {
    private Embedding embedding;

    public List<Double> getEmbedding() {
        if (embedding == null || embedding.values == null) {
            return List.of();
        }
        return embedding.values;
    }

    public static class Embedding {
        private List<Double> values;

        public List<Double> getValues() {
            return values;
        }

        public void setValues(List<Double> values) {
            this.values = values;
        }
    }
}