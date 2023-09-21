package hotciv.standard;

import hotciv.framework.AttackingStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;

public class AttackAlpha implements AttackingStrategy {

    @Override
    public boolean isSuccessfulAttack(Position from, Position to, Game game) {
        return true;
    }

    @Override
    public int getAllies(Position unitPosition, Game game) {
        return 0;
    }

    @Override
    public int getTerrainBonus(Position p, Game game) {
        return 0;
    }

    @Override
    public int computeStatValue(Position unitPosition, boolean isAttacking, Game game) {
        return 0;
    }

}
