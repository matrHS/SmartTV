package no.hunt;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/**
 * Represents the client handler
 */
public class ClientHandler {
  private final DatagramSocket clientSocket;
  private final DatagramPacket clientDatagram;
  private OutputStream outputStream;
  private ObjectOutputStream objectOutputStream;
  private InputStream inputStream;
  private ObjectInputStream objectInputStream;

  /**
   * Constructor for the ClientHandler
   *
   * @param clientSocket The client socket
   */
  public ClientHandler(DatagramSocket clientSocket, DatagramPacket packet) {
    this.clientSocket = clientSocket;
    this.clientDatagram = packet;
    System.out.println("UDP datagram received from " + clientDatagram.getAddress()
        + ", port " + clientDatagram.getPort());
  }

  public void run() {

    System.out.println(extractClientCommand() );

  }

  private String extractClientCommand() {
    return new String(clientDatagram.getData(), 0, clientDatagram.getLength(),
        StandardCharsets.UTF_8);
  }

  /**
   * Handle the client.
   *
   * @return The command from the client.
   */
  public String[] handleClient() {
    return recieveOneCommand();
  }

  /**
   * Receive one command from the client.
   *
   * @return The command from the client.
   */
  private String[] recieveOneCommand() {
    String clientCommand = "";
    String[] args = null;
    try {
      clientCommand = objectInputStream.readObject().toString();
      System.out.println(clientCommand);

      clientCommand = clientCommand.toLowerCase().strip();
      args = clientCommand.split(" ");
    } catch (IOException e) {
      System.out.println("Error reading from client: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return args;
  }

  /**
   * Send a response to the client.
   *
   * @param response The response to send.
   */
  public void sendToClient(String response) {
    try {
      objectOutputStream.writeObject(response);
    } catch (IOException e) {
      System.out.println("Error writing to client: " + e.getMessage());
    }
  }
}
