package com.chads.chat;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    // Lista de sessões sincronizada para suportar acesso concorrente
    private static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());
    // Mapa para armazenar o nome de usuário associado à sessão
    private static Map<WebSocketSession, String> userNames = new HashMap<>();
    // Lista para armazenar as mensagens do chat
    private static List<String> messages = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Adiciona a nova sessão à lista de sessões
        sessions.add(session);

        // Envia uma mensagem de boas-vindas para o novo cliente
        session.sendMessage(new TextMessage("Bem-vindo ao chat!"));

        // Envia todas as mensagens anteriores para o novo cliente
        for (String message : messages) {
            session.sendMessage(new TextMessage(message));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String fullMessage = message.getPayload();  // A mensagem contém nome + texto

        // Armazenando a mensagem
        messages.add(fullMessage);

        // Envia a mensagem para todos os clientes conectados
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(fullMessage));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove a sessão da lista quando o cliente se desconectar
        sessions.remove(session);

        // Obtém o nome do usuário que está se desconectando
        String username = userNames.get(session);
        String exitMessage = username + " saiu do chat.";

        // Notifica a todos que um usuário saiu
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(exitMessage));
            }
        }

        // Remove o nome do usuário do mapa
        userNames.remove(session);
    }

    // Método auxiliar para definir o nome do usuário
    public void setUsername(WebSocketSession session, String username) {
        userNames.put(session, username);
    }
}
