package hotciv.standard;

import com.google.gson.Gson;
import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.Client.CityProxy;
import hotciv.Client.GameProxy;
import hotciv.Common.OperationNames;
import hotciv.Domain.NullObserver;
import hotciv.MarshallAndJSON.HotCivGameInvoker;
import hotciv.MarshallAndJSON.HotCivRootInvoker;
import hotciv.Service.InMemoryNameService;
import hotciv.Service.NameService;
import hotciv.framework.*;
import hotciv.ipcAndHTTP.LocalMethodClientRequestHandler;
import hotciv.stub.FakeBrokerGame;
import hotciv.stub.FakeObjectGame;
import hotciv.stub.StubCity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.naming.Name;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;



public class TestBrokerGame {

    private Position threeThree = new Position(3, 3);
    private Position threeFour = new Position(3, 4);
    private Game game;
    private FakeBrokerGame servant;

    @BeforeEach
    public void setup(){
        servant = new FakeBrokerGame();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new HotCivRootInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy("Mogens", requestor);
        game.addObserver(nullObserver);
    }

    @Test
    public void shouldHaveWinner() {
        Player winner = game.getWinner();
        assertThat(winner, is(Player.YELLOW));
    }

    @Disabled
    @Test
    public void shouldGetFreePosition() {
        Position freePos = game.getFreePosition(threeThree);
        assertThat(freePos, is(notNullValue()));
    }

    @Test
    public void shouldGetTile() {
        Tile tile = game.getTileAt(threeThree);
        assertThat(tile.getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void shouldGetCity() {
        City city = game.getCityAt(threeThree);
        assertThat(city, is(notNullValue()));
        assertThat(city.getOwner(), is(Player.YELLOW));
    }

    @Disabled
    @Test
    public void roundShouldBe666() {
        int round = game.getCurrentRound();
        assertThat(round, is(666));
    }

    @Test
    public void shouldGetUnit() {
        Unit unit = game.getUnitAt(threeThree);
        assertThat(unit.getOwner(), is(Player.BLUE));
        assertThat(unit.getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void shouldGetPlayerInTurn() {
        Player player = game.getPlayerInTurn();
        assertThat(player, is(Player.GREEN));
    }

    @Test
    public void ageShouldBe420() {
        int age = game.getAge();
        assertThat(age, is(420));
    }

    @Test
    public void shouldMoveAUnit() {
        boolean moveSuccessful = game.moveUnit(threeThree, threeFour);
        assertThat(moveSuccessful, is(true));
    }

    @Test
    public void shouldEndTurn() {
        game.endOfTurn();
        assertThat(servant.getLastMethodCalled(), is(OperationNames.GAME_END_OF_TURN));
    }

    @Test
    public void shouldChangeWorkforceFocus() {
        game.changeWorkForceFocusInCityAt(threeThree, "hammer");
        assertThat(servant.getLastMethodCalled(), is(OperationNames.GAME_CHANGE_WORKFORCE_FOCUS));
    }

    @Test
    public void shouldChangeProduction() {
        game.changeProductionInCityAt(threeThree, "Big titty goth GF");
        assertThat(servant.getLastMethodCalled(), is(OperationNames.GAME_CHANGE_PRODUCTION));
    }

    @Test
    public void shouldPerformAction() {
        game.performUnitActionAt(threeThree);
        assertThat(servant.getLastMethodCalled(), is(OperationNames.GAME_ACTION));
    }

    @Disabled
    @Test
    public void shouldEndRound() {
        game.endOfRound();
        assertThat(servant.getLastMethodCalled(), is(OperationNames.GAME_END_OF_ROUND));
    }

    @Disabled
    @Test
    public void shouldEndGame() {
        game.endOfGame();
        assertThat(servant.getLastMethodCalled(), is(OperationNames.GAME_END));
    }

    @Disabled
    @Test
    public void shouldAddCity() {
        game.addCity(threeFour, new StubCity(Player.GREEN, 42));
        assertThat(servant.getLastMethodCalled(), is(OperationNames.GAME_ADD_CITY));
    }
    
    @Test
    public void shouldFocusTile() {
        game.setTileFocus(threeFour);
        assertThat(servant.getLastMethodCalled(), is(OperationNames.GAME_SET_TILE_FOCUS));
    }

}
