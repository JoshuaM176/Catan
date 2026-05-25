package luis.josh.catan.host.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;

import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.Game;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.host.game.actions.*;

public class GameTest {


    public static void main(String[] args) {
        Logger logger = HostLogger.getLogger(GameTest.class);

        Consumer<JSONObject> messageQueue = (data) -> {
            logger.info("Sent message: {}", data);
        };
        Game testGame = new Game(messageQueue, 2) {

            @Override
            public Board generateBoard() {
                return GenerateTestBoard.generateTestBoard();
            }

            @Override
            public HashMap<String, Action> generateActions(Board board, Player[] players) {
                HashMap<String, Action> actionMap = new HashMap<>();
                actionMap.put("placeSettlement", new PlaceSettlement(board, Map.of(Resource.BRICK, 1)));
                return actionMap;
            }};

        JSONObject data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 1,
                    "col": 1,
                    "vertex": 1
                },
                "start": 1
            }
                """
        );
        JSONObject action = new JSONObject();
        action.put("action", "placeSettlement");
        action.put("data", data);
        action.put("player", 1);
        
        testGame.acceptData(action);
    }
}
