package hotciv.Client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.Common.OperationNames;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitProxy implements Unit, ClientProxy {

    private String unitObject;
    private final Requestor requestor;

    public UnitProxy(String unitObject, Requestor requestor) {
        this.unitObject = unitObject;
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        String type = requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_GET_TYPESTRING, String.class);
        return type;
    }

    @Override
    public Player getOwner() {
        Player owner = requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_GET_OWNER, Player.class);
        return owner;
    }

    @Override
    public int getMoveCount() {
        int move = requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_GET_MOVECOUNT, Integer.class);
        return move;
    }

    @Override
    public int getDefensiveStrength() {
        int defStrength = requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_GET_DEFENSIVE_STRENGTH, Integer.class);
        return defStrength;
    }

    @Override
    public void setDefensiveStrength(int newstrength) {
        requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_SET_DEFENSIVE_STRENGTH, void.class, newstrength);
    }

    @Override
    public int getAttackingStrength() {
        int attackStrength = requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_GET_ATTACKING_STRENGTH, Integer.class);
        return attackStrength;
    }

    @Override
    public void setMoved(boolean b) {
        requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_SET_MOVED, void.class, b);
    }

    @Override
    public boolean getMoved() {
        boolean moved = requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_GET_MOVED, Boolean.class);
        return moved;
    }

    @Override
    public boolean isFortified() {
        boolean moved = requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_IS_FORTIFIED, Boolean.class);
        return moved;
    }

    @Override
    public void setMoveCount(int count) {
        requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_SET_MOVEMENT_COUNT, void.class, count);
    }

    @Override
    public void setFortified(boolean b) {
        requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_SET_FORTIFIED, void.class, b);
    }

    @Override
    public int getMaxMove() {
        int maxMove = requestor.sendRequestAndAwaitReply(unitObject, OperationNames.UNIT_GET_MAX_MOVE, Integer.class);
        return maxMove;
    }

    public String getId() {
        return unitObject;
    }
}
