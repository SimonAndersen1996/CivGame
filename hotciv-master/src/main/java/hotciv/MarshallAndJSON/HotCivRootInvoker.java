package hotciv.MarshallAndJSON;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.Common.OperationNames;
import hotciv.Service.InMemoryNameService;
import hotciv.Service.NameService;
import hotciv.framework.Game;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HotCivRootInvoker implements Invoker {
    private Game gameServant;
    private Gson gson = new Gson();
    private Map<String, Invoker> invokerMap = new HashMap<>();

    public HotCivRootInvoker(Game gameServant) {
        this.gameServant = gameServant;


        NameService nameService = new InMemoryNameService();

        // Create an invoker for each handled type/class
        // and put them in a map, binding them to the
        // operationName prefixes
        Invoker tileInvoker = new HotCivTileInvoker(nameService, gameServant, gson);
        invokerMap.put(OperationNames.TILE_PREFIX, tileInvoker);
        Invoker unitInvoker = new HotCivUnitInvoker(nameService, gameServant, gson);
        invokerMap.put(OperationNames.UNIT_PREFIX, unitInvoker);
        Invoker gameInvoker = new HotCivGameInvoker(nameService, gameServant, gson);
        invokerMap.put(OperationNames.GAME_PREFIX, gameInvoker);
        Invoker cityInvoker = new HotCivCityInvoker(nameService, gson);
        invokerMap.put(OperationNames.CITY_PREFIX, cityInvoker);
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String operationName = requestObject.getOperationName();

        String reply;

        // Identify the invoker to use
        String type = operationName.substring(0, operationName.indexOf(OperationNames.SEPARATOR));
        Invoker subInvoker = invokerMap.get(type);

        // And do the upcall on the subInvoker
        try {
            reply = subInvoker.handleRequest(request);

        } catch (Exception e) {
            reply = gson.toJson(
                    new ReplyObject(
                            HttpServletResponse.SC_NOT_FOUND,
                            e.getMessage()));
        }

        return reply;
    }
}
