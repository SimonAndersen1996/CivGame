package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.standard.impls.GameImpl;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class FractalTest {

    @Test
    public void fractalTest() {
        for (int i = 0; i < 25; i++) {
            Game game = new GameImpl(new FactoryFractal());
            Tile startTile = game.getTileAt(new Position(0, 0));
            boolean isSameTiles = true;
            for (Tile tile : game.getWorldLayout().values()) {
                isSameTiles = isSameTiles && startTile.getTypeString().equals(tile.getTypeString());
            }
            assertThat(isSameTiles, is(false));
        }
    }

}
