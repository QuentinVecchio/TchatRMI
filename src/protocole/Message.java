package protocole;

public class Message {
	private String expediteur;
	private String destinataire;
	private String message;
	
	public Message(String expediteur, String destinataire, String message) {
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.message = message;
	}

	public String getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}

	public String getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
