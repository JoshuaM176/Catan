package luis.josh.catan.client.game.eventmanager;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.client.ClientLogger;
import luis.josh.catan.client.game.events.Event;

public class EventManager {
    
    private Map<String, Event> eventMap;
    private static final Logger logger = ClientLogger.getLogger(EventManager.class);

    /**
     * @param eventMap Map the event names to corresponding Event object.
     */
    public EventManager(Map<String, Event> eventMap) {
        this.eventMap = new HashMap<>(eventMap);
    }

    public EventManager(Event[] events) {
        this.eventMap = new HashMap<>();
        for(Event action: events) {
            addEvent(action);
        }
    }

    /**
     * Add an event automatically setting name to the action's className as camelCase.
     * @param event Event to execute when the name is used.
     */
    public void addEvent(Event event) {
        String name = event.getClass().getSimpleName();
        char firstLetter = name.charAt(0);
        String camelCaseName = String.valueOf(firstLetter).toLowerCase() + name.substring(1);
        addEvent(camelCaseName, event);
    }

    /**
     * Adds an event to the map of available actions.
     * @param name Name that will be used to reference the action.
     * @param event Event to execute when that name is used.
     */
    public void addEvent(String name, Event event) {
        eventMap.put(name, event);
    }

    /**
     * Executes the event from the given data. Data should be formatted as
     * {"event": "eventName", "data": {eventData}}
     * @param data The data containing information about the action to execute.
     */
    public void executeEvent(JSONObject data) {
        String eventName = (String)data.get("event");
        logger.info("Executing event {}", eventName);
        Event event = eventMap.get(eventName);
        JSONObject actionData = (JSONObject)data.get("data");
        event.execute(actionData);
    }
}
