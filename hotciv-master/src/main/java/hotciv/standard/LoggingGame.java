package hotciv.standard;

import hotciv.framework.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LoggingGame implements Game {
    private Game decorateeGame;

    public LoggingGame(Game decorateeGame) {
        this.decorateeGame = decorateeGame;
    }

    @Override
    public Tile getTileAt(Position p) {
        System.out.println("Getting tile at position: (" + p.getRow() + ", " + p.getColumn() + ")");
        return decorateeGame.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        System.out.println("Getting unit at position: (" + p.getRow() + ", " + p.getColumn() + ")");
        return decorateeGame.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        System.out.println("Getting city at position: (" + p.getRow() + ", " + p.getColumn() + ")");
        return decorateeGame.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        Player player = decorateeGame.getPlayerInTurn();
        System.out.println("Getting current player in turn: " + player);
        return player;
    }

    @Override
    public Player getWinner() {
        Player winner = decorateeGame.getWinner();
        System.out.println("Getting winner of the game: " + winner);
        return winner;
    }

    @Override
    public int getAge() {
        int age = decorateeGame.getAge();
        System.out.println("Getting current age: " + age);
        return age;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        System.out.println("Moving unit from: (" + from.getRow() + ", " + from.getColumn() + ") and to: (" + to.getRow() + ", " + to.getColumn() + ")");
        return decorateeGame.moveUnit(from, to);
    }

    @Override
    public void endOfTurn() {
        System.out.println("Ending turn");
        decorateeGame.endOfTurn();
    }

    @Override
    public int getCityPopulation(City c) {
        System.out.println("Getting city population");
        return decorateeGame.getCityPopulation(c);
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        System.out.println("Changing workforce focus");
        decorateeGame.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        System.out.println("Changing production");
        decorateeGame.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        System.out.println("Performing unit action");
        decorateeGame.performUnitActionAt(p);
    }

    @Override
    public void endOfRound() {
        System.out.println("Ending round");
        decorateeGame.endOfRound();
    }

    @Override
    public void endOfGame() {
        System.out.println("Ending game");
        decorateeGame.endOfGame();
    }

    @Override
    public int getCurrentRound() {
        System.out.println("Getting current round");
        return decorateeGame.getCurrentRound();
    }

    @Override
    public void addCity(Position pos, City city) {
        System.out.println("Adding city");
        decorateeGame.addCity(pos, city);
    }

    @Override
    public void addUnit(Position pos, Unit unit) {
        System.out.println("Adding unit");
        decorateeGame.addUnit(pos, unit);
    }

    @Override
    public HashMap<Position, City> getCities() {
        System.out.println("Getting all cities");
        return decorateeGame.getCities();
    }

    @Override
    public ArrayList<Player> getPlayers() {
        System.out.println("Getting all players");
        return decorateeGame.getPlayers();
    }

    @Override
    public void addPlayer(Player p) {
        System.out.println("Adding player: " + p);
        decorateeGame.addPlayer(p);
    }

    @Override
    public HashMap<Position, Unit> getUnits() {
        System.out.println("Getting all units");
        return decorateeGame.getUnits();
    }

    @Override
    public void setAge(int a) {
        System.out.println("Setting age to: " + a);
        decorateeGame.setAge(a);
    }

    @Override
    public void increaseProduction(Position p, int amount) {
        System.out.println("Increasing production");
        decorateeGame.increaseProduction(p, amount);
    }

    @Override
    public Position getFreePosition(Position cityPosition) {
        System.out.println("Getting free positions");
        return decorateeGame.getFreePosition(cityPosition);
    }

    @Override
    public void removeUnitAt(Position p) {
        System.out.println("Removing unit");
        decorateeGame.removeUnitAt(p);
    }

    @Override
    public HashMap<Position, Tile> getWorldLayout() {
        System.out.println("Getting the world layout");
        return decorateeGame.getWorldLayout();
    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }

    @Override
    public String getId() {
        return null;
    }
}
