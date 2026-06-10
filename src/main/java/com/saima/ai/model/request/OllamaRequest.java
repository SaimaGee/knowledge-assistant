package com.saima.ai.model.request;

public record OllamaRequest(
        String model,
        String prompt,
        boolean stream) {
}