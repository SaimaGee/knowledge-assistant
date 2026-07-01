package com.saima.ai.model.dto;

import java.time.Instant;
import java.util.UUID;

public record ConversationSummaryDto(
        UUID id,
        String title,
        Instant createdAt,
        int messageCount
) {}
