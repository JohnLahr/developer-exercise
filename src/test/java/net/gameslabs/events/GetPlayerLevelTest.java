package net.gameslabs.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import net.gameslabs.api.Player;
import net.gameslabs.implem.PlayerImplem;
import net.gameslabs.model.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetPlayerLevelTest {
  private GetPlayerLevel sut;

  protected Player player;

  @BeforeEach
  public void setup() throws Exception {
    player = PlayerImplem.newPlayer("Bewgs");
  }

  @Test
  void getPlayerLevelReturnsCorrectSkill() {
    sut = new GetPlayerLevel(player, Skill.CONSTRUCTION);

    assertSame(Skill.CONSTRUCTION, sut.getSkill(),
        "Get skill should return the same skill that was assigned");
  }

  @Test
  void getPlayerLevelReturnsCorrectLevel() {
    sut = new GetPlayerLevel(player, Skill.FIREMAKING);

    sut.setLevel(99);

    assertEquals(99, sut.getLevel(),
        "Level getter and setter should function correctly");
  }

  @Test
  void getPlayerLevelShouldReturnCorrectPlayer() {
    sut = new GetPlayerLevel(player, Skill.MINING);

    assertSame(player, sut.getPlayer(),
        "Get player should return correct player");
  }
}
