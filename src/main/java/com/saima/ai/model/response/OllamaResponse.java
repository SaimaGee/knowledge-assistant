package com.saima.ai.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OllamaResponse(
        String response,
        String model,
        boolean done,
        @JsonProperty("done_reason") String doneReason,
        @JsonProperty("total_duration") Long totalDuration) {
}