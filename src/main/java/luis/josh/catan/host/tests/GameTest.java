package luis.josh.catan.host.tests;

import java.util.Map;
import java.util.function.Consumer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.Monopoly;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.RoadBuilding;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.YearOfPlenty;
import luis.josh.catan.util.JSONUtil;

public class GameTest {


    public static void main(String[] args) {
        Logger logger = HostLogger.getLogger(GameTest.class);

        Consumer<JSONObject> messageQueue = (data) -> {
            logger.info("Sent message: {}", data);
        };
        TestGame testGame = new TestGame(messageQueue, 2);

        // Player 0 place first settlement
        JSONObject action = new JSONObject(
            Map.of(
                "action", "placeSettlement",
                "player", 0,
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
        
        // Player 0 place first road
        action = new JSONObject(
            Map.of(
                "action", "placeRoad",
                "player",0,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", 3,
                        "col", 3,
                        "edge",4
                    )),
                    "start", 1
                ))
            )
        );

        testGame.acceptData(action);

        // Player 1 place first settlement
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
                        "edge", 2
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
                        "row", 2,
                        "col", 1,
                        "vertex", 3
                    )),
                    "start",2
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
                        "row", 2,
                        "col", 1,
                        "edge",3
                    )),
                    "start", 2
                ))
            )
        );

        testGame.acceptData(action);

        // Player 0 place first settlement
        action = new JSONObject(
            Map.of(
                "action", "placeSettlement",
                "player", 0,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", 2,
                        "col", 2,
                        "vertex",1
                    )),
                    "start", 2
                ))
            )
        );

        testGame.acceptData(action);
        
        // Player 0 place first road
        action = new JSONObject(
            Map.of(
                "action", "placeRoad",
                "player",0,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", 2,
                        "col", 2,
                        "edge",0
                    )),
                    "start", 2
                ))
            )
        );

        testGame.acceptData(action);

        testGame.addDevCard(new Monopoly(testGame.players()), 0);

        action = new JSONObject(Map.of(
            "action", "useDevelopmentCard",
            "player", 0,
            "data", new JSONObject(Map.of(
                "card", "monopoly",
                "resource", "WHEAT"
            ))
        ));

        testGame.acceptData(action);

        testGame.addDevCard(new YearOfPlenty(), 0);

        action = new JSONObject(Map.of(
            "action", "useDevelopmentCard",
            "player", 0,
            "data", new JSONObject(Map.of(
                "card", "yearOfPlenty2",
                "resources", JSONUtil.ArrayToJSON(new JSONObject[]{
                    new JSONObject(Map.of(
                        "resource", "WHEAT",
                        "count", 2
                    ))
                })
            ))
        ));

        testGame.acceptData(action);

        testGame.addDevCard(new RoadBuilding(), 0);

        action = new JSONObject(Map.of(
            "action", "useDevelopmentCard",
            "player", 0,
            "data", new JSONObject(Map.of(
                "card", "roadBuilding2"
            ))
        ));

        testGame.acceptData(action);

        System.out.println(testGame);
    }
}
