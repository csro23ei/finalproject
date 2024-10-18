package com.finalproject.finalproject.AiChat;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public CompletableFuture<String> postChat(@RequestBody String prompt) {
        return CompletableFuture.supplyAsync(() -> {
            ChatResponse response = chatService.sendChatResponse(prompt);
            return response.getChoices().get(0).getMessage().getContent();
        });
    }
}
