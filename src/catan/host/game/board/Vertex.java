package catan.host.game.board;

import catan.host.game.board.resources.Resource;
import catan.host.game.board.resources.ResourceListener;

public class Vertex implements ResourceListener{

    VertexPlaceable placedItem = null;

    public void setPlacedItem(VertexPlaceable item) {
        placedItem = item;
    }

    @Override
    public void addResource(Resource resource) {
        if(placedItem != null) {
            placedItem.addResource(resource);
        }
    }
    
}
