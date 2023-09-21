package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.FactoryDelta;
import hotciv.standard.impls.CityImpl;
import hotciv.standard.impls.GameImpl;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestDeltaCiv {
    private final Position threeThree = new Position(3, 3);
    private final Position threeFour = new Position(3, 4);
    private final Position threeFive = new Position(3, 5);
    private final Position threeSix = new Position(3, 6);
    private final Position nineOne = new Position(9, 1);
    private final Position nineTwo = new Position(9, 2);
    private final Position nineThree = new Position(9, 3);
    private final Position nineFour = new Position(9, 4);
    private Game game;
    private City city;
    private Player red, blue;

    /**
     * Fixture for DeltaCiv testing.
     */
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new FactoryDelta());
        red = Player.RED;
        blue = Player.BLUE;
        city = new CityImpl(red);
        game.addPlayer(red);
        game.addPlayer(blue);
    }

    @Test
    public void thereIsMountainsAtThreeThreeAndRightOf(){
        String mountain = GameConstants.MOUNTAINS;
        assertThat(game.getTileAt(threeThree).getTypeString(), is(mountain));
        assertThat(game.getTileAt(threeFour).getTypeString(), is(mountain));
        assertThat(game.getTileAt(threeFive).getTypeString(), is(mountain));
        assertThat(game.getTileAt(threeSix).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void thereIsForestsAtNineOneAndRightOf(){
        String forest = GameConstants.FOREST;

        assertThat(game.getTileAt(nineOne).getTypeString(), is(forest));
        assertThat(game.getTileAt(nineTwo).getTypeString(), is(forest));
        assertThat(game.getTileAt(nineThree).getTypeString(), is(forest));
        assertThat(game.getTileAt(nineFour).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void thereIsOceanAtSixRowExcept3Tiles(){
        String ocean = GameConstants.OCEANS;
        String plains = GameConstants.PLAINS;

        for(int c = 0 ; c < 3 ; c++){
            assertThat(game.getTileAt(new Position(6,c)).getTypeString(),is(ocean));
        }
        for(int c = 3 ; c < 6 ; c++){
            assertThat(game.getTileAt(new Position(6,c)).getTypeString(),is(plains));
        }
        for(int c = 6 ; c < 16 ; c++){
            assertThat(game.getTileAt(new Position(6,c)).getTypeString(),is(ocean));
        }
    }
}
