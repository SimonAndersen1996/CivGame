package hotciv.standard;

import hotciv.Utility.Utility;
import hotciv.framework.*;
import hotciv.standard.TestEpsilon.UnfairDice;
import hotciv.standard.factories.FactoryTheta;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import hotciv.standard.impls.UnitImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;

public class TestThetaCiv {
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
    private final Position sevenSix = new Position(7, 6);
    private final Position eightTwelve = new Position(8, 12);
    private final Position nineSix = new Position(9, 6);
    private final Position nineSeven = new Position(9, 7);
    private final Position tenZero = new Position(10, 0);
    private final Position tenOne = new Position(10, 1);
    private final Position elevenZero = new Position(11, 0);
    private final Position elevenOne = new Position(11, 1);
    private final Position twelveZero = new Position(12, 0);
    private final Position twelveOne = new Position(12, 1);
    private UnfairDice unfairDice = new UnfairDice();
    private Game game;
    private City city;
    private Player red, blue;
    private Unit archer, settler;
    private ArrayList<Player> players = new ArrayList<>();
    private Unit sandworm;
    private MoveStrategy moveStrategy = new MoveTheta();
    private ProductionStrategy productionStrategy = new ProductionTheta();


    @BeforeEach
    public void setUp() {
        game = new GameImpl(new FactoryTheta());
        red = Player.RED;
        blue = Player.BLUE;
        city = new CityImpl(red);
        game.addPlayer(red);
        game.addPlayer(blue);
        archer = game.getUnitAt(twoZero);
        settler = game.getUnitAt(fourThree);
        sandworm = game.getUnitAt(new Position(9,6));
        players.add(red);
        players.add(blue);
    }

    @Test
    public void thereShouldBeDesert() {
        String desert = GameConstants.DESERT;

        for(int i = 2; i<10; i++){
            assertThat(game.getTileAt(new Position(8,i)).getTypeString(),is(desert));
        }
    }

    @Test
    public void theresShouldBeDesertAndSandwormExist() {
        assertThat(game.getTileAt(nineSix).getTypeString(),is(GameConstants.DESERT));
        assertThat(game.getUnitAt(nineSix).getTypeString(), is(GameConstants.SANDWORM));
    }

    @Test
    public void sandwormShouldMove2(){
        game.endOfTurn();
        assertThat(game.getUnitAt(nineSix).getMoveCount(),is(2));
        game.moveUnit(nineSix, sevenSix);
        assertThat(game.getUnitAt(sevenSix).getTypeString(), is(GameConstants.SANDWORM));
    }

    @Test
    public void sandwormShouldNotMoveMoreThan2(){
        assertThat(game.getUnitAt(nineSix).getTypeString(), is(GameConstants.SANDWORM));
        assertThat(moveStrategy.isValidMove(nineSix,new Position(12,6), game),is(false));
    }

    @Test
    public void sandwormCanOnlyMoveOnDesert(){
        assertThat(moveStrategy.isValidMove(nineSix, nineSeven, game), is(false));
    }

    @Test
    public void sandwormCanOnlyBeProducedOnDesert(){
        game.getCityAt(eightTwelve).addTreasury(30);
        assertThat(game.getUnitAt(eightTwelve), is(nullValue()));
        assertThat(game.getTileAt(eightTwelve).getTypeString(), is(GameConstants.DESERT));
        game.getCityAt(eightTwelve).setProductionFocus(GameConstants.SANDWORM);
        productionStrategy.produceUnit(eightTwelve, game);
        assertThat(game.getUnitAt(eightTwelve).getTypeString(), is(GameConstants.SANDWORM));
    }

    @Test
    public void sandwormDevoursEnemies() {
        assertThat(game.getUnitAt(nineSix).getTypeString(), is(GameConstants.SANDWORM));
        for(Position p : Utility.get8neighborhoodOf(nineSix)) {
            game.addUnit(p, new UnitImpl(GameConstants.ARCHER, red));
        }
        for(Position p : Utility.get8neighborhoodOf(nineSix)) {
            assertThat(game.getUnitAt(p), is(notNullValue()));
        }
        assertThat(game.getUnitAt(nineSix).getOwner(), is(blue));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(blue));
        game.performUnitActionAt(nineSix);
        for(Position p : Utility.get8neighborhoodOf(nineSix)) {
            assertThat(game.getUnitAt(p), is(nullValue()));
        }
    }

}


