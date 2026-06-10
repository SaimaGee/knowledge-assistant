package com.saima.ai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.saima.ai.config.QdrantProperties;
import com.saima.ai.exception.QdrantException;
import com.saima.ai.model.qdrant.PointPayload;
import com.saima.ai.model.qdrant.QdrantPoint;
import com.saima.ai.model.qdrant.QdrantSearchRequest;
import com.saima.ai.model.qdrant.QdrantSearchResponse;
import com.saima.ai.model.qdrant.UpsertPointsRequest;
import com.saima.ai.model.qdrant.VectorParams;
import com.saima.ai.model.request.CreateCollectionRequest;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ConfigurationProperties(prefix = "qdrant")
public class QdrantService {

    private final RestClient        client;
    private final QdrantProperties  props;

    public QdrantService(QdrantProperties props) {
        this.props = props;
        this.client = RestClient.builder()
                .baseUrl(props.baseUrl())
                .defaultStatusHandler(
                    HttpStatusCode::isError,
                    (req, res) -> {
                        String body = new String(res.getBody().readAllBytes());
                        throw new QdrantException(
                            "Qdrant error %s: %s".formatted(res.getStatusCode(), body));
                    })
                .build();
    }

    @PostConstruct
    public void initialize() {
        var body = new CreateCollectionRequest(
                new VectorParams(props.vectorSize(), "Cosine"));
        try {
            client.put()
                    .uri("/collections/{name}", props.collection())
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();
            log.info("Qdrant collection '{}' ready", props.collection());
        } catch (QdrantException e) {
            if (e.getMessage().contains("already exists")) {
                log.info("Qdrant collection '{}' already exists — skipping", props.collection());
            } else {
                throw e; // surface real failures (connection refused, auth, etc.)
            }
        }
    }

    public void saveChunk(String sourceId, String text, List<Double> embedding) {
        String pointId = UUID.randomUUID().toString();
        log.debug("Saving chunk source_id={} pointId={} vectorLen={} preview={}",
                sourceId, pointId, embedding.size(), preview(text));

        var point   = new QdrantPoint(pointId, embedding,
                          new PointPayload(text, sourceId));
        var request = new UpsertPointsRequest(List.of(point));

        try {
            client.put()
                    .uri("/collections/{name}/points", props.collection())
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
            log.info("Saved chunk source_id={} as pointId={}", sourceId, pointId);
        } catch (QdrantException e) {
            log.error("Failed to save chunk source_id={}: {}", sourceId, e.getMessage());
            throw e; // let caller decide — don't silently drop data
        }
    }

    public List<String> search(List<Double> vector) {
        log.info("Searching Qdrant vectorLen={}", vector.size());

        var request  = new QdrantSearchRequest(vector, props.searchLimit(), true);
        var response = client.post()
                .uri("/collections/{name}/points/search", props.collection())
                .body(request)
                .retrieve()
                .body(QdrantSearchResponse.class);

        if (response == null || response.result() == null) {
            log.warn("Qdrant returned null response");
            return List.of();
        }

        log.info("Qdrant returned {} result(s)", response.result().size());

        return response.result().stream()
                .filter(hit -> hit.payload() != null)
                .peek(hit -> log.debug("Hit id={} score={} preview={}",
                        hit.id(), hit.score(), preview(hit.payload().text())))
                .map(hit -> hit.payload().text())
                .filter(t -> t != null && !t.isBlank())
                .toList();
    }

    private static String preview(String text) {
        if (text == null) return "null";
        return text.length() > 80 ? text.substring(0, 80) + "..." : text;
    }
}
