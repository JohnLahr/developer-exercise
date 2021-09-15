package net.gameslabs.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetXpForLevelEventTest {
  private GetXpForLevelEvent sut;

  @Test
  void getXpForLevelEventShouldReturnCorrectLevel() {
    sut = new GetXpForLevelEvent(40);

    assertEquals(40, sut.getLevel(),
        "Get level should return correct level");
  }

  @Test
  void getXpForLevelEventDefaultsTo0Xp() {
    sut = new GetXpForLevelEvent(71);

    assertEquals(0, sut.getXp(),
        "Initial XP should be 0");
  }

  @Test
  void getXpForLevelEventReturnsCorrectXp() {
    sut = new GetXpForLevelEvent(28);

    sut.setXp(5000);

    assertEquals(5000, sut.getXp(),
        "XP getter and setter should function correctly");
  }
}
