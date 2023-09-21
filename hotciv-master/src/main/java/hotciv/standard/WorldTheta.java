package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.TileImpl;
import hotciv.standard.impls.UnitImpl;

import java.util.HashMap;

public class WorldTheta implements WorldStrategy {
    private Game game;


    @Override
    /** Define the world as the DeltaCiv layout */
    public HashMap<Position,Tile> defineWorld() {
        // Basically we use a 'data driven' approach - code the
        // layout in a simple semi-visual representation, and
        // convert it to the actual Game representation.
        String[]  layout = new String[]{
                "...oododdoo.....",
                "..ohhdddddddddd.",
                ".oddddMddd...dd.",
                ".odMMddododdddoo",
                "...ofodddddddo..",
                ".ofddddoddddddo.",
                "...odd..dddddd..",
                ".oddddddddddoM..",
                ".oddddddddddddf.",
                "offddddoddddddoo",
                "oodddodo...oddoo",
                ".ooMMdddd...ddd.",
                "..oodddddfoddd..",
                "....oddddMMdo...",
                "..ooddddddMd....",
                ".....oddddddoo..",
        };
        // Conversion...
        HashMap<Position, Tile> theWorld = new HashMap<Position, Tile>();
        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            line = layout[r];
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = line.charAt(c);
                String type = "error";
                if (tileChar == '.') {
                    type = GameConstants.OCEANS;
                }
                if (tileChar == 'o') {
                    type = GameConstants.PLAINS;
                }
                if (tileChar == 'M') {
                    type = GameConstants.MOUNTAINS;
                }
                if (tileChar == 'f') {
                    type = GameConstants.FOREST;
                }
                if (tileChar == 'h') {
                    type = GameConstants.HILLS;
                }
                if (tileChar == 'd'){
                    type = GameConstants.DESERT;
                }
                Position p = new Position(r, c);
                theWorld.put(p, new TileImpl(type));
            }
        }
        return theWorld;
    }

    @Override
    public HashMap<Position, Unit> defineUnits() {
        HashMap<Position, Unit> units = new HashMap<>();

        units.put(new Position(3,8),new UnitImpl(GameConstants.ARCHER,Player.RED));
        units.put(new Position(4,4),new UnitImpl(GameConstants.LEGION,Player.BLUE));
        units.put(new Position(5,5),new UnitImpl(GameConstants.SETTLER,Player.RED));
        units.put(new Position(9,6),new UnitImpl(GameConstants.SANDWORM,Player.BLUE));
        return units;
    }

    @Override
    public HashMap<Position, City> defineCities() {
        HashMap<Position, City> cities = new HashMap<>();

        cities.put(new Position(4,5),new CityImpl(Player.BLUE));
        cities.put(new Position(8,12),new CityImpl(Player.RED));
        return cities;
    }
}

