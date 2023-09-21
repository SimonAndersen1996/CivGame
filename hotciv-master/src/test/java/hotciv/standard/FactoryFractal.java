package hotciv.standard;

import hotciv.framework.*;

public class FactoryFractal implements GameFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return null;
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return null;
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return null;
    }

    @Override
    public WorldStrategy createWorldGenerator() {
        return new WorldFractal();
    }

    @Override
    public ActionStrategy createActionStrategy() {
        return null;
    }

    @Override
    public ProductionStrategy createProductionStrategy() {
        return null;
    }

    @Override
    public MoveStrategy createMoveStrategy() {
        return null;
    }
}
