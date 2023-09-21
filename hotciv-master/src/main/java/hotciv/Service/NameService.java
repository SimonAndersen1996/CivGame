package hotciv.Service;

import hotciv.Client.UnitProxy;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Tile;
import hotciv.framework.Unit;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import hotciv.standard.impls.TileImpl;
import hotciv.standard.impls.UnitImpl;

public interface NameService {

    /**
     * Store a unit in the unitMap
     * @param objectId
     * @param unit
     */
    void putUnit(String objectId, Unit unit);

    /**
     * Retrieve the corresponding unit of objectId from unitMap
     * @param objectId
     * @return
     */
    Unit getUnit(String objectId);

    void putCity(String objectId, City city);

    City getCity(String objectId);

    void putTile(String objectId, Tile tile);

    Tile getTile(String objectId);

    void putGame(String objectId, Game game);

    Game getGame(String objectId);


}
