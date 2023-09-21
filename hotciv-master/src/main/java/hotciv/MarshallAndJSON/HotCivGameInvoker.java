package hotciv.MarshallAndJSON;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.Common.OperationNames;
import hotciv.Service.NameService;
import hotciv.framework.*;

import javax.servlet.http.HttpServletResponse;

public class HotCivGameInvoker implements Invoker {
    private final NameService nameservice;
    private final Game game;
    private final Gson gson = new Gson();

    public HotCivGameInvoker(NameService nameService, Game servant, Gson gson) {
        this.game = servant;
        this.nameservice = nameService;
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

        if(operationName.equals(OperationNames.GAME_GET_WINNER)){
            Player name = game.getWinner();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(name));
        }
        else if(operationName.equals(OperationNames.GAME_GET_PLAYER_IN_TURN)){
            Player name = game.getPlayerInTurn();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(name));
        }
        else if(operationName.equals(OperationNames.GAME_GET_AGE)){
            int age = game.getAge();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(age));
        }
        else if(operationName.equals(OperationNames.GAME_MOVE)){
            Position from = gson.fromJson(array.get(0), Position.class);
            Position to = gson.fromJson(array.get(1), Position.class);
            boolean move = game.moveUnit(from, to);
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(move));
        }
        else if(operationName.equals(OperationNames.GAME_ACTION)){
            Position unitPos = gson.fromJson(array.get(0), Position.class);
            game.performUnitActionAt(unitPos);
        }
        else if(operationName.equals(OperationNames.GAME_TILE_AT)){
            Position tilePos = gson.fromJson(array.get(0), Position.class);
            Tile tile = game.getTileAt(tilePos);
            nameservice.putTile(tile.getId(), tile);
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(tile.getId()));
        }
        else if(operationName.equals(OperationNames.GAME_CITY_AT)){
            Position cityPos = gson.fromJson(array.get(0), Position.class);
            City city = game.getCityAt(cityPos);
            boolean isCity = city != null;
            if (isCity) {
                nameservice.putCity(city.getId(), city);
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(city.getId()));
            }
            else {
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("null"));
            }
        }
        else if(operationName.equals(OperationNames.GAME_CURRENT_ROUND)){
            int currentround = game.getCurrentRound();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(currentround));
        }
        else if(operationName.equals(OperationNames.GAME_UNIT_AT)){
            Position unitPos = gson.fromJson(array.get(0), Position.class);
            Unit unit = game.getUnitAt(unitPos);
            boolean isUnit = unit != null;
            if (isUnit) {
                nameservice.putUnit(unit.getId(), unit);
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(unit.getId()));
            }
            else {
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("null"));
            }
        }
        else if(operationName.equals(OperationNames.GAME_CITY_POPULATION)){
            City city = gson.fromJson(array.get(0), City.class);
            int cityPop = game.getCityPopulation(city);
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(cityPop));
        }
        else if(operationName.equals(OperationNames.GAME_ADD_UNIT)){
            Position position = gson.fromJson(array.get(0), Position.class);
            Unit unit = gson.fromJson(array.get(1), Unit.class);
            game.addUnit(position,unit);
        }
        else if(operationName.equals(OperationNames.GAME_ADD_CITY)){
            Position position = gson.fromJson(array.get(0), Position.class);
            City city = gson.fromJson(array.get(1), City.class);
            game.addCity(position, city);
            nameservice.putCity(city.getId(), city);
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(city.getId()));
        }
        else if(operationName.equals(OperationNames.GAME_ADD_OBSERVER)){
            System.out.println(array);
            GameObserver observer = gson.fromJson(array.get(0), GameObserver.class);
            game.addObserver(observer);
        }
        else if(operationName.equals(OperationNames.GAME_SET_TILE_FOCUS)){
            Position position = gson.fromJson(array.get(0), Position.class);
            game.setTileFocus(position);
        }
        else if(operationName.equals(OperationNames.GAME_END_OF_TURN)){
            game.endOfTurn();
        }
        else if(operationName.equals(OperationNames.GAME_END_OF_ROUND)){
            game.endOfRound();
        }
        else if(operationName.equals(OperationNames.GAME_CHANGE_WORKFORCE_FOCUS)){
            Position position = gson.fromJson(array.get(0), Position.class);
            String balance = gson.fromJson(array.get(1), String.class);
            game.changeWorkForceFocusInCityAt(position,balance);
        }
        else if(operationName.equals(OperationNames.GAME_CHANGE_PRODUCTION)){
            Position position = gson.fromJson(array.get(0), Position.class);
            String unitType = gson.fromJson(array.get(1), String.class);
            game.changeProductionInCityAt(position,unitType);
        }
        else if(operationName.equals(OperationNames.GAME_END)){
            game.endOfGame();
        }
        return gson.toJson(reply);
    }
}
