package event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventHandler {
    private static Map<String, Consumer<Double>> eventListeners = new HashMap<String, Consumer<Double>>();

    public static void register(String string, Consumer<Double> eventListener) {
        EventHandler.eventListeners.put(string, eventListener);
    }

    public static void ignore(String string) {
        EventHandler.eventListeners.remove(string);
    }

    public static void invoke(String name, Double pos) {
        if (EventHandler.eventListeners.containsKey(name)) {
            new Thread(() -> eventListeners.get(name).accept(pos)).start();
        }
    }

    public static void invoke(String name) {
        invoke(name, -1.0);
    }
}
