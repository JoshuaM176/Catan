package luis.josh.catan.host.game.dice;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class Dice {

    private List<DiceRollListener> listeners = new ArrayList<DiceRollListener>();

    public void addListener(DiceRollListener listener) {
        listeners.add(listener);
    }

    public void removeListener(DiceRollListener listener) {
        listeners.remove(listener);
    }

    public int rollDice() {
        Random random = new Random();
        int num1 = Math.abs(random.nextInt() % 6) + 1;
        int num2 = Math.abs(random.nextInt() % 6) + 1;
        for(DiceRollListener listener : listeners) {
            listener.NumberRolled(num1 + num2);
        }
        return num1 + num2;
    }

}
