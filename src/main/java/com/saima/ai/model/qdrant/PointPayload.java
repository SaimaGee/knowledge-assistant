package com.saima.ai.model.qdrant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PointPayload(
    String text,
    @JsonProperty("source_id") String sourceId,
    @JsonProperty("document_name") String documentName,
    @JsonProperty("chunk_number") Integer chunkNumber) {}