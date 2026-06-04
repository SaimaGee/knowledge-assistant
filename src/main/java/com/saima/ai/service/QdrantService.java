package com.saima.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class QdrantService {

    private final RestClient client =
            RestClient.builder()
                    .baseUrl("http://localhost:6333")
                    .build();

    @PostConstruct
    public void initialize() {
        try {
            Map<String, Object> body =
                    Map.of(
                            "vectors",
                            Map.of(
                                    "size", 768,
                                    "distance", "Cosine"
                            )
                    );

            client.put()
                    .uri("/collections/knowledge-base")
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();

            log.info("Qdrant collection initialized");
        } catch (Exception e) {
            log.info("Collection may already exist");
        }
    }

    public void saveChunk(
            int id,
            String text,
            List<Double> embedding) {
        Map<String, Object> payload =
                Map.of(
                        "points",
                        List.of(
                                Map.of(
                                        "id", id,
                                        "vector", embedding,
                                        "payload",
                                        Map.of(
                                                "text", text
                                        )
                                )
                        )
                );

        try {

                client.put()
                        .uri("/collections/knowledge-base/points")
                        .body(payload)
                        .retrieve()
                        .toBodilessEntity();

        } catch (Exception e) {
                log.error(
                        "Failed to save chunk {}",
                        id,
                        e
                );
        }        
    }

    public List<String> search(List<Double> vector) {
        Map<String, Object> body =
                Map.of(
                        "vector", vector,
                        "limit", 5,
                        "with_payload", true
                );

        Map<String, Object> response =
                client.post()
                        .uri("/collections/knowledge-base/points/search")
                        .body(body)
                        .retrieve()
                        .body(Map.class);

        List<String> chunks = new ArrayList<>();

        List<Map<String, Object>> results =
                (List<Map<String, Object>>) response.get("result");

        if (results != null) {
            for (Map<String, Object> hit : results) {
                Object payloadObj = hit.get("payload");

                if (payloadObj instanceof Map<?, ?> payload) {
                    Object textObj = payload.get("text");
                    if (textObj instanceof String text) {
                        chunks.add(text);
                        continue;
                    }
                }

                Object id = hit.get("id");
                if (id != null) {
                    chunks.add("point-id:" + id);
                }
            }
        }

        return chunks;
    }
}
