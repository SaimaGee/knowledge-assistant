package com.saima.ai.model.qdrant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QdrantPayload(
        String text,
        @JsonProperty("source_id") String sourceId,
        String documentName,
        Integer chunkNumber
) {}