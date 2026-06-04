package com.saima.ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saima.ai.model.request.ChatRequest;
import com.saima.ai.model.response.ChatResponse;
import com.saima.ai.service.RagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final RagService ragService;

    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request) {

        String answer;
        answer = ragService.askQuestion(
                request.message());

        return new ChatResponse(answer);
    }
}