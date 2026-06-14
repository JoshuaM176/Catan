package luis.josh.catan.host.game.gamepieces.cards;

public abstract class Card {

    /**
     * Returns the name of this card. Card name must be unique for each card type.
     */
    public abstract String getName();

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Card)) {
            return false;
        }
        if(!getName().equals(((Card)obj).getName())) {
            return false;
        }
        return true;
    }
}
