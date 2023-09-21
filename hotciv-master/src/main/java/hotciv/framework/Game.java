package hotciv.framework;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Game is the central interface allowing a client to access and
 * modify the state of a HotCiv game.
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

public interface Game {

    // === Accessor/Query methods ===================================

    /**
     * return a specific tile.
     * Precondition: Position p is a valid position in the world.
     *
     * @param p the position in the world that must be returned.
     * @return the tile at position p.
     */
    Tile getTileAt(Position p);

    /**
     * return the uppermost unit in the stack of units at position 'p'
     * in the world.
     * Precondition: Position p is a valid position in the world.
     *
     * @param p the position in the world.
     * @return the unit that is at the top of the unit stack at position
     * p, OR null if no unit is present at position p.
     */
    Unit getUnitAt(Position p);

    /**
     * return the city at position 'p' in the world.
     * Precondition: Position p is a valid position in the world.
     *
     * @param p the position in the world.
     * @return the city at this position or null if no city here.
     */
    City getCityAt(Position p);

    /**
     * return the player that is 'in turn', that is, is able to
     * move units and manage cities.
     *
     * @return the player that is in turn
     */
    Player getPlayerInTurn();

    /**
     * return the player that has won the game.
     *
     * @return the player that has won. If the game is still
     * not finished then return null.
     */
    Player getWinner();

    /**
     * return the age of the world. Negative numbers represent a world
     * age BC (-4000 equals 4000 BC) while positive numbers are AD.
     *
     * @return world age.
     */
    int getAge();

    // === Mutator/Command methods ======================================

    /**
     * move a unit from one position to another. If that other position
     * is occupied by an opponent unit, a battle is conducted leading to
     * either victory or defeat. If victorious then the opponent unit is
     * removed from the game and the move conducted. If defeated then
     * the attacking unit is removed from the game. If a successful move
     * results in the unit entering the position of a city then this
     * city becomes owned by the owner of the moving unit.
     * Precondition: both from and to are within the limits of the
     * world.  Precondition: there is a unit located at position from.
     *
     * @param from the position that the unit has now
     * @param to   the position the unit should move to
     * @return true if the move is valid (no mountain, move is valid
     * under the rules of the game variant etc.), and false
     * otherwise. If false is returned, the unit stays in the same
     * position and its "move" is intact (it can be moved to another
     * position.)
     */
    boolean moveUnit(Position from, Position to);

    /**
     * Tell the game that the current player has
     * finished his/her turn. The next player is then
     * in turn. If all players have had their turns
     * then do end-of-round processing:
     * A) restore all units' move counts
     * B) produce food and production in all cities
     * C) produce units in all cities (if enough production)
     * D) increase population size in all cities (if enough food)
     * E) increment the world age.
     */
    void endOfTurn();

    /**
     * Get the population of the given city c
     * @param c is the City to get the population of
     * @return the population of City c
     */
    int getCityPopulation(City c);

    /**
     * change the work force's focus in a city, i.e. what
     * kind of production there will be emphasis on in the city.
     * Precondition: there is a city at location 'p'.
     *
     * @param p       the position of the city whose focus
     *                should be changed.
     * @param balance a string defining the focus of the work
     *                force in a city. Valid values are at least
     *                GameConstants.productionFocus and
     *                GameConstants.foodFocus.
     */
    void changeWorkForceFocusInCityAt(Position p, String balance);

    /**
     * change the type of unit a city will produce next.
     * Precondition: there is a city at location 'p'.
     * Predondition: the unit type is a valid type.
     *
     * @param p        the position of the city whose production
     *                 should be changed.
     * @param unitType a string defining the type of unit that the
     *                 city should produce next.
     */
    void changeProductionInCityAt(Position p, String unitType);

    /**
     * perform the action associated with the unit at position p.
     * Example: a settler unit may create a new city at its location.
     * Precondition: there is a unit at location 'p'.
     * @param p the position of a unit that must perform its action.
     *          Nothing happens in case the unit has no associated action.
     */
    void performUnitActionAt(Position p);

    /**
     * End the current round, moving 100 years forward, increase
     * production of each City by 6 and allow all units to move
     * again.
     */
    void endOfRound();

    /**
     * End the game and get the winner.
     */
    void endOfGame();

    /**
     * Get the current round.
     * @return the current round.
     */
    int getCurrentRound();

    /**
     * When a new City is built, it is added to the collection of Cities
     * of the game, allowing the game to track the position of the City.
     * @param pos is the position of the new City.
     * @param city is the new City.
     */
    void addCity(Position pos, City city);

    /**
     * When a new Unit is produced, it is added to the collection of Units
     * of the game, allowing the game to track the position of the Unit.
     * @param pos is the position of the new Unit.
     * @param unit is the new Unit.
     */
    void addUnit(Position pos, Unit unit);

    /**
     * Retrieve the collection of all existing Cities.
     * @return the HashMap of all Cities.
     */
    HashMap<Position, City> getCities();

    /**
     * Retrieve the collection of all Players of the game.
     * @return the ArrayList of all Players
     */
    ArrayList<Player> getPlayers();

    /**
     * Add a new Player to the game.
     * @param p is the new Player to be added.
     */
    void addPlayer(Player p);

    /**
     * Get the collection of all currently existing Units in the game.
     * @return the HashMap of all existing Units.
     */
    HashMap<Position, Unit> getUnits();

    /**
     * Set age for convenience of testing aging.
     * @param a is the age to set
     */
    void setAge(int a);

    void increaseProduction(Position p, int amount);

    Position getFreePosition(Position cityPosition);

    void removeUnitAt(Position p);

    /**
     * Get the layout of the world.
     */
    void addObserver(GameObserver observer);

    HashMap<Position, Tile> getWorldLayout();
    void setTileFocus(Position position);

    String getId();

}
