package protocole;

import java.util.Date;

public class Message implements MessageProtocol {
	private String expediteur;
	private String destinataire;
	private Date date;
	private String message;
	
	public Message(String expediteur, String destinataire, String message) {
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
