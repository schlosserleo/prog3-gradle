package listeners;

import java.util.EventListener;
import java.util.EventObject;

public interface GenericListener<genericEvent extends EventObject> extends EventListener {
    void onEvent(genericEvent event);
}
