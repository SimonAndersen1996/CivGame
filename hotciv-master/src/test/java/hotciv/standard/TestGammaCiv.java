package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.FactoryGamma;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGammaCiv {
    private final Position oneOne = new Position(1, 1);
    private final Position threeTwo = new Position(3, 2);
    private final Position twoZero = new Position(2, 0);
    private final Position twoOne = new Position(2, 1);
    private final Position fourZero = new Position(4, 0);
    private final Position fourThree = new Position(4, 3);
    private final Position threeFour = new Position(3, 4);
    private Game game;
    private City city;
    private Player red, blue;
    private Unit archer, settler;

    /**
     * Fixture for GammaCiv testing.
     */
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new FactoryGamma());
        red = Player.RED;
        blue = Player.BLUE;
        city = new CityImpl(red);
        game.addPlayer(red);
        game.addPlayer(blue);
        archer = game.getUnitAt(twoZero);
        settler = game.getUnitAt(fourThree);
    }

    @Test
    public void archersCanFortify(){
        assertThat(archer.getTypeString(), is(GameConstants.ARCHER));
        assertThat(archer.getDefensiveStrength(),is(3));
        // (2, 0) is the position of the archer
        game.performUnitActionAt(twoZero);
        assertThat(archer.getDefensiveStrength(),is(6));
    }

    @Test
    public void archersCannotMoveWhileFortified(){
        assertThat(archer.getMoveCount(), is(1));
        game.performUnitActionAt(twoZero);
        assertThat(archer.getMoveCount(), is(0));
    }

    @Test
    public void archersCanMoveAgainAfterFortify(){
        assertThat(archer.getMoveCount(), is(1));
        game.performUnitActionAt(twoZero);
        assertThat(archer.getMoveCount(), is(0));
        game.performUnitActionAt(twoZero);
        assertThat(archer.getMoveCount(), is(1));
    }

    @Test
    public void archerShouldRevertDefAfterFortify(){
        assertThat(archer.getDefensiveStrength(),is(3));
        game.performUnitActionAt(twoZero);
        assertThat(archer.getDefensiveStrength(),is(6));
        game.performUnitActionAt(twoZero);
        assertThat(archer.getDefensiveStrength(),is(3));
    }

    @Test
    public void settlerShouldPerformAction(){
        assertThat(settler.getTypeString(), is(GameConstants.SETTLER));
        assertThat(settler.getOwner(), is(red));
        assertThat(game.getCityAt(fourThree), is(nullValue()));
        game.performUnitActionAt(fourThree);
        assertThat(game.getUnitAt(fourThree), is(nullValue()));
        assertThat(game.getCityAt(fourThree), is(notNullValue()));
        assertThat(game.getCityAt(fourThree).getOwner(), is(red));
    }

}
