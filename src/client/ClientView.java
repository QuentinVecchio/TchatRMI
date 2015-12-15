package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ClientView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public ClientView(ClientController c) {
		
	}
  
	public void Affiche() {
		this.setVisible(true);
	}
	
	public void Exit() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}