package assignment.components;

import net.gameslabs.api.Component;
import net.gameslabs.events.GiveXpEvent;

/**
 * Component to handle XP boosting functionality.
 */
public class XpBoosterComponent extends Component {
  @Override
  public void onLoad() {
    registerEvent(GiveXpEvent.class, this::onGiveXp);
  }

  private void onGiveXp(GiveXpEvent event) {
    event.setXp(event.getXp() * event.getSkill().getMultiplier());
  }

  @Override
  public void onUnload() {
    // Do nothing
  }
}
