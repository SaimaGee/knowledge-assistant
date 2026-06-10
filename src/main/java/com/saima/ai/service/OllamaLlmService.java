package com.saima.ai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.saima.ai.exception.LlmServiceException;
import com.saima.ai.model.request.OllamaRequest;
import com.saima.ai.model.response.OllamaResponse;

@Primary
@Service
@ConditionalOnProperty(name = "app.llm-provider", havingValue = "ollama")
public class OllamaLlmService implements LlmService {

    private final RestClient restClient;
    private final String model;

    public OllamaLlmService(
            @Value("${ollama.base-url:http://localhost:11434}") String baseUrl,
            @Value("${ollama.model:llama3.2}") String model,
            @Value("${ollama.timeout:120000}") int timeoutMs) {

        this.model = model;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeoutMs);
        factory.setReadTimeout(timeoutMs);

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .defaultStatusHandler(
                    HttpStatusCode::isError,
                    (req, res) -> {
                        String body = new String(res.getBody().readAllBytes());
                        throw new LlmServiceException(
                            "Ollama error %s: %s".formatted(res.getStatusCode(), body));
                    })
                .build();
    }

    @Override
    public String ask(String prompt) {
        OllamaResponse response = restClient.post()
                .uri("/api/generate")
                .body(new OllamaRequest(model, prompt, false))
                .retrieve()
                .body(OllamaResponse.class);

        return Optional.ofNullable(response)
                .map(OllamaResponse::response)
                .filter(s -> !s.isBlank())
                .orElseThrow(() -> new LlmServiceException("Empty response from Ollama"));
    }
}
