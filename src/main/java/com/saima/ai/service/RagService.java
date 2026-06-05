package com.saima.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        log.info("Retrieved {} relevant chunk(s) for question={}", relevantChunks.size(), question);
        relevantChunks.stream()
                .limit(3)
                .forEach(chunk -> log.debug("Retrieved chunk preview={}", chunk.length() > 120 ? chunk.substring(0, 120) + "..." : chunk));

        String context =
                String.join("\n\n",
                        relevantChunks);

        String prompt =
                """
                Answer using ONLY the context.

                If the user asks to identify a person from the provided CV, extract and return the person's full name, current title, and a one-sentence summary. If that information is not present in the context, respond with "Not in context." Always be concise and use only information from the context.

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

        public List<String> retrieveChunks(String question) {
                List<Double> questionVector =
                                embeddingService.generateEmbedding(
                                                question);

                List<String> relevantChunks =
                                qdrantService.search(questionVector);

                return relevantChunks;
        }
}