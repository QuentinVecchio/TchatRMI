package protocole;

import java.rmi.Remote;
import java.rmi.RemoteException;
import client.ClientTchat;

public interface CommunicationProtocol extends Remote {
    boolean Register(ClientTchat c) throws RemoteException;
    void Disconnection(ClientTchat c) throws RemoteException;
    void Send(MessageProtocol message) throws RemoteException;
}
