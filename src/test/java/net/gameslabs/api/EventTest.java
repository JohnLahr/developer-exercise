package net.gameslabs.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventTest {
  private Event sut;

  @Test
  void eventCancelledStateDefaultsToFalse() {
    sut = new Event();

    assertFalse(sut.isCancelled(),
        "Event cancelled state should default to false");
  }

  @Test
  void eventReturnsCorrectCancelledState() {
    sut = new Event();

    sut.setCancelled(true);
    assertTrue(sut.isCancelled(),
        "Event cancelled state should correctly set to true from false");

    sut.setCancelled(false);
    assertFalse(sut.isCancelled(),
        "Event cancelled state should correctly set to false from true");
  }
}
