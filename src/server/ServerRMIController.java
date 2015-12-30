package server;

import client.ClientTchat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import protocole.CommunicationProtocol;

public class ServerRMIController {
    private Server s; 
    private Tchat obj;
    private CommunicationProtocol stub;
    private Registry registry;
    private ServerCreatView scv;
    private ServerView sv;
    
    public ServerRMIController() {

    }
    
    public void Start(){
        scv = new ServerCreatView(this);
        InitServeur(1099);
        run();
    }
    public void run (){
        sv = new ServerView(this);
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
            scv.Exit();
        } catch (Exception e) {
        	scv.ErrorPort();
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }       
    }

    public Tchat getTchar(){
        return obj;
    }
    public ServerView getServerView(){
        return sv;
    }
    
    
    //-------------------NOT RMI---------------------//
    public void StartNotRIM(){
        scv = new ServerCreatView(this);
        scv.setVisible(true);
    }
}
