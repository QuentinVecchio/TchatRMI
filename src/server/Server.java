package server;

import java.util.Vector;

import client.ClientTchat;
import protocole.MessageProtocol;

public class Server {
    private int port;
    private Vector<ClientTchat> clients = new Vector<ClientTchat>();
    private Vector<MessageProtocol> messages = new Vector<MessageProtocol>();
    private String link = null;
    
    /**
     * Constructeur de la classe Serveur
     */
    public Server (){
        port = 1099;  
    }
    
    /**
    * Constructeur de la classe Serveur
    * @param aPort : numero de port au quel le seveur se connecte
    */
    public Server (int aPort){
        if (! setPort(aPort)){
            System.err.println("numéro de protocol inférieur à 1024");
        }
    }
        
    /**
     * @return
     *      le port du serveur (int)
     */
    public int getPort() {
    	return port; 
    }
    
    /**
     * @param aPort : nouveau port du serveur
     * @return
     *      true si la modification a eu lieu, false sinon (boolean)
     */
    public boolean setPort(int aPort){
    	if (aPort>1024) {
    		port = aPort;
            return true;
    	}
    	return false;
    }
   
    /**
     * Ajoute un message
     * @param aMessage : nouveau message     
     */
    public void addMessage(MessageProtocol aMessage){
        messages.add(aMessage);
    }
    
    /**
     * Ajoute un client
     * @param aClient : nouveau client     
     */
    public void addClients(ClientTchat aClient){
        clients.add(aClient);
    }
    
    /**
     * Supprime un client
     * @param aClient : client     
     */
    public void supClients (ClientTchat aClient){
        clients.remove(aClient);
    }
    
    /**
     * Test si un client existe
     * @param aClient : client 
     * 
     * @return true si le client existe, false sinon (boolean)
     */
    public boolean ClientExist (ClientTchat aClient){
        return -1 != clients.lastIndexOf(aClient);
    }
    
    /**
     * 
     * @return lien de l'historique
     */
    public String getLink() {
    	return link;
    }
    
    /**
     * 
     * @param lien de l'historique
     */
    public void setLink(String link) {
    	this.link = link;
    }
}
