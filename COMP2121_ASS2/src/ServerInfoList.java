import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class ServerInfoList {

    ArrayList<ServerInfo> serverInfos;

    public ServerInfoList() {
        serverInfos = new ArrayList<>();
    }

    public void initialiseFromFile(String filename) {
        // implement your code here
    	try {
    		Scanner sc = new Scanner(new File(filename));
    		int num_servers = Integer.MAX_VALUE;
    		int l = 0;
    		for(int i = 0; sc.hasNextLine(); i++) {
    			String[] line = sc.nextLine().split("[=]");
    			if(line[0].equals("servers.num")) {
    				l = i;
    				num_servers = Integer.parseInt(line[1]);
    				
    			}
    		}
    		sc.close();
    		sc = new Scanner(new File(filename));
    		for(int i = 0; i <= l; ++i)
    			sc.nextLine();
    		
    		String key = null;
    		String last_host = null;
    		Integer port = null;
    		while(sc.hasNextLine()) {
    			String line = sc.nextLine();
    			String[] split = line.split("[.=]");
    			if(split.length != 3)
    				continue;
    			if(key == null)
    				key = split[0];
    			else if(!key.equals(split[0])) {
    				if(last_host == null || port == null)
    					serverInfos.add(null);
    				else {
    					if(serverInfos.size() < num_servers)
    						addServerInfo(new ServerInfo(last_host, port));
    				}
    					
    				key = split[0];
    				last_host = null;
    				port = null;
    			}
    			switch(split[1]) {
    				case "host":
    					last_host = split[2];
    					break;
    				case "port":
    					port = Integer.parseInt(split[2]);
    					if(port.intValue() < 1024 || port.intValue() > 65535)
    						port = null;
    					break;
    			}
    				
    		}
    	}
    	catch(Exception e) {
    		System.err.println(e);
    	}
    	
    	
    }

    public ArrayList<ServerInfo> getServerInfos() {
        return serverInfos;
    }

    public void setServerInfos(ArrayList<ServerInfo> serverInfos) {
        this.serverInfos = serverInfos;
    }

    public boolean addServerInfo(ServerInfo newServerInfo) { 
        // implement your code here
    	if(!validServer(newServerInfo))
    		return false;
    	return serverInfos.add(newServerInfo);
    }

    public boolean updateServerInfo(int index, ServerInfo newServerInfo) { 
        // implement your code here
    	if(index >= serverInfos.size() || index < 0 || !validServer(newServerInfo))
    		return false;
    	serverInfos.set(index, newServerInfo);
    	return true;
    }
    
    public boolean removeServerInfo(int index) { 
        // implement your code here
    	if(index >= serverInfos.size() || index < 0)
    		return false;
    	serverInfos.set(index, null);
    	return true;
    }

    public boolean clearServerInfo() { 
        // implement your code here
    	ListIterator it = serverInfos.listIterator();
    	boolean ret = false;
    	while(it.hasNext()) 
    		if(it.next() == null) {
    			it.remove();
    			ret = true;
    		}
    	return ret;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < serverInfos.size(); i++) {
            if (serverInfos.get(i) != null) {
                s += "Server" + i + ": " + serverInfos.get(i).getHost() + " " + serverInfos.get(i).getPort() + "\n";
            }
        }
        return s;
    }

    // implement any helper method here if you need any
    public boolean validServer(ServerInfo server) {
    	if(server == null)
    		return false;
    	if(server.getPort() < 1024 || server.getPort() > 65535)
    		return false;
    	if(server.getHost() == null)
    		return false;
		return true;
    }
}