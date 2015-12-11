package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import protocole.CommunicationProtocol;

public class Client implements ClientTchat {
	private String name;
	private String host;
	private LinkedList<String> messages;
    //private ClientView view;
    private CommunicationProtocol communication;
    
	public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        String port = (args.length < 2) ? null : args[1];
        Client c = new Client("Client 1", "127.0.0.1"/*host*/, "1090");
        c.Send("Bonjour je suis le client 1");
        while(true)
        {
        	//c.AfficheMessages();
        }
    }
	
	public Client(String name, String host, String port)
	{
		this.name = name;
		this.host = host;
		this.messages = new LinkedList<String>();
		//this.view = new ClientView();
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

	public void Connection()
	{
		try 
		{
			if(this.communication.Register(this) == false)
			{
				
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
	
    public String GetName()
    {
    	return this.name;
    }
    
    public String GetHost()
    {
    	return this.host;
    }
    
    public void AfficheMessages()
    {
    	//System.out.println(messages.size());
    }
}

