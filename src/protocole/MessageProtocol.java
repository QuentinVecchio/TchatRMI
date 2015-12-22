package protocole;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageProtocol extends Remote {
	public String GetExpediteur() throws RemoteException;
	public String GetDestinataire() throws RemoteException;
	public String GetDate() throws RemoteException;
	public String GetMessage() throws RemoteException;
	public Color GetColor() throws RemoteException;
}
