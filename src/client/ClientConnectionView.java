package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClientConnectionView extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton connectionButton = new JButton("Connection");
	private JTextField pseudoTextField = new JTextField("Pseudo");
	private JTextField hostTextField = new JTextField("Host");
	private JTextField portTextField = new JTextField("Port");
	private JLabel pseudoLabel = new JLabel("Pseudo : ");
	private JLabel hostLabel = new JLabel("Host : ");
	private JLabel portLabel = new JLabel("Port : ");
	private ClientController c;
	
	public ClientConnectionView(ClientController c)
	{
		this.c = c;
		this.setSize(400, 200);
		this.setTitle("Tchat - Connexion");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.getContentPane().add( connectionButton, BorderLayout.SOUTH);
		connectionButton.addActionListener(this);
	}
  
	public void Affiche()
	{
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == connectionButton)
		{	c.Connection();		
		}
	}
}