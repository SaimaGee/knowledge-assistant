package com.saima.ai.service;

import org.springframework.beans.factory.annotation.Value;
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

    public BedrockLlmService(
            @Value("${bedrock.region:ap-southeast-2}") String bedrockRegion,
            @Value("${bedrock.model-id:anthropic.claude-sonnet-4-64k-20240909}") String bedrockModelId) {
        this.model =
            BedrockChatModel.builder()
                .region(Region.of(bedrockRegion))
                .modelId(bedrockModelId)
                .build();
    }

    public String ask(String prompt) {
        return model.chat(prompt);
    }
}
