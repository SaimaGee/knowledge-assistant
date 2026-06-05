package com.saima.ai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

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
            Object id,
            String text,
            List<Double> embedding) {
        // Qdrant requires point id to be an unsigned integer or UUID. Use a UUID for the point id and
        // store the original chunk id in the payload as `source_id`.
        String pointId = UUID.randomUUID().toString();

        Map<String, Object> payload =
                Map.of(
                        "points",
                        List.of(
                                Map.of(
                                        "id", pointId,
                                        "vector", embedding,
                                        "payload",
                                        Map.of(
                                                "text", text,
                                                "source_id", id
                                        )
                                )
                        )
                );

        try {
            log.debug("Saving chunk source_id={} as pointId={} vectorLength={} textPreview={}", id, pointId, embedding.size(), text.length() > 80 ? text.substring(0, 80) + "..." : text);

                client.put()
                        .uri("/collections/knowledge-base/points")
                        .body(payload)
                        .retrieve()
                        .toBodilessEntity();

                log.info("Saved chunk source_id={} as pointId={} to Qdrant", id, pointId);
        } catch (Exception e) {
                log.error(
                        "Failed to save chunk {}",
                        id,
                        e
                );
        }        
    }

    public List<String> search(List<Double> vector) {
        log.info("Searching Qdrant with question embedding length={}", vector.size());
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
            log.info("Qdrant returned {} result(s)", results.size());
            for (Map<String, Object> hit : results) {
                Object payloadObj = hit.get("payload");
                Object id = hit.get("id");
                String preview = "";

                if (payloadObj instanceof Map<?, ?> payload) {
                    Object textObj = payload.get("text");
                    if (textObj instanceof String text) {
                        preview = text.length() > 80 ? text.substring(0, 80) + "..." : text;
                        log.debug("Qdrant hit id={} payloadPreview={}", id, preview);
                        chunks.add(text);
                        continue;
                    }
                }

                if (id != null) {
                    log.debug("Qdrant hit id={} missing payload text, storing placeholder", id);
                    chunks.add("point-id:" + id);
                }
            }
        }

        return chunks;
    }
}
