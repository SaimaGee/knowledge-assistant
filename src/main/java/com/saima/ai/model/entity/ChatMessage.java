package com.saima.ai.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Conversation conversation;

    private MessageRole role; // USER, ASSISTANT

    private String content;

    @OneToMany(cascade = CascadeType.ALL)
    private List<StoredCitation> citations;

    private Instant timestamp;

    public ChatMessage() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public MessageRole getRole() {
        return role;
    }

    public void setRole(MessageRole role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<StoredCitation> getCitations() {
        return citations;
    }

    public void setCitations(List<StoredCitation> citations) {
        this.citations = citations;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
