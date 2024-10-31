package com.finalproject.finalproject.AiChat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiChatService {

    @Value("${openai.api.url}")
    String apiUrl;

    private final RestTemplate restTemplate;

    public AiChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ChatResponse sendChatResponse(String prompt) {
        ChatRequest chatRequest = new ChatRequest(
                "gpt-4",
                "You are a helpful assistant.and a programer + very funny",
                prompt,
                1);
        return restTemplate.postForObject(apiUrl, chatRequest, ChatResponse.class);
    }
}
