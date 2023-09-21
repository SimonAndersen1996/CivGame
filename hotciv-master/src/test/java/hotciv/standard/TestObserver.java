package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.FactoryGamma;
import hotciv.standard.factories.FactorySemi;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import hotciv.standard.impls.ObserverSpy;
import hotciv.standard.impls.UnitImpl;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestObserver {
    private Game game = new GameImpl(new FactorySemi());
    private ObserverSpy spy = new ObserverSpy();
    private Position oneOne = new Position(1, 1);
    private Position oneTwo = new Position(1, 2);
    private Position threeEight = new Position(3,8);
    private Position threeNine = new Position(3,9);
    private String worldChange = "worldChangedAt";


    @BeforeEach
    public void setup() {
        game.addObserver(spy);
        game.addPlayer(Player.RED);
        game.addPlayer(Player.BLUE);
    }

    @Test
    public void testAddUnit() {
        game.addUnit(oneOne, new UnitImpl(GameConstants.ARCHER, Player.RED));
        assertThat(spy.lastMethodCalled(), is(worldChange));
    }

    @Test
    public void testAddCity() {
        game.addCity(oneOne, new CityImpl(Player.RED));
        assertThat(spy.lastMethodCalled(), is(worldChange));
    }

    @Test
    public void testMovedUnit() {
        game.moveUnit(threeEight, threeNine);
        assertThat(spy.lastMethodCalled(), is(worldChange));
    }

    @Test
    public void testRemoveUnit() {
        game.removeUnitAt(threeEight);
        assertThat(spy.lastMethodCalled(), is(worldChange));
    }

    @Test
    public void testEndTurn() {
        game.endOfTurn();
        assertThat(spy.lastMethodCalled(), is("turnEnds"));
    }
}
