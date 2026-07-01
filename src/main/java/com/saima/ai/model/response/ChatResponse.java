package com.saima.ai.model.response;

import java.util.List;
import java.util.UUID;

public record ChatResponse(
    String answer,
    List<SourceCitation> sources,
    UUID conversationId
) {}