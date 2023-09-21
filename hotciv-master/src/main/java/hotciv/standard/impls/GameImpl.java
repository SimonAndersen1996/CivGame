package hotciv.standard.impls;

import frds.broker.Servant;
import hotciv.Utility.Utility;
import hotciv.framework.*;
import hotciv.standard.ProductionAlpha;
import hotciv.view.CivDrawing;

import java.util.*;

/**
 * Skeleton implementation of HotCiv.
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class GameImpl implements Game, Servant {
    private Player playerinturn = Player.RED;
    private int currentRound = 1;
    private int currentage = -4000;
    private ArrayList<Player> players = new ArrayList<>();
    private HashMap<Position, City> cities = new HashMap<>();
    private HashMap<Position, Tile> tiles = new HashMap<>();
    private HashMap<Position, Unit> units = new HashMap<>();
    private WinningStrategy winningStrategy;
    private AgingStrategy agingStrategy;
    private ActionStrategy actionStrategy;
    private WorldStrategy worldGen;
    private ProductionStrategy productionStrategy = new ProductionAlpha();
    private AttackingStrategy attackStrat;
    private MoveStrategy moveStrategy;
    private GameObserver observer = new ObserverSpy();
    private HashSet<GameObserver> observers = new HashSet<>();
    private final String id;

    public GameImpl(GameFactory factory) {
        this.winningStrategy = factory.createWinningStrategy();
        this.agingStrategy = factory.createAgingStrategy();
        this.actionStrategy = factory.createActionStrategy();
        this.worldGen = factory.createWorldGenerator();
        this.attackStrat = factory.createAttackingStrategy();
        this.moveStrategy = factory.createMoveStrategy();

        //Insert cities
        cities = worldGen.defineCities();

        //Insert Units
        units = worldGen.defineUnits();

        //Insert tiles
        tiles = worldGen.defineWorld();

        id = UUID.randomUUID().toString();
    }

    @Override
    public Tile getTileAt(Position p) {
        return tiles.get(p);
    }

    @Override
    public HashMap<Position, City> getCities() {
        return cities;
    }

    @Override
    public City getCityAt(Position p) {
        return cities.get(p);
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public Unit getUnitAt(Position p) {
        return units.get(p);
    }

    @Override
    public int getCityPopulation(City c) {
        return c.getSize();
    }

    @Override
    public HashMap<Position, Unit> getUnits() {
        return units;
    }

    @Override
    public void setAge(int a) {
        currentage = a;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public Player getPlayerInTurn() {
        return playerinturn;
    }

    @Override
    public Player getWinner() {
        return winningStrategy.getWinner(this);
    }

    @Override
    public int getAge() {
        return currentage;
    }

    @Override
    public void addUnit(Position pos, Unit unit){
        getUnits().put(pos, unit);
        worldChangedAt(pos);
    }

    @Override
    public void addCity(Position pos, City city) {
        getCities().put(pos, city);
        worldChangedAt(pos);
    }

    @Override
    public void addPlayer(Player p) {
        getPlayers().add(p);
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        if (!moveStrategy.isValidMove(from, to, this)) {
            System.out.println("Invalid move");
            return false;}

        boolean attackSuccessful = false;
        if (isAttack(to)) {
            attackSuccessful = attackStrat.isSuccessfulAttack(from, to, this);
        }
        if(attackSuccessful) {
            winningStrategy.incrementBattlesWon(getPlayerInTurn());
            System.out.println("Battle won");
        }

        boolean isConquerableCity = getCityAt(to) != null && getCityAt(to).getOwner() != getPlayerInTurn();
        if (isConquerableCity) {getCityAt(to).setOwner(getPlayerInTurn());}

        performMovement(from, to);
        return true;
    }

    private boolean isAttack(Position to) {
        boolean isEnemyUnit = getUnitAt(to) != null && getUnitAt(to).getOwner() != getPlayerInTurn();
        if(isEnemyUnit) return true;
        return false;
    }

    @Override
    public void removeUnitAt(Position p) {
        getUnits().remove(p);
        worldChangedAt(p);
    }

    @Override
    public HashMap<Position, Tile> getWorldLayout() {
        return tiles;
    }

    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    @Override
    public void setTileFocus(Position position) {
        for (GameObserver o : observers) {
            o.tileFocusChangedAt(position);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    private void worldChangedAt(Position position) {
        for (GameObserver o : observers) {
            o.worldChangedAt(position);
        }
    }

    private void observerTurnEnds(Player player, int age) {
        for (GameObserver o : observers) {
            o.turnEnds(player, age);
        }
    }

    private void performMovement(Position from, Position to) {
        if(getUnitAt(from) == null) return;
        Unit unit = getUnits().remove(from);
        int distance = getMoveDistance(from, to);
        unit.setMoveCount(unit.getMoveCount() - distance);
        addUnit(to, unit);
        boolean hasMovesLeft = unit.getMoveCount() <= 0;
        unit.setMoved(hasMovesLeft);
        worldChangedAt(from);
        worldChangedAt(to);
    }

    private int getMoveDistance(Position from, Position to) {
        int distance;
        int verticalDist = Math.abs(from.getRow() - to.getRow());
        int horizontalDist = Math.abs(from.getColumn() - to.getColumn());
        distance = Math.max(verticalDist, horizontalDist);
        return distance;
    }

    @Override
    public void endOfTurn() {
        if (getPlayerInTurn() == Player.RED) {
            playerinturn = Player.BLUE;
        } else {
            playerinturn = Player.RED;
            endOfRound();
        }
        observerTurnEnds(getPlayerInTurn(), getAge());
    }

    @Override
    public void endOfRound() {
        currentRound++;
        winningStrategy.incrementRoundsPlayed();
        currentage = agingStrategy.passTime(currentage);
        for (Position p : getCities().keySet()) {
            increaseProduction(p, 6);
            produceNewUnits(p);
        }

        //Enable moved units to move again
        for (Map.Entry<Position, Unit> unitEntry : getUnits().entrySet()) {
            Unit unit = unitEntry.getValue();
            Position position = unitEntry.getKey();
            unit.setMoveCount(unit.getMaxMove());
            unit.setMoved(false);
            worldChangedAt(position); // Det her fiksede vores infamous bug
        }
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        getCities().get(p).setWorkForceFocus(balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        getCities().get(p).setProductionFocus(unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        Unit unit = getUnitAt(p);
        actionStrategy.action(unit,p,this);
    }

    @Override
    public void increaseProduction(Position p, int amount) {
        City city = getCityAt(p);
        city.addTreasury(amount);
        getCities().put(p, city);
    }

    public void produceNewUnits(Position p) {
        boolean isFreePos = getFreePosition(p) != null;
        if (!isFreePos) return;
        if(getCityAt(p) != null) {
            productionStrategy.produceUnit(p, this);
        }
    }

    @Override
    public Position getFreePosition(Position cityPosition) {
        if(getUnitAt(cityPosition) == null){
            return  cityPosition;
        }
        for (Position p : Utility.get8neighborhoodOf(cityPosition)) {
            if (getUnitAt(p) == null && getTileAt(p).getTypeString() != GameConstants.MOUNTAINS && getTileAt(p).getTypeString() != GameConstants.OCEANS) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void endOfGame() {
        getWinner();
    }
}