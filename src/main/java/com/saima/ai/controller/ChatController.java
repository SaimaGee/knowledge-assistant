package com.saima.ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saima.ai.service.LlmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final LlmService llmService;

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt) {
        return llmService.ask(prompt);
    }
}
