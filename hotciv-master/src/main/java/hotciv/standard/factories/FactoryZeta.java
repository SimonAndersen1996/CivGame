package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.winning.WinningBeta;
import hotciv.standard.winning.WinningEpsilon;
import hotciv.standard.winning.WinningZeta;

public class FactoryZeta implements GameFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new WinningZeta(new WinningBeta(), new WinningEpsilon());
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
