package luis.josh.catan.host.game.gamepieces.cards;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CardDeck<C extends Card> {

    Map<C, Integer> cards;

    /**
     * Initializes an empty card deck.
     */
    public CardDeck() {
        cards = new HashMap<>();
    }

    /**
     * Initializes a card deck from the map of cards.
     * @param cards Maps card type to number of cards in the deck.
     */
    public CardDeck(Map<C, Integer> cards) {
        this.cards = new HashMap<>(cards);
    }

    /**
     * Add a card to the deck.
     * @param card Type of card to add.
     */
    public void addCard(C card) {
        if(cards.containsKey(card)) {
            cards.put(card, cards.get(card)+1);
        }
        else {
            cards.put(card, 1);
        }
    }

    /**
     * Add multiple of a card to the deck.
     * @param card Type of card to add.
     * @param amount Amount to add.
     */
    public void addCards(C card, int amount) {
        if(cards.containsKey(card)) {
            cards.put(card, cards.get(card)+amount);
        }
        else {
            cards.put(card, amount);
        }
    }

    /**
     * Removes a card from the deck.
     * @param card The type of card to remove.
     */
    public void subtractCard(C card) {
        cards.put(card, cards.get(card)-1);
    }

    /**
     * Removes multiple of a card from the deck.
     * @param card The type of card to remove.
     * @param amount The amount to remove.
     */
    public void subtractCards(C card, int amount) {
        if(amount == 0) {
            return;
        }
        cards.put(card, cards.get(card)-amount);
    }

    /**
     * Check if the deck contains at least one of the card passed.
     * @param card The card to check for.
     * @return True if the deck has the card.
     */
    public boolean hasCard(C card) {
        return cards.get(card) > 0;
    }

    /**
     * Check if the deck contains equal to or greater than the cards.
     * @param cards Map of type of card to amount to check for.
     * @return True if the deck has the cards.
     */
    public boolean hasCards(Map<C, Integer> cards) {
        for(C card: cards.keySet()) {
            if(this.cards.get(card) == null) {
                return false;
            }
            if(this.cards.get(card) < cards.get(card)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return Sum total of cards in the deck.
     */
    public int totalCards() {
        int totalCards = 0;
        for(int count: cards.values()) {
            totalCards += count;
        }
        return totalCards;
    }

    /**
     * Remove a random card from the deck.
     * @return The card removed.
     */
    public C drawCard() {
        Random random = new Random();
        int totalCards = totalCards();
        if(totalCards > 0){
            int randomInt = random.nextInt()%totalCards+ 1;
            for(C card: cards.keySet()) {
                randomInt -= cards.get(card);
                if(randomInt <= 0) {
                    subtractCard(card);
                    return card;
                }
            }
        }
        return null;
    }

    public boolean isEmpty() {
        if(totalCards() == 0) {
            return true;
        }
        return false;
    }

    public int cardCount(C card) {
        Integer count = cards.get(card);
        if(count == null) {
            return 0;
        }
        return count.intValue();
    }

    @Override
    public String toString() {
        String string = "";
        for(C card: cards.keySet()) {
            string += card.getName() + " :: " + cards.get(card) + " ";
        }
        return string;
    }
}
