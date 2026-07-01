package com.saima.ai.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saima.ai.model.dto.ConversationDetailDto;
import com.saima.ai.model.dto.ConversationSummaryDto;
import com.saima.ai.model.request.ChatRequest;
import com.saima.ai.model.response.ChatResponse;
import com.saima.ai.service.ConversationService;
import com.saima.ai.service.RagService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final RagService ragService;
    private final ConversationService conversationService;

    @GetMapping("/conversations")
    public List<ConversationSummaryDto> listConversations() {
        return conversationService.listConversations();
    }

    @GetMapping("/conversations/{id}")
    public ConversationDetailDto getConversation(@PathVariable UUID id) {
        return conversationService.getConversation(id);
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        ChatResponse response = ragService.askQuestion(request.message());
        conversationService.createOrGetConversation(
                request.conversationId(),
                request.message(),
                response.answer());
        return response;
    }

    @DeleteMapping("/conversations/{id}")
    public void deleteConversation(@PathVariable UUID id) {
        conversationService.deleteConversation(id);
    }
}