package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.FactoryAlpha;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import hotciv.standard.impls.UnitImpl;
import org.junit.jupiter.api.*;

import static hotciv.standard.LogToggler.toggleDecorator;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

/**
 * Skeleton class for AlphaCiv test cases
 * <p>
 * Updated Aug 2020 for JUnit 5 includes
 * Updated Oct 2015 for using Hamcrest matchers
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
public class TestAlphaCiv {
    private final Position oneOne = new Position(1, 1);
    private final Position fourOne = new Position(4, 1);
    private final Position twoTwo = new Position(2, 2);
    private final Position oneZero = new Position(1, 0);
    private final Position twoZero = new Position(2, 0);
    private final Position twoOne = new Position(2, 1);
    private final Position threeOne = new Position(3, 1);
    private final Position threeTwo = new Position(3, 2);
    private final Position threeThree = new Position(3, 3);
    private final Position threeZero = new Position(3, 0);
    private Game currentGame, decorateeGame;
    private City city;
    private Player red, blue;

    /**
     * Fixture for alphaciv testing.
     */
    @BeforeEach
    public void setUp() {
        currentGame = new GameImpl(new FactoryAlpha());
        decorateeGame = currentGame;
        red = Player.RED;
        blue = Player.BLUE;
        city = new CityImpl(red);
        currentGame.addPlayer(red);
        currentGame.addPlayer(blue);
    }

    // FRS p. 455 states that 'Red is the first player to take a turn'.
    @Test
    public void shouldBeRedAsStartingPlayer() {
        // TODO: reenable the assert below to get started...
        assertThat(currentGame.getPlayerInTurn(), is(red));
    }

    @Test
    public void shouldBeTwoPlayers() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(currentGame.getPlayers().size(), is(2));
    }

    @Test
    public void shouldRedCityAtOneOne() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(currentGame.getCityAt(oneOne).getOwner(), is(red));
    }

    @Test
    public void shouldBeOceanAtOneZero() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(currentGame.getTileAt(oneZero).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void shouldCityAtOneOneBeOne() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(currentGame.getCityAt(oneOne).getSize(), is(1));
    }

    @Test
    public void shouldCityPopAlwaysBeOne() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(currentGame.getCityPopulation(city), is(1));
    }

    @Test
    public void shouldBeBlueAfterRed() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(currentGame.getPlayerInTurn(), is(red));
        currentGame.endOfTurn();
        assertThat(currentGame.getPlayerInTurn(), is(blue));
    }

    @Test
    public void gameShouldStartAtRoundOne() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(currentGame.getCurrentRound(), is(1));
    }

    @Test
    public void shouldBeRoundEndAfterBothPlayers() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        gameShouldStartAtRoundOne();
        currentGame.endOfTurn();
        currentGame.endOfTurn();
            assertThat(currentGame.getCurrentRound(), is(2));
    }

    @Test
    public void ageShouldStartAt4000BC() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(currentGame.getAge(), is(-4000));
    }

    @Test
    public void ageShouldIncreaseHundredAfterRound() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        currentGame.endOfRound();
        assertThat(currentGame.getAge(), is(-3900));
    }

    @Test
    public void shouldRedWinAt3000BC() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        while (currentGame.getAge() < -3000) {
            currentGame.endOfRound();
        }
        assertThat(currentGame.getAge(), is(-3000));
        assertThat(currentGame.getWinner(), is(red));
    }

    @Test
    public void shouldBeNoWinnerAt3500BC() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        while (currentGame.getAge() < -3500) {
            currentGame.endOfRound();
        }
        assertThat(currentGame.getAge(), is(-3500));
        assertThat(currentGame.getWinner(), is(nullValue()));
    }


    @Test
    public void citiesShouldStartWithZeroProduction() {
        currentGame = toggleDecorator(currentGame, decorateeGame);
        assertThat(city.getTreasury(), is(0));
    }

    @Test
    public void citiesShouldProduce6ProductionAfterRound() {
        currentGame.endOfRound();
        assertThat(currentGame.getCityAt(oneOne).getTreasury(), is(6));
    }

    @Test
    public void citiesShouldProduceUnitAt10TreasureAtCity() {
        assertThat(currentGame.getUnitAt(oneOne), is(nullValue()));
        currentGame.endOfRound();
        currentGame.endOfRound();
        assertThat(currentGame.getCityAt(oneOne).getTreasury(), is(2));
        assertThat(currentGame.getUnitAt(oneOne), is(notNullValue()));
    }

    @Test
    public void redCityAtOneOneProduceArcherAt10Treasure() {
        assertThat(currentGame.getUnitAt(oneOne), is(nullValue()));
        currentGame.endOfRound();
        currentGame.endOfRound();
        assertThat(currentGame.getCityAt(oneOne).getTreasury(), is(2));
        assertThat(currentGame.getUnitAt(oneOne).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void citiesShouldChangeWorkFocus() {
        assertThat(currentGame.getCityAt(oneOne), is(notNullValue()));
        assertThat(currentGame.getCityAt(oneOne).getWorkforceFocus(), is(GameConstants.foodFocus));
        currentGame.changeWorkForceFocusInCityAt(oneOne, GameConstants.productionFocus);
        assertThat(currentGame.getCityAt(oneOne).getWorkforceFocus(), is(GameConstants.productionFocus));
    }

    @Test
    public void citiesShouldChangeProductionFocus() {
        City cityAt = currentGame.getCityAt(oneOne);

        assertThat(cityAt.getProductionType(), is(GameConstants.ARCHER));
        currentGame.changeProductionInCityAt(oneOne, GameConstants.SETTLER);
        assertThat(cityAt.getProductionType(), is(GameConstants.SETTLER));
    }

    @Test
    public void redUnitsShouldSpawnOnTheirCity() {
        assertThat(currentGame.getCityAt(oneOne).getOwner(), is(red));
        assertThat(currentGame.getUnitAt(oneOne), is(nullValue()));
        currentGame.endOfRound();
        currentGame.endOfRound();
        assertThat(currentGame.getUnitAt(oneOne).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getUnitAt(oneOne).getOwner(), is(red));
    }

    @Test
    public void blueUnitsShouldSpawnOnTheirCity() {
        assertThat(currentGame.getCityAt(fourOne).getOwner(), is(blue));
        assertThat(currentGame.getUnitAt(fourOne), is(nullValue()));
        currentGame.endOfRound();
        currentGame.endOfRound();
        assertThat(currentGame.getUnitAt(fourOne).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getUnitAt(fourOne).getOwner(), is(blue));
    }

    @Test
    public void unitsShouldSpawnOnAdjacentTilesIfOccupied() {
        for (int i = 0; i < 10; i++) {
            currentGame.endOfRound();
        }
        assertThat(currentGame.getCityAt(fourOne).getTreasury(), is(0));
        assertThat(currentGame.getUnitAt(fourOne), is(notNullValue()));
        assertThat(currentGame.getUnitAt(threeOne), is(notNullValue()));
        assertThat(currentGame.getUnitAt(threeTwo), is(notNullValue()));
        assertThat(currentGame.getUnitAt(new Position(4, 2)), is(notNullValue()));
        assertThat(currentGame.getUnitAt(new Position(5, 2)), is(notNullValue()));
        assertThat(currentGame.getUnitAt(new Position(5, 1)), is(notNullValue()));
        assertThat(currentGame.getUnitAt(new Position(5, 0)), is(notNullValue()));
        assertThat(currentGame.getUnitAt(new Position(4, 0)), is(nullValue()));
    }

    @Test
    public void unitsShouldNotSpawnOnMountainsOrOceans() {
        assertThat(currentGame.getUnitAt(twoTwo), is(nullValue()));
        assertThat(currentGame.getUnitAt(oneZero), is(nullValue()));
        for (int i = 0; i < 10; i++) {
            currentGame.endOfRound();
        }
        assertThat(currentGame.getUnitAt(twoTwo), is(nullValue()));
        assertThat(currentGame.getUnitAt(oneZero), is(nullValue()));
    }

    @Test
    public void unitShouldBeAtTwoZero() {
        assertThat(currentGame.getUnitAt(twoZero), is(notNullValue()));
    }

    @Test
    public void unitShouldBeRedArcherAtTwoZero() {
        assertThat(currentGame.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getUnitAt(twoZero).getOwner(), is(red));
    }

    @Test
    public void unitsShouldMove() {
        assertThat(currentGame.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getUnitAt(twoOne), is(nullValue()));
        currentGame.moveUnit(twoZero, twoOne);
        assertThat(currentGame.getUnitAt(twoZero), is(nullValue()));
        assertThat(currentGame.getUnitAt(twoOne).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void unitsShouldOnlyMove1TilePerRound() {
        assertThat(currentGame.moveUnit(twoZero, threeThree), is(false));
    }

    @Test
    public void unitsShouldNotMoveOnOceanTiles() {
        assertThat(currentGame.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getTileAt(oneZero).getTypeString(), is(GameConstants.OCEANS));
        currentGame.moveUnit(twoZero, oneZero);
        assertThat(currentGame.getUnitAt(oneZero), is(nullValue()));
    }

    @Test
    public void unitsShouldNotMoveOnMountainTiles() {
        assertThat(currentGame.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getTileAt(twoTwo).getTypeString(), is(GameConstants.MOUNTAINS));
        currentGame.moveUnit(twoZero, twoTwo);
        assertThat(currentGame.getUnitAt(twoTwo), is(nullValue()));
    }

    @Test
    public void unitsShouldNotAttackSameOwnerUnits() {
        for (int i = 0; i < 4; i++) {
            currentGame.endOfRound();
        }

        //We produce a blue archer at 3,1 and we have a blue legion at 3,2
        assertThat(currentGame.getUnitAt(threeOne).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getUnitAt(threeOne).getOwner(), is(blue));
        assertThat(currentGame.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        assertThat(currentGame.getUnitAt(threeOne).getOwner(), is(blue));

        //We try and move the blue archer into the blue legion (attack)
        currentGame.moveUnit(threeOne, threeTwo);

        //The units have not moved
        assertThat(currentGame.getUnitAt(threeOne).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
    }


    @Test
    public void redShouldNotMoveBlueUnit() {
        assertThat(currentGame.getPlayerInTurn(), is(red));
        assertThat(currentGame.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        currentGame.moveUnit(threeTwo, threeThree);
        assertThat(currentGame.getUnitAt(threeThree), is(nullValue()));
    }

    @Test
    public void blueShouldNotMoveRedUnit() {
        currentGame.endOfTurn();
        assertThat(currentGame.getPlayerInTurn(), is(blue));
        assertThat(currentGame.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        currentGame.moveUnit(twoZero, threeZero);
        assertThat(currentGame.getUnitAt(threeZero), is(nullValue()));
    }

    @Test
    public void unitsCanOnlyMoveOncePerTurn() {
        assertThat(currentGame.getPlayerInTurn(), is(red));
        assertThat(currentGame.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        currentGame.moveUnit(twoZero, twoOne);
        assertThat(currentGame.getUnitAt(twoOne).getTypeString(), is(GameConstants.ARCHER));
        currentGame.moveUnit(twoOne, threeOne);
        assertThat(currentGame.getUnitAt(threeOne), is(nullValue()));
    }

    @Test
    public void sameUnitCanMoveAfterRoundEnd() {
        assertThat(currentGame.getPlayerInTurn(), is(red));
        assertThat(currentGame.getUnitAt(twoZero).getTypeString(), is(GameConstants.ARCHER));
        currentGame.moveUnit(twoZero, twoOne);
        assertThat(currentGame.getUnitAt(twoOne).getTypeString(), is(GameConstants.ARCHER));
        currentGame.endOfRound();
        currentGame.moveUnit(twoOne, threeOne);
        assertThat(currentGame.getUnitAt(threeOne).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void unitsShouldRemoveOtherUnitIfSameTile() {
        Unit archer = new UnitImpl(GameConstants.ARCHER, red);
        currentGame.addUnit(threeOne, archer);

        assertThat(archer.getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getUnitAt(threeTwo).getTypeString(), is(GameConstants.LEGION));
        currentGame.moveUnit(threeOne, threeTwo);
        assertThat(currentGame.getUnitAt(threeTwo).getTypeString(), is(GameConstants.ARCHER));
        currentGame.endOfRound();
        currentGame.moveUnit(threeTwo, threeOne);
        assertThat(currentGame.getUnitAt(threeOne).getTypeString(), is(GameConstants.ARCHER));
        assertThat(currentGame.getUnitAt(threeTwo), is(nullValue()));
    }

    /**
     * Test for GammaCiv
     */
    @Test
    public void archerShouldDoubleDefense() {

    }

    /**
     * REMOVE ME. Not a test of HotCiv, just an example of the
     * matchers that the hamcrest library has...
     */
    @Test
    public void shouldDefinitelyBeRemoved() {
        // Matching null and not null values
        // 'is' require an exact match
        String s = null;
        assertThat(s, is(nullValue()));
        s = "Ok";
        assertThat(s, is(notNullValue()));
        assertThat(s, is("Ok"));

        // If you only validate substrings, use containsString
        assertThat("This is a dummy test", containsString("dummy"));

        // Match contents of Lists
        List<String> l = new ArrayList<String>();
        l.add("Bimse");
        l.add("Bumse");
        // Note - ordering is ignored when matching using hasItems
        assertThat(l, hasItems(new String[]{"Bumse", "Bimse"}));

        // Matchers may be combined, like is-not
        assertThat(l.get(0), is(not("Bumse")));
    }


}