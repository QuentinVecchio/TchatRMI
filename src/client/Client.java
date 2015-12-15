package client;

import java.util.LinkedList;

public class Client {
	private String name;
	private String host;
	private String port;
	private LinkedList<String> messages;

	public Client()
	{
		this.name = "";
		this.host = "";
		this.port = "";
		this.messages = new LinkedList<String>();
	}
	
	public Client(String name, String host, String port)
	{
		this.name = name;
		this.host = host;
		this.port = port;
		this.messages = new LinkedList<String>();
	}

	public String GetName()
	{
		return name;
	}
    
	public String GetHost()
	{
		return host;
	}
	
	public String GetPort()
	{
		return port;
	}
	
	public void SetName(String name)
	{
		this.name = name;
	}
    
	public void SetHost(String host)
	{
		this.host = host;
	}
	
	public void SetPort(String port)
	{
		this.port = port;
	}
	
	public void AddMessage(String message)
	{
		messages.add(message);
	}
	
    public String GetMessages()
    {
    	String s = "";
    	for(int i=0;i<messages.size();i++)
    	{
    		s += messages.get(i);
    	}
    	return s;
    }
}

