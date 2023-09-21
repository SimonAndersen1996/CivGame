package hotciv.standard.factories;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.winning.WinningEpsilon;
import hotciv.standard.winning.WinningZeta;

public class FactorySemi implements GameFactory {

    @Override
    public WinningStrategy createWinningStrategy() {
        return new WinningEpsilon();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new AgingBeta();
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return new AttackEpsilon(new FairDice());
    }

    @Override
    public WorldStrategy createWorldGenerator() {
        return new WorldDelta();
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
