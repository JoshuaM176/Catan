package luis.josh.catan.host.game.board;

public class Edge {

    EdgePlaceable placedItem = null;
    public boolean coast = true;

    public void setPlacedItem(EdgePlaceable item) {
        placedItem = item;
    } 

}
