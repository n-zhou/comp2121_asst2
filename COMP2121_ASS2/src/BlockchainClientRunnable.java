import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BlockchainClientRunnable implements Runnable {

    private String reply;
    private int serverNumber;
    private String serverName;
    private int portNumber;
    private String message;
    
    public BlockchainClientRunnable(int serverNumber, String serverName, int portNumber, String message) {
        this.reply = "Server" + serverNumber + ": " + serverName + " " + portNumber; // header string
        this.serverNumber = serverNumber;
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.message = message;
    }

    public void run() {
        // implement your code here
    	try {
    		Socket socket = new Socket(serverName, portNumber);
    		socket.setSoTimeout(2000);
    		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        	if(message.startsWith("pb"))
        		pw.println("pb");
        	else if(message.startsWith("tx"))
        		pw.println(message);
        	String line;
        	while((line = br.readLine()) != null) {
        		reply += String.format("\n%s", line);
        		if(!br.ready())
        			break;
        	}
        	pw.println("cc");
        	socket.close();
    	}
    	catch(java.net.ConnectException e) {
    		reply += "\nServer is not available\n";
    	}
    	catch(UnknownHostException e) {
    		reply += "\nServer is not available\n";
    	}
    	catch (Exception e) {
    		System.err.println(e);
    	}
    	
    	
    	
    }

    public String getReply() {
        return reply;
    }

    // implement any helper method here if you need any
}