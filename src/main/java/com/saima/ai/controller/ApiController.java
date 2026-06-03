package com.saima.ai.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    @Value("${ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    private RestClient getOllamaClient() {
        return RestClient.builder()
                .baseUrl(ollamaBaseUrl)
                .build();
    }

    @PostMapping("/health")
    public ResponseEntity<?> health() {
        try {
            getOllamaClient().get()
                    .uri("/api/tags")
                    .retrieve()
                    .body(Map.class);

            Map<String, Object> healthResponse = new HashMap<>();
            healthResponse.put("status", "UP");
            healthResponse.put("llm_service", "healthy");
            healthResponse.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.ok(healthResponse);
        } catch (RestClientException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "DOWN");
            errorResponse.put("llm_service", "unhealthy");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(503).body(errorResponse);
        }
    }

    @GetMapping("/models")
    public ResponseEntity<?> getModels() {
        try {
            Map<String, Object> response = getOllamaClient().get()
                    .uri("/api/tags")
                    .retrieve()
                    .body(Map.class);

            return ResponseEntity.ok(response);
        } catch (RestClientException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch models");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(503).body(errorResponse);
        }
    }
}
