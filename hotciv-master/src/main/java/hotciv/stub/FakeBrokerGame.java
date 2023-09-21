package hotciv.stub;

import frds.broker.Servant;
import hotciv.Common.OperationNames;
import hotciv.framework.*;
import hotciv.standard.ActionGamma;
import hotciv.standard.impls.CityImpl;
import hotciv.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/** FakeObject implementation for Game. Base your
 * development of Tools and CivDrawing on this test double,
 * and gradually add EVIDENT TEST = simple code
 * to it, to support your development of all features
 * necessary for a complete CivDrawing and your suite
 * of Tools.
 *
 This source code is from the book
 "Flexible, Reliable Software:
 Using Patterns and Agile Development"
 published 2010 by CRC Press.
 Author:
 Henrik B Christensen
 Department of Computer Science
 Aarhus University

 Please visit http://www.baerbak.com/ for further information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

public class FakeBrokerGame implements Game, Servant {

    private HashMap<Position, Unit> unitMap;
    private HashMap<Position, City> cityMap;
    private int age = -4000;
    private ActionStrategy actionStrategy = new ActionGamma();
    private String lastMethod;

    public Unit getUnitAt(Position p) {
        return unitMap.get(p);
    }
    private final Position position_of_green_city = new Position(1,1);

    public boolean moveUnit(Position from, Position to) {
        return true;
    }

    // === Turn handling ===
    private Player inTurn = Player.GREEN;


    public void endOfTurn() {
        System.out.println( "-- FakeObjectGame / endOfTurn called." );
        inTurn = (getPlayerInTurn() == Player.GREEN ?
                Player.YELLOW :
                Player.GREEN );
        // no age increments implemented...
        // Now it is
        incrementAge();
        setLastMethodCalled(OperationNames.GAME_END_OF_TURN);
    }

    @Override
    public int getCityPopulation(City c) {
        return 69;
    }

    @Override
    public Player getPlayerInTurn() { return Player.GREEN; }

    // === Observer handling ===
    protected GameObserver gameObserver;
    // observer list is fake code, only having a single
    // one, suffices for development.
    public void addObserver(GameObserver observer) {
        gameObserver = observer;
        setLastMethodCalled(OperationNames.GAME_ADD_OBSERVER);
    }

    public FakeBrokerGame(){
        defineWorld();
        // Put some units into play
        unitMap = new HashMap<>();
        unitMap.put(new Position(2,0), new StubUnit( GameConstants.ARCHER, Player.RED ));
        unitMap.put(new Position(3,2), new StubUnit( GameConstants.LEGION, Player.BLUE ));
        unitMap.put(new Position(3,3), new StubUnit( GameConstants.LEGION, Player.BLUE ));
        unitMap.put(new Position(4,2), new StubUnit( GameConstants.SETTLER, Player.RED ));
        unitMap.put(new Position(6,3), new StubUnit( ThetaConstants.SANDWORM, Player.RED ));
        unitMap.put(new Position(7,3), new StubUnit( GameConstants.ARCHER, Player.BLUE));
        inTurn = Player.RED;

        // Put some cities into play
        cityMap = new HashMap<>();
        cityMap.put(new Position(1,1), new CityImpl(Player.GREEN));
        CityImpl redCity = new CityImpl(Player.RED);
        redCity.setProductionFocus(GameConstants.LEGION);
        redCity.setWorkForceFocus(GameConstants.productionFocus);
        cityMap.put(new Position(3,3), new CityImpl(Player.YELLOW));
        cityMap.put(new Position(2,4), redCity);
        cityMap.put(new Position(7,7), new StubCity(Player.YELLOW, 69));
    }

    // A simple implementation to draw the map of DeltaCiv
    protected Map<Position,Tile> world;
    public Tile getTileAt( Position p ) { return world.get(p); }

    /** define the world.
     */
    protected void defineWorld() {
        world = new HashMap<Position,Tile>();
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                Position p = new Position(r,c);
                world.put( p, new StubTile(GameConstants.PLAINS));
            }
        }
        // Create a little area around the theta unit of special terrain
        world.put(new Position(5,4), new StubTile(ThetaConstants.DESERT));
        world.put(new Position(6,2), new StubTile(ThetaConstants.DESERT));
        world.put(new Position(6,3), new StubTile(ThetaConstants.DESERT));
        world.put(new Position(6,4), new StubTile(ThetaConstants.DESERT));
        world.put(new Position(6,5), new StubTile(ThetaConstants.DESERT));
        world.put(new Position(7,3), new StubTile(ThetaConstants.DESERT));
        world.put(new Position(7,4), new StubTile(ThetaConstants.DESERT));
        world.put(new Position(7,5), new StubTile(ThetaConstants.DESERT));
    }

    // TODO: Add more fake object behaviour to test MiniDraw updating
    public City getCityAt( Position p ) {
        if (p == position_of_green_city){
            return new StubCity(Player.GREEN, 4);
        }
        return cityMap.get(p);
    }

    public Player getWinner() { return Player.YELLOW;}

    public int getAge() { return 420; }

    public void changeWorkForceFocusInCityAt( Position p, String balance ) {
        setLastMethodCalled(OperationNames.GAME_CHANGE_WORKFORCE_FOCUS);
    }

    public void changeProductionInCityAt( Position p, String unitType ) {
        setLastMethodCalled(OperationNames.GAME_CHANGE_PRODUCTION);
    }

    public void performUnitActionAt( Position p ) {
        Unit unit = getUnitAt(p);
        actionStrategy.action(unit, p, this);
        gameObserver.worldChangedAt(p);
        for (Position pos : Utility.get8neighborhoodOf(p)) {
            gameObserver.worldChangedAt(pos);
        }
        setLastMethodCalled(OperationNames.GAME_ACTION);
    }

    private void incrementAge() {
        age += 100;
    }

    @Override
    public void endOfRound() {
        setLastMethodCalled(OperationNames.GAME_END_OF_ROUND);
    }

    @Override
    public void endOfGame() {
        setLastMethodCalled(OperationNames.GAME_END);
    }

    @Override
    public int getCurrentRound() {
        return 666;
    }

    @Override
    public void addCity(Position pos, City city) {
        getCities().put(pos, city);
        gameObserver.worldChangedAt(pos);
        setLastMethodCalled(OperationNames.GAME_ADD_CITY);
    }

    @Override
    public void addUnit(Position pos, Unit unit) {
        setLastMethodCalled(OperationNames.GAME_ADD_UNIT);
    }

    @Override
    public HashMap<Position, City> getCities() {
        return cityMap;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return null;
    }

    @Override
    public void addPlayer(Player p) {

    }

    @Override
    public HashMap<Position, Unit> getUnits() {
        return unitMap;
    }

    @Override
    public void setAge(int a) {

    }

    @Override
    public void increaseProduction(Position p, int amount) {

    }

    @Override
    public Position getFreePosition(Position cityPosition) {
        return new Position(3,3);
    }

    @Override
    public void removeUnitAt(Position p) {
        getUnits().remove(p);
        setLastMethodCalled(OperationNames.GAME_REMOVE_UNIT);
    }

    @Override
    public HashMap<Position, Tile> getWorldLayout() {
        return null;
    }

    public void setTileFocus(Position position) {
        // TODO: setTileFocus implementation pending.
        System.out.println("-- FakeObjectGame / setTileFocus called.");
        gameObserver.tileFocusChangedAt(position);
        setLastMethodCalled(OperationNames.GAME_SET_TILE_FOCUS);
    }

    @Override
    public String getId() {
        return null;
    }

    public void setLastMethodCalled(String method) {
        lastMethod = method;
    }

    public String getLastMethodCalled(){
        return lastMethod;
    }
}