package com.finalproject.finalproject.WebConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getChatMessages(String userId, String friendId) {
        // Retrieve all messages where userId is either the sender or recipient
        List<ChatMessage> sentMessages = chatMessageRepository.findBySenderIdAndRecipientId(userId, friendId);
        List<ChatMessage> receivedMessages = chatMessageRepository.findBySenderIdAndRecipientId(friendId, userId);

        List<ChatMessage> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(receivedMessages);
        return allMessages;
    }

    public List<ChatMessage> getMessagesForUser(String recipientId) {
        return chatMessageRepository.findByRecipientId(recipientId);
    }
}
