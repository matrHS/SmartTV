package no.hunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler {
  private OutputStream outputStream;
  private ObjectOutputStream objectOutputStream;
  private InputStream inputStream;
  private ObjectInputStream objectInputStream;
  private final Socket clientSocket;
  
  public ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {

    System.out.println(clientSocket.getRemoteSocketAddress());
    try {
      outputStream = clientSocket.getOutputStream();
      objectOutputStream = new ObjectOutputStream(outputStream);
      
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
      sendToClient("response");
    } catch (IOException e) {
      System.out.println("Error reading from client: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return clientCommand;
  }
  
  private void sendToClient(String response) {
    try {
      objectOutputStream.writeObject(response);
    } catch (IOException e) {
      System.out.println("Error writing to client: " + e.getMessage());
    }
  }
}
