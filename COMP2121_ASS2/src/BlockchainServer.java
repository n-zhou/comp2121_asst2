import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BlockchainServer {

    public static void main(String[] args) {

        if (args.length != 1) {
            return;
        }
        Scanner sc = new Scanner(args[0]);
        if(!sc.hasNextInt())
        	return;
        int portNumber = Integer.parseInt(args[0]);
        Blockchain blockchain = new Blockchain();

        
        PeriodicCommitRunnable pcr = new PeriodicCommitRunnable(blockchain);
        Thread pct = new Thread(pcr);
        pct.start();
        
        // implement your code her
        ServerSocket server = null;
        try {
        	server = new ServerSocket(portNumber);
        	 while(true){
              	Socket socket = server.accept();
              	new Thread(new BlockchainServerRunnable(socket, blockchain)).start();
              }
        }
        catch (Exception e) {
        	System.err.println(e);
        }
        finally {
        	reallyClose(server);
        	pcr.setRunning(false);
            //pct.join();
        }
        
        
    }
    
    public static void reallyClose(ServerSocket s) {
    	try {s.close();}catch(Exception e) {}
    }
    
    

    // implement any helper method here if you need any
}
