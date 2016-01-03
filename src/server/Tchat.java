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
	private LinkedList<ClientTchat> clients = new LinkedList<ClientTchat>();
	private LinkedList<MessageProtocol> messages = new LinkedList<MessageProtocol>();
        private LinkedList<String> PsudoForbiden = new LinkedList<String>();
        private ServerRMIController sc;

	
    public Tchat(ServerRMIController aSc)  {
        sc =aSc;
        PsudoForbiden.add("Server");
        PsudoForbiden.add("Moderateur");
        PsudoForbiden.add("all");

    }

    /**
     * enregistrement d'un client avent de pouvoir commencer à échanger. 
     * @param c 
     *          ClientTchat à enregistré
     * @return
     *          true si l'enrgistrement a été eféctuer false sinon.
     * @throws RemoteException
     */
    public boolean Register(ClientTchat c) throws RemoteException {
    	if(NameCanBeUse(c.GetName())) {
    		return false;
    	} else {
	    	System.out.println("Le client <" + c.GetName() + "> est connectÃ©");
	    	clients.add(c);
                sc.getServerView().addClient(c.GetName());
            for(MessageProtocol ms : messages) {
	    		c.Receive(ms);
	    	}
	    	Message m = new Message(c.GetName(), "all", c.GetName() + " est connectÃ©", c.GetColor());
	    	messages.add(m);
            for(ClientTchat cl : clients) {
	    		cl.Receive(m);
	    		if(cl.GetName().equals(c.GetName()) == false) {
	    			cl.AddClient(c.GetName());
	    		}
	    	}
	    	for(ClientTchat cl : clients) {
	    		c.AddClient(cl.GetName());
	    	}
	    	return true;
    	}
    }

    /**
     * déconection d'un Client.
     * @param c 
     *          Client a déconécter.
     * @throws RemoteException
     */
    public void Disconnection(ClientTchat c) throws RemoteException{
    	Message m = new Message(c.GetName(), "all", c.GetName() + " est dÃ©connectÃ©", c.GetColor());
    	messages.add(m);
        sc.getServerView().addMessage(m.GetExpediteur()+" > "+m.GetDestinataire()+" :"+m.GetMessage());
    	clients.remove(c);
        sc.getServerView().supClient(c.GetName());
        for(ClientTchat cl : clients) {
    		try {
    			cl.Receive(m);
    			cl.DeleteClient(c.GetName());
    		} catch (Exception e) {
                System.err.println("Tchat exception: " + e.toString());
                e.printStackTrace();
            }
    	}
    }

    /**
     * envoyer un messages au(x) déstinatére(s) et renvoye le massage a l'expéditeur. 
     * @param message
     *          Message a envoyer
     */
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

    /**
     * banie un client, sont pesudo ne peut plus étre utiliser et se jusqu'a ce que l'historique soit suprimer.
     * @param name
     *          nom de client a banire.
     */
    public void BanishClient(String name){
        ClientTchat toBanish = FindClient(name);
        try {
            toBanish.Receive(new Message("Moderateur", toBanish.GetName(), "Vous avez été bannie", toBanish.GetColor()));
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


    /**
     * @return  
     *          liste des client conecter.
     */
    public LinkedList<ClientTchat> getClients() {
        return clients;
    }

    /**
     * @return
     *          liste des messages échangés.
     */
    public LinkedList<MessageProtocol> getMessage(){
        return messages;
    }

    /**
     * @return
     *      liste des pseudo interdits.
     */
    public LinkedList<String> getPsudoForbiden(){
        return PsudoForbiden;
    }

    /**
     * remplasse la liste de massages pas une nouvel 
     * @param messageList
     *      nouvel liste des message 
     */
    public void setMessage(LinkedList<MessageProtocol> messageList){
        messages = messageList;
    }
    
    /**
     * remplasse la liste de pseudo Interdis  pas une nouvel 
     * @param aPsudoForbiden
     *      nouvel liste des pseudo Interdis 
     */public void setPsudoForbiden(LinkedList<String> aPsudoForbiden){
        PsudoForbiden = aPsudoForbiden;
    }

    /**
     * vide la liset des message.
     */
    public void eraseMessages(){
        messages = new LinkedList<MessageProtocol>();
    }

    /**
     * vide la liste de pseudo Interdis et y remplasse les 3 pseudo inutilisable. 
     */
    public void erasePsudoForbiden(){
        PsudoForbiden.clear();
        PsudoForbiden.add("Server");
        PsudoForbiden.add("Moderateur");
        PsudoForbiden.add("all");
    }

}