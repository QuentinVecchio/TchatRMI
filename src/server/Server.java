package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import protocole.CommunicationProtocol;

public class Server {
    private int port;
	public static void main(String args[]) {


    }
    public Server (){
        port = 1099;  
    }
    public Server (int aPort){
        if (! setPort(aPort)){
            System.err.println("numérau de prot < à 1024");
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
}
