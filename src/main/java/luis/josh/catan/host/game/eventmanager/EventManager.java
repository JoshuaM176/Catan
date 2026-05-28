package luis.josh.catan.host.game.eventmanager;

import java.util.Map;
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
    private Map<String, Function<JSONObject, Event>> eventMap;
    private static Logger logger = HostLogger.getLogger(EventManager.class);

    public EventManager(Board board, Player[] players, Map<String, Function<JSONObject, Event>> eventMap) {
        this.board = board;
        this.players = players;
        this.eventMap = eventMap;
    }
    
    public Event processEvent(JSONObject data) {
        String eventName = (String)data.get("event");
        Function<JSONObject, Event> eventFunction = eventMap.get(eventName);
        if(eventFunction == null) {
            return null;
        }
        Event event = eventFunction.apply(data);
        event.initialize(board, players);
        logger.info("Starting event: {}. Data: {}", eventName, data);
        return event;
    }
}
