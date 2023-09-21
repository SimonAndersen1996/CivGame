package hotciv.standard;

import hotciv.framework.Dice;

import java.util.Random;

public class FairDice implements Dice {
private Random random = new Random();

    @Override
    public int rollDie() {
        return random.nextInt(6) + 1;
    }
}
