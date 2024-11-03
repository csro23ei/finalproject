package com.finalproject.finalproject.WebConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChatTest {

    @MockBean
    private ChatMessageRepository chatMessageRepository;

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @MockBean
    private ChatService chatService;

    @Autowired
    private ChatController chatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSkickaMeddelande() {
        // Skapa ett meddelande
        ChatMessage meddelande = new ChatMessage("anvandare1", "anvandare2", "Hej där!");

        // Mocka spara meddelande
        when(chatService.saveMessage(meddelande)).thenReturn(meddelande);

        // Skicka meddelande med ChatController
        ResponseEntity<ChatMessage> svar = chatController.sendMessage(meddelande);

        // Verifiera att meddelandet skickas korrekt
        assertEquals(meddelande, svar.getBody());
        verify(chatService, times(1)).saveMessage(meddelande);
        verify(messagingTemplate, times(1)).convertAndSendToUser("anvandare2", "/queue/messages", meddelande);
    }

    @Test
    public void testHamtaMeddelandeHistorik() {
        // Mocka hämtning av meddelandehistorik
        ChatMessage meddelande1 = new ChatMessage("anvandare1", "anvandare2", "Hej!");
        ChatMessage meddelande2 = new ChatMessage("anvandare2", "anvandare1", "Hallå!");
        when(chatService.getChatMessages("anvandare1", "anvandare2"))
                .thenReturn(Arrays.asList(meddelande1, meddelande2));

        // Hämta historik med ChatController
        ResponseEntity<List<ChatMessage>> historikSvar = chatController.getChatHistory("anvandare1", "anvandare2");

        // Verifiera att historiken innehåller båda meddelandena
        assertEquals(2, historikSvar.getBody().size());
        assertEquals("Hej!", historikSvar.getBody().get(0).getContent());
        assertEquals("Hallå!", historikSvar.getBody().get(1).getContent());
    }
}
