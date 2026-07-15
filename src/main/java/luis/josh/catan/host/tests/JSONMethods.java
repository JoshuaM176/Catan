package luis.josh.catan.host.tests;

import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.util.JSONUtil;

public class JSONMethods {

    public static JSONObject placeSettlement(int player, int row, int col, int vertex, int start) {
        return new JSONObject(
            Map.of(
                "action", "placeSettlement",
                "player", player,
                "data", new JSONObject(Map.of(
                    "tile", createTileVertexObject(row, col, vertex),
                    "start", start
                ))
            )
        );
    }

    public static JSONObject placeRoad(int player, int row, int col, int edge, int start) {
        return new JSONObject(
            Map.of(
                "action", "placeRoad",
                "player", player,
                "data", new JSONObject(Map.of(
                    "tile", new JSONObject(Map.of(
                        "row", row,
                        "col", col,
                        "edge", edge
                    )),
                    "start", start
                ))
            )
        );
    }

    public static JSONObject placeCity(int player, int row, int col, int vertex) {
        return new JSONObject(
            Map.of(
                "action", "placeCity",
                "player", player,
                "data", new JSONObject(Map.of(
                    "tile", createTileVertexObject(row, col, vertex)
                ))
            )
        );
    }

    public static JSONObject useDevCard(int player, String card) {
        return new JSONObject(Map.of(
            "action", "useDevelopmentCard",
            "player", player,
            "data", new JSONObject(Map.of(
                "card", card
            ))
        ));
    }

    public static JSONObject useMonopolyCard(int player, String resource) {
        return new JSONObject(Map.of(
            "action", "useDevelopmentCard",
            "player", player,
            "data", new JSONObject(Map.of(
                "card", "monopoly",
                "resource", resource
            ))
        ));
    }

    public static JSONObject useYearOfPlentyCard(int player, String resource, int count) {
        JSONObject resourceObject = new JSONObject(Map.of(
            "resource", resource,
            "count", count
        ));
        return new JSONObject(Map.of(
            "action", "useDevelopmentCard",
            "player", player,
            "data", new JSONObject(Map.of(
                "card", "yearOfPlenty2",
                "resources", JSONUtil.ArrayToJSON(new JSONObject[]{ resourceObject })
            ))
        ));
    }

    public static JSONObject moveRobber(int player, int sourceRow, int sourceCol, int targetRow, int targetCol, int targetPlayer) {
        return new JSONObject(Map.of(
            "action", "moveRobber",
            "player", player,
            "data", new JSONObject(Map.of(
                "sourceTile", createTileObject(sourceRow, sourceCol),
                "targetTile", createTileObject(targetRow, targetCol),
                "targetPlayer", targetPlayer
            ))
        ));
    }

    private static JSONObject createTileObject(int row, int col) {
        return new JSONObject(Map.of(
            "row", row,
            "col", col
        ));
    }

    private static JSONObject createTileVertexObject(int row, int col, int vertex) {
        return new JSONObject(Map.of(
            "row", row,
            "col", col,
            "vertex", vertex
        ));
    }

    private static JSONObject createTileEdgeObject(int row, int col, int edge) {
        return new JSONObject(Map.of(
            "row", row,
            "col", col,
            "edge", edge
        ));
    }
}
