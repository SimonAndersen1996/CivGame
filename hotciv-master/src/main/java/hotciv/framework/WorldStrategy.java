package hotciv.framework;

import java.util.HashMap;
import java.util.Map;

public interface WorldStrategy {

    HashMap<Position, Tile> defineWorld();

    HashMap<Position, Unit> defineUnits();

    HashMap<Position, City> defineCities();

}
