package hotciv.MarshallAndJSON;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.Common.OperationNames;
import hotciv.Service.NameService;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.stub.StubCity;

import javax.servlet.http.HttpServletResponse;

public class HotCivCityInvoker implements Invoker {
    private final Gson gson;
    private final NameService nameService;

    public HotCivCityInvoker(NameService nameService, Gson gson) {
        this.gson = gson;
        this.nameService = nameService;
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

        if(operationName.equals(OperationNames.CITY_GET_OWNER)){
            City city = nameService.getCity(gson.fromJson(objectId, String.class));
            Player owner = city.getOwner();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));
        }
        else if(operationName.equals(OperationNames.CITY_GET_SIZE)){
            City city = nameService.getCity(objectId);
            int size = city.getSize();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(size));
        }
        else if(operationName.equals(OperationNames.CITY_GET_PRODUCTION_TYPE)){
            City city = nameService.getCity(objectId);
            String type = city.getProductionType();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
        }
        else if(operationName.equals(OperationNames.CITY_GET_WORKFORCE_TYPE)){
            City city = nameService.getCity(objectId);
            String type = city.getWorkforceFocus();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
        }
        else if(operationName.equals(OperationNames.CITY_GET_TREASURY)){
            City city = nameService.getCity(objectId);
            int treasury = city.getTreasury();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(treasury));
        }
        else if(operationName.equals(OperationNames.CITY_ADD_TREASURY)){
            int addAmount = gson.fromJson(array.get(0), Integer.class);
            City city = nameService.getCity(objectId);
            city.addTreasury(addAmount);
        }
        else if(operationName.equals(OperationNames.CITY_SET_OWNER)){
            Player newOwner = gson.fromJson(array.get(0), Player.class);
            City city = nameService.getCity(objectId);
            city.setOwner(newOwner);
        }
        else if(operationName.equals(OperationNames.CITY_SET_PRODUCTION_FOCUS)){
            String newFocus = gson.fromJson(array.get(0), String.class);
            City city = nameService.getCity(objectId);
            city.setProductionFocus(newFocus);
        }
        else if(operationName.equals(OperationNames.CITY_SET_WORKFORCE_FOCUS)){
            String newFocus = gson.fromJson(array.get(0), String.class);
            City city = nameService.getCity(objectId);
            city.setWorkForceFocus(newFocus);
        }

        return gson.toJson(reply);
    }
}
