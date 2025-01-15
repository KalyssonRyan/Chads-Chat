package com.chads.chat;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote {
    void sendMessage(String user, String message) throws RemoteException;
    void registerClient(String user) throws RemoteException;
    void unregisterClient(String user) throws RemoteException;
    String getMessages() throws RemoteException;
}
