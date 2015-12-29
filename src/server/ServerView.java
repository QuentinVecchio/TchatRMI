package server;

import java.awt.BorderLayout;

import java.awt.Color;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import protocole.Message;

//import oracle.jdeveloper.layout.PaneConstraints;
//import oracle.jdeveloper.layout.PaneLayout;

public class ServerView extends JFrame  {
    
    ServerRMIController sc;

    //String Message[] = {"adrien","pierre","michel"};
    Vector<String> Clients = new Vector<String>();
    Vector<String> Message = new Vector<String>();
    private JTabbedPane GeneralePane = new JTabbedPane();
    //private PaneLayout paneLayout1 = new PaneLayout();
    private JPanel MessagePane = new JPanel();
    private JPanel ClientPane = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel MessageTritre = new JLabel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JLabel ClientTitre = new JLabel();
    private JScrollPane scrollMssageList = new JScrollPane();
    private JList MessageListe;
    private JList ClientListe;
    
    public ServerView(ServerRMIController asc ) {
        sc = asc;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        

        MessageListe= new JList(Message);
        scrollMssageList.setViewportView(MessageListe);
        //Message.add("Pierre");
        ClientListe= new JList(Clients);
        
        //jPanel1.setLayout(paneLayout1);
        GeneralePane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        MessagePane.setLayout(borderLayout1);
        ClientPane.setLayout(borderLayout2);
        MessageTritre.setText("messege en temps directe :");
        MessagePane.add(scrollMssageList, BorderLayout.CENTER);
        MessagePane.add(MessageTritre, BorderLayout.NORTH);
        
        GeneralePane.addTab("Messages", MessagePane);
        ClientTitre.setText("clientes acctuelement connecte");
        ClientPane.add(ClientTitre, BorderLayout.NORTH);
        ClientPane.add(ClientListe, BorderLayout.CENTER);
        GeneralePane.addTab("Clients", ClientPane);
        
        this.getContentPane().add(GeneralePane, null);
        this.setSize(800, 500);
        this.setVisible(true);
    }
    public void addMessage(String message){
        Message.add(message);
        MessageListe.setListData(Message);
    }
    public void addClient(String client){
        Clients.add(client);
        ClientListe.setListData(Clients);
    }
}
