package server;

import java.io.PrintStream;

public class MainRMI {
    public static void main(String args[]){
        ServerRMIController sc = new ServerRMIController();
        sc.run();
        //ServerView sv = new ServerView(); 
    }
}
