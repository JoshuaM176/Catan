package luis.josh.catan.client.game.util;

public class Coordinate {

    public int x;
    public int y;
    public int row;
    public int col;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        row = y;
        col = x;
    }
}
