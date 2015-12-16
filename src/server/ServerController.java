package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import protocole.CommunicationProtocol;

public class ServerController {
    private Server s; 
    private Tchat obj;
    private CommunicationProtocol stub;
    private Registry registry;
    
    public ServerController() {

    }
    public void run(){
        ServerCreatView scv = new ServerCreatView(this);
    }
    public void InitServeur(int port){
        try {
            s = new Server(port);
            obj = new Tchat();
            stub = (CommunicationProtocol) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            java.rmi.registry.LocateRegistry.createRegistry(s.getPort());
            registry = LocateRegistry.getRegistry();
            registry.bind("Tchat", stub);
            System.err.println("Server ready");         
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }       
    }
    public void Run (){
        
    }
    
}
