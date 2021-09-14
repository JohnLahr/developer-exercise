package net.gameslabs.events;

import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;
import net.gameslabs.model.Skill;

/**
 * Player Event to handle the addition of XP to a Player skill.
 */
public class GiveXpEvent extends PlayerEvent {
  private final Skill skill;
  private int xp;

  /**
   * GiveXpEvent constructor with Player, skill, and XP.
   *
   * @param player Player to which the XP will be given
   * @param skill  Player skill in which the XP will be calculated
   * @param xp     Amount of XP to be given
   */
  public GiveXpEvent(Player player, Skill skill, int xp) {
    super(player);
    this.skill = skill;
    this.xp = xp;
  }

  public Skill getSkill() {
    return skill;
  }

  public int getXp() {
    return xp;
  }

  public void setXp(int xp) {
    this.xp = xp;
  }
}
