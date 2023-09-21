package hotciv.stub;

import hotciv.Common.OperationNames;
import hotciv.framework.City;
import hotciv.framework.Player;

import java.util.UUID;

public class StubCity implements City {
    private String lastMethodCalled;
    private String id;

    public StubCity(Player green, int i) {
        id = UUID.randomUUID().toString();
    }

    @Override
    public Player getOwner() {
        return Player.GREEN;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getTreasury() {
        return 0;
    }

    @Override
    public String getProductionType() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }

    @Override
    public void addTreasury(int x) {

    }

    @Override
    public void setWorkForceFocus(String newfocus) {
        setLastMethodCalled(OperationNames.CITY_SET_WORKFORCE_FOCUS);
    }

    @Override
    public void setProductionFocus(String newfocus) {
        setLastMethodCalled(OperationNames.CITY_SET_PRODUCTION_FOCUS);
    }

    @Override
    public void setOwner(Player newowner) {

    }

    @Override
    public String getId() {
        return id;
    }

    public void setLastMethodCalled(String methodCalled) {
        lastMethodCalled = methodCalled;
    }

    public String getLastMethodCalled() {
        return lastMethodCalled;
    }

}
