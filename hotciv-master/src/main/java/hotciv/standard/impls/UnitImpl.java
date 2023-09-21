package hotciv.standard.impls;

import frds.broker.Servant;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

import java.util.UUID;

public class UnitImpl implements Unit, Servant {
    private String unit;
    private Player owner;
    private boolean isFortified = false;
    private boolean hasmoved = false;
    private int defense;
    private int attack;
    private int currentMoveCount = 1;
    private int maxMoveCount;
    private boolean hasCharmed = false;
    private final String id;

    public UnitImpl(String unit, Player owner) {
        id = UUID.randomUUID().toString();
        this.unit = unit;
        this.owner = owner;
        switch (unit) {
            case GameConstants.ARCHER:
                setStats(3, 2,1);
                break;
            case GameConstants.SETTLER:
                setStats(3, 0,1);
                break;
            case GameConstants.LEGION:
                setStats(2, 4,1);
                break;
            case GameConstants.SANDWORM:
                setStats(10,0,2);
        }
    }

    private void setStats(int def, int att, int move) {
        defense = def;
        attack = att;
        currentMoveCount = move;
        maxMoveCount = move;
    }

    @Override
    public String getTypeString() {
        return unit;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return currentMoveCount;
    }

    @Override
    public boolean getMoved() {
        return hasmoved;
    }

    @Override
    public boolean isFortified() {
        return isFortified;
    }

    @Override
    public void setMoveCount(int count) {
        currentMoveCount = count;
    }

    @Override
    public void setFortified(boolean b) {
        isFortified = b;
    }

    @Override
    public void setMoved(boolean b) {
        hasmoved = b;
    }

    @Override
    public int getDefensiveStrength() {
        return defense;
    }

    @Override
    public void setDefensiveStrength(int newstrength) {
        defense = newstrength;
    }

    @Override
    public int getAttackingStrength() {
        return attack;
    }

    @Override
    public int getMaxMove() {
        return maxMoveCount;
    }

    @Override
    public String getId() {
        return id;
    }

}

