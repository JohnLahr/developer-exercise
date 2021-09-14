package net.gameslabs.events;

import java.util.Random;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;
import net.gameslabs.model.Logs;

/**
 * Player Event to handle a player burning logs with the Firemaking skill.
 */
public class FiremakingEvent extends PlayerEvent {
  private final int burnDuration;
  private final Logs logs;
  private final Random random = new Random();

  /**
   * FiremakingEvent constructor with Player and Logs context.
   *
   * @param player Player to which the FiremakingEvent is scoped.
   * @param logs   Logs to which the FiremakingEvent is scoped.
   */
  public FiremakingEvent(Player player, Logs logs) {
    super(player);
    this.logs = logs;
    this.burnDuration = calculateBurnDuration(logs);
  }

  /**
   * Returns the Logs specified in the FiremakingEvent.
   *
   * @return Logs specified in the FiremakingEvent.
   */
  public Logs getLogs() {
    return logs;
  }

  /**
   * Returns the FiremakingEvent's burn duration.
   *
   * @return FiremakingEvent burn duration.
   */
  public int getBurnDuration() {
    return burnDuration;
  }

  private int calculateBurnDuration(Logs logs) {
    int base = 30;
    int multiplier = logs.getBurnDurationMultiplier();
    int randomBurn = random.nextInt(15);

    return (base + randomBurn) * multiplier;
  }
}
