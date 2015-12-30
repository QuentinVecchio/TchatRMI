package server;

import java.awt.BorderLayout;

import java.awt.Color;

import java.awt.FlowLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import protocole.Message;

//import oracle.jdeveloper.layout.PaneConstraints;
//import oracle.jdeveloper.layout.PaneLayout;

public class ServerView extends JFrame implements ActionListener  {
    
    ServerRMIController sc;

    Vector<String> Clients = new Vector<String>();
    Vector<String> Message = new Vector<String>();
    private JList MessageListe;
    private JList ClientListe;
    
    private JTabbedPane GeneralePane = new JTabbedPane();
    
    //-------------------onglait Messages----------------//
    private JLabel MessageTritre = new JLabel();
    private JPanel MessagePane = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane scrollMssageList = new JScrollPane();
    
    private JPanel Historique = new JPanel();
    private FlowLayout flowLayoutHisto = new FlowLayout();
    private JButton SaveHistorique = new JButton();
    private JButton SupHistorique = new JButton();
    private JButton LoadHistorique = new JButton();
    
    //-------------------onglait Clients----------------//
    private JPanel ClientPane = new JPanel();
    private JLabel ClientTitre = new JLabel();
    private BorderLayout borderLayout2 = new BorderLayout();


    
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
        ClientListe= new JList(Clients);
        
        GeneralePane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        MessagePane.setLayout(borderLayout1);
        ClientPane.setLayout(borderLayout2);
        Historique.setLayout(flowLayoutHisto);
        SaveHistorique.setText("Save Historique");
        SupHistorique.setText("Sup Historique");
        LoadHistorique.setText("Charger Historique");
        SaveHistorique.addActionListener(this);
        SupHistorique.addActionListener(this);
        LoadHistorique.addActionListener(this);
        
        Historique.setBorder(BorderFactory.createTitledBorder("Gsetion de l'historique"));
        Historique.add(LoadHistorique);
        Historique.add(SaveHistorique);
        Historique.add(SupHistorique);        
        MessageTritre.setText("messege en temps directe :");
        MessagePane.add(scrollMssageList, BorderLayout.CENTER);
        MessagePane.add(MessageTritre, BorderLayout.NORTH);
        MessagePane.add(Historique, BorderLayout.SOUTH);
        
        GeneralePane.addTab("Messages", MessagePane);
        ClientTitre.setText("clientes acctuelement connecte");
        ClientPane.add(ClientTitre, BorderLayout.NORTH);
        ClientPane.add(ClientListe, BorderLayout.CENTER);
        GeneralePane.addTab("Clients", ClientPane);
        
        this.getContentPane().add(GeneralePane, null);
        this.setSize(800, 500);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == SaveHistorique ) {
            sc.saveHistorique();
            System.out.println("Save");
        }else
        if (e.getSource() == LoadHistorique ){
            sc.restorHistorique();
        }else
        if (e.getSource() == SupHistorique ){
            
        }
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
