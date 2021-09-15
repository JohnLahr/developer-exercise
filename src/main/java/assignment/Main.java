package assignment;

import assignment.components.FiremakingComponent;
import assignment.components.InventoryComponent;
import assignment.components.MiningComponent;
import assignment.components.XpBoosterComponent;
import net.gameslabs.model.Assignment;

/**
 * Main assignment Class.
 */
public class Main {
  /**
   * Main function.
   *
   * @param args Args to be passed to the function
   */
  public static void main(String[] args) {
    new Assignment(
        new FiremakingComponent(),
        new InventoryComponent(),
        new MiningComponent(),
        new XpBoosterComponent()
    ).run();
  }
}
