package server;

import client.ClientTchat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Vector;

import protocole.CommunicationProtocol;
import protocole.Message;
import protocole.MessageProtocol;

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
            obj = new Tchat(this);
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
    public void saveHistorique(){
        ObjectOutputStream oos = null;
            try {
              final FileOutputStream fichier = new FileOutputStream("donnees.ser");
              oos = new ObjectOutputStream(fichier);
                Integer size =(Integer) obj.getMessage().size();
                oos.writeObject(size);
                for (MessageProtocol m : obj.getMessage()){
                    oos.writeObject((Message) m );
                }
              oos.flush();
            } catch (final java.io.IOException e) {
              e.printStackTrace();
            } finally {
              try {
                if (oos != null) {
                  oos.flush();
                  oos.close();
                }
              } catch (final IOException ex) {
                ex.printStackTrace();
              }
            }
    }
    public void restorHistorique(){
        ObjectInputStream ois = null;

        try {
            final FileInputStream fichier = new FileInputStream("donnees.ser");
            ois = new ObjectInputStream(fichier);
            Integer size = (Integer)ois.readObject();
            System.out.println(size);
            Vector<MessageProtocol> messageList = new Vector<MessageProtocol>();
            for (int i = 0 ; i< size; i++){
                messageList.add((Message)ois.readObject());
            }
            obj.setMessage(messageList);
            for (MessageProtocol m: messageList){
                sv.addMessage(m.GetMessage());
            }
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
