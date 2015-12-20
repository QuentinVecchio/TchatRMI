package protocole;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Message implements MessageProtocol, Serializable {

	private static final long serialVersionUID = 1L;
	private String expediteur;
	private String destinataire;
	private String date;
	private String message;
	
	public Message(String expediteur, String destinataire, String message) {
		try {
			this.expediteur = expediteur;
			this.destinataire = destinataire;
			this.date = new Date().toString();
			this.message = message;
			MessageProtocol stub = (MessageProtocol) UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
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
	
	public String GetDate() {
		return date;
	}
	
	public String GetMessage() {
		return message;
	}

	public void SetMessage(String message) {
		this.message = message;
	}
}
