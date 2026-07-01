package com.saima.ai.model.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ConversationDetailDto(
        UUID id,
        String title,
        Instant createdAt,
        List<MessageDto> messages
) {
    public record MessageDto(
            UUID id,
            String role,
            String content,
            Instant timestamp
    ) {}
}
