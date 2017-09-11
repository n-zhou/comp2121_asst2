public class ServerInfo {

    private String host;
    private int port;

    public ServerInfo() {}
    
    public ServerInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public static boolean validServerInfo(ServerInfo server) {
    	if(server.port < 1024|| server.port > 65535)
    		return false;
    	if(server.host == null)
    		return false;
    	return true;
    }

    // implement any helper method here if you need any
}
