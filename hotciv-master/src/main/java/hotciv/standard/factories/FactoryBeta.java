package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.winning.WinningBeta;

public class FactoryBeta implements GameFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new WinningBeta();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new AgingBeta();
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
        return new ActionAlpha();
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
