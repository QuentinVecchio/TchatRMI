package protocole;

import java.rmi.Remote;
import java.util.Date;

public interface MessageProtocol extends Remote {
	public String GetExpediteur();
	public String GetDestinataire();
	public Date GetDate();
	public String GetMessage();
}
