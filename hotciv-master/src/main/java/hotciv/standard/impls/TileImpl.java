package hotciv.standard.impls;

import frds.broker.Servant;
import hotciv.framework.Tile;

import java.util.UUID;

public class TileImpl implements Tile, Servant {
    private String tile;
    private final String id;


    public TileImpl(String tile) {
        this.tile = tile;

        id = UUID.randomUUID().toString();
    }

    @Override
    public String getTypeString() {
        return tile;
    }

    @Override
    public String getId() {
        return id;
    }

}
