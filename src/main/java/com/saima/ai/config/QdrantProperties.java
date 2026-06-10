package com.saima.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "qdrant")
public record QdrantProperties(
        String baseUrl,
        String collection,
        int    vectorSize,
        int    searchLimit) {

    public QdrantProperties {
        if (baseUrl    == null) baseUrl    = "http://localhost:6333";
        if (collection == null) collection = "knowledge-base";
        if (vectorSize == 0)    vectorSize = 768;
        if (searchLimit == 0)   searchLimit = 5;
    }
}