package com.finalproject.finalproject.WebConfig;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;

@Controller
public class StompController {

    private final SimpMessagingTemplate messagingTemplate;

    public StompController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/greet")
    @SendToUser("/queue/greeting") // Sends the greeting back to the specific user
    public Hello hello(HelloMessage message, Principal user) {
        System.out.println("/Hello for user: " + user.getName());
        return new Hello(message.getName() + " joined the chat!");
    }

    @MessageMapping("/chat")
    public void chat(ChatMessage chat, Principal user) {
        System.out.println("/chat message from: " + user.getName());
        // Send the message only to the specific user
        messagingTemplate.convertAndSendToUser(user.getName(), "/queue/chat", new Chat(chat.getContent()));
    }
}
