package net.gameslabs.model;

import java.util.Arrays;
import net.gameslabs.api.Component;
import net.gameslabs.api.ComponentRegistry;
import net.gameslabs.api.Player;
import net.gameslabs.components.ChartComponent;
import net.gameslabs.events.AddItemEvent;
import net.gameslabs.events.FiremakingEvent;
import net.gameslabs.events.GetPlayerInventoryEvent;
import net.gameslabs.events.GetPlayerLevel;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.events.MineEvent;
import net.gameslabs.events.RemoveItemEvent;
import net.gameslabs.implem.PlayerImplem;


/**
 * Main assignment Class.
 */
public class Assignment {

  protected final ComponentRegistry registry;
  private final Player mainPlayer;

  /**
   * Assignment constructor for one or more Components.
   *
   * @param myComponentsToAdd Components to be registered on startup
   */
  public Assignment(Component... myComponentsToAdd) {
    registry = new ComponentRegistry();
    Arrays.asList(myComponentsToAdd).forEach(registry::registerComponent);
    registry.registerComponent(new ChartComponent());
    registry.load();
    mainPlayer = PlayerImplem.newPlayer("MyPlayer");
  }

  /**
   * Main assignment method to run checks.
   */
  public final void run() {
    runCheckItems();
    runCheckSkills();
    runCheckMining();
    runCheckFiremaking();
    registry.unload();
  }

  private void runCheckItems() {
    registry.sendEvent(new AddItemEvent(mainPlayer, Item.COINS, 500));
    registry.sendEvent(new AddItemEvent(mainPlayer, Item.SHRIMP_BURNT));

    registry.sendEvent(new RemoveItemEvent(mainPlayer, Item.COINS, 150));
    registry.sendEvent(new RemoveItemEvent(mainPlayer, Item.SHRIMP_BURNT, 10));

    GetPlayerInventoryEvent event = new GetPlayerInventoryEvent(mainPlayer);
    registry.sendEvent(event);
    PlayerInventory playerInventory = event.getPlayerInventory();

    int coinsQuantity = playerInventory.getQuantity(Item.COINS);
    int burntShrimpQuantity = playerInventory.getQuantity(Item.SHRIMP_BURNT);
    log("Coins quantity       : ", coinsQuantity);
    log("Burnt Shrimp quantity: ", burntShrimpQuantity);

    if (!playerHasQuantity(Item.COINS, 350)) {
      throw new AssignmentFailed("The player should have 350 coins");
    }
    if (!playerHasQuantity(Item.SHRIMP_BURNT, 0)) {
      throw new AssignmentFailed("The player should not have any burnt shrimp");
    }
  }

