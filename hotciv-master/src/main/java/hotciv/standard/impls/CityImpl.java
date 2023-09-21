package hotciv.standard.impls;

import frds.broker.Servant;
import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.UUID;

public class CityImpl implements City, Servant {
    private int population = 1;
    private int treasure = 0;
    private final String id;
    private Player player;
    private String workfocus = GameConstants.foodFocus;
    private String productionfocus = GameConstants.ARCHER;

    public CityImpl(Player player) {
        this.player = player;

        id = UUID.randomUUID().toString();
    }

    @Override
    public Player getOwner() {
        return player;
    }

    @Override
    public void setOwner(Player newowner) {
        player = newowner;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getSize() {
        return population;
    }

    @Override
    public int getTreasury() {
        return treasure;
    }

    @Override
    public void addTreasury(int x){
        treasure += x;
    }

    @Override
    public String getProductionType() {
        return productionfocus;
    }

    @Override
    public void setProductionFocus(String newfocus){
        productionfocus = newfocus;
    }

    @Override
    public void setWorkForceFocus(String newfocus) {
        workfocus = newfocus;
    }

    @Override
    public String getWorkforceFocus() {
        return workfocus;
    }

}
