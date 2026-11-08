package luis.josh.catan.host.game.board.resources;

public enum Resource{
    STONE,
    BRICK,
    WHEAT,
    LOG,
    SHEEP,
    DESERT,
    ANY;

    public String toString() {
        return '"' + this.name() + '"';
    }
}