package com.chads.chat;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessions = new ArrayList<>(); // Lista de sessões WebSocket
    private static List<String> messages = new ArrayList<>(); // Lista para armazenar mensagens enviadas

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Adiciona a nova sessão à lista de sessões
        sessions.add(session);

        // Envia uma mensagem de boas-vindas ou histórico de mensagens
        session.sendMessage(new TextMessage("Bem-vindo ao chat!"));

        // Envia todas as mensagens anteriores para o novo cliente (se necessário)
        for (String message : messages) {
            session.sendMessage(new TextMessage(message));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Armazena a mensagem recebida (se necessário em banco de dados ou lista)
        messages.add(message.getPayload()); // Exemplo de armazenamento em memória

        // Envia a mensagem para todos os clientes conectados
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message); // Envia para todos os clientes conectados
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove a sessão da lista quando o cliente se desconectar
        sessions.remove(session);
    }
}
