package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClientConnectionRMIView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton connectionButton = new JButton("Connection");
	private JTextField pseudoTextField = new JTextField("Pseudo");
	private JTextField hostTextField = new JTextField("Host");
	private JLabel pseudoLabel = new JLabel("Pseudo : ");
	private JLabel hostLabel = new JLabel("Host : ");
	private JLabel colorLabel = new JLabel("Votre couleur d'écriture");
	private JButton colorButton = new JButton("Choisir couleur");
	private ClientRMIController c;
	
	public ClientConnectionRMIView(ClientRMIController c) {
		super();
		this.c = c;
		//Définition de la fenêtre
		this.setSize(405, 150);
		this.setTitle("Tchat - Connexion");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		//Définition du formulaire
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 1;
		//Définition label et textfield pseudo
	    gbc.gridwidth = 1;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    this.add(pseudoLabel, gbc);
	    gbc.gridx = 1;
	    gbc.gridwidth = 1;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    this.add(pseudoTextField, gbc);
	    //Définition label et textfield host
	    gbc.gridy = 1;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    this.add(hostLabel, gbc);
	    gbc.gridx = 1;
	    gbc.gridwidth = 1;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    this.add(hostTextField, gbc);
		//Définition du choix de couleur
	    gbc.gridy = 2;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    this.add(colorButton, gbc);
	    gbc.gridx = 1;
	    gbc.gridwidth = 1;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    this.add(colorLabel, gbc);
	    colorButton.addActionListener(this);
	    colorButton.setPreferredSize(new Dimension(150, 30));
	    colorLabel.setPreferredSize(new Dimension(250, 30));
	    //Définition du bouton connexion
		gbc.gridy = 4;
	    gbc.gridx = 0;
	    gbc.gridwidth = 1;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    this.add(connectionButton, gbc);
		connectionButton.addActionListener(this);
		connectionButton.setPreferredSize(new Dimension(400, 30));
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
		this.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == connectionButton) {
			c.SetHost(hostTextField.getText());
			c.SetName(pseudoTextField.getText());
			c.SetPort("1099");
			c.Connection();		
		} else if(e.getSource() == colorButton){
			try {
				Color color = JColorChooser.showDialog(null, "Choisir une couleur", c.GetColor());
				c.SetColor(color);
				colorLabel.setForeground(color);
			} catch (HeadlessException e1) {
				System.out.println("Erreur color");
				e1.printStackTrace();
			}
		}
	}
}