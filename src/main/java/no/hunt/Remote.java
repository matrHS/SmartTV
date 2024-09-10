package no.hunt;

import static no.hunt.Server.TCP_PORT;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Remote {
  private static final String SERVER_HOST = "localhost";
  private Socket socket;
  private OutputStream outputStream;
  private ObjectOutputStream objectWriter;

  private InputStream inputStream;
  private ObjectInputStream objectInputStream;


  public static void main(String[] args) {
    Remote client = new Remote();
    client.run();
  }

  /**
   * Run the client
   */
  private void run() {
    if (connect()) {
      Scanner scanner = new Scanner(System.in);
      String command ="";
      while(!command.equals( "quit")) {
        command = scanner.nextLine();
        sendAndReceive(command);
      }

    }
  }

  /**
   * Connect to the server, and set up the input and output streams.
   * @return True if the connection is successfully established, false otherwise.
   */
  private boolean connect() {
    boolean success = false;
    try {
      socket = new Socket(SERVER_HOST, TCP_PORT);
      inputStream = socket.getInputStream();
      objectInputStream = new ObjectInputStream(inputStream);

      outputStream = socket.getOutputStream();
      objectWriter = new ObjectOutputStream(outputStream);
      System.out.println("connection established");
      success = true;
    } catch (IOException e) {
      System.out.println("connection failed");
    }
    return success;
  }

  /**
   * Send a command to the server and receive the response.
   * @param command The command to send.
   */
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

  /**
   * Receive a line from the server.
   * We assume that the connection is already established.
   * @return
   */
  private String receiveOneLineFromServer() {
    String response = null;
    try {
      response = objectInputStream.readObject().toString();
    } catch (IOException e) {
      System.err.println("Error reading from server: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      System.err.println("Error reading from server: " + e.getMessage());
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
      objectWriter.writeObject(message);
      sent = true;
    } catch (Exception e) {
      System.err.println("Error while sending the message: " + e.getMessage());
    }
    return sent;
  }

}
