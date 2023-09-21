package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.impls.TileImpl;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.HashMap;

/**
 * WorldFractal is an Adapter that implements WorldStrategy with the purpose of
 * adapting the ThirdPartyFractalGenerator in order to use it with the HotCiv
 * project.
 */
public class WorldFractal implements WorldStrategy {

    private ThirdPartyFractalGenerator thirdParty = new ThirdPartyFractalGenerator();

    @Override
    public HashMap<Position, Tile> defineWorld() {
        HashMap<Position, Tile> theWorld = new HashMap<Position, Tile>();
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = thirdParty.getLandscapeAt(r,c);
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
                Position p = new Position(r, c);
                theWorld.put(p, new TileImpl(type));
            }
        }
        return theWorld;
    }

    @Override
    public HashMap<Position, Unit> defineUnits() {
        return new HashMap<Position, Unit>();
    }

    @Override
    public HashMap<Position, City> defineCities() {
        return new HashMap<Position, City>();
    }
}
