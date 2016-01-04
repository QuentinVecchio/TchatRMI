package client;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import protocole.MessageProtocol;

public interface ClientTchat extends Remote{
	public void Deconnect() throws RemoteException;
	public void Receive(MessageProtocol message) throws RemoteException;
	public void AddClient(String client) throws RemoteException;
	public void DeleteClient(String client) throws RemoteException;
	public String GetName() throws RemoteException;
	public Color GetColor() throws RemoteException;
}
