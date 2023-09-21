package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.FactoryBeta;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import hotciv.standard.impls.UnitImpl;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

public class TestBetaCiv {
    private final Position oneOne = new Position(1, 1);
    private final Position threeTwo = new Position(3, 2);
    private final Position twoOne = new Position(2, 1);
    private final Position fourZero = new Position(4, 0);
    private Game game;
    private City city;
    private Player red, blue;

    /**
     * Fixture for betaciv testing.
     */
    @BeforeEach
    public void setUp() {

        game = new GameImpl(new FactoryBeta());
        red = Player.RED;
        blue = Player.BLUE;
        city = new CityImpl(red);
        game.addPlayer(red);
        game.addPlayer(blue);
    }

    @Test
    public void unitsShouldConquerOtherCities() {
        City cityAt = game.getCityAt(oneOne);
        Unit archer = new UnitImpl(GameConstants.ARCHER, blue);
        game.addUnit(twoOne,archer);

        game.endOfTurn(); //Change to Blue's turn

        assertThat(cityAt.getOwner(), is(red));
        game.moveUnit(twoOne, oneOne); //Conquer Red's city
        assertThat(cityAt.getOwner(), is(blue));
    }

    @Test
    public void gameShouldEndIfBlueHasAllCities() {
        Unit archer = new UnitImpl(GameConstants.ARCHER, blue);
        game.addUnit(twoOne,archer);

        game.endOfTurn(); //Change to Blue's turn

        game.moveUnit(twoOne, oneOne); //Conquer Red's city
        assertThat(game.getWinner(), is(blue));
    }

    @Test
    public void gameShouldEndIfRedHasAllCities() {
        Unit archer = new UnitImpl(GameConstants.ARCHER, red);
        game.addUnit(fourZero,archer);

        game.moveUnit(new Position(4, 0), new Position(4, 1)); //Conquer Blue's city
        assertThat(game.getWinner(), is(red));
    }

    @Test
    public void shouldChangeFromFirstToSecondAgingPhase(){
        assertThat(game.getAge(), is(-4000));
        for(int i = 1; i < 40; i++){
            game.endOfRound();
        } // Move forward to age 100BC
        // Now switch to the special aging sequence
        for(int i = 0; i < 4; i++){
            game.endOfRound();
        }
        assertThat(game.getAge(), is(50));
    }

    @Test
    public void shouldChangeFromSecondToThirdAgingPhase(){
        this.game.setAge(-100);
        for(int i = 0; i < 4; i++){
            this.game.endOfRound();
        }
        assertThat(this.game.getAge(), is(50));
        for(int i = 0; i < 34; i++){
            this.game.endOfRound();
        }
        assertThat(this.game.getAge(), is(1750));
    }

    /**
     * Test for BetaCiv
     */
    @Test
    public void shouldChangeFromThirdToFourthPhase(){
        this.game.setAge(50);
        for(int i = 0; i < 34; i++){
            this.game.endOfRound();
        }
        assertThat(this.game.getAge(), is(1750));
        for(int i = 0; i < 6; i++){
            this.game.endOfRound();
        }
        assertThat(this.game.getAge(), is(1900));
    }

    /**
     * Test for BetaCiv
     */
    @Test
    public void shouldChangeFromFourthToFifthPhase(){
        this.game.setAge(1750);
        for(int i = 0; i < 6; i++){
            this.game.endOfRound();
        }
        assertThat(this.game.getAge(), is(1900));
        for(int i = 0; i < 14; i++){
            this.game.endOfRound();
        }
        assertThat(this.game.getAge(), is(1970));
    }

    /**
     * Test for BetaCiv
     */
    @Test
    public void shouldChangeFromFifthToSixthPhase(){
        this.game.setAge(1900);
        for(int i = 0; i < 14; i++){
            this.game.endOfRound();
        }
        assertThat(this.game.getAge(), is(1970));
        int n = new Random().nextInt(100);
        for(int i = 0; i < n; i++){
            this.game.endOfRound();
        }
        assertThat(this.game.getAge(), is(n + 1970));
    }
}
