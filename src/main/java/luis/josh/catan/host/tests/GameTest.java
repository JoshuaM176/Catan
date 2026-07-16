package luis.josh.catan.host.tests;

import java.util.function.Consumer;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.Knight;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.Monopoly;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.RoadBuilding;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.YearOfPlenty;

public class GameTest {

    public static void main(String[] args) {
        Logger logger = HostLogger.getLogger(GameTest.class);

        Consumer<JSONObject> messageQueue = (data) -> {
            logger.info("Sent message: {}", data);
        };
        TestGame testGame = new TestGame(messageQueue, 2);

        JSONObject action = JSONMethods.placeSettlement(0,3, 3, 4, 1);
        testGame.acceptData(action);
        
        action = JSONMethods.placeRoad(0, 3, 3, 4, 1);
        testGame.acceptData(action);

        action = JSONMethods.placeSettlement(1,1,1, 3, 1);
        testGame.acceptData(action);
        
        action = JSONMethods.placeRoad(1,1, 1,2, 1);
        testGame.acceptData(action);
        
        action = JSONMethods.placeSettlement(1, 2, 1, 3, 2);
        testGame.acceptData(action);

        action = JSONMethods.placeRoad(1, 2, 1, 3, 2);
        testGame.acceptData(action);

        action = JSONMethods.placeSettlement(0,2, 2, 1, 2);
        testGame.acceptData(action);
        
        action = JSONMethods.placeRoad(0, 2, 2, 0, 2);
        testGame.acceptData(action);

        testGame.addDevCard(new Monopoly(testGame.players()), 0);

        action = JSONMethods.useMonopolyCard(0, "WHEAT");
        testGame.acceptData(action);

        testGame.addDevCard(new YearOfPlenty(), 0);

        action = JSONMethods.useYearOfPlentyCard(0, "WHEAT", 2);
        testGame.acceptData(action);

        testGame.addDevCard(new RoadBuilding(), 0);
        
        action = JSONMethods.useDevCard(0, "roadBuilding2");
        testGame.acceptData(action);

        action = JSONMethods.placeRoad(1, 3, 3, 3, 0);
        testGame.acceptData(action);
        action = JSONMethods.placeRoad(0, 3, 3, 3, 0);
        testGame.acceptData(action);
        action = JSONMethods.placeRoad(0, 3, 3, 5, 0);
        testGame.acceptData(action);

        testGame.addDevCard(new RoadBuilding(), 0);
        
        action = JSONMethods.useDevCard(0, "roadBuilding2");
        testGame.acceptData(action);

        action = JSONMethods.placeRoad(0, 4, 3, 1, 0);
        testGame.acceptData(action);

        action = JSONMethods.placeRoad(0, 4, 3, 2, 0);
        testGame.acceptData(action);

        testGame.addDevCard((new Knight()), 0);
        testGame.addDevCard((new Knight()), 0);
        testGame.addDevCard((new Knight()), 0);

        JSONObject robberDevCardAction = JSONMethods.useDevCard(0, "knight");
        testGame.setRobber(3, 3);

        testGame.acceptData(robberDevCardAction);
        testGame.acceptData(JSONMethods.moveRobber(0, 3, 3, 1, 1, 1));
        testGame.acceptData(robberDevCardAction);
        testGame.acceptData(JSONMethods.moveRobber(0, 1, 1, 2, 1, 1));
        testGame.acceptData(robberDevCardAction);
        testGame.acceptData(JSONMethods.moveRobber(0, 2, 1, 1, 1, 1));

        System.out.println(testGame);
    }
}
