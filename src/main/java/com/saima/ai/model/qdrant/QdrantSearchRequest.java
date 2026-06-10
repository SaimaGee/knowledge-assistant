package com.saima.ai.model.qdrant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QdrantSearchRequest(
        List<Double> vector,
        Integer limit,
        @JsonProperty("with_payload") boolean withPayload) {}