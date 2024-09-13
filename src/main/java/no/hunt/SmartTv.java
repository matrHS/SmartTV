package no.hunt;

/**
 * Represents a smart TV
 */
public class SmartTv {

  public static final int TCP_PORT = 1238;
  private boolean onOff = false;
  private final String[] channels = {"NRK1", "NRK2", "TV2", "TV Norge", "TV3", "Discovery", "National Geographic"};
  private int currentChannel = 0;

  /**
   * Turn the TV on or off
   */
  public void powerButton() {
    if (onOff) {
      onOff = false;
    } else {
      onOff = true;
    }
  }

  /**
   * Get the number of available channels on tv
   *
   * @return the number of available channels on tv
   */
  public int getChannels() {
    if (onOff) {
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
    if (onOff) {
      return currentChannel+1;
    } else {
      return -1;
    }
  }

  /**
   * Change the current channel on the TV
   */
  public void changeChannel(int channelNumber) {
    if (onOff) {
      if (channelNumber >= 0 && channelNumber < channels.length) {
        currentChannel = channelNumber;
      }
    }
  }

  /**
   * Increase the current channel on the TV by one channel
   */
  public void changeChannelUp() {
    if (onOff) {
      this.currentChannel += 1;
      if (currentChannel >= channels.length) {
        currentChannel = 0;
      }
    }
  }


  /**
   * Decrease the current channel on the TV by one channel
   */
  public void changeChannelDown() {
    if (onOff) {
      currentChannel--;
      if (currentChannel < 0) {
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
    if (onOff) {
      return channels[currentChannel];
    } else {
      return null;
    }
  }

  public boolean isOn() {
    return this.onOff;
  }
}
