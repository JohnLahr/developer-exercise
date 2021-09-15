package net.gameslabs.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component registry.
 */
public class ComponentRegistry {

  private final List<Component> componentList;

  public ComponentRegistry() {
    componentList = new ArrayList<>();
  }

  public void registerComponent(Component component) {
    componentList.add(component);
  }

  /**
   * Send an event.
   *
   * @param event Event to be sent
   */
  public void sendEvent(Event event) {
    List<EventMethod> methods = componentList.stream().map(component -> component
        .getEvents(event))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    for (EventMethod eventMethod : methods) {
      // Would require proper handling in a production environment
      eventMethod.onExecute(event);
      if (event.isCancelled()) {
        break;
      }
    }
  }

  /**
   * Loads all components.
   */
  public void load() {
    componentList.forEach(component -> component.initialize(this));
    componentList.forEach(Component::onLoad);
  }

  /**
   * Unloads all components.
   */
  public void unload() {
    componentList.forEach(Component::onUnload);
  }
}
