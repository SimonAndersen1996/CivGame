package hotciv.Client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.Common.OperationNames;
import hotciv.framework.*;
import hotciv.view.CivDrawing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameProxy implements Game, ClientProxy {

    private final String gameObject;
    private final Requestor requestor;
    private HashMap<Position, UnitProxy> unitMap = new HashMap<>();
    private HashMap<Position, CityProxy> cityMap = new HashMap<>();
    private Set<GameObserver> observers = new HashSet<>();

    public GameProxy(String gameObject, Requestor requestor) {
        this.gameObject = gameObject;
        this.requestor = requestor;
    }

    @Override
    public Tile getTileAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_TILE_AT, String.class, p);
        TileProxy tile = new TileProxy(id, requestor);
        return tile;
    }

    @Override
    public Unit getUnitAt(Position p) {
            String id = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_UNIT_AT, String.class, p);
            if (id.equals("null")) return null;
            UnitProxy unit = new UnitProxy(id, requestor);
            return unit;
    }

    @Override
    public City getCityAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_CITY_AT, String.class, p);
        if (id.equals("null")) return null;
        CityProxy city = new CityProxy(id, requestor);
        return city;
    }

    @Override
    public Player getPlayerInTurn() {
        Player player = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_GET_PLAYER_IN_TURN, Player.class);
        return player;
    }

    @Override
    public Player getWinner() {
        Player player = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_GET_WINNER, Player.class);
        return player;
    }

    @Override
    public int getAge() {
        int age = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_GET_AGE, Integer.class);
        return age;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        boolean move = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_MOVE, Boolean.class, from, to);
        worldChangedAt(from);
        worldChangedAt(to);
        if (move) System.out.println("Battle won");
        else System.out.println("Battle lost");
        return move;
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_CHANGE_WORKFORCE_FOCUS, void.class, p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_CHANGE_PRODUCTION, void.class, p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_ACTION, void.class, p);
    }

    @Override
    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_END_OF_TURN, null);
    }

    @Override
    public int getCityPopulation(City c) {
        int population = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_CITY_POPULATION, Integer.class, c);
        return population;
    }

    @Override
    public void setTileFocus(Position position) {
        requestor.sendRequestAndAwaitReply(getId(), OperationNames.GAME_SET_TILE_FOCUS, void.class, position);
        for (GameObserver o : observers) {
            o.tileFocusChangedAt(position);
        }
    }

    @Override
    public void endOfRound() {
        //requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_END_OF_ROUND, void.class);
    }

    @Override
    public void endOfGame() {
        //requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_END, void.class);
    }

    @Override
    public int getCurrentRound() {
        /*
        int round = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_CURRENT_ROUND, Integer.class);
        return round;
         */
        return 0;
    }

    @Override
    public void addCity(Position pos, City city) {
        /*
        String id = requestor.sendRequestAndAwaitReply(gameObject,OperationNames.GAME_ADD_CITY, String.class, pos, city);
        CityProxy proxy = new CityProxy(id, requestor);
        cityMap.put(pos, proxy);
         */
    }

    @Override
    public void addUnit(Position pos, Unit unit) {
        /*
        String id = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_ADD_UNIT, String.class, pos, unit);
        UnitProxy proxy = new UnitProxy(id, requestor);
        unitMap.put(pos, proxy);
         */
    }

    public HashMap<Position, City> getCities() {
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return null;
    }

    public void addPlayer(Player p) {
    }

    public HashMap<Position, Unit> getUnits() {
        return null;
    }

    public void setAge(int a) {

    }

    public void increaseProduction(Position p, int amount) {

    }

    public Position getFreePosition(Position cityPosition) {
        //Position freePosition = requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_GET_FREE_POSITION, Position.class, cityPosition);
        //return freePosition;
        return null;
    }

    public void removeUnitAt(Position p) {
        //requestor.sendRequestAndAwaitReply(gameObject, OperationNames.GAME_REMOVE_UNIT, void.class, p);
    }

    public HashMap<Position, Tile> getWorldLayout() {
        return null;
    }

    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    @Override
    public String getId() {
        return gameObject;
    }

    private void worldChangedAt(Position p) {
        for (GameObserver o : observers) {
            o.worldChangedAt(p);
        }
    }

}