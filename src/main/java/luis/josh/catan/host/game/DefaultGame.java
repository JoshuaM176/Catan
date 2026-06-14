package luis.josh.catan.host.game;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.*;
import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.harborassigner.DefaultHarborAssigner;
import luis.josh.catan.host.game.board.tile.harborassigner.HarborAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.DefaultNumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.tilecreator.DefaultTileCreator;
import luis.josh.catan.host.game.board.tile.tilecreator.TileCreator;
import luis.josh.catan.host.game.events.DiscardEvent;
import luis.josh.catan.host.game.events.Event;
import luis.josh.catan.host.game.events.MoveRobberEvent;
import luis.josh.catan.host.game.events.RoadBuildingEvent;
import luis.josh.catan.host.game.events.SetupEvent;
import luis.josh.catan.host.game.gamepieces.cards.CardDeck;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.DevelopmentCard;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.Knight;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.Monopoly;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.RoadBuilding;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.VictoryPoint;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.YearOfPlenty;
import luis.josh.catan.host.game.player.Player;

public class DefaultGame extends Game{
  
    public DefaultGame(Consumer<JSONObject> messageQueue, int players) {
        super(messageQueue, players);
    }

    @Override
    protected void startGame() {
        processEvent(EventResponses.eventResponse("setupTrigger", "none", new JSONObject()));
    }
    
    @Override
    protected int[][] getTilePattern() {
        return new int[][] {
            {0,1,1,1,0},
            {1,1,1,1,0},
            {1,1,1,1,1},
            {1,1,1,1,0},
            {0,1,1,1,0}
        };
    }

    @Override
    protected NumberTokenAssigner getNumberTokenAssigner() {
        int[] numberTokens = new int[] {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
        return new DefaultNumberTokenAssigner(numberTokens);
    }

    @Override
    protected TileCreator getTileCreator() {
        return new DefaultTileCreator(Map.of(
            Resource.STONE, 3,
            Resource.BRICK, 3,
            Resource.WHEAT, 4,
            Resource.LOG, 4,
            Resource.SHEEP, 4,
            Resource.DESERT, 1
        ));
    }
    
    @Override
    protected HarborAssigner getHarborAssigner() {
        return new DefaultHarborAssigner(9,
            Map.of(
                Resource.STONE, 1,
                Resource.BRICK, 1,
                Resource.WHEAT, 1,
                Resource.LOG, 1,
                Resource.SHEEP, 1,
                Resource.ANY, 4
            )
        );
    }

    protected Action[] generateActions(Board board, Player[] players) {
        return new Action[]{
            new PlaceCity(board, Map.of(
                Resource.STONE, 3,
                Resource.WHEAT, 2
            )),
            new PlaceRoad(board, Map.of(
                Resource.BRICK, 1,
                Resource.LOG, 1
            )),
            new PlaceSettlement(board, Map.of(
                Resource.BRICK, 1,
                Resource.LOG, 1,
                Resource.SHEEP, 1,
                Resource.WHEAT, 1
            )),
            new RollDice(board),
            new PurchaseDevelopmentCard(
                generateDevCards(),
                Map.of(
                    Resource.SHEEP, 1,
                    Resource.STONE, 1,
                    Resource.WHEAT, 1
                )
            ),
            new UseDevelopmentCard(new DevelopmentCard[]{
                new Knight(),
                new Monopoly(players)
            })
        };
    }

    private CardDeck<DevelopmentCard> generateDevCards() {
        CardDeck<DevelopmentCard> cardDeck = new CardDeck<>(Map.of(
            new Knight(), 14,
            new Monopoly(players), 2,
            new VictoryPoint(), 5,
            new RoadBuilding(), 2,
            new YearOfPlenty(), 2
        ));
        return cardDeck;
    }
    
    @Override
    protected Map<String, Function<JSONObject, Event>> generateEvents() {
        return Map.of(
            "moveRobberTrigger", data -> new MoveRobberEvent(data, turn),
            "discardTrigger", data -> new DiscardEvent(data),
            "setupTrigger", data -> new SetupEvent(() -> prevTurn(), () -> nextTurn(), () -> turn),
            "roadBuildingTrigger", data -> new RoadBuildingEvent(data, turn)
        );
    }
}
