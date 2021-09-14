package net.gameslabs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerStatsTest {

  private PlayerStats playerStats;

  @BeforeEach
  public void setup() throws Exception {
    playerStats = new PlayerStats();
  }

  @Test
  void playerStatsSetsXpCorrectly() {
    playerStats.setXp(Skill.MINING, 40);
    playerStats.setXp(Skill.MINING, 98);
    playerStats.setXp(Skill.MINING, 12);

    assertEquals(12, playerStats.getXp(Skill.MINING),
        "Set XP correctly sets skill XP");
  }

  @Test
  void playerStatsAddsXpCorrectly() {
    playerStats.setXp(Skill.EXPLORATION, 600);
    playerStats.addXp(Skill.EXPLORATION, 80);

    assertEquals(680, playerStats.getXp(Skill.EXPLORATION),
        "Set XP correctly adds skill XP");
  }
}
