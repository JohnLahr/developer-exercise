package net.gameslabs.events;

import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;
import net.gameslabs.model.Skill;

/**
 * Player Event to handle the retrieval of a Player's level.
 */
public class GetPlayerLevel extends PlayerEvent {
  private final Skill skill;
  private int level;

  public GetPlayerLevel(Player player, Skill skill) {
    super(player);
    this.skill = skill;
  }

  public Skill getSkill() {
    return skill;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
