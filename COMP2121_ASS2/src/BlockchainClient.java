import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedList;

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
            String[] split = message.split("[|]");
            switch(split[0]) {
            	case "ls":
            		System.out.println(pl.toString());
            		break;
            	case "ad":
            		if(pl.addServerInfo(new ServerInfo(split[1], Integer.parseInt(split[2]))))
            			System.out.println("Succeeded\n");
            		else
            			System.out.println("Failed\n");
            		break;
            	case "rm":
            		if(pl.removeServerInfo(Integer.parseInt(split[1])))
            			System.out.println("Succeeded\n");
            		else
            			System.out.println("Failed\n");
            		break;
            	case "up":
            		if(pl.updateServerInfo(Integer.parseInt(split[1]), new ServerInfo(split[2], Integer.parseInt(split[3]))))
            			System.out.println("Succeeded\n");
            		else
            			System.out.println("Failed\n");
            		break;
            	case "cl":
            		if(pl.clearServerInfo())
            			System.out.println("Succeeded\n");
            		else
            			System.out.println("Failed\n");
            		break;
            	case "tx":
            		bcc.broadcast(pl, message);
            		break;
            	case "pb":
            		if(split.length == 1)
            			bcc.broadcast(pl, message);
            		else if(split.length == 2)
            			bcc.unicast(Integer.parseInt(split[1]), pl.getServerInfos().get(Integer.parseInt(split[1])), message);
            		else {
            			ArrayList<Integer> indices = new ArrayList<>();
            			for(int i = 1; i < split.length; ++i)
            				indices.add(Integer.parseInt(split[i]));
            			bcc.multicast(pl, indices, message);
            		}   		
            		break;
            	case "sd":
            		return;
            	default: 
            		System.out.println("Unknown Command\n"); 
            }
            	
            
        }
    }

    public void unicast (int serverNumber, ServerInfo p, String message) {
        // implement your code here
    	try {
    		BlockchainClientRunnable bccr = new BlockchainClientRunnable(serverNumber, p.getHost(), p.getPort(), message);
        	Thread t = new Thread(bccr);
        	t.start();
        	t.join(2000);
        	System.out.println(bccr.getReply());
    	}
    	catch(Exception e) {
    		e.getMessage();
    	}
    	
    	
    }

	public void broadcast (ServerInfoList pl, String message) {
        // implement your code here
    	LinkedList<Thread> threads = new LinkedList<>();
    	LinkedList<BlockchainClientRunnable> objects = new LinkedList<>();
    	try {
    		for(int i = 0; i < pl.getServerInfos().size(); ++i)
        		if(pl.getServerInfos().get(i) != null) {
        			objects.add(new BlockchainClientRunnable(i, pl.getServerInfos().get(i).getHost(), pl.getServerInfos().get(i).getPort(), message));
        			threads.add(new Thread(objects.getLast()));
        			threads.getLast().start();
        		}
        	Thread.sleep(2000);
        	for(Thread t : threads)
        		t.join();
        	for(BlockchainClientRunnable bccr : objects)
        		System.out.println(bccr.getReply());
    	}
    	catch(Exception e) {
    		System.err.println(e);
    	}
    	
    }

    public void multicast (ServerInfoList serverInfoList, ArrayList<Integer> serverIndices, String message) {
        // implement your code here
    	List<ServerInfo> list = serverInfoList.getServerInfos();
    	for(int index : serverIndices) {
    		if(index < 0 || index >= list.size())
    			continue;
    		if(list.get(index) != null) {
    			new Thread(new BlockchainClientRunnable(index, list.get(index).getHost(), list.get(index).getPort(), message)).start();
    		}
    	}
    }

    // implement any helper method here if you need any
}