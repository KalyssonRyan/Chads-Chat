package com.chads.chat;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Interface remota
public interface ChatService extends Remote {
    void sendMessage(String user, String message) throws RemoteException;
    List<String> getMessages() throws RemoteException;
}

