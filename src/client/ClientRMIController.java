package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import protocole.CommunicationProtocol;
import protocole.Message;
import protocole.MessageProtocol;

public class ClientRMIController implements ClientTchat {

	private Client c;
	private ClientConnectionRMIView connectionView;
	private ClientView view;
	private CommunicationProtocol communication;
	
	public ClientRMIController() {
		c = new Client();
		connectionView = new ClientConnectionRMIView(this);
		view = new ClientView(this);
	}
	
	public void Run() {
		//view.Affiche();
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
				connectionView=null;
				System.gc();
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
	
	public void Receive(MessageProtocol message) {	
		c.AddMessage(message);
		view.AddMessage(message);
	}
	
	public void AddClient(String client) {
		view.AddClient(client);
	}
	
	public void Send(Message message) {
		try {
			//MessageProtocol stub = (MessageProtocol) UnicastRemoteObject.exportObject(this, 0);
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
