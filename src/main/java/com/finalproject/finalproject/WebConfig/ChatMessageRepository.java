package com.finalproject.finalproject.WebConfig;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySenderIdAndRecipientId(String senderId, String recipientId);

    List<ChatMessage> findByRecipientId(String recipientId);
}
