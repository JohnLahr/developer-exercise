package net.gameslabs.events;

import static org.junit.jupiter.api.Assertions.assertSame;

import net.gameslabs.api.Player;
import net.gameslabs.implem.PlayerImplem;
import net.gameslabs.model.Ore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MineEventTest {
  private MineEvent sut;

  protected Player player;

  @BeforeEach
  public void setup() throws Exception {
    player = PlayerImplem.newPlayer("Bewgs");
  }

  @Test
  void mineEventShouldReturnCorrectOre() {
    sut = new MineEvent(player, Ore.IRON);

    assertSame(Ore.IRON, sut.getOre(),
        "Get ore should return correct ore");
  }

  @Test
  void mineEventShouldReturnCorrectPlayer() {
    sut = new MineEvent(player, Ore.COPPER);

    assertSame(player, sut.getPlayer(),
        "Get player should return correct player");
  }
}
