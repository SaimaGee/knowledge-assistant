package com.saima.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saima.ai.model.RetrievedChunk;
import com.saima.ai.model.response.ChatResponse;
import com.saima.ai.model.response.SourceCitation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagService {

    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;
    private final LlmService llmService;

    public ChatResponse askQuestion(String question) {

        List<RetrievedChunk> relevantChunks = retrieveChunks(question);

        log.info("Retrieved {} relevant chunk(s) for question={}", relevantChunks.size(), question);
        relevantChunks.stream()
                .limit(3)
                .forEach(chunk -> log.debug("Retrieved chunk preview={}",
                        chunk.text().length() > 120 ? chunk.text().substring(0, 120) + "..." : chunk.text()));

        String context = relevantChunks.stream()
                .map(RetrievedChunk::text)
                .reduce((a, b) -> a + "\n\n" + b)
                .orElse("");

        String prompt =
                """
                Answer using ONLY the context.

                If the user asks to identify a person from the provided CV, extract and return the person's full name, current title, and a one-sentence summary. If that information is not present in the context, respond with "Not in context." Always be concise and use only information from the context.

                Context:
                %s

                Question:
                %s
                """.formatted(context, question);

        String answer = llmService.ask(prompt);

        List<SourceCitation> citations = relevantChunks.stream()
                .map(chunk -> new SourceCitation(
                        chunk.documentId(),
                        chunk.documentName(),
                        chunk.pageNumber(),
                        chunk.text().length() > 200 ? chunk.text().substring(0, 200) + "..." : chunk.text(),
                        chunk.score()))
                .toList();

        return new ChatResponse(answer, citations, null); // conversationId filled in by caller once persistence lands
    }

    public List<RetrievedChunk> retrieveChunks(String question) {
        List<Double> questionVector = embeddingService.generateEmbedding(question);
        return qdrantService.search(questionVector);
    }
}