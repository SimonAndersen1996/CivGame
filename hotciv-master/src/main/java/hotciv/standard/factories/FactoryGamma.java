package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.winning.WinningAlpha;

public class FactoryGamma implements GameFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new WinningAlpha();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new AgingAlpha();
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return new AttackAlpha();
    }

    @Override
    public WorldStrategy createWorldGenerator() {
        return new WorldAlpha();
    }

    @Override
    public ActionStrategy createActionStrategy() {
        return new ActionGamma();
    }

    @Override
    public ProductionStrategy createProductionStrategy() {
        return new ProductionAlpha();
    }

    @Override
    public MoveStrategy createMoveStrategy() {
        return new MoveAlpha();
    }
}
