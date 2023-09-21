package hotciv.standard.TestEpsilon;

import hotciv.framework.*;

import hotciv.standard.AttackEpsilon;
import hotciv.standard.factories.FactoryEpsilon;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import hotciv.standard.impls.UnitImpl;
import hotciv.standard.winning.WinningEpsilon;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestEpsilonCiv {
    private final Position oneOne = new Position(1, 1);
    private final Position threeTwo = new Position(3, 2);
    private final Position twoZero = new Position(2, 0);
    private final Position twoOne = new Position(2, 1);
    private final Position fourZero = new Position(4, 0);
    private final Position fourThree = new Position(4, 3);
    private final Position threeFour = new Position(3, 4);
    private final Position sixFive = new Position(6, 5);
    private final Position sixSix = new Position(6, 6);
    private final Position sevenSeven = new Position(7,7);
    private UnfairDice unfairDice = new UnfairDice();
    private Game game;
    private City city;
    private Player red, blue;
    private Unit archer, settler;
    private AttackingStrategy attackStrat;
    private WinningStrategy winningEpsilon;
    private ArrayList<Player> players = new ArrayList<>();
    /**
     * Fixture for EpsilonCiv testing.
     */
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new FactoryEpsilon());
        red = Player.RED;
        blue = Player.BLUE;
        city = new CityImpl(red);
        game.addPlayer(red);
        game.addPlayer(blue);
        archer = game.getUnitAt(twoZero);
        settler = game.getUnitAt(fourThree);
        attackStrat = new AttackEpsilon(unfairDice);
        winningEpsilon = new WinningEpsilon();
    }

    @Test
    public void unitCanLoseAttack(){
        assertThat(game.getUnitAt(twoZero).getTypeString(),is (GameConstants.ARCHER));
        assertThat(game.getUnitAt(twoZero).getOwner(),is (red));
        Unit archer = new UnitImpl(GameConstants.ARCHER,blue);
        game.addUnit(twoOne, archer);
        unfairDice.setRoll(1);
        boolean battleResult = attackStrat.isSuccessfulAttack(twoZero, twoOne, game);
        assertThat(battleResult, is(false));
    }

    @Test
    public void unitCanWinAttack(){
        assertThat(game.getUnitAt(twoZero).getTypeString(),is (GameConstants.ARCHER));
        assertThat(game.getUnitAt(twoZero).getOwner(),is (red));
        game.endOfTurn();
        Unit legion = new UnitImpl(GameConstants.LEGION,blue);
        game.addUnit(twoOne, legion);
        unfairDice.setRoll(6);
        boolean battleResult = attackStrat.isSuccessfulAttack(twoOne, twoZero, game);
        assertThat(battleResult, is(true));
    }

    @Test
    public void oneSingleArcherHas2Attack(){
        Unit archer = new UnitImpl(GameConstants.ARCHER, red);
        game.addUnit(sixSix, archer);

        assertThat(attackStrat.computeStatValue(sixSix, true, game),is(2));
    }

    @Test
    public void unitsGetBonusAttackForAdjacentAllies(){
        Unit archer = new UnitImpl(GameConstants.ARCHER, red);
        Unit archer1 = new UnitImpl(GameConstants.ARCHER, red);
        Unit legion = new UnitImpl(GameConstants.LEGION, red);
        game.addUnit(sixSix, archer);
        game.addUnit(sixFive, archer1);
        game.addUnit(sevenSeven, legion);

        assertThat(attackStrat.computeStatValue(sixSix, true, game),is(4));
    }

    @Test
    public void unitsGetBonusDefenseForAdjacentAllies(){
        Unit archer = new UnitImpl(GameConstants.ARCHER, red);
        Unit archer1 = new UnitImpl(GameConstants.ARCHER, red);
        Unit legion = new UnitImpl(GameConstants.LEGION, red);
        game.addUnit(sixSix, archer);
        game.addUnit(sixFive, archer1);
        game.addUnit(sevenSeven, legion);

        assertThat(attackStrat.computeStatValue(sixSix, false, game), is(5));
    }

    @Test
    public void winningAttackUnitReplaceDefeatedUnit(){
        Unit redLegion = new UnitImpl(GameConstants.LEGION, red);
        Unit blueArcher = new UnitImpl(GameConstants.ARCHER, blue);
        game.addUnit(sixSix, redLegion);
        game.addUnit(sixFive, blueArcher);
    }

    @Test
    public void playerWith3SuccessfulAttacksWin(){
        assertThat(winningEpsilon.getWinner(game), is(nullValue()));
        winningEpsilon.incrementBattlesWon(red);
        assertThat(winningEpsilon.getWinner(game), is(nullValue()));
        winningEpsilon.incrementBattlesWon(red);
        assertThat(winningEpsilon.getWinner(game), is(nullValue()));
        winningEpsilon.incrementBattlesWon(red);
        assertThat(winningEpsilon.getWinner(game), is(red));
    }

    @Test
    public void terrainBonusOnCityIs3() {
        assertThat(attackStrat.getTerrainBonus(oneOne, game), is(3));
    }
}
