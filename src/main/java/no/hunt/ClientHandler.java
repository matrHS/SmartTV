package no.hunt;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Represents the client handler
 */
public class ClientHandler {
  private final Socket clientSocket;
  private OutputStream outputStream;
  private ObjectOutputStream objectOutputStream;
  private InputStream inputStream;
  private ObjectInputStream objectInputStream;

  /**
   * Constructor for the ClientHandler
   * @param clientSocket
   */
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
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Handle the client.
   * @return The command from the client.
   */
  public String handleClient() {
    return recieveOneCommand();
  }

  /**
   * Recieve one command from the client.
   * @return
   */
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

  /**
   * Send a response to the client.
   * @param response
   */
  private void sendToClient(String response) {
    try {
      objectOutputStream.writeObject(response);
    } catch (IOException e) {
      System.out.println("Error writing to client: " + e.getMessage());
    }
  }
}
