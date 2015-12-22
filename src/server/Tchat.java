package server;

import java.rmi.RemoteException;
import java.util.LinkedList;
import client.ClientTchat;
import protocole.CommunicationProtocol;
import protocole.Message;
import protocole.MessageProtocol;

public class Tchat implements CommunicationProtocol {
	private LinkedList<ClientTchat> clients = new LinkedList<ClientTchat>();
	private LinkedList<MessageProtocol> messages = new LinkedList<MessageProtocol>();
	
    public Tchat()  {
    	
    }

    public boolean Register(ClientTchat c) throws RemoteException {
    	if(NameExist(c.GetName())) {
    		return false;
    	} else {
	    	System.out.println("Le client <" + c.GetName() + "> est connecté");
	    	clients.add(c);
	    	for(int i=0;i<messages.size();i++) {
	    		c.Receive(messages.get(i));
	    	}
	    	Message m = new Message(c.GetName(), "all", c.GetName() + " est connecté", c.GetColor());
	    	messages.add(m);
	    	for(int i=0;i<clients.size();i++) {
	    		clients.get(i).Receive(m);
	    		if(clients.get(i).GetName().equals(c.GetName()) == false) {
	    			clients.get(i).AddClient(c.GetName());
	    		}
	    	}
	    	for(int i=0;i<clients.size();i++) {
	    		c.AddClient(clients.get(i).GetName());
	    	}
	    	return true;
    	}
    }
    
    public void Disconnection(ClientTchat c) throws RemoteException{
    	Message m = new Message(c.GetName(), "all", c.GetName() + " est déconnecté", c.GetColor());
    	messages.add(m);
    	for(int i=0;i<clients.size();i++) {
    		try {
    			clients.get(i).Receive(m);
    		} catch (Exception e) {
                System.err.println("Tchat exception: " + e.toString());
                e.printStackTrace();
            }
    	}
    	clients.remove(c.GetName());
    }
    
    public void Send(MessageProtocol message) {	
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
	    		if(clients.get(i).GetName().equals(name)) {
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