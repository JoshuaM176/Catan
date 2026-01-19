package luis.josh.catan.host.game.board;

import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.resources.ResourceListener;

public class Vertex implements ResourceListener{

    VertexPlaceable placedItem = null;
    public Harbor harbor = null;

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
