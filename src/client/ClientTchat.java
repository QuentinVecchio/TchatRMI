package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import protocole.Message;

public interface ClientTchat extends Remote{
	public void Receive(Message message) throws RemoteException;	
	public String GetName() throws RemoteException;
}
