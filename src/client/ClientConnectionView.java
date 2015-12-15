package client;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientConnectionView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton connectionButton = new JButton("Connection");
	private JTextField pseudoTextField = new JTextField("Pseudo");
	private JTextField hostTextField = new JTextField("Host");
	private JTextField portTextField = new JTextField("Port");
	private JLabel pseudoLabel = new JLabel("Pseudo : ");
	private JLabel hostLabel = new JLabel("Host : ");
	private JLabel portLabel = new JLabel("Port : ");
	private JPanel content = new JPanel();
	private ClientController c;
	
	public ClientConnectionView(ClientController c) {
		this.c = c;
		//Définition de la fenêtre
		this.setSize(400, 200);
		this.setTitle("Tchat - Connexion");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		//Définition du bouton connexion
		this.getContentPane().add( connectionButton, BorderLayout.SOUTH);
		connectionButton.addActionListener(this);
		//Définition du formulaire
		this.getContentPane().add( content, BorderLayout.CENTER);
		content.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 1;
		//Définition label et textfield pseudo
	    gbc.gridwidth = 1;
	    content.add(pseudoLabel, gbc);
	    gbc.gridx = 1;
	    gbc.gridwidth = 3;
	    content.add(pseudoTextField, gbc);
	    //Définition label et textfield host
	    gbc.gridy = 1;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    content.add(hostLabel, gbc);
	    gbc.gridx = 1;
	    gbc.gridwidth = 3;
	    content.add(hostTextField, gbc);
	    //Définition label et textfield port
	    gbc.gridy = 2;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    content.add(portLabel, gbc);
	    gbc.gridx = 1;
	    gbc.gridwidth = 3;
	    content.add(portTextField, gbc);
	}
  
	public void Affiche() {
		this.setVisible(true);
	}
	
	public void ErrorName() {
		JOptionPane.showMessageDialog(null, "Error Pseudo", "Error : Pseudo is already used.", JOptionPane.ERROR_MESSAGE);
	}
	
	public void ErrorHost() {
		JOptionPane.showMessageDialog(null, "Error Host", "Error : Host doesn't exist or Port is bad.", JOptionPane.ERROR_MESSAGE);
	}

	public void Exit() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == connectionButton) {
			c.GetClient().SetHost(hostTextField.getText());
			c.GetClient().SetName(pseudoTextField.getText());
			c.GetClient().SetPort(portTextField.getText());
			c.Connection();		
		}
	}
}