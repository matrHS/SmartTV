package no.hunt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

  /**
   * Run the server, and handle the client.
   * !!! MUST RETURN A RESPONSE TO THE CLIENT !!!
   */
  private void run() {
    this.tv = new SmartTv();
    if (startListening()) {
      Socket clientSocket = acceptNextClient();
      ClientHandler clientHandler = new ClientHandler(clientSocket);
      clientHandler.run();
      boolean quit = false;
        while (!quit) {
          if (tv.isOn()) {
            String command = clientHandler.handleClient().toLowerCase().strip();
            switch (command) {
              case "is on":
                this.isOn(clientHandler);
                break;
              case "get channels":
                clientHandler.sendToClient("The TV has " + tv.getChannels() + " channels");
                break;
              case "quit":
                quit = true;
                break;
                case "power off":
                tv.powerButton();
                clientHandler.sendToClient("The TV is now off");
                break;
              case "current channel":
                clientHandler.sendToClient("The current channel is " + tv.getCurrentChannel());
                break;

              default:
                clientHandler.sendToClient("Unknown command");
                break;
            }
          }else{
            String command = clientHandler.handleClient().toLowerCase().strip();
            if (command.equals("power")){
                tv.powerButton();
                clientHandler.sendToClient("The TV is now on");

            }else{
              clientHandler.sendToClient("The TV is off");

            }
      }
      }
    }
  }
private void isOn(ClientHandler handler) {
    if (tv.isOn()) {
      handler.sendToClient("The TV is on");
    } else {
      handler.sendToClient("The TV is off");
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
