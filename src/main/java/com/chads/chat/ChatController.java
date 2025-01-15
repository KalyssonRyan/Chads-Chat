package com.chads.chat;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    // Construtor com injeção de dependência
    @Autowired
    public ChatController() {
        try {
            // Conecta ao servidor RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            this.chatService = (ChatService) registry.lookup("ChatService");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao conectar com o serviço RMI.");
        }
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestParam String user, @RequestParam String message) {
        try {
            chatService.sendMessage(user, message);  // Envia a mensagem via RMI
            return ResponseEntity.ok().build();
        } catch (RemoteException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Erro no envio
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<String>> getMessages() {
        try {
            List<String> messages = Collections.singletonList(chatService.getMessages());  // Obtém as mensagens via RMI
            return ResponseEntity.ok(messages);  // Retorna as mensagens no formato JSON
        } catch (RemoteException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Erro ao obter mensagens
        }
    }
}
