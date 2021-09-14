package net.gameslabs.events;

import net.gameslabs.api.Event;

/**
 * Player Event to handle the XP during a level Event.
 */
public class GetXpForLevelEvent extends Event {
  private final int level;
  private int xp;

  public GetXpForLevelEvent(int level) {
    this.level = level;
    this.xp = 0;
  }

  public int getLevel() {
    return level;
  }

  public int getXp() {
    return xp;
  }

  public void setXp(int xp) {
    this.xp = xp;
  }
}
