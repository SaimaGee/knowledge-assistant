package com.saima.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagService {

    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;
    private final LlmService llmService;

    public String askQuestion(
            String question) {

        List<Double> questionVector =
                embeddingService.generateEmbedding(
                        question);

        List<String> relevantChunks =
                qdrantService.search(questionVector);

        String context =
                String.join("\n\n",
                        relevantChunks);

        String prompt =
                """
                Answer using ONLY the context.

                Context:
                %s

                Question:
                %s
                """.formatted(
                        context,
                        question
                );

        return llmService.ask(prompt);
    }
}