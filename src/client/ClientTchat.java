package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientTchat extends Remote{
	public void Connection() throws RemoteException;
	public void Disconnection() throws RemoteException;
	public void Receive(String message) throws RemoteException;	
	public void Send(String message) throws RemoteException;
	public String GetName() throws RemoteException;
}
