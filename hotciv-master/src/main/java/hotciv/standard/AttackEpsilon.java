package hotciv.standard;

import hotciv.Utility.Utility;
import hotciv.framework.*;

import java.util.*;

public class AttackEpsilon implements AttackingStrategy {
    private Dice dice;

    public AttackEpsilon(Dice d) {
        dice = d;
    }

    @Override
    public boolean isSuccessfulAttack(Position from, Position to, Game game) {
        int die = dice.rollDie();
        int die2 = dice.rollDie();
        int attack = computeStatValue(from,true,game);
        int defense = computeStatValue(to,false,game);
        if (attack*die > defense*die2){
            return true;
        }
        else {
            game.removeUnitAt(from);
            return false;
        }
    }

    @Override
    public int getAllies(Position unitPosition, Game game) {
        int allies = 0;
        for(Position p : Utility.get8neighborhoodOf(unitPosition)){
            boolean emptyTile = game.getUnitAt(p) == null;

            if (!emptyTile) {
                boolean isEnemyUnit = game.getUnitAt(p).getOwner() != game.getPlayerInTurn();

                if (!isEnemyUnit) {
                    allies++;
                }
            }
        }
        return allies;
    }

    @Override
    public int getTerrainBonus(Position p, Game game) {
        if (game.getCityAt(p) != null) {
            boolean isOwnCity = game.getCityAt(p).getOwner() == game.getPlayerInTurn();
            if (isOwnCity) return 3;
        }

        boolean isForestOrHill = game.getTileAt(p).getTypeString().equals(GameConstants.FOREST)
                || game.getTileAt(p).getTypeString().equals(GameConstants.HILLS);
        if(isForestOrHill) return 2;

        return 1;
    }


    @Override
    public int computeStatValue(Position unitPosition, boolean isAttacking, Game game) {
        if (game.getUnitAt(unitPosition) == null) {
            return 0;
        }
        if (isAttacking) {
            int attack = game.getUnitAt(unitPosition).getAttackingStrength();
            return (attack + getAllies(unitPosition, game)) * getTerrainBonus(unitPosition, game);
        }
        else {
            int defense = game.getUnitAt(unitPosition).getDefensiveStrength();
            return (defense + getAllies(unitPosition, game)) * getTerrainBonus(unitPosition, game);
        }
    }

}
