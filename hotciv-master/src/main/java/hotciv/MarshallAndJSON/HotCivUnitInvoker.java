package hotciv.MarshallAndJSON;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.Common.OperationNames;
import hotciv.Service.NameService;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.standard.impls.UnitImpl;

import javax.servlet.http.HttpServletResponse;

public class HotCivUnitInvoker implements Invoker {
    private Gson gson;
    private NameService nameService;
    private Game servant;

    public HotCivUnitInvoker(NameService nameService, Game servant, Gson gson) {
        this.gson = gson;
        this.nameService = nameService;
        this.servant = servant;
    }

    @Override
    public String handleRequest(String request) {
        // Perform demarshalling
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String objectId = requestObject.getObjectId();
        String operationName = requestObject.getOperationName();
        String arguments = requestObject.getPayload();
        JsonArray array = JsonParser.parseString(arguments).getAsJsonArray();

        ReplyObject reply = new ReplyObject(HttpServletResponse.SC_OK, "");
        System.out.println(operationName);

        if(operationName.equals(OperationNames.UNIT_GET_OWNER)){
            Unit unit = nameService.getUnit(objectId);
            Player owner = unit.getOwner();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));
        }
        else if(operationName.equals(OperationNames.UNIT_GET_TYPESTRING)){
            Unit unit = nameService.getUnit(objectId);
            String unitType = unit.getTypeString();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(unitType));
        }
        else if(operationName.equals(OperationNames.UNIT_GET_ATTACKING_STRENGTH)){
            Unit unit = nameService.getUnit(objectId);
            int attack = unit.getAttackingStrength();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(attack));
        }
        else if(operationName.equals(OperationNames.UNIT_GET_DEFENSIVE_STRENGTH)){
            Unit unit = nameService.getUnit(objectId);
            int defense = unit.getDefensiveStrength();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(defense));
        }
        else if(operationName.equals(OperationNames.UNIT_GET_MAX_MOVE)){
            Unit unit = nameService.getUnit(objectId);
            int maxMove = unit.getMaxMove();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(maxMove));
        }
        else if(operationName.equals(OperationNames.UNIT_GET_MOVECOUNT)){
            Unit unit = nameService.getUnit(objectId);
            int moveCount = unit.getMoveCount();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(moveCount));
        }
        else if(operationName.equals(OperationNames.UNIT_GET_MOVED)){
            Unit unit = nameService.getUnit(objectId);
            boolean hasMoved = unit.getMoved();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(hasMoved));
        }
        else if(operationName.equals(OperationNames.UNIT_IS_FORTIFIED)){
            Unit unit = nameService.getUnit(objectId);
            boolean isFortified = unit.isFortified();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(isFortified));
        }
        else if(operationName.equals(OperationNames.UNIT_SET_DEFENSIVE_STRENGTH)){
            int newValue = gson.fromJson(array.get(0), Integer.class);
            Unit unit = nameService.getUnit(objectId);
            unit.setDefensiveStrength(newValue);
        }
        else if(operationName.equals(OperationNames.UNIT_SET_FORTIFIED)){
            boolean isFortified = gson.fromJson(array.get(0), Boolean.class);
            Unit unit = nameService.getUnit(objectId);
            unit.setFortified(isFortified);
        }
        else if(operationName.equals(OperationNames.UNIT_SET_MOVED)){
            boolean setMoved = gson.fromJson(array.get(0), Boolean.class);
            Unit unit = nameService.getUnit(objectId);
            unit.setMoved(setMoved);
        }
        else if(operationName.equals(OperationNames.UNIT_SET_MOVEMENT_COUNT)){
            int moveCount = gson.fromJson(array.get(0), Integer.class);
            Unit unit = nameService.getUnit(objectId);
            unit.setMoveCount(moveCount);
        }

        return gson.toJson(reply);
    }
}
