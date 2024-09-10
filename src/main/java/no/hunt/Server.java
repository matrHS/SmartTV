package no.hunt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;

/**
 * Represents the server
 */
public class Server {
  public static final int TCP_PORT = 1234;
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private SmartTv tv;

  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }


  private void run() {
    this.tv = new SmartTv();
    if (startListening()) {
      Socket clientSocket = acceptNextClient();
      ClientHandler clientHandler = new ClientHandler(clientSocket);
      clientHandler.run();
      boolean quit = false;
      while (!quit) {
        String command = clientHandler.handleClient();

        switch (command) {
          case "power":
            tv.powerButton();
            System.out.println("Power button pressed");
            break;
          case "quit":
            quit = true;
            break;
        }
      }
    }
  }

  /**
   * Accepts the connecting client.
   *
   * @return The connected socket.
   */
  private Socket acceptNextClient() {
    try {
      clientSocket = serverSocket.accept();
    } catch (IOException e) {
      System.out.println("Error accepting client: " + e.getMessage());
    }
    return clientSocket;
  }

  /**
   * Open a TCP listening socket.
   *
   * @return true on success, false on error
   */
  private boolean startListening() {
    boolean success = false;
    try {
      serverSocket = new ServerSocket(TCP_PORT);
      success = true;
    } catch (IOException e) {
      System.out.println("Could not listen on port " + TCP_PORT + " " + e.getMessage());
    }
    return success;
  }

}
