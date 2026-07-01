package com.saima.ai.model.dto;

import java.time.Instant;
import java.util.UUID;

public record DocumentDto(
        UUID id,
        String name,
        Instant uploadedAt,
        int pageCount,
        int chunkCount
) {}
