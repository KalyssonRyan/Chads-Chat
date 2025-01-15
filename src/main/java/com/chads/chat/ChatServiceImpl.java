package com.chads.chat;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
    private final List<String> messages;
    private final List<String> users;

    protected ChatServiceImpl() throws RemoteException {
        messages = new ArrayList<>();
        users = new ArrayList<>();
    }

    @Override
    public synchronized void sendMessage(String user, String message) throws RemoteException {
        String fullMessage = user + ": " + message;
        messages.add(fullMessage);
        System.out.println("Mensagem enviada: " + fullMessage);
    }

    @Override
    public synchronized void registerClient(String user) throws RemoteException {
        users.add(user);
        System.out.println("Usuário registrado: " + user);
    }

    @Override
    public synchronized void unregisterClient(String user) throws RemoteException {
        users.remove(user);
        System.out.println("Usuário removido: " + user);
    }

    @Override
    public synchronized String getMessages() throws RemoteException {
        return String.join("\n", messages);
    }
}