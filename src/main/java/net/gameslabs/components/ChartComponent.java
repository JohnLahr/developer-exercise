package net.gameslabs.components;

import java.util.HashMap;
import java.util.Map;
import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import net.gameslabs.events.GetPlayerLevel;
import net.gameslabs.events.GetXpForLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.PlayerStats;

/**
 * Chart Component.
 */
public class ChartComponent extends Component {
  private static final int XP_STEP = 50;

  private final Map<Player, PlayerStats> persistence;

  public ChartComponent() {
    persistence = new HashMap<>();
  }

  @Override
  public void onLoad() {
    registerEvent(GetXpForLevelEvent.class, this::onGetXpForLevel);
    registerEvent(GiveXpEvent.class, this::onGiveXpToPlayer);
    registerEvent(GetPlayerLevel.class, this::onGetPlayerLevel);
  }

  private void onGetXpForLevel(GetXpForLevelEvent event) {
    event.setXp(event.getLevel() * XP_STEP);
  }

  private void onGiveXpToPlayer(GiveXpEvent event) {
    getStats(event.getPlayer()).addXp(event.getSkill(), event.getXp());
  }

  private void onGetPlayerLevel(GetPlayerLevel event) {
    event.setLevel(getLevelFromXp(getStats(event.getPlayer()).getXp(event.getSkill())));
  }

  private int getLevelFromXp(int xp) {
    return 1 + Math.floorDiv(xp, XP_STEP);
  }

  private PlayerStats getStats(Player player) {
    return persistence.computeIfAbsent(player, p -> new PlayerStats());
  }

  @Override
  public void onUnload() {
    // Nothing to do
  }
}
