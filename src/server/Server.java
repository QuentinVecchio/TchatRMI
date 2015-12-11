package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import protocole.CommunicationProtocol;

public class Server {
	public static void main(String args[]) {

        try {
            Tchat obj = new Tchat();
            CommunicationProtocol stub = (CommunicationProtocol) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Tchat", stub);
            System.err.println("Server ready");         
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
