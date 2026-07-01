package com.saima.ai.model;

import java.util.UUID;

public record RetrievedChunk(
    String text,
    UUID documentId,
    String documentName,
    int pageNumber,
    double score
) {}