package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientTchat extends Remote{
	public void Receive(String message) throws RemoteException;	
	public String GetName() throws RemoteException;
}
