package server;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import protocole.CommunicationProtocol;
import protocole.Message;
import protocole.MessageProtocol;

public class ServerRMIController {
    private Server s; 
    private Tchat obj;
    private CommunicationProtocol stub;
    private Registry registry;
    private ServerView sv;
    
    /**
     * Constructeur de la classe ServerRMIController 
     */
    public ServerRMIController() {

    }
    
    /**
     * Méthode qui lance le serveur 
     */
    public void Start(){
        InitServeur(1099);
        run();
    }
    
    /**
     * Méthode qui lance l'interface graphique du serveur
     */
    public void run (){
        sv = new ServerView(this);
    }

    /**
     * initialisation du Serveur
     * @param port : numero de port au quel le seveur se connecte
     */
    public void InitServeur(int port){
        try {
            s = new Server(port);
            obj = new Tchat(this);
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

    /**
     * Sauvegarde l'historique.
     */
    public void saveHistorique(String path){
    	ObjectOutputStream oos = null;
        try {
        	s.setLink(path);
		 	final FileOutputStream fichier = new FileOutputStream(path);
		 	oos = new ObjectOutputStream(fichier);
		 	Historique history = new Historique(obj.getMessage().size(), obj.getPsudoForbiden().size(), obj.getMessage(), obj.getPsudoForbiden());
		 	oos.writeObject(history);
		 	oos.flush();
		 	oos.close();
        } catch (final java.io.IOException e) {
        	e.printStackTrace();
        }
    }

    /**
     * charge l'historique préalablement sauvegarder
     */
    public void restorHistorique(String path){
        ObjectInputStream ois = null;
        try {
            final FileInputStream fichier = new FileInputStream(path);
            s.setLink(path);
            ois = new ObjectInputStream(fichier);
            Historique history = (Historique)ois.readObject();
            LinkedList<MessageProtocol> messages = new LinkedList<MessageProtocol>();
            Message mess = new Message("Server","all","Changement d'historique", Color.RED);
            obj.Send(mess);
            sv.eraseAllMessages();
            for (Message m: history.getMessages()){
            	messages.add(m);
            	obj.SendSpecial(m);
            }
            obj.setPsudoForbiden(history.getPseudoForbidden());
        } catch (final java.io.IOException e) {
        	e.printStackTrace();
        } catch (final ClassNotFoundException e) {
        	e.printStackTrace();
        } finally {
        	try {
        		if (ois != null) {
        			ois.close();
        		}
        	} catch (final IOException ex) {
        		ex.printStackTrace();
        	}
        }
    }
      
    /**
     *	Méthode qui supprime tous les messages et tous les pseudoInterdit
     */
    public void eraseHistorique(){
    	obj.eraseMessages();
        obj.erasePseudoForbiden();   
    }

    /**
     * @return
     *      l'objet Tchat au quel est lier le Serveur
     */
    public Tchat getTchat(){
        return obj;
    }

    /**
     * @return
     *      la vue de serveur (ServerView)
     */
    public ServerView getServerView(){
        return sv;
    }
    
}
