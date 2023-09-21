package hotciv.standard;

import com.google.gson.Gson;
import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.Client.GameProxy;
import hotciv.Client.TileProxy;
import hotciv.MarshallAndJSON.HotCivRootInvoker;
import hotciv.MarshallAndJSON.HotCivTileInvoker;
import hotciv.Service.InMemoryNameService;
import hotciv.Service.NameService;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.ipcAndHTTP.LocalMethodClientRequestHandler;
import hotciv.stub.FakeBrokerGame;
import hotciv.stub.FakeBrokerTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBrokerTile {
    private Tile tile;
    private FakeBrokerGame servant;
    private Game game;

    @BeforeEach
    public void setup(){
        servant = new FakeBrokerGame();

        Invoker invoker = new HotCivRootInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);
        game = new GameProxy("Mogens", requestor);
        tile = game.getTileAt(new Position(3, 3));
    }

    @Test
    public void shouldHaveGetTypestring(){
        String type = tile.getTypeString();
        assertThat(type, is(GameConstants.PLAINS));
    }
}
