package client;

import java.awt.Color;
import java.rmi.RemoteException;
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
	
	/**
	 * Constructeur de la classe ClientRMIController
	 * 
     */
	public ClientRMIController() {
		c = new Client();
		connectionView = new ClientConnectionRMIView(this);
		try {
			UnicastRemoteObject.exportObject(this, 0);
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui lance le serveur
	 * 
     */
	public void Start() {
		connectionView.Affiche();
	}
	
	/**
	 * Méthode qui se connecte au près du serveur
	 * 
     */
	public void Connection() {
		try {
			view = new ClientView(this);
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

	/**
	 * Méthode qui se déconnecte au près du serveur
	 * 
     */
	public void Disconnection() {
		view.Exit();
		try {
			this.communication.Disconnection(this);
			System.exit(0);
		} catch (Exception e) {
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui gère la reception d'un message
	 * 
     */
	public void Receive(MessageProtocol message) {	
		c.AddMessage(message);
		view.AddMessage(message);
	}
	
	/**
	 * Méthode qui gère l'ajout d'un client
	 * 
     */
	public void AddClient(String client) {
		view.AddClient(client);
	}
	
	/**
	 * Méthode qui gère la suppression d'un client
	 * 
     */
	public void DeleteClient(String client) {
		view.DeleteClient(client);
	}
	
	/**
	 * Méthode qui envoie un message
	 * 
     */
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
    
    public Color GetColor()  {
    	return c.GetColor();
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
	
	public void SetColor(Color color) {
		c.SetColor(color);
	}
    public String toString(){
        return c.GetName();
    }

	public void Deconnect() throws RemoteException {
		this.view.DeconnectError();
	}
}
