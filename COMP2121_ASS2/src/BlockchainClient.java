import java.util.ArrayList;
import java.util.Scanner;

public class BlockchainClient {

    public static void main(String[] args) {

        if (args.length != 1) {
            return;
        }
        String configFileName = args[0];

        ServerInfoList pl = new ServerInfoList();
        pl.initialiseFromFile(configFileName);

        Scanner sc = new Scanner(System.in);
        BlockchainClient bcc = new BlockchainClient();
        while (true) {
            String message = sc.nextLine();
            // implement your code here
            if(message.equals("sd"))
            	return;
            if(message.equals("ls"))
            	System.out.println(pl.toString());
            else if(message.equals("pb"))
            	bcc.broadcast(pl, message);
            String[] split = message.split("[|]");
            
        }
    }

    public void unicast (int serverNumber, ServerInfo p, String message) {
        // implement your code here
    	
    }

    public void broadcast (ServerInfoList pl, String message) {
        // implement your code here
    }

    public void multicast (ServerInfoList serverInfoList, ArrayList<Integer> serverIndices, String message) {
        // implement your code here
    }

    // implement any helper method here if you need any
}