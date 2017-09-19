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
        while (sc.hasNextLine()) {
            String message = sc.nextLine();
            // implement your code here
            String[] split = message.split("[|]");
            switch(split[0]) {
            	case "ls":
            		System.out.println(pl.toString());
            		break;
            	case "ad":
            		if(split.length != 3)
            			System.out.println("Failed\n");
            		else if(!isValidInt(split[2]))
            			System.out.println("Failed\n");
            		else if(pl.addServerInfo(new ServerInfo(split[1], Integer.parseInt(split[2]))))
            			System.out.println("Succeeded\n");
            		else
            			System.out.println("Failed\n");
            		break;
            	case "rm":
            		if(split.length != 2)
            			System.out.println("Failed\n");
            		else if(!isValidInt(split[1]))
            			System.out.println("Failed\n");
            		else if(pl.removeServerInfo(Integer.parseInt(split[1])))
            			System.out.println("Succeeded\n");
            		else
            			System.out.println("Failed\n");
            		break;
            	case "up":
            		if(split.length != 4)
            			System.out.println("Failed\n");
            		else if(!isValidInt(split[1]) || !isValidInt(split[3]))
            			System.out.println("Failed\n");
            		else if(pl.updateServerInfo(Integer.parseInt(split[1]), new ServerInfo(split[2], Integer.parseInt(split[3]))))
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
            		else if(split.length == 2) {
            			if(!isValidInt(split[1]))
            				System.out.println("Invalid index");
            			else if(Integer.parseInt(split[1]) >= pl.getServerInfos().size() || Integer.parseInt(split[1]) < 0)
            				System.out.println("Invalid index");
            			else {
            				System.out.println(split[1]);
            				System.out.println(pl.getServerInfos().size());
            				bcc.unicast(Integer.parseInt(split[1]), pl.getServerInfos().get(Integer.parseInt(split[1])), message);       
            			}
            				
            				
            		}
            			
            		else {
            			ArrayList<Integer> indices = new ArrayList<>();
            			for(int i = 1; i < split.length; ++i) {
            				if(isValidInt(split[1]))
            					indices.add(Integer.parseInt(split[i]));
            			}
            				
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
        	t.join(2100);
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
    		for(int i = 0; i < threads.size(); ++i) {
	    		threads.get(i).join();
	    		System.out.println(objects.get(i).getReply());
	    	}
    	}
    	catch(Exception e) {
    		System.err.println(e);
    	}
    	
    }

    public void multicast (ServerInfoList serverInfoList, ArrayList<Integer> serverIndices, String message) {
        // implement your code here
    	try {
	    	List<ServerInfo> list = serverInfoList.getServerInfos();
	    	
	    	LinkedList<BlockchainClientRunnable> runnables = new LinkedList<>();
	    	LinkedList<Thread> threads = new LinkedList<>();
	    	for(int index : serverIndices) {
	    		if(index < 0 || index >= list.size())
	    			continue;
	    		if(list.get(index) != null) {
	    			runnables.add(new BlockchainClientRunnable(index, list.get(index).getHost(), list.get(index).getPort(), message));
	    			threads.add(new Thread(runnables.getLast()));
	    			threads.getLast().start();
	    		}
	    	}
	    	for(int i = 0; i < threads.size(); ++i) {
	    		threads.get(i).join();
	    		System.out.println(runnables.get(i).getReply());
	    	}
    	}
    	catch(Exception e) {
    		
    	}
    	
    }
    
    public static boolean isValidInt(String s) {
    	try {
    		Integer.parseInt(s);
    		return true;
    	}
    	catch(Exception e) {
    		return false;
    	}
    }

    // implement any helper method here if you need any
}