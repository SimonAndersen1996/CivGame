package hotciv.stub;

import hotciv.Common.OperationNames;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class FakeBrokerUnit implements Unit {
    private String lastMethod;

    @Override
    public String getTypeString() {
        return GameConstants.HILLS;
    }

    @Override
    public Player getOwner() {
        return Player.YELLOW;
    }

    @Override
    public int getMoveCount() {
        return 800;
    }

    @Override
    public int getDefensiveStrength() {
        return 9000;
    }

    @Override
    public void setDefensiveStrength(int newstrength) {
        setLastMethodCalled(OperationNames.UNIT_SET_DEFENSIVE_STRENGTH);
    }

    @Override
    public int getAttackingStrength() {
        return 4500;
    }

    @Override
    public void setMoved(boolean b) {
        setLastMethodCalled(OperationNames.UNIT_SET_MOVED);
    }

    @Override
    public boolean getMoved() {
        return true;
    }

    @Override
    public boolean isFortified() {
        return true;
    }

    @Override
    public void setMoveCount(int count) {
        setLastMethodCalled(OperationNames.UNIT_SET_MOVEMENT_COUNT);
    }

    @Override
    public void setFortified(boolean b) {
        setLastMethodCalled(OperationNames.UNIT_SET_FORTIFIED);
    }

    @Override
    public int getMaxMove() {
        return 666;
    }

    @Override
    public String getId() {
        return null;
    }

    public void setLastMethodCalled(String method) {
        lastMethod = method;
    }

    public String getLastMethodCalled(){
        return lastMethod;
    }
}
