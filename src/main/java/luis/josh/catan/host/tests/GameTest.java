package luis.josh.catan.host.tests;

import java.util.Map;
import java.util.function.Consumer;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.Game;

public class GameTest {


    public static void main(String[] args) {
        Logger logger = HostLogger.getLogger(GameTest.class);

        Consumer<JSONObject> messageQueue = (data) -> {
            logger.info("Sent message: {}", data);
        };
        Game testGame = new Game(messageQueue, 2) {

        };

        // Player 1 place first settlement
        JSONObject action = new JSONObject(
            Map.of(
                "action", "placeSettlement",
                "player", 1,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", 3,
                        "col", 3,
                        "vertex",4
                    )),
                    "start", 1
                ))
            )
        );

        testGame.acceptData(action);
        
        // Player 1 place first road
        action = new JSONObject(
            Map.of(
                "action", "placeRoad",
                "player", 1,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", 1,
                        "col", 1,
                        "edge", 1
                    )),
                    "start", 1
                ))
            )
        );

        testGame.acceptData(action);

        // Player 1 place second settlement
        action = new JSONObject(
            Map.of(
                "action", "placeSettlement",
                "player", 1,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", 1,
                        "col", 1,
                        "vertex", 3
                    )),
                    "start", 2
                ))
            )
        );
        
        testGame.acceptData(action);
        
        // Player 1 place second road
        action = new JSONObject(
            Map.of(
                "action", "placeRoad",
                "player", 1,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", 1,
                        "col", 1,
                        "edge", 2
                    )),
                    "start", 2
                ))
            )
        );

        testGame.acceptData(action);
        
        // Player 1 try placing another road
        action = new JSONObject(
            Map.of(
                "action", "placeRoad",
                "player", 1,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", 1,
                        "col", 1,
                        "edge", 3
                    )),
                    "start", 0
                ))
            )
        );
        
        testGame.acceptData(action);

        // Player 1 rolls the dice
        action = new JSONObject(
            Map.of(
                "action", "rollDice",
                "player", 1
            )
        );

        testGame.acceptData(action);

    }
}
