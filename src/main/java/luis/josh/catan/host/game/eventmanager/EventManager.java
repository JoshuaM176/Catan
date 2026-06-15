package luis.josh.catan.host.game.eventmanager;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.events.Event;
import luis.josh.catan.host.game.player.Player;

public class EventManager {

    private Board board;
    private Player[] players;
    private Consumer<JSONObject> messageQueue;
    private Map<String, Function<JSONObject, Event>> eventMap;
    private Queue<Event> eventQueue = new LinkedList<>();
    private static Logger logger = HostLogger.getLogger(EventManager.class);

    public EventManager(Board board, Player[] players, Consumer<JSONObject> messageQueue, Map<String, Function<JSONObject, Event>> eventMap) {
        this.board = board;
        this.players = players;
        this.messageQueue = messageQueue;
        this.eventMap = eventMap;
    }
    
    /**
     * Check if an event triggers a special event, if so create the event and add it to the eventQueue.
     * @param data The event data returned from an executed action.
     * @return True if a special event is added to the queue.
     */
    public boolean processEvent(JSONObject data) {
        String eventName = (String)data.get("event");
        Function<JSONObject, Event> eventFunction = eventMap.get(eventName);
        if(eventFunction == null) {
            return false;
        }
        JSONObject eventData = (JSONObject)data.get("data");
        Event event = eventFunction.apply(eventData);
        logger.info("Adding event to queue: {}. Data: {}", event.getName(), eventData);
        eventQueue.add(event);
        return true;
    }

    /**
     * Return the next special event in the eventQueue.
     * @return The next special event in the eventQueue.
     */
    public Event next() {
        if(eventQueue.size() == 0) {
            return null;
        }
        Event nextEvent = eventQueue.remove();
        messageQueue.accept(new JSONObject(Map.of(
            "event", "specialEventStarted",
            "data", new JSONObject(Map.of(
                "name", nextEvent.getName()
            ))
        )));
        nextEvent.initialize(board, players, messageQueue);
        if(nextEvent.isFinished()) {
            logger.info("Skipping event: {}.", nextEvent.getName());
            return next();
        }
        logger.info("Starting event: {}.", nextEvent.getName());
        return nextEvent;
    }
}
