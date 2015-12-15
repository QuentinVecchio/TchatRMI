package protocole;

import java.rmi.Remote;
import java.rmi.RemoteException;
import client.ClientTchat;

public interface CommunicationProtocol extends Remote {
    boolean Register(ClientTchat c) throws RemoteException;
    void Disconnection() throws RemoteException;
    void Send(Message message) throws RemoteException;
}
