package no.hunt;

import static no.hunt.Server.TCP_PORT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Remote {
  private static final String SERVER_HOST = "localhost";
  private Socket socket;
  private BufferedReader socketReader;
  private ObjectOutputStream objectWriter;


  public static void main(String[] args) {
    Remote client = new Remote();
    client.run();
  }

  private void run() {
    if (connect()){
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      sendAndReceive("hello");
    }
  }

  private boolean connect() {
    boolean success = false;
    try {
      socket = new Socket(SERVER_HOST, TCP_PORT);
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      objectWriter = new ObjectOutputStream(socket.getOutputStream());
      System.out.println("connection established");
      success = true;
    } catch (IOException e) {
      System.out.println("connection failed");
    }
    return  success;
  }
  private void sendAndReceive(String command) {
    if (sendToServer(command)) {
      String response = receiveOneLineFromServer();
      if (response != null) {
        System.out.println("Server's response: " + response);
      }
    }
  }
  private void disconnect() {
    try {
      if (socket != null) {
        socket.close();
        System.out.println("Socket closed");
      } else {
        System.err.println("Can't close a socket which has not been open");
      }
    } catch (IOException e) {
      System.err.println("Could not close the socket: " + e.getMessage());
    }
  }
  private String receiveOneLineFromServer() {
    String response = null;
    try {
      response = socketReader.readLine();

    } catch (IOException e) {
      System.err.println("Error while receiving data from the server: " + e.getMessage());
    }
    return response;
  }

  /**
   * Send a message to the TCP server.
   * We assume that the connection is already established.
   *
   * @param message The message to send
   * @return True when the message is successfully sent, false on error.
   */
  private boolean sendToServer(String message) {
    boolean sent = false;
    try {
      PrintWriter writer = new PrintWriter(this.objectWriter);
      writer.println(message);
      //objectWriter.writeObject(message);
      sent = true;
    } catch (Exception e) {
      System.err.println("Error while sending the message: " + e.getMessage());
    }
    return sent;
  }

}
