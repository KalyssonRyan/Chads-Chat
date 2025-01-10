package com.chads.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Implementação do serviço
public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
    private List<String> messages = new CopyOnWriteArrayList<>();

    protected ChatServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void sendMessage(String user, String message) throws RemoteException {
        messages.add(user + ": " + message);
    }

    @Override
    public List<String> getMessages() throws RemoteException {
        return messages;
    }
}
