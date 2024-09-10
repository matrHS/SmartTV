package no.hunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler {
  private BufferedReader socketReader;
  private InputStream inputStream;
  private ObjectInputStream objectInputStream;
  
  private final Socket clientSocket;
  
  public ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {

    System.out.println(clientSocket.getRemoteSocketAddress());
    try {
      socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      inputStream = clientSocket.getInputStream();
      objectInputStream = new ObjectInputStream(inputStream);
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
        clientCommand = objectInputStream.readObject().toString();
      System.out.println(clientCommand);
    } catch (IOException e) {
      System.out.println("Error reading from client: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return clientCommand;
  }
}
