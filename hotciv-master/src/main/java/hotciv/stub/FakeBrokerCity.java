package hotciv.stub;

import frds.broker.Servant;
import hotciv.Common.OperationNames;
import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

public class FakeBrokerCity implements City, Servant {
    private String lastMethod;

    @Override
    public Player getOwner() {
        return Player.GREEN;
    }

    @Override
    public int getSize() {
        return 420;
    }

    @Override
    public int getTreasury() {
        return 8008;
    }

    @Override
    public String getProductionType() {
        return GameConstants.MOUNTAINS;
    }

    @Override
    public String getWorkforceFocus() {
        return GameConstants.OCEANS;
    }

    @Override
    public void addTreasury(int x) {
        setLastMethodCalled(OperationNames.CITY_ADD_TREASURY);
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
        setLastMethodCalled(OperationNames.CITY_SET_OWNER);
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