  private void runCheckMining() {
    registry.sendEvent(new MineEvent(mainPlayer, Ore.COPPER));
    registry.sendEvent(new MineEvent(mainPlayer, Ore.IRON));
    registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.MINING, 410));
    registry.sendEvent(new MineEvent(mainPlayer, Ore.IRON));
    registry.sendEvent(new MineEvent(mainPlayer, Ore.TIN));
    registry.sendEvent(new MineEvent(mainPlayer, Ore.TIN));

    GetPlayerInventoryEvent event = new GetPlayerInventoryEvent(mainPlayer);
    registry.sendEvent(event);
    PlayerInventory playerInventory = event.getPlayerInventory();

    int copperQuantity = playerInventory.getQuantity(Item.ORE_COPPER);
    int ironQuantity = playerInventory.getQuantity(Item.ORE_IRON);
    int tinQuantity = playerInventory.getQuantity(Item.ORE_TIN);
    log("Copper quantity: ", copperQuantity);
    log("Iron quantity  : ", ironQuantity);
    log("Tin quantity   : ", tinQuantity);

    if (!playerHasQuantity(Item.ORE_COPPER, 1)) {
      throw new AssignmentFailed("The player should have 1 copper ore");
    }
    if (!playerHasQuantity(Item.ORE_IRON, 1)) {
      throw new AssignmentFailed("The player should have 1 iron ore");
    }
    if (!playerHasQuantity(Item.ORE_TIN, 2)) {
      throw new AssignmentFailed("The player should have 2 tin ore");
    }
  }

  private void runCheckFiremaking() {
    FiremakingEvent standardLogsFiremakingEvent = new FiremakingEvent(mainPlayer, Logs.STANDARD);
    registry.sendEvent(new AddItemEvent(mainPlayer, Item.LOGS_STANDARD, 1));
    registry.sendEvent(standardLogsFiremakingEvent);

    FiremakingEvent oakLogsFiremakingEvent = new FiremakingEvent(mainPlayer, Logs.OAK);
    registry.sendEvent(new AddItemEvent(mainPlayer, Item.LOGS_OAK, 4));
    registry.sendEvent(oakLogsFiremakingEvent);
    registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.FIREMAKING, 415));
    registry.sendEvent(oakLogsFiremakingEvent);

    FiremakingEvent willowLogsFiremakingEvent = new FiremakingEvent(mainPlayer, Logs.WILLOW);
    registry.sendEvent(new AddItemEvent(mainPlayer, Item.LOGS_WILLOW, 1));
    registry.sendEvent(willowLogsFiremakingEvent);

    GetPlayerInventoryEvent event = new GetPlayerInventoryEvent(mainPlayer);
    registry.sendEvent(event);
    PlayerInventory playerInventory = event.getPlayerInventory();

    int standardLogsQuantity = playerInventory.getQuantity(Item.LOGS_STANDARD);
    int oakLogsQuantity = playerInventory.getQuantity(Item.LOGS_OAK);
    int willowLogsQuantity = playerInventory.getQuantity(Item.LOGS_WILLOW);
    log("Standard logs quantity: ", standardLogsQuantity);
    log("Oak logs quantity     : ", oakLogsQuantity);
    log("Willow logs quantity  : ", willowLogsQuantity);

    int standardLogsBurnDuration = standardLogsFiremakingEvent.getBurnDuration();
    int oakLogsBurnDuration = oakLogsFiremakingEvent.getBurnDuration();
    int willowLogsBurnDuration = willowLogsFiremakingEvent.getBurnDuration();
    log("Standard logs burn duration: ", standardLogsBurnDuration);
    log("Oak logs burn duration: ", oakLogsBurnDuration);
    log("Willow logs burn duration: ", willowLogsBurnDuration);

    if (!playerHasQuantity(Item.LOGS_STANDARD, 0)) {
      throw new AssignmentFailed("The player should have 0 standard logs");
    }
    if (!playerHasQuantity(Item.LOGS_OAK, 3)) {
      throw new AssignmentFailed("The player should have 3 oak logs");
    }
    if (!playerHasQuantity(Item.LOGS_WILLOW, 1)) {
      throw new AssignmentFailed("The player should have 1 willow logs");
    }

    if (standardLogsBurnDuration < 30 && standardLogsBurnDuration > 45) {
      throw new AssignmentFailed("Standard logs should burn for between 30 and 45 seconds");
    }
    if (oakLogsBurnDuration < 30 && oakLogsBurnDuration > 45) {
      throw new AssignmentFailed("Oak logs should burn for between 60 and 90 seconds");
    }
    if (willowLogsBurnDuration < 30 && willowLogsBurnDuration > 45) {
      throw new AssignmentFailed("Willow logs should burn for between 90 and 135 seconds");
    }
  }

  private void runCheckSkills() {
    registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.CONSTRUCTION, 25));
    registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.EXPLORATION, 25));
    registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.FIREMAKING, 25));
    registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.MINING, 25));

    log("Construction level: ", getLevel(Skill.CONSTRUCTION));
    log("Exploration level : ", getLevel(Skill.EXPLORATION));
    log("Firemaking level  : ", getLevel(Skill.FIREMAKING));
    log("Mining level      : ", getLevel(Skill.MINING));

    if (getLevel(Skill.CONSTRUCTION) != 2) {
      throw new AssignmentFailed("Construction XP should be set to level 2");
    }
    if (getLevel(Skill.EXPLORATION) != 1) {
      throw new AssignmentFailed("Exploration XP should be set to level 1");
    }
    if (getLevel(Skill.FIREMAKING) != 1) {
      throw new AssignmentFailed("Firemaking XP should be set to level 1");
    }
    if (getLevel(Skill.MINING) != 1) {
      throw new AssignmentFailed("Mining XP should be set to level 1");
    }
  }

  private int getLevel(Skill skill) {
    GetPlayerLevel getPlayerLevel = new GetPlayerLevel(mainPlayer, skill);
    registry.sendEvent(getPlayerLevel);
    return getPlayerLevel.getLevel();
  }

  public void log(Object... arguments) {
    System.out.println(Arrays.asList(arguments));
  }

  private boolean playerHasQuantity(Item item, int quantity) {
    GetPlayerInventoryEvent event = new GetPlayerInventoryEvent(mainPlayer);
    registry.sendEvent(event);

    PlayerInventory playerInventory = event.getPlayerInventory();

    return playerInventory.getQuantity(item) == quantity;
  }
}
