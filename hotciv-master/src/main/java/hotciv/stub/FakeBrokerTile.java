package hotciv.stub;

import frds.broker.Servant;
import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

public class FakeBrokerTile implements Tile, Servant {

    @Override
    public String getTypeString() {
        return GameConstants.FOREST;
    }

    @Override
    public String getId() {
        return null;
    }
}
