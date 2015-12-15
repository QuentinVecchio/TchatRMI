package protocole;

import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import client.ClientTchat;

public class Message implements MessageProtocol {
	private String expediteur;
	private String destinataire;
	private Date date;
	private String message;
	
	public Message(String expediteur, String destinataire, String message) {
		try {
			MessageProtocol stub = (MessageProtocol) UnicastRemoteObject.exportObject(this, 0);
		} catch (Exception e) {
			System.err.println("Message exception: " + e.toString());
			e.printStackTrace();
		}
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.date = new Date();
		this.message = message;
	}

	public String GetExpediteur() {
		return expediteur;
	}

	public void SetExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}

	public String GetDestinataire() {
		return destinataire;
	}

	public void SetDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}
	
	public Date GetDate() {
		return date;
	}
	
	public String GetMessage() {
		return message;
	}

	public void SetMessage(String message) {
		this.message = message;
	}
}
