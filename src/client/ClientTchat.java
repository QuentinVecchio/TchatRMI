package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import protocole.MessageProtocol;

public interface ClientTchat extends Remote{
	public void Receive(MessageProtocol message) throws RemoteException;
	public void AddClient(String client) throws RemoteException;
	public String GetName() throws RemoteException;
}
