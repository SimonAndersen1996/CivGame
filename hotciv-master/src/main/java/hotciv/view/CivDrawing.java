package hotciv.view;

import hotciv.framework.*;
import hotciv.view.figure.CityFigure;
import hotciv.view.figure.HotCivFigure;
import hotciv.view.figure.TextFigure;
import hotciv.view.figure.UnitFigure;
import minidraw.framework.*;
import minidraw.standard.ImageFigure;
import minidraw.standard.StandardFigureCollection;
import minidraw.standard.handlers.ForwardingFigureChangeHandler;
import minidraw.standard.handlers.StandardDrawingChangeListenerHandler;
import minidraw.standard.handlers.StandardSelectionHandler;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * CivDrawing is a specialized Drawing (MVC model component) from
 * MiniDraw that dynamically builds the list of Figures for MiniDraw
 * to render the Unit and other information objects that are visible
 * in the Game instance.
 * <p>
 * TODO: This is a TEMPLATE for the SWEA Exercise! This means
 * that it is INCOMPLETE and that there are several options
 * for CLEANING UP THE CODE when you add features to it!
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

public class CivDrawing implements Drawing, GameObserver {

    // CivDrawing uses standard implementations from the MiniDraw
    // library for many of its sub responsibilities.
    private final SelectionHandler selectionHandler;
    private final DrawingChangeListenerHandler listenerHandler;
    private final ForwardingFigureChangeHandler figureChangeListener;
    private final FigureCollection figureCollection;

    // A mapping between position to the Unit figure at that position
    // allowing us to track where units move
    private Map<Position, UnitFigure> positionToUnitFigureMap;
    private Map<Position, CityFigure> positionToCityFigureMap;

    /**
     * the Game instance that this CivDrawing is going to render units,
     * cities, ages, player-in-turn, from
     */
    protected Game game;

    public CivDrawing(DrawingEditor editor, Game game) {
        // Much of our behaviour can be delegated to standard MiniDraw
        // implementations, so we just reuse those...
        selectionHandler = new StandardSelectionHandler();
        listenerHandler = new StandardDrawingChangeListenerHandler();
        figureChangeListener = new ForwardingFigureChangeHandler(this,
                (StandardDrawingChangeListenerHandler) listenerHandler);
        figureCollection = new StandardFigureCollection(figureChangeListener);

        positionToUnitFigureMap = new HashMap<>();
        positionToCityFigureMap = new HashMap<>();

        // associate with game
        this.game = game;
        // register this unit drawing as listener to any game state
        // changes...
        game.addObserver(this);

        // ensure our drawing's figure collection of UnitFigures
        // reflects those present in the game
        synchronizeUnitFigureCollectionWithGameUnits();
        System.out.println("Call function right below");
        synchronizeCityFigureCollectionWithGameCities();
        // and the set of 'icons' in status panel represents game state
        synchronizeIconsWithGameState();
    }

    /**
     * The CivDrawing should not allow client side
     * units to add and manipulate figures; only figures
     * that renders game objects are relevant, and these
     * should be handled by observer events from the game
     * instance. Thus these methods are 'killed'.
     */
    @Override
    public Figure add(Figure arg0) {
        throw new RuntimeException("Should not be used, handled by Observing Game");
    }

    @Override
    public Figure remove(Figure arg0) {
        throw new RuntimeException("Should not be used, handled by Observing Game");
    }


