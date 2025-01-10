package com.chads.chat;

// Inicialização do servidor RMI
public class RMIServer {
    public static void main(String[] args) {
        try {
            ChatService chatService = new ChatServiceImpl();
            java.rmi.registry.LocateRegistry.createRegistry(1099).rebind("ChatService", chatService);
            System.out.println("RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
