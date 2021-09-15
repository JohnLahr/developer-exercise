package net.gameslabs.api;

/**
 * Event method interface.
 *
 * @param <T> Event type
 */
public interface EventMethod<T extends Event> {
  /**
   * Called when an event is executed.
   *
   * @param event the target event
   */
  void onExecute(T event);
}
