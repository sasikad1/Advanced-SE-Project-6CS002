package base;
import java.net.InetAddress;


public class ConnectionGenius {

  InetAddress serverAddress;
  
  public ConnectionGenius(InetAddress serverAddress) {
    this.serverAddress = serverAddress;
  }

  public void startGame() {
    downloadWebVersion();
    connectToWebService();
    completeInitialization();
  }
  
  public void downloadWebVersion(){
    System.out.println("Getting specialised web version.");
    System.out.println("Wait a couple of moments");  
  }
  
  public void connectToWebService() {
    System.out.println("Connecting");
  }
  
  public void completeInitialization(){
    System.out.println("Ready to play");
  }

}