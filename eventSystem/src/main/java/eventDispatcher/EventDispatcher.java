package eventDispatcher;

import listeners.GenericListener;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class EventDispatcher {

  private final Map<Class<? extends EventObject>, GenericListener<? extends EventObject>> listeners;

  public EventDispatcher() {
    listeners = new HashMap<>();
  }

  public <E extends EventObject> void registerListener(
      Class<E> eventType,
      GenericListener<E> listener) {
    listeners.put(eventType, listener);
  }

  @SuppressWarnings("unchecked")
  public <E extends EventObject> void dispatch(E event) {
    var listener = (GenericListener<E>) listeners.get(event.getClass());
    if (listener != null) {
      listener.onEvent(event);
    }
  }
}
