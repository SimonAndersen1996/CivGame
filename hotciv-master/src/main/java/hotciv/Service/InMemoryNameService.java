package hotciv.Service;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Tile;
import hotciv.framework.Unit;

import java.util.HashMap;
import java.util.Map;

public class InMemoryNameService implements NameService {
    private Map<String, Unit> unitMap;
    private Map<String, City> cityMap;
    private Map<String, Tile> tileMap;
    private Map<String, Game> gameMap;

    public InMemoryNameService(){
        this.cityMap = new HashMap<>();
        this.unitMap = new HashMap<>();
        this.gameMap = new HashMap<>();
        this.tileMap = new HashMap<>();
    }

    @Override
    public void putUnit(String objectId, Unit unit) {
        unitMap.put(objectId, unit);
    }

    @Override
    public Unit getUnit(String objectId) {
        return unitMap.get(objectId);
    }

    @Override
    public void putCity(String objectId, City city) {
        cityMap.put(objectId, city);
    }

    @Override
    public City getCity(String objectId) {
        return cityMap.get(objectId);
    }

    @Override
    public void putTile(String objectId, Tile tile) {
    tileMap.put(objectId, tile);
    }

    @Override
    public Tile getTile(String objectId) {
        return tileMap.get(objectId);
    }

    @Override
    public void putGame(String objectId, Game game) {
        gameMap.put(objectId, game);
    }

    @Override
    public Game getGame(String objectId) {
        return gameMap.get(objectId);
    }

}
