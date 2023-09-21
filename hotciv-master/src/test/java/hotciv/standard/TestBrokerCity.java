package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.Client.GameProxy;
import hotciv.Common.OperationNames;
import hotciv.MarshallAndJSON.HotCivRootInvoker;
import hotciv.framework.*;
import hotciv.ipcAndHTTP.LocalMethodClientRequestHandler;
import hotciv.stub.FakeBrokerGame;
import hotciv.stub.StubCity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBrokerCity {
    private City cityProxy;
    private FakeBrokerGame servant;
    private Game game;
    private StubCity spy;

    @BeforeEach
    public void setup(){
        servant = new FakeBrokerGame();

        Invoker invoker = new HotCivRootInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy("mogens", requestor);
        cityProxy = game.getCityAt(new Position(7,7));
        spy = (StubCity) servant.getCityAt(new Position(7, 7));
    }

    @Test
    public void shouldHaveCityOwner(){
        Player owner = cityProxy.getOwner();
        assertThat(owner, is(Player.GREEN));
    }

    @Test
    public void shouldHaveSetWorkforceFocus(){
        cityProxy.setWorkForceFocus("Mogens");
        assertThat(spy.getLastMethodCalled(), is(OperationNames.CITY_SET_WORKFORCE_FOCUS));
    }

    @Test
    public void shouldHaveSetProductionFocus(){
        cityProxy.setProductionFocus("Mogens");
        assertThat(spy.getLastMethodCalled(), is(OperationNames.CITY_SET_PRODUCTION_FOCUS));
    }

    @Disabled
    @Test
    public void shouldHaveGetSize(){
        int size = cityProxy.getSize();
        assertThat(size, is(420));
    }

    @Disabled
    @Test
    public void shouldGetTreasury(){
        int treasury = cityProxy.getTreasury();
        assertThat(treasury, is(8008));
    }

    @Disabled
    @Test
    public void shouldGetProductionType(){
        String prodType = cityProxy.getProductionType();
        assertThat(prodType, is(GameConstants.MOUNTAINS));
    }

    @Disabled
    @Test
    public void shouldGetWorkforceType(){
        String workType = cityProxy.getWorkforceFocus();
        assertThat(workType, is(GameConstants.OCEANS));
    }

    @Disabled
    @Test
    public void shouldHaveAddTreasury(){
        cityProxy.addTreasury(42);
        assertThat(servant.getLastMethodCalled(), is(OperationNames.CITY_ADD_TREASURY));
    }


    @Disabled
    @Test
    public void shouldHaveSetOwner(){
        cityProxy.setOwner(Player.GREEN);
        assertThat(servant.getLastMethodCalled(), is(OperationNames.CITY_SET_OWNER));
    }
}
