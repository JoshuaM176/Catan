package luis.josh.catan.host.game.board;

public class Edge {

    public EdgePlaceable placedItem = null;
    public boolean coast = true;

    public void setPlacedItem(EdgePlaceable item) {
        placedItem = item;
    } 

}
