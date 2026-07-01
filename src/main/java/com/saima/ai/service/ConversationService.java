package com.saima.ai.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.saima.ai.model.dto.ConversationDetailDto;
import com.saima.ai.model.dto.ConversationSummaryDto;
import com.saima.ai.model.entity.ChatMessage;
import com.saima.ai.model.entity.Conversation;
import com.saima.ai.model.entity.MessageRole;
import com.saima.ai.model.entity.StoredCitation;
import com.saima.ai.model.response.SourceCitation;

@Service
public class ConversationService {

    private final Map<UUID, Conversation> conversations = new ConcurrentHashMap<>();
    private final Map<UUID, DocumentRecord> documents = new ConcurrentHashMap<>();

    public List<ConversationSummaryDto> listConversations() {
        return conversations.values().stream()
            .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
            .map(conversation -> new ConversationSummaryDto(
                conversation.getId(),
                conversation.getTitle(),
                conversation.getCreatedAt(),
                conversation.getMessages() == null ? 0 : conversation.getMessages().size()))
                .toList();
    }

    public ConversationDetailDto getConversation(UUID id) {
        Conversation conversation = conversations.get(id);
        if (conversation == null) {
            throw new IllegalArgumentException("Conversation not found");
        }

        List<ConversationDetailDto.MessageDto> messages = new ArrayList<>();
        if (conversation.getMessages() != null) {
            for (ChatMessage message : conversation.getMessages()) {
                messages.add(new ConversationDetailDto.MessageDto(
                        message.getId(),
                        message.getRole().name(),
                        message.getContent(),
                        message.getTimestamp()));
            }
        }

        return new ConversationDetailDto(
                conversation.getId(),
                conversation.getTitle(),
                conversation.getCreatedAt(),
                messages);
    }

    public ConversationDetailDto createOrGetConversation(
            String conversationId, String userMessage, String assistantMessage, List<SourceCitation> citations) {
        Conversation conversation;
        if (conversationId != null && !conversationId.isBlank()) {
            UUID id = UUID.fromString(conversationId);
            conversation = conversations.computeIfAbsent(id, ignored -> createConversation("Conversation " + id.toString().substring(0, 8)));
        } else {
            conversation = createConversation("New conversation");
            conversations.put(conversation.getId(), conversation);
        }
        if (conversation.getMessages() == null) {
            conversation.setMessages(new ArrayList<>());
        }

        ChatMessage user = new ChatMessage();
        user.setId(UUID.randomUUID());
        user.setConversation(conversation);
        user.setRole(MessageRole.USER);
        user.setContent(userMessage);
        user.setTimestamp(Instant.now());
        conversation.getMessages().add(user);

        ChatMessage assistant = new ChatMessage();
        assistant.setId(UUID.randomUUID());
        assistant.setConversation(conversation);
        assistant.setRole(MessageRole.ASSISTANT);
        assistant.setContent(assistantMessage);
        assistant.setTimestamp(Instant.now());
        assistant.setCitations(citations == null ? List.of() : citations.stream()
                .map(c -> {
                    StoredCitation sc = new StoredCitation();
                    sc.setDocumentId(c.documentId());
                    sc.setDocumentName(c.documentName());
                    sc.setPageNumber(c.pageNumber());
                    sc.setSnippet(c.snippet());
                    return sc;
                })
                .toList());
        conversation.getMessages().add(assistant);

        if (conversation.getTitle() == null || conversation.getTitle().equals("New conversation")) {
            conversation.setTitle(userMessage.length() > 40 ? userMessage.substring(0, 40) + "..." : userMessage);
        }

        return getConversation(conversation.getId());
    }

    public void deleteConversation(UUID id) {
        conversations.remove(id);
    }

    public List<com.saima.ai.model.dto.DocumentDto> listDocuments() {
        return documents.values().stream()
                .sorted((a, b) -> b.uploadedAt.compareTo(a.uploadedAt))
                .map(document -> new com.saima.ai.model.dto.DocumentDto(
                        document.id,
                        document.name,
                        document.uploadedAt,
                        document.pageCount,
                        document.chunkCount))
                .toList();
    }

    public void deleteDocument(UUID id) {
        documents.remove(id);
    }

    public void registerDocument(UUID id, String name, int pageCount, int chunkCount) {
        DocumentRecord document = new DocumentRecord(
                id,
                name,
                Instant.now(),
                pageCount,
                chunkCount);
        documents.put(document.id, document);
    }

    private Conversation createConversation(String title) {
        Conversation conversation = new Conversation();
        conversation.setId(UUID.randomUUID());
        conversation.setTitle(title);
        conversation.setCreatedAt(Instant.now());
        conversation.setMessages(new ArrayList<>());
        return conversation;
    }

    private record DocumentRecord(
            UUID id,
            String name,
            Instant uploadedAt,
            int pageCount,
            int chunkCount) {}
}
