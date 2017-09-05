import java.io.File;
import java.util.ArrayList;

public class ServerInfoList {

    ArrayList<ServerInfo> serverInfos;

    public ServerInfoList() {
        serverInfos = new ArrayList<>();
    }

    public void initialiseFromFile(String filename) {
        // implement your code here
    	File file = new File(filename);
    	
    }

    public ArrayList<ServerInfo> getServerInfos() {
        return serverInfos;
    }

    public void setServerInfos(ArrayList<ServerInfo> serverInfos) {
        this.serverInfos = serverInfos;
    }

    public boolean addServerInfo(ServerInfo newServerInfo) { 
        // implement your code here
    	return serverInfos.add(newServerInfo);
    }

    public boolean updateServerInfo(int index, ServerInfo newServerInfo) { 
        // implement your code here
    	serverInfos.set(index, newServerInfo);
    	return false;
    }
    
    public boolean removeServerInfo(int index) { 
        // implement your code here
    	serverInfos.remove(index);
    	return false;
    }

    public boolean clearServerInfo() { 
        // implement your code here
    	return false;
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
}