package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.TestEpsilon.UnfairDice;
import hotciv.standard.factories.FactoryZeta;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import hotciv.standard.impls.UnitImpl;
import hotciv.standard.winning.WinningBeta;
import hotciv.standard.winning.WinningEpsilon;
import hotciv.standard.winning.WinningZeta;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestZetaCiv {
    private final Position oneOne = new Position(1, 1);
    private final Position threeTwo = new Position(3, 2);
    private final Position twoZero = new Position(2, 0);
    private final Position twoOne = new Position(2, 1);
    private final Position fourZero = new Position(4, 0);
    private final Position fourOne = new Position(4, 1);
    private final Position fourThree = new Position(4, 3);
    private final Position threeFour = new Position(3, 4);
    private final Position sixFive = new Position(6, 5);
    private final Position sixSix = new Position(6, 6);
    private final Position tenZero = new Position(10, 0);
    private final Position elevenZero = new Position(11, 0);
    private final Position twelveZero = new Position(12, 0);
    private final Position tenOne = new Position(10, 1);
    private final Position elevenOne = new Position(11, 1);
    private final Position twelveOne = new Position(12, 1);
    private UnfairDice unfairDice = new UnfairDice();
    private Game game;
    private City city;
    private Player red, blue;
    private Unit archer, settler;
    private AttackingStrategy attackStrat;
    private WinningStrategy winningZeta;
    private ArrayList<Player> players = new ArrayList<>();

    /**
     * Fixture for EpsilonCiv testing.
     */
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new FactoryZeta());
        red = Player.RED;
        blue = Player.BLUE;
        city = new CityImpl(red);
        game.addPlayer(red);
        game.addPlayer(blue);
        archer = game.getUnitAt(twoZero);
        settler = game.getUnitAt(fourThree);
        winningZeta = new WinningZeta(new WinningBeta(), new WinningEpsilon());
        players.add(red);
        players.add(blue);
    }

    @Test
    public void before20RoundsRedWinsByConquering(){
        assertThat(winningZeta.getRounds() < 20, is(true));
        Unit archer = new UnitImpl(GameConstants.ARCHER, red);
        game.addUnit(fourZero, archer);
        assertThat(winningZeta.getWinner(game), is(nullValue()));
        game.moveUnit(fourZero, fourOne);
        assertThat(winningZeta.getWinner(game), is(red));
    }

    @Test
    public void after20RoundsRedWinsByThreeAttacks(){
        for (int i = 0; i < 22; i++) {
            game.endOfRound();
        }
        assertThat(game.getPlayerInTurn(), is(red));
        assertThat(game.getCurrentRound() >= 20, is(true));
        Unit redLeg1 = new UnitImpl(GameConstants.LEGION, red);
        Unit redLeg2 = new UnitImpl(GameConstants.LEGION, red);
        Unit redLeg3 = new UnitImpl(GameConstants.LEGION, red);
        Unit blueLeg1 = new UnitImpl(GameConstants.LEGION, blue);
        Unit blueLeg2 = new UnitImpl(GameConstants.LEGION, blue);
        Unit blueLeg3 = new UnitImpl(GameConstants.LEGION, blue);

        game.addUnit(tenZero, redLeg1);
        game.addUnit(elevenZero, redLeg2);
        game.addUnit(twelveZero, redLeg3);
        game.addUnit(tenOne, blueLeg1);
        game.addUnit(elevenOne, blueLeg2);
        game.addUnit(twelveOne, blueLeg3);
        assertThat(winningZeta.getWinner(game), is(nullValue()));
        game.moveUnit(tenZero, tenOne);
        assertThat(winningZeta.getWinner(game), is(nullValue()));
        game.moveUnit(elevenZero, elevenOne);
        assertThat(winningZeta.getWinner(game), is(nullValue()));
        game.moveUnit(twelveZero, twelveOne);
        assertThat(game.getWinner(), is(red));
    }




}
