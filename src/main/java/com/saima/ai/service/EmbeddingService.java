package com.saima.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.saima.ai.model.request.EmbeddingRequest;
import com.saima.ai.model.response.EmbeddingResponse;

@Service
public class EmbeddingService {

    private final RestClient restClient;

    public EmbeddingService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    public List<Double> generateEmbedding(
            String text) {

        EmbeddingRequest request =
                new EmbeddingRequest(
                        "nomic-embed-text",
                        text
                );

        EmbeddingResponse response =
                restClient.post()
                        .uri("/api/embeddings")
                        .body(request)
                        .retrieve()
                        .body(EmbeddingResponse.class);

        return response.embedding();
    }
}