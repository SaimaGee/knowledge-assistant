package com.saima.ai.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class StoredCitation {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID documentId;

    private String documentName;

    private int pageNumber;

    private String snippet;

    @ManyToOne
    private ChatMessage chatMessage;

    protected StoredCitation() {
    }

    public StoredCitation(UUID documentId, String documentName, int pageNumber, String snippet) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.pageNumber = pageNumber;
        this.snippet = snippet;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getSnippet() {
        return snippet;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
}