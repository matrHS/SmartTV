package no.hunt;

public class SmartTv {

  private final String[] channels = {"NRK1", "NRK2", "TV2"};
  private boolean OnOff = false;
  private int currentChannel = 0;

  /**
   * Turn the TV on or off
   */
  public void powerButton() {
    if (OnOff) {
      OnOff = false;
    } else {
      OnOff = true;
    }
  }

  /**
   * Get the number of available channels on tv
   *
   * @return the number of available channels on tv
   */
  public int getChannels() {
    if (OnOff) {
      return channels.length;
    } else {
      return -1;
    }
  }

  /**
   * Get the current channel on the tv
   *
   * @return The current channel
   */
  public int getCurrentChannel() {
    if (OnOff) {
      return currentChannel;
    } else {
      return -1;
    }
  }

  /**
   * Change the current channel on the TV
   */
  public void changeChannel(int channelNumber) {
    if (OnOff) {
      if (channelNumber >= 0 && channelNumber < channels.length) {
        currentChannel = channelNumber;
      }
    }
  }

  /**
   * Increase the current channel on the TV by one channel
   */
  public void changeChannelUp() {
    if (OnOff) {
      currentChannel++;
      if (currentChannel >= channels.length) {
        currentChannel = 0;
      }
    }
  }

  /**
   * Decrease the current channel on the TV by one channel
   */
  public void changeChannelDown() {
    if (OnOff) {
      currentChannel--;
      if (currentChannel > 0) {
        currentChannel = channels.length - 1;
      }
    }
  }

  /**
   * Get the current channel name
   *
   * @return The current channel name
   */
  public String getCurrentChannelName() {
    if (OnOff) {
      return channels[currentChannel];
    } else {
      return null;
    }
  }
}
