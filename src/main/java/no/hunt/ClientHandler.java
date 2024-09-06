package no.hunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler {
  private ObjectInputStream socketReader;
  
  private final Socket clientSocket;
  
  public ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {

    System.out.println(clientSocket.getRemoteSocketAddress());
    try {
      socketReader = new ObjectInputStream(clientSocket.getInputStream());
      handleClient();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public void handleClient() {
    String clientCommand = recieveOneCommand();
  }

  private String recieveOneCommand() {
    String clientCommand = "";
    try {
      System.out.println("write here");
        clientCommand = (String) socketReader.readObject();
      System.out.println(clientCommand);
    } catch (IOException e) {
      System.out.println("Error reading from client: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return clientCommand;
  }
}
