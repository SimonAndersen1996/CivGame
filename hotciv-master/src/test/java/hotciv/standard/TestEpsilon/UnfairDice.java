package hotciv.standard.TestEpsilon;

import hotciv.framework.Dice;

public class UnfairDice implements Dice {
    private int result;

    @Override
    public int rollDie() {
        return result;
    }

    public void setRoll(int i) {
        result = i;
    }
}
