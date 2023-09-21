package hotciv.standard;

import hotciv.framework.AgingStrategy;

public class AgingAlpha implements AgingStrategy {

    @Override
    public int passTime(int currentAge) {
        return currentAge + 100;
    }
}
