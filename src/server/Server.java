package server;

import client.ClientTchat;

import java.util.Vector;

import protocole.MessageProtocol;

public class Server {
    private int port;
    //private Vector<ClientTchat> clients = new Vector<ClientTchat>();
    //private Vector<MessageProtocol> messages = new Vector<MessageProtocol>();

    public Server (){
        port = 1099;  
    }
    
    public Server (int aPort){
        if (! setPort(aPort)){
            System.err.println("numéro de protocol inférieur à 1024");
        }
    }
        
    public int getPort() {
    	return port; 
    }
    
    public boolean setPort(int aPort){
    	if (aPort>1024) {
    		port = aPort;
            return true;
    	}
    	return false;
    }
  /* 
    public void addMessage(MessageProtocol aMessage){
        messages.add(aMessage);
    }
    public void addClients(ClientTchat aClient){
        clients.add(aClient);
    }
    public void supClients (ClientTchat aClient){
        clients.remove(aClient);
    }
    public boolean ClientExist (ClientTchat aClient){
        return -1 !=clients.lastIndexOf(aClient);
    }
*/
}
