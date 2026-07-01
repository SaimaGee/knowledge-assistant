package com.saima.ai.model.response;

import java.util.UUID;

public record SourceCitation(
    UUID documentId,
    String documentName,
    int pageNumber,
    String snippet,
    double relevanceScore
) {}