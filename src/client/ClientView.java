package client;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ClientView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public ClientView(ClientController c) {
		//Définition de la fenêtre
		this.setSize(800, 600);
		this.setTitle("Tchat");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		BuildGUILeft();
		BuildGUINorth();
		BuildGUIRigth();
		BuildGUICenter();
	}
  
	public void Affiche() {
		this.setVisible(true);
	}
	
	public void Exit() {
		this.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		
	}
	
	private void BuildGUILeft()
	{
		
	}
	
	private void BuildGUIRigth()
	{
		
	}
	
	private void BuildGUINorth()
	{
		
	}
	
	private void BuildGUICenter()
	{
		
	}
}