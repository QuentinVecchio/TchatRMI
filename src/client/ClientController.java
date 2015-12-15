package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import protocole.CommunicationProtocol;
import protocole.Message;

public class ClientController implements ClientTchat {

	private Client c;
	private ClientConnectionView connectionView;
	private ClientView view;
	private CommunicationProtocol communication;
	
	public ClientController() {
		c = new Client();
		connectionView = new ClientConnectionView(this);
		view = new ClientView(this);
	}
	
	public void Run() {
		connectionView.Affiche();
	}
	
	public void Connection() {
		try {
			ClientTchat stub = (ClientTchat) UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.getRegistry(c.GetHost(),Integer.parseInt(c.GetPort()));
	    	this.communication = (CommunicationProtocol) registry.lookup("Tchat");
	    	if(this.communication.Register(this) == false) {
	    		connectionView.ErrorName();
	    	} else {
				view.Affiche();
				connectionView.Exit();
			}
		} catch (Exception e) {
			connectionView.ErrorHost();
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public void Disconnection() {
		view.Exit();
		try {
			this.communication.Disconnection();
		} catch (Exception e) {
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
	}
	
	public void Receive(Message message) {	
		c.AddMessage(message);
	}
	
	public void Send(Message message) {
		try {
			communication.Send(message);
		} catch (Exception e) {
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
	}
	
    public Client GetClient()  {
    	return this.c;
    }

	public String GetName() {
		return c.GetName();
	}
}
