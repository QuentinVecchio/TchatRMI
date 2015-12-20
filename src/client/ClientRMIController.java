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
	}
	
	public void Run() {
		connectionView.Affiche();
	}
	
	public void Connection() {
		try {
			view = new ClientView(this);
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
			communication.Send(message);
		} catch (Exception e) {
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
	}
	
	public String GetName() {
		return c.GetName();
	}
	
    public String GetHost()  {
    	return c.GetHost();
    }

    public String GetPort()  {
    	return c.GetPort();
    }
    
	public void SetName(String name) {
		c.SetName(name);
	}
	
	public void SetHost(String host) {
		c.SetHost(host);
	}
	
	public void SetPort(String port) {
		c.SetPort(port);
	}
}
