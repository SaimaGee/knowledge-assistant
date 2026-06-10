package com.saima.ai.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final RestClient ollamaClient;

    public ApiController(@Value("${ollama.base-url:http://localhost:11434}") String ollamaBaseUrl) {
        this.ollamaClient = RestClient.builder()
                .baseUrl(ollamaBaseUrl)
                .build();
    }

    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        try {
            ollamaClient.get()
                    .uri("/api/tags")
                    .retrieve()
                    .toBodilessEntity();

            return ResponseEntity.ok(HealthResponse.healthy());
        } catch (RestClientException e) {
            return ResponseEntity.status(503)
                    .body(HealthResponse.unhealthy(e.getMessage()));
        }
    }

    @GetMapping("/models")
    public ResponseEntity<?> getModels() {
        try {
            var response = ollamaClient.get()
                    .uri("/api/tags")
                    .retrieve()
                    .body(OllamaTagsResponse.class);

            return ResponseEntity.ok(response);
        } catch (RestClientException e) {
            return ResponseEntity.status(503)
                    .body(new ErrorResponse("Failed to fetch models", e.getMessage()));
        }
    }

    // --- DTOs as records for immutability and less boilerplate ---

    public record HealthResponse(
            String status,
            String llmService,
            String error,
            Instant timestamp
    ) {
        public static HealthResponse healthy() {
            return new HealthResponse("UP", "healthy", null, Instant.now());
        }

        public static HealthResponse unhealthy(String error) {
            return new HealthResponse("DOWN", "unhealthy", error, Instant.now());
        }
    }

    public record ErrorResponse(
            String error,
            String message,
            Instant timestamp
    ) {
        public ErrorResponse(String error, String message) {
            this(error, message, Instant.now());
        }
    }

    // Placeholder — expand to match Ollama's actual response structure
    public record OllamaTagsResponse(java.util.List<Model> models) {
        public record Model(String name, String model, long size) {}
    }
}
