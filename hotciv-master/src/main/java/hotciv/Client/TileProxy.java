package hotciv.Client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.Common.OperationNames;
import hotciv.framework.Tile;

public class TileProxy implements Tile, ClientProxy {
    private String tileObject;
    private final Requestor requestor;

    public TileProxy(String tileObject, Requestor requestor) {
        this.tileObject = tileObject;
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        String type = requestor.sendRequestAndAwaitReply(tileObject, OperationNames.TILE_GET_TYPESTRING, String.class);
        return type;
    }

    public String getId() {
        return tileObject;
    }
}
