package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import protocole.CommunicationProtocol;

public class ClientController implements ClientTchat {

	private Client c;
	private ClientConnectionView connectionView;
	private ClientView view;
	private CommunicationProtocol communication;
	
	public static void main(String[] args) 
	{
		ClientController controllerClient = new ClientController();
        
		String host = (args.length < 1) ? null : args[0];
        String port = (args.length < 2) ? null : args[1];
        Client c = new Client("Client 1", "127.0.0.1"/*host*/, "1090");
        c.Send("Bonjour je suis le client 1");
        while(true)
        {
        	//c.AfficheMessages();
        }
		try 
		{
			ClientTchat stub = (ClientTchat) UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.getRegistry(host);
	    	this.communication = (CommunicationProtocol) registry.lookup("Tchat");
		} 
		catch (Exception e) 
		{
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
		this.Connection();
    }
	
	public ClientController()
	{
		c = new Client();
		connectionView = new ClientConnectionView(this);
		connectionView.Affiche();
	}
	
	public void Connection()
	{
		try 
		{
			ClientTchat stub = (ClientTchat) UnicastRemoteObject.exportObject(this, 0);
			Registry registry = LocateRegistry.getRegistry(c.GetHost(),Integer.parseInt(c.GetPort()));
	    	this.communication = (CommunicationProtocol) registry.lookup("Tchat");
	    	if(this.communication.Register(this) == false)
			{
				ClientAlert alert = new ClientAlert("Erreur, pseudo déjà existant");
				alert.Affiche();
			} 
			else
			{
				System.out.println("Client <" + this.name +  "> connecté à " + this.host);
			}
		} 
		catch (Exception e) 
		{
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
		try 
		{
			
		}
		catch (Exception e) 
		{
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
	}

	public void Disconnection()
	{
		try 
		{
			this.communication.Disconnection();
			System.out.println("Client <" + this.name +  "> connecté à " + this.host);
		}
		catch (Exception e) 
		{
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
	}
	
	public void Receive(String message)
	{	
		System.out.println(message);
		messages.add(message);
	}
	
	public void Send(String message)
	{
		try 
		{
			communication.Send(name + " > " + message);
		}
		catch (Exception e) 
		{
	    	System.err.println("Client exception: " + e.toString());
	    	e.printStackTrace();
		}
	}
	
    public Client GetClient()
    {
    	return this.c;
    }

	@Override
	public String GetName() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
