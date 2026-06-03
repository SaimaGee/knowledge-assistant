package com.saima.ai.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import dev.langchain4j.model.bedrock.BedrockChatModel;
import dev.langchain4j.model.chat.ChatModel;
import software.amazon.awssdk.regions.Region;


@Service
@ConditionalOnProperty(
        name = "app.llm-provider",
        havingValue = "bedrock")
public class BedrockLlmService implements LlmService {
    private final ChatModel model;

    public BedrockLlmService() {
        this.model =
            BedrockChatModel.builder()
                .region(Region.AP_SOUTHEAST_2)
                .modelId(
                    "anthropic.claude-sonnet-4-64k-20240909"
                )
                .build();
            }            

    public String ask(String prompt) {
        return model.chat(prompt);
    }
}
