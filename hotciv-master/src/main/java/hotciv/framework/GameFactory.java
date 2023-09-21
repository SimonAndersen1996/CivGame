package hotciv.framework;

public interface GameFactory {

    WinningStrategy createWinningStrategy();
    AgingStrategy createAgingStrategy();
    AttackingStrategy createAttackingStrategy();
    WorldStrategy createWorldGenerator();
    ActionStrategy createActionStrategy();
    ProductionStrategy createProductionStrategy();
    MoveStrategy createMoveStrategy();
}
