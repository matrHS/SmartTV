package no.hunt;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
      displayMenu();
      boolean quit = false;
        while (!quit) {
          String[] commands = clientHandler.handleClient();
          if (tv.isOn()) {
            quit = mainSwitch(commands, clientHandler);
          }else{
            if (commands[0].equals("power")){
                tv.powerButton();
                clientHandler.sendToClient("The TV is now on");
            }else{
              clientHandler.sendToClient("The TV is off");
            }
        }
      }
    }
  }


  private boolean mainSwitch(String[] commands, ClientHandler clientHandler) {
    boolean quit = false;
    switch (commands[0]) {
      case "channel":
        if (commands.length > 1) {
          this.channelSwitch(commands, clientHandler);
        } else {
          clientHandler.sendToClient("missing parameter for channel command");
        }
        break;
      case "quit":
        quit = true;
        break;
      case "power":
        tv.powerButton();
        clientHandler.sendToClient("The TV is now off");
        break;
      case "status":
        this.isOn(clientHandler);
        break;
      case "options":
        clientHandler.sendToClient(this.displayMenu());
        break;
      default:
        clientHandler.sendToClient("Unknown command");
        break;
    }
    return quit;
  }
  private void channelSwitch(String[] commands, ClientHandler clientHandler) {
    switch (commands[1]) {
      case "number":
        clientHandler.sendToClient("The TV has " + tv.getChannels() + " channels");
        break;
      case "name":
        clientHandler.sendToClient("The current channel is " +
            tv.getCurrentChannel() + " : " +
            tv.getCurrentChannelName());
        break;
      case "up":
        tv.changeChannelUp();
        clientHandler.sendToClient("The current channel is " +
            tv.getCurrentChannel() + " : " +
            tv.getCurrentChannelName());
        break;
      case "down":
        tv.changeChannelDown();
        clientHandler.sendToClient("The current channel is " +
            tv.getCurrentChannel() + " : " +
            tv.getCurrentChannelName());
        break;
      case "select":
        if(commands.length > 2) {
          int channel = Integer.parseInt(commands[2]);
          tv.changeChannel(channel);
            clientHandler.sendToClient("The current channel is " +
                tv.getCurrentChannel() + " : " +
                tv.getCurrentChannelName());
        }
        break;
      default:
        clientHandler.sendToClient(tv.getCurrentChannelName());
        break;
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

  /**
   * Display the menu
   */
  private String displayMenu() {
    StringBuilder menu = new StringBuilder();
    menu.append("\n");
    menu.append("+-------------------------------------------------+ \n");
    menu.append("|                        MENU                     | \n");
    menu.append("+-------------------------------------------------+ \n");
    menu.append("| power / power off                               | \n");
    menu.append("| channel name                                    | \n");
    menu.append("| channel number                                  | \n");
    menu.append("| channel up                                      | \n");
    menu.append("| channel down                                    | \n");
    menu.append("| channel select <number>                         | \n");
    menu.append("| status                                          | \n");
    menu.append("| options                                         | \n");
    menu.append("| quit                                            | \n");
    menu.append("+-------------------------------------------------+ \n");
    return menu.toString();
  }

}
