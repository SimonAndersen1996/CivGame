package hotciv.Client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.Common.OperationNames;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Tile;

public class CityProxy implements City, ClientProxy {
    private final String cityObject;
    private final Requestor requestor;

    public CityProxy(String cityObject, Requestor requestor) {
        this.cityObject = cityObject;
        this.requestor = requestor;
    }

    @Override
    public Player getOwner() {
        Player owner = requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_GET_OWNER, Player.class);
        return owner;
    }

    @Override
    public int getSize() {
        int size = requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_GET_SIZE, Integer.class);
        return size;
    }

    @Override
    public int getTreasury() {
        int treasury = requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_GET_TREASURY, Integer.class);
        return treasury;
    }

    @Override
    public String getProductionType() {
        String prodType = requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_GET_PRODUCTION_TYPE, String.class);
        return prodType;
    }

    @Override
    public String getWorkforceFocus() {
        String workType = requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_GET_WORKFORCE_TYPE, String.class);
        return workType;
    }

    @Override
    public void addTreasury(int x) {
        requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_ADD_TREASURY, void.class, x);
    }

    @Override
    public void setWorkForceFocus(String newfocus) {
        requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_SET_WORKFORCE_FOCUS, void.class, newfocus);
    }

    @Override
    public void setProductionFocus(String newfocus) {
        requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_SET_PRODUCTION_FOCUS, void.class, newfocus);
    }

    @Override
    public void setOwner(Player newowner) {
        requestor.sendRequestAndAwaitReply(cityObject, OperationNames.CITY_SET_OWNER, void.class, newowner);
    }

    public String getId() {
        return cityObject;
    }
}
