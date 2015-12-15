package server;

import java.rmi.RemoteException;
import java.util.LinkedList;
import client.ClientTchat;
import protocole.CommunicationProtocol;
import protocole.Message;

public class Tchat implements CommunicationProtocol {
	private LinkedList<ClientTchat> clients = new LinkedList<ClientTchat>();
	private LinkedList<Message> messages = new LinkedList<Message>();
	
    public Tchat()  {
    	
    }

    public boolean Register(ClientTchat c) throws RemoteException {
    	if(NameExist(c.GetName())) {
    		return false;
    	} else {
	    	System.out.println("Le client <" + c.GetName() + "> est connecté");
	    	clients.add(c);
	    	for(int i=0;i<messages.size();i++)
	    	{
	    		c.Receive(messages.get(i));
	    	}
	    	return true;
    	}
    }
    
    public void Disconnection() {
    	
    }
    
    public void Send(Message message) {	
    	messages.add(message);
    	for(int i=0;i<clients.size();i++) {
    		try {
    			clients.get(i).Receive(message);
    		} catch (Exception e) {
                System.err.println("Tchat exception: " + e.toString());
                e.printStackTrace();
            }
    	}
    }
    
    private boolean NameExist(String name) {
    	for(int i=0;i<clients.size();i++) {
    		try {
	    		if(clients.get(i).GetName() == name) {
	    			return true;
	    		}
    		} catch (Exception e) {
                System.err.println("Tchat exception: " + e.toString());
                e.printStackTrace();
            }
    	}
    	return false;
    }
}