package com.finalproject.finalproject.WebConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/sendMessage")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        ChatMessage savedMessage = chatService.saveMessage(message);
        messagingTemplate.convertAndSendToUser(message.getRecipientId(), "/queue/messages", savedMessage);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/history/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @PathVariable String senderId, @PathVariable String recipientId) {
        List<ChatMessage> chatHistory = chatService.getChatMessages(senderId, recipientId);
        return ResponseEntity.ok(chatHistory);
    }
}
