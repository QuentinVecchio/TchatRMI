package client;

import java.util.LinkedList;

import protocole.Message;

public class Client {
	private String name;
	private String host;
	private String port;
	private LinkedList<Message> messages = new LinkedList<Message>();

	public Client() {
		this.name = "";
		this.host = "";
		this.port = "";
	}
	
	public Client(String name, String host, String port) {
		this.name = name;
		this.host = host;
		this.port = port;
	}

	public String GetName() {
		return name;
	}
    
	public String GetHost() {
		return host;
	}
	
	public String GetPort() {
		return port;
	}
	
	public void SetName(String name) {
		this.name = name;
	}
    
	public void SetHost(String host) {
		this.host = host;
	}
	
	public void SetPort(String port) {
		this.port = port;
	}
	
	public void AddMessage(Message message) {
		messages.add(message);
	}
	
    public LinkedList<Message> GetMessages() {
    	return messages;
    }
}

