package net.gameslabs.implem;

import java.util.Objects;
import net.gameslabs.api.Player;

/**
 * Player implementation.
 */
public class PlayerImplem implements Player {
  private final String id;
  private final String name;

  private PlayerImplem(String id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlayerImplem that = (PlayerImplem) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "(" + id + ", " + name + ")";
  }

  private static int players;

  public static Player newPlayer(String name) {
    return new PlayerImplem("player-" + (++players), name);
  }
}
