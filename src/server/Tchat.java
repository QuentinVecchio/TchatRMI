package server;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.LinkedList;
import client.ClientTchat;
import protocole.CommunicationProtocol;
import protocole.Message;
import protocole.MessageProtocol;

public class Tchat implements CommunicationProtocol {
	private LinkedList<ClientTchat> clients = new LinkedList<ClientTchat>();
	private LinkedList<MessageProtocol> messages = new LinkedList<MessageProtocol>();
	private LinkedList<String> PseudoForbiden = new LinkedList<String>();
    private ServerRMIController sc;

    /**
     *Constructeur de la classe Tchat. 
     * @param aSc 
     *         Controlleur du Serveur
     */
    public Tchat(ServerRMIController aSc)  {
        sc = aSc;
        PseudoForbiden.add("Server");
        PseudoForbiden.add("Moderateur");
        PseudoForbiden.add("all");

    }

    /**
     * enregistrement d'un client avent de pouvoir commencer à échanger. 
     * @param c 
     *          ClientTchat à enregistrer
     * @return
     *          true si l'enrgistrement a été effectué false sinon.
     * @throws RemoteException
     */
    public boolean Register(ClientTchat c) throws RemoteException {
    	if(NameCanBeUse(c.GetName())) {
    		return false;
    	} else {
	    	System.out.println("Le client <" + c.GetName() + "> est connecté");
	    	clients.add(c);
                sc.getServerView().addClient(c.GetName());
            for(MessageProtocol ms : messages) {
	    		c.Receive(ms);
	    	}
	    	Message m = new Message(c.GetName(), "all", c.GetName() + " est connecté", c.GetColor());
	    	messages.add(m);
	    	sc.getServerView().AddMessage(m);  
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
     * déconnection d'un Client.
     * @param c 
     *          Client à déconnecter.
     * @throws RemoteException
     */
    public void Disconnection(ClientTchat c) throws RemoteException{
    	Message m = new Message(c.GetName(), "all", c.GetName() + " est déconnecté", c.GetColor());
    	messages.add(m);
        sc.getServerView().AddMessage(m);
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
     * envoyer un messages au(x) destinataire(s) et renvoye le message a l'expecditeur. 
     * @param message
     *          Message à envoyer
     */
    public void Send(MessageProtocol message) {	
    try {
        if (NameExist(message.GetExpediteur())){
            Message m = new Message(message);
            messages.add(m);
            sc.getServerView().AddMessage(m);      
            for(ClientTchat c :clients) {
            	if(message.GetDestinataire().equals("all")) {
            		 c.Receive(message);
            	} else {
            		if (message.GetDestinataire().equals(c.GetName())  || message.GetExpediteur().equals(c.GetName())){
                        c.Receive(message);
                    }
            	}
            }
        }
        } catch (Exception e) {
        System.err.println("Tchat exception: " + e.toString());
        e.printStackTrace();
        }
    }
    
    /**
     * envoyer un messages au(x) destinataire(s). 
     * @param message
     *          Message à envoyer
     */
    public void SendSpecial(MessageProtocol message) {	
        try {
                Message m = new Message(message);
                messages.add(m);
                sc.getServerView().AddMessage(m);      
                for(ClientTchat c :clients) {
                    if (message.GetDestinataire().equals("all") 
                                || 
                        message.GetDestinataire().equals(c.GetName()) 
                                ||
                        message.GetExpediteur().equals(c.GetName())){
                        c.Receive(message);
                    }
                }
            } catch (Exception e) {
            System.err.println("Tchat exception: " + e.toString());
            e.printStackTrace();
            }
        }

    /**
     * banie un client, son pesudo ne peut plus être utilisé et ce jusqu'a ce que l'historique soit supprimé.
     * @param name
     *          nom du client à banire.
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
        PseudoForbiden.add(name);
    }
    
    /**
     * deconnecte tous les clients
     * @param name
     *          nom du client à banire.
     */
    public void DeconnectAll() {
    	for(ClientTchat c :clients) {
    		try {
    			Message m = new Message("Server","all","Arrêt du serveur", Color.RED);
    			SendSpecial(m);
				c.Deconnect();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
    	clients.remove();
    	messages.remove();
    }
    
    /**
     * Recherche d'un client 
     * @param name
     *          Nom du client
     * @return
     *          le client si il est trouvé, null sinon
     */
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
    
    /**
     * Recherche d'un nom de client existant 
     * @param name
     *          Nom du client
     * @return
     *          true si il est trouvé, false sinon
     */
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
    
    /**
     * Test si le pseudo n'est pas utilisé ou si il est interdit 
     * @param name
     *          Nom du client
     * @return
     *          true si il est utilisable, false sinon
     */
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
        for (String s :PseudoForbiden){
            if(s.equals(name)) {
                    return true;
            }
        }
    	return false;
    }


    /**
     * @return  
     *          liste des clients connecté.
     */
    public LinkedList<ClientTchat> getClients() {
        return clients;
    }

    /**
     * @return
     *          liste des messages échanges.
     */
    public LinkedList<MessageProtocol> getMessage(){
        return messages;
    }

    /**
     * @return
     *      liste des pseudo interdits.
     */
    public LinkedList<String> getPsudoForbiden(){
        return PseudoForbiden;
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
        PseudoForbiden = aPsudoForbiden;
    }

    /**
     * vide la liste des messages.
     */
    public void eraseMessages(){
        messages = new LinkedList<MessageProtocol>();
    }

    /**
     * vide la liste de pseudo Interdis et y remplace les 3 pseudos inutilisable. 
     */
    public void erasePseudoForbiden(){
        PseudoForbiden.clear();
        PseudoForbiden.add("Server");
        PseudoForbiden.add("Moderateur");
        PseudoForbiden.add("all");
    }

}