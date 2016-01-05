package protocole;

import java.awt.Color;
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
	private Color color;
	
	/**
	 * Constructeur de la classe Message
	 * 
     * @param expediteur : nom de l'expediteur
     * @param destinataire : nom du destinataire
     * @param message : le message
     * @param c : la couleur du message
     */
	public Message(String expediteur, String destinataire, String message, Color c) {
		try {
			this.expediteur = expediteur;
			this.destinataire = destinataire;
			this.date = new Date().toString();
			this.message = message;
			this.color = c;
			UnicastRemoteObject.exportObject(this, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Constructeur de la classe Message
	 * 
     * @param message : le message
     *
     */
	public Message(MessageProtocol message) {
		try {
			this.message = message.GetMessage();
			this.expediteur = message.GetExpediteur();
			this.destinataire = message.GetDestinataire();
			this.date = message.GetDate();
			this.color = message.GetColor();
			UnicastRemoteObject.exportObject(this, 0);
		} catch(Exception e) {
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
	
	public Color GetColor() {
		return this.color;
	}
	
	public void SetColor(Color c) {
		this.color = c;
	}
        
	public String toString(){
		return "<span style=\"color : rgb(" + GetColor().getRed() + "," + GetColor().getGreen() + "," + GetColor().getBlue() + ")\">" + GetExpediteur() + " > " + GetDestinataire() + " : " + GetMessage() + "</<span></br>";
    }
}