    /**
     * Ensure our collection of unit figures match those of the
     * game's units.
     */
    protected void synchronizeUnitFigureCollectionWithGameUnits() {
        // iterate all tile positions and ensure that our figure
        // collection truthfully match that of game by adding/removing
        // figures.
        Position p;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                p = new Position(r, c);
                Unit unit = game.getUnitAt(p);
                UnitFigure unitFigure = positionToUnitFigureMap.get(p);
                figureCollection.remove(unitFigure);
                // Synchronize each tile position with figure collection
                if (unit != null) {
                    // if a unit is present in game, then
                    if (unitFigure == null) {
                        // if the associated unit figure has not been created, make it
                        unitFigure = createUnitFigureFor(p, unit);
                        // We add the figure both to our internal data structure
                        positionToUnitFigureMap.put(p, unitFigure);
                        // and of course to MiniDraw's figure collection for
                        // visual rendering
                        figureCollection.add(unitFigure);
                    }
                    else{
                        UnitFigure newUnit = createUnitFigureFor(p, unit);

                        figureCollection.add(newUnit);
                        positionToUnitFigureMap.put(p, newUnit);
                    }
                } else {
                    // no unit at tile, maybe there is a unitFigure wrongly here
                    if (unitFigure != null) {
                        positionToUnitFigureMap.remove(p);
                        figureCollection.remove(unitFigure);
                    }
                }


            }
        }
    }

    protected void synchronizeCityFigureCollectionWithGameCities() {
        System.out.println("Called function");
        // iterate all tile positions and ensure that our figure
        // collection truthfully match that of game by adding/removing
        // figures.
        Position p;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                p = new Position(r, c);
                City city = game.getCityAt(p);
                CityFigure cityFigure = positionToCityFigureMap.get(p);
                if (city != null) {
                    System.out.println("Found a city in game");
                    // if a city is present in game, then
                    if (cityFigure == null) {
                        // if the associated city figure has not been created, make it
                        cityFigure = createCityFigureFor(p, city);
                        // We add the figure both to our internal data structure
                        positionToCityFigureMap.put(p, cityFigure);
                        // and of course to MiniDraw's figure collection for
                        // visual rendering
                        figureCollection.add(cityFigure);
                        System.out.println("Adding city to GUI");
                    }
                } else {
                    // no city at tile, maybe there is a cityFigure wrongly here
                    if (cityFigure != null) {
                        positionToCityFigureMap.remove(p);
                        figureCollection.remove(cityFigure);
                    }
                }
            }
        }
    }

    /**
     * Create a figure representing a unit at given position
     */
    private UnitFigure createUnitFigureFor(Position p, Unit unit) {
        String type = unit.getTypeString();
        // convert the unit's Position to (x,y) coordinates
        Point point = new Point(GfxConstants.getXFromColumn(p.getColumn()),
                GfxConstants.getYFromRow(p.getRow()));
        UnitFigure unitFigure =
                new UnitFigure(type, point, unit);
        return unitFigure;
    }

    private CityFigure createCityFigureFor(Position p, City city) {
        Point point = new Point(GfxConstants.getXFromColumn(p.getColumn()),
                GfxConstants.getYFromRow(p.getRow()));
        CityFigure cityFigure =
                new CityFigure(city, point);
        return cityFigure;
    }

    // Figures representing icons (showing status in status panel)
    protected ImageFigure turnShieldIcon;
    protected TextFigure ageText;
    protected ImageFigure unitShieldIcon;
    protected ImageFigure unitTypeIcon;
    protected TextFigure unitMoveCountTxt;
    protected ImageFigure cityShieldIcon;
    protected ImageFigure produceIcon;
    protected ImageFigure balanceIcon;
    protected ImageFigure refreshIcon;

    protected void synchronizeIconsWithGameState() {
        // Note - we have to guard creating figures and adding
        // them to the collection, so we do not create multiple
        // instances; this method is called on every 'requestRedraw'
        if (turnShieldIcon == null) {
            turnShieldIcon =
                    new HotCivFigure("redshield",
                            new Point(GfxConstants.TURN_SHIELD_X,
                                    GfxConstants.TURN_SHIELD_Y),
                            GfxConstants.TURN_SHIELD_TYPE_STRING);
            // insert in delegate figure list to ensure graphical
            // rendering.
            figureCollection.add(turnShieldIcon);
        }
        updateTurnShield(game.getPlayerInTurn());

        // TODO: Further development to include rest of figures needed
        // for other status panel info, like age, etc.
        if (ageText == null) {
            ageText = new TextFigure("4000 BC", new Point(GfxConstants.AGE_TEXT_X, GfxConstants.AGE_TEXT_Y));
            figureCollection.add(ageText);
        }
        updateAgeText(game.getAge());

        // Create a refresh button
        if (refreshIcon == null) {
            refreshIcon = new HotCivFigure("refresh", new Point(GfxConstants.REFRESH_BUTTON_X,
                    GfxConstants.REFRESH_BUTTON_Y), GfxConstants.REFRESH_BUTTON);
            figureCollection.add(refreshIcon);
        }
    }

    // === Observer Methods ===
    public void worldChangedAt(Position pos) {
        Unit u = game.getUnitAt(pos);
        UnitFigure oldUf = positionToUnitFigureMap.get(pos);
        if (u == null) {
            // Unit has been removed
            UnitFigure uf = positionToUnitFigureMap.remove(pos);
            figureCollection.remove(uf);
        } else {
            if (oldUf == null) {
                // Unit has appeared
                UnitFigure uf = createUnitFigureFor(pos, u);
                positionToUnitFigureMap.put(pos, uf);
                figureCollection.add(uf);
            }
            else {
                // Might be a different Unit
                figureCollection.remove(oldUf);
                UnitFigure newUf = createUnitFigureFor(pos, u);
                positionToUnitFigureMap.put(pos, newUf);
                figureCollection.add(newUf);
            }
        }
        // TODO: Cities may change on position as well
        City c = game.getCityAt(pos);

        // A city is found
        if (c != null) {
            if (u != null) {
                boolean hasSameOwner = c.getOwner() == u.getOwner();
                if (!hasSameOwner) {
                    // Enemy conquers city
                    c.setOwner(u.getOwner());

                }
            }
            else {
            // Display the city
            CityFigure cf = createCityFigureFor(pos, c);
            positionToCityFigureMap.put(pos, cf);
            figureCollection.add(cf);
            }
        }
    }

    public void turnEnds(Player nextPlayer, int age) {
        updateTurnShield(nextPlayer);
        // TODO: Age output pending
        // Let's assume that age advances for each turn and not rounds
        // for the sake of testing.
        // Since that is what the template of turnEnds implies by having
        // the above TODO: Age output pending.
        // Most likely a mistake, age advances at end of rounds - Andy
        updateAgeText(age);
        synchronizeIconsWithGameState();
        synchronizeUnitFigureCollectionWithGameUnits();
        synchronizeCityFigureCollectionWithGameCities();
    }

    private void updateTurnShield(Player nextPlayer) {
        String playername = "red";
        if (nextPlayer == Player.BLUE) {
            playername = "blue";
        }
        turnShieldIcon.set(playername + "shield",
                new Point(GfxConstants.TURN_SHIELD_X,
                        GfxConstants.TURN_SHIELD_Y));
    }

    private void updateAgeText(int age) {
        if (age < 0) {
            ageText.setText(-age + " BC");
        } else {
            ageText.setText(age + " AD");
        }
    }

    public void tileFocusChangedAt(Position position) {
        // TODO: Implementation pending
        Unit unit = game.getUnitAt(position);
        displayUnitStatus(unit);
        // TODO: Implement focus on a city
        City city = game.getCityAt(position);
        displayCityStatus(city);
    }

    private void displayCityStatus(City city) {
        if (city != null) {
            // Display team color
            displayCityShieldIcon(city);

            // Display unit production type
            displayProduceIcon(city);

            // Display work focus type
            displayBalanceIcon(city);
        } else {
            removeCityStatus();
        }
    }

    private void displayBalanceIcon(City city) {
        figureCollection.remove(balanceIcon);
        String balanceType = city.getWorkforceFocus();
        balanceIcon = new HotCivFigure(balanceType,
                new Point(GfxConstants.WORKFORCEFOCUS_X,
                        GfxConstants.WORKFORCEFOCUS_Y),
                GfxConstants.CITY_TYPE_STRING);
        figureCollection.add(balanceIcon);
    }

    private void displayProduceIcon(City city) {
        figureCollection.remove(produceIcon);
        String productionType = city.getProductionType();
        produceIcon = new HotCivFigure(productionType,
                new Point(GfxConstants.CITY_PRODUCTION_X,
                        GfxConstants.CITY_PRODUCTION_Y),
                GfxConstants.UNIT_TYPE_STRING);
        figureCollection.add(produceIcon);
    }

    private void displayCityShieldIcon(City city) {
        figureCollection.remove(cityShieldIcon);
        String shieldColor = getShieldColor(city.getOwner());
        cityShieldIcon = new HotCivFigure(shieldColor,
                new Point(GfxConstants.CITY_SHIELD_X,
                        GfxConstants.CITY_SHIELD_Y),
                GfxConstants.UNIT_SHIELD_TYPE_STRING);
        figureCollection.add(cityShieldIcon);
    }

    private void removeCityStatus() {
        // Remove team color
        figureCollection.remove(cityShieldIcon);
        cityShieldIcon = null;
        // Remove production and workforcefocus type
        figureCollection.remove(produceIcon);
        produceIcon = null;
        figureCollection.remove(balanceIcon);
        balanceIcon = null;
    }

    private void removeUnitStatus() {
        // Remove team color
        figureCollection.remove(unitShieldIcon);
        unitShieldIcon = null;
        // Remove unit type
        figureCollection.remove(unitTypeIcon);
        unitTypeIcon = null;
        // Remove unit move count
        figureCollection.remove(unitMoveCountTxt);
        unitMoveCountTxt = null;
    }

    private void displayUnitStatus(Unit unit) {
        if (unit != null) {
            // Display team color in status view
            displayUnitShieldIcon(unit);

            // Display unit type in status view
            displayUnitTypeIcon(unit);

            // Display unit move count in status view
            displayUnitMoveCount(unit);
        } else {
            removeUnitStatus();
        }
    }

    private void displayUnitMoveCount(Unit unit) {
        String moveCount = unit.getMoveCount() + "";
        if (unit.getMoved()) {
            moveCount = "0";
        }
        figureCollection.remove(unitMoveCountTxt);
        unitMoveCountTxt = new TextFigure(moveCount,
                new Point(GfxConstants.UNIT_COUNT_X, GfxConstants.UNIT_COUNT_Y));
        figureCollection.add(unitMoveCountTxt);
    }

    private void displayUnitTypeIcon(Unit unit) {
        String unitType = unit.getTypeString();
        figureCollection.remove(unitTypeIcon);
        unitTypeIcon = new HotCivFigure(unitType,
                new Point(GfxConstants.UNIT_SHIELD_X - 60,
                        GfxConstants.UNIT_SHIELD_Y),
                GfxConstants.UNIT_TYPE_STRING);
        figureCollection.add(unitTypeIcon);
    }

    private void displayUnitShieldIcon(Unit unit) {
        String shieldColor = getShieldColor(unit.getOwner());
        figureCollection.remove(unitShieldIcon);
        unitShieldIcon = new HotCivFigure(shieldColor,
                new Point(GfxConstants.UNIT_SHIELD_X,
                        GfxConstants.UNIT_SHIELD_Y),
                GfxConstants.UNIT_SHIELD_TYPE_STRING);
        figureCollection.add(unitShieldIcon);
    }

    private String getShieldColor(Player player) {
        if (player == Player.RED) return "redshield";
        else return "blueshield";
    }

    @Override
    public void requestUpdate() {
        // A request to redraw from scratch, so we
        // synchronize with all game state
        synchronizeUnitFigureCollectionWithGameUnits();
        synchronizeIconsWithGameState();
        // TODO: Cities pending
        synchronizeCityFigureCollectionWithGameCities();
    }

    @Override
    public void addToSelection(Figure arg0) {
        selectionHandler.addToSelection(arg0);
    }

    @Override
    public void clearSelection() {
        selectionHandler.clearSelection();
    }

    @Override
    public void removeFromSelection(Figure arg0) {
        selectionHandler.removeFromSelection(arg0);
    }

    @Override
    public List<Figure> selection() {
        return selectionHandler.selection();
    }

    @Override
    public void toggleSelection(Figure arg0) {
        selectionHandler.toggleSelection(arg0);
    }

    @Override
    public void figureChanged(FigureChangeEvent arg0) {
        figureChangeListener.figureChanged(arg0);
    }

    @Override
    public void figureInvalidated(FigureChangeEvent arg0) {
        figureChangeListener.figureInvalidated(arg0);
    }

    @Override
    public void addDrawingChangeListener(DrawingChangeListener arg0) {
        listenerHandler.addDrawingChangeListener(arg0);
    }

    @Override
    public void removeDrawingChangeListener(DrawingChangeListener arg0) {
        listenerHandler.removeDrawingChangeListener(arg0);
    }

    @Override
    public Figure findFigure(int arg0, int arg1) {
        return figureCollection.findFigure(arg0, arg1);
    }

    @Override
    public Figure zOrder(Figure figure, ZOrder order) {
        return figureCollection.zOrder(figure, order);
    }

    @Override
    public Iterator<Figure> iterator() {
        return figureCollection.iterator();
    }

    @Override
    @Deprecated
    public void lock() {
        // MiniDraw 2 has deprecated these methods...
    }

    @Override
    @Deprecated
    public void unlock() {
        // MiniDraw 2 has deprecated these methods...
    }
}
