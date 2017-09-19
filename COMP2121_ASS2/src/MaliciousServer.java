import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.io.PrintWriter;
import java.io.*;


public class MaliciousServer extends Thread {

  private Socket client;

  public MaliciousServer(Socket client) {
    this.client = client;
  }

  public void run() {
    try{
      handler(client.getInputStream(), client.getOutputStream());
    }
    catch(Exception e){}
  }

  public void handler(InputStream is, OutputStream os) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    PrintWriter pw = new PrintWriter(os, true);
    String line;
    Random rand = new Random();
    while((line = br.readLine()) != null){
      while(true){
        pw.println(rand.nextInt() + rand.nextInt() + rand.nextInt());
      }
    }
  }

  public static void main(String[] args) throws Exception {
    if(args.length != 1)
      return;
    ServerSocket ss = new ServerSocket(new Integer(args[0]));
    System.out.println(ss);
    while(true){
      new MaliciousServer(ss.accept()).start();
    }
  }

}
