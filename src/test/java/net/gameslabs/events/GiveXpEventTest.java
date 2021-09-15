package net.gameslabs.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import net.gameslabs.api.Player;
import net.gameslabs.implem.PlayerImplem;
import net.gameslabs.model.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GiveXpEventTest {
  private GiveXpEvent sut;

  protected Player player;

  @BeforeEach
  public void setup() throws Exception {
    player = PlayerImplem.newPlayer("Bewgs");
  }

  @Test
  void giveXpEventShouldReturnCorrectSkill() {
    sut = new GiveXpEvent(player, Skill.CONSTRUCTION, 9);

    assertSame(Skill.CONSTRUCTION, sut.getSkill(),
        "Get skill should return correct skill");
  }

  @Test
  void giveXpEventReturnsCorrectXp() {
    sut = new GiveXpEvent(player, Skill.MINING, 112);

    sut.setXp(80);

    assertEquals(80, sut.getXp(),
        "XP getter and setter should function correctly");
  }

  @Test
  void giveXpEventShouldReturnCorrectPlayer() {
    sut = new GiveXpEvent(player, Skill.EXPLORATION, 600);

    assertSame(player, sut.getPlayer(),
        "Get player should return correct player");
  }
}
