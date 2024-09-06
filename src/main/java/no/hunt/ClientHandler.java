package no.hunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler {
  
  Socket clientSocket = new Socket();
  
  public ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {
    handleClient(clientSocket);
    System.out.println(clientSocket.getRemoteSocketAddress());
  }

  public void handleClient(Socket clientSocket) {
    String clientCommand = recieveOneCommand(clientSocket);
  }

  private String recieveOneCommand(Socket clientSocket) {
    String clientCommand = "";
    try (BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
      clientCommand = socketReader.readLine();
    } catch (IOException e) {
      System.out.println("Error reading from client: " + e.getMessage());
    }
    return clientCommand;
  }
}
