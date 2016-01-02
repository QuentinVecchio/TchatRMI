package server;

import client.Client;

import java.rmi.RemoteException;
import java.util.LinkedList;
import client.ClientTchat;

import java.util.Vector;

import protocole.CommunicationProtocol;
import protocole.Message;
import protocole.MessageProtocol;

public class Tchat implements CommunicationProtocol {
	private Vector<ClientTchat> clients = new Vector<ClientTchat>();
	private Vector<MessageProtocol> messages = new Vector<MessageProtocol>();
        private Vector<String> PsudoForbiden = new Vector<String>();
        private ServerRMIController sc;

	
    public Tchat(ServerRMIController aSc)  {
        sc =aSc;
        PsudoForbiden.add("Server");
        PsudoForbiden.add("Moderateur");

    }

    public boolean Register(ClientTchat c) throws RemoteException {
    	if(NameCanBeUse(c.GetName())) {
    		return false;
    	} else {
	    	System.out.println("Le client <" + c.GetName() + "> est connecté");
	    	clients.add(c);
                sc.getServerView().addClient(c.GetName());
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
        sc.getServerView().addMessage(m.GetExpediteur()+" > "+m.GetDestinataire()+" :"+m.GetMessage());
    	clients.remove(c);
        sc.getServerView().supClient(c.GetName());
    	for(int i=0;i<clients.size();i++) {
    		try {
    			clients.get(i).Receive(m);
    			clients.get(i).DeleteClient(c.GetName());
    		} catch (Exception e) {
                System.err.println("Tchat exception: " + e.toString());
                e.printStackTrace();
            }
    	}
    }
    
    public void Send(MessageProtocol message) {	
    try {
        if (NameExist(message.GetExpediteur())){
            Message m = new Message(message);
            messages.add(m);
            sc.getServerView().addMessage(m.GetExpediteur()+" > "+m.GetDestinataire()+": "+m.GetMessage());
            
            for(ClientTchat c :clients) {
                if (message.GetDestinataire().equals("all") 
                            || 
                    message.GetDestinataire().equals(c.GetName()) 
                            ||
                    message.GetExpediteur().equals(c.GetName())){
                    c.Receive(message);
                }
            }
        }
        } catch (Exception e) {
        System.err.println("Tchat exception: " + e.toString());
        e.printStackTrace();
        }
    }
    public void BanishClient(String name){
        ClientTchat toBanish = FindClient(name);
        try {
            toBanish.Receive(new Message("Moderateur", toBanish.GetName(), "Vous avez �t� bannie", toBanish.GetColor()));
            Disconnection(toBanish);
            
        } catch (Exception e) {
        System.err.println("Tchat exception: " + e.toString());
        e.printStackTrace();
        }
        PsudoForbiden.add(name);
    }
    
    private ClientTchat FindClient(String name){
        for(ClientTchat c :clients) {
                try {
                        if(c.GetName().equals(name)) {
                                return c;
                        }
                } catch (Exception e) {
                System.err.println("Tchat exception: " + e.toString());
                e.printStackTrace();
            }
        }
        return null;
    }
    private boolean NameExist(String name) {
        for(ClientTchat c :clients) {
                try {
                        if(c.GetName().equals(name)) {
                                return true;
                        }
                } catch (Exception e) {
                System.err.println("Tchat exception: " + e.toString());
                e.printStackTrace();
            }
        }
        if (name.equals("Moderateur")){
            return true;
        }
        return false;
    }
    private boolean NameCanBeUse(String name) {
    	for(ClientTchat c :clients) {
    		try {
	    		if(c.GetName().equals(name)) {
	    			return true;
	    		}
    		} catch (Exception e) {
                System.err.println("Tchat exception: " + e.toString());
                e.printStackTrace();
            }
    	}
        for (String s :PsudoForbiden){
            if(s.equals(name)) {
                    return true;
            }
        }
    	return false;
    }


    public Vector<ClientTchat> getClients() {
        return clients;
    }
    public Vector<MessageProtocol> getMessage(){
        return messages;
    }
    public Vector<String> getPsudoForbiden(){
        return PsudoForbiden;
    }
    public void setMessage(Vector<MessageProtocol> messageList){
        messages = messageList;
    }
    public void setPsudoForbiden(Vector<String> aPsudoForbiden){
        PsudoForbiden = aPsudoForbiden;
    }
    public void eraseMessages(){
        messages = new Vector<MessageProtocol>();
    }
    public void erasePsudoForbiden(){
        PsudoForbiden = new Vector<String>();
        PsudoForbiden.add("Server");
        PsudoForbiden.add("Moderateur");
    }

}