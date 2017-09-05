import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockchainServer {

    public static void main(String[] args) throws InterruptedException, IOException {

        if (args.length != 1) {
            return;
        }

        int portNumber = Integer.parseInt(args[0]);
        Blockchain blockchain = new Blockchain();


        PeriodicCommitRunnable pcr = new PeriodicCommitRunnable(blockchain);
        Thread pct = new Thread(pcr);
        pct.start();

        // implement your code her
        ServerSocket server = new ServerSocket(portNumber);
        
        try {
        	 while(true){
             	Socket socket = server.accept();
             	new Thread(new BlockchainServerRunnable(socket, blockchain)).start();
             }
        } 
        catch(Exception e) {
        	
        }
        finally {
        	server.close();
        	pcr.setRunning(false);
            pct.join();
        }
        
        
    }

    // implement any helper method here if you need any
}
