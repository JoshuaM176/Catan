package luis.josh.catan.host.game.board.tile;

import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Vertex;
import luis.josh.catan.host.game.board.resources.Resource;

public class ResourceTile extends Tile {

    public int numberToken;

    public ResourceTile(Resource resource) {
        super(resource);
    }

    public void assignNumberToken(int numberToken) {
        this.numberToken = numberToken;
    }

    @Override
    public void NumberRolled(int rolledNumber) {
        if(numberToken == rolledNumber && robber == null) {
            for(Vertex vertex : vertices) {
                vertex.addResource(resource);
            }
        }
    }

    @Override
    public JSONObject toJsonObject() {
        return new JSONObject(Map.of(
            "resource", resource,
            "numberToken", numberToken
        ));
    }

    public String toString() {
        return resource.toString() + " :: " + numberToken;
    }
}
