import java.io.File;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.HashMap;

public class ServerInfoList {

    ArrayList<ServerInfo> serverInfos;

    public ServerInfoList() {
        serverInfos = new ArrayList<>();
    }

    public void initialiseFromFile(String filename) {
        // implement your code here
    	HashMap<String, ServerInfo> key_sets = new HashMap<>(); 
    	try {
    		Scanner sc = new Scanner(new File(filename));
    		int servers_num = -1;
    		while(sc.hasNextLine()) {
    			String line = sc.nextLine();
    			if(line.startsWith("servers.num"))
    				servers_num = Integer.parseInt(line.split("[=]")[1]);
    		}
    		sc.close();
    		if(servers_num == -1)
    			return;
    		sc = new Scanner(new File(filename));
    		while(sc.hasNextLine()) {
    			String line = sc.nextLine();
    			String[] split = line.split("[.=]");
    			if(split.length != 3 || line.startsWith("servers.num"))
    				continue;
    			if(!key_sets.containsKey(split[0]))
    				key_sets.put(split[0], new ServerInfo());
    			switch(split[1]) {
    				case "host":	
    					key_sets.get(split[0]).setHost(split[2]);
    					break;
    				case "port":
    					key_sets.get(split[0]).setPort(Integer.parseInt(split[2]));
    					break;
    			}
    		}
    		ArrayList<ServerInfo> poo = new ArrayList<>();
    		for(int i = 0; i < servers_num; ++i)
    			poo.add(null);
    		for(String s : key_sets.keySet()) {
    			String in = s.replaceAll("[^0-9]", "");
    			int index = Integer.parseInt(in);
    			if(index >= servers_num)
    				continue;
    			if(ServerInfo.validServerInfo(key_sets.get(s)))
    				poo.set(index, key_sets.get(s));
    		}
    		setServerInfos(poo);
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