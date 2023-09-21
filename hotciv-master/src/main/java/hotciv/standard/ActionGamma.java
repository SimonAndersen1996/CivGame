package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.impls.CityImpl;
import hotciv.Utility.*;

public class ActionGamma implements ActionStrategy {

    @Override
    public void action(Unit unit, Position position, Game game) {
        if (unit == null) return;
        switch (unit.getTypeString()){
            case GameConstants.ARCHER:
                if (!unit.isFortified()) {
                    unit.setDefensiveStrength(unit.getDefensiveStrength() * 2);
                    unit.setMoveCount(0);
                    unit.setFortified(true);
                }
                else {
                    unit.setDefensiveStrength(unit.getDefensiveStrength() / 2);
                    unit.setMoveCount(1);
                    unit.setFortified(false);
                }
                break;
            case GameConstants.SETTLER:
                game.getUnits().remove(position);
                game.addCity(position, new CityImpl(unit.getOwner()));
                break;
            case GameConstants.LEGION:
                // Nothing to do...
                break;
            case GameConstants.SANDWORM:
                for(Position p : Utility.get8neighborhoodOf(position)) {
                    Unit neighbor = game.getUnitAt(p);
                    if (neighbor != null) {
                        boolean isEnemyUnit = neighbor.getOwner() != unit.getOwner();
                        if (isEnemyUnit) {
                            game.removeUnitAt(p);
                        }
                    }
                }
        }
    }
}
