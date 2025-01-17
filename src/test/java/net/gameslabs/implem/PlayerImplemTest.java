package net.gameslabs.implem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.gameslabs.api.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerImplemTest {
  private Player player;

  @BeforeEach
  public void setup() throws Exception {
    player = PlayerImplem.newPlayer("Bewgs");
  }

  @Test
  void playerImplemIdShouldBeReturnedCorrectly() {
    Pattern pattern = Pattern.compile("^player-\\d+$");
    Matcher matcher = pattern.matcher(player.getId());

    assertTrue(matcher.find(),
        "Player ID should be returned correctly");
  }

  @Test
  void playerImplemNameShouldBeReturnedCorrectly() {
    assertEquals("Bewgs", player.getName(),
        "Player name should be returned correctly");
  }

  @Test
  void playerImplemEqualsComparesBothIdAndName() {
    Player newPlayer = PlayerImplem.newPlayer("Bewgs");

    assertFalse(newPlayer.equals(player),
        "Player equals compares both ID and string");
    assertTrue(player.equals(player),
        "Player should equal itself");
    assertTrue(newPlayer.equals(newPlayer),
        "Player should equal itself");
  }

  @Test
  void playerImplemHashShouldGenerateConsistentValues() {
    Player newPlayer = PlayerImplem.newPlayer("NotBewgs");

    int playerHash = player.hashCode();
    int newPlayerHash = newPlayer.hashCode();

    assertEquals(playerHash, player.hashCode(),
        "Hashes of the same player should always be identical");
    assertEquals(newPlayerHash, newPlayer.hashCode(),
        "Hashes of the same player should always be identical");
    assertNotEquals(playerHash, newPlayerHash,
        "Hashes of different players should not be identical");
  }

  @Test
  void playerImplemToStringShouldBeFormattedCorrectly() {
    Pattern pattern = Pattern.compile("^\\(player-\\d+, Bewgs\\)$");
    Matcher matcher = pattern.matcher(player.toString());

    assertTrue(matcher.find(),
        "Player.toString() should be formatted correctly");
  }
}
