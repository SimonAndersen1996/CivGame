package hotciv.MarshallAndJSON;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.Common.OperationNames;
import hotciv.Service.NameService;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Tile;
import hotciv.standard.impls.TileImpl;

import javax.servlet.http.HttpServletResponse;

public class HotCivTileInvoker implements Invoker {
    private final Gson gson;
    private Game servant;
    private NameService nameService;

    public HotCivTileInvoker(NameService nameService, Game servant, Gson gson) {
        this.servant = servant;
        this.gson = gson;
        this.nameService = nameService;
    }

    @Override
    public String handleRequest(String request) {
        // Perform demarshalling
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String objectId = requestObject.getObjectId();
        String operationName = requestObject.getOperationName();

        ReplyObject reply = new ReplyObject(HttpServletResponse.SC_OK, "");
        System.out.println(operationName);

        if(operationName.equals(OperationNames.TILE_GET_TYPESTRING)){
            Tile tile = nameService.getTile(objectId);
            String type = tile.getTypeString();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
        }
        return gson.toJson(reply);
    }
}
