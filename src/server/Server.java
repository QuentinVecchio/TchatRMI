package server;

public class Server {
    private int port;

    public Server (){
        port = 1099;  
    }
    
    public Server (int aPort){
        if (! setPort(aPort)){
            System.err.println("numéro de protocol inférieur à 1024");
        }
    }
        
    public int getPort() {
    	return port; 
    }
    
    public boolean setPort(int aPort){
    	if (aPort>1024) {
    		port = aPort;
            return true;
    	}
    	return false;
    }
}
