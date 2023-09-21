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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBrokerUnit {

    private Unit unitProxy;
    private FakeBrokerGame servant;
    private Game client;

    @BeforeEach
    public void setup() {
        servant = new FakeBrokerGame();

        Invoker invoker = new HotCivRootInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        client = new GameProxy("Mogens", requestor);

        unitProxy = client.getUnitAt(new Position(2,0));
    }

    @Test
    public void shouldGetType(){
        String type = unitProxy.getTypeString();
        assertThat(type, is(GameConstants.ARCHER));
    }

    @Test
    public void shouldGetOwner(){
        Player owner = unitProxy.getOwner();
        assertThat(owner, is(Player.RED));
    }

    @Disabled
    @Test
    public void shouldGetMoveCount(){
        int count = unitProxy.getMoveCount();
        assertThat(count, is(800));
    }

    @Disabled
    @Test
    public void shouldGetDefStrength(){
        int def = unitProxy.getDefensiveStrength();
        assertThat(def, is(9000));
    }

    @Disabled
    @Test
    public void shouldSetDefensiveStrength(){
        unitProxy.setDefensiveStrength(23);
        assertThat(servant.getLastMethodCalled(), is(OperationNames.UNIT_SET_DEFENSIVE_STRENGTH));
    }

    @Disabled
    @Test
    public void shouldGetAttackingStrength(){
        int attack = unitProxy.getAttackingStrength();
        assertThat(attack, is(4500));
    }

    @Disabled
    @Test
    public void shouldSetMoved(){
        unitProxy.setMoved(false);
        assertThat(servant.getLastMethodCalled(), is(OperationNames.UNIT_SET_MOVED));
    }

    @Disabled
    @Test
    public void shouldGetMoved() {
        boolean moved = unitProxy.getMoved();
        assertThat(moved, is(true));
    }

    @Disabled
    @Test
    public void shouldGetIsFortified() {
        boolean isFortified = unitProxy.isFortified();
        assertThat(isFortified, is(true));
    }

    @Disabled
    @Test
    public void shouldSetMoveCount() {
        unitProxy.setMoveCount(42);
        assertThat(servant.getLastMethodCalled(), is(OperationNames.UNIT_SET_MOVEMENT_COUNT));
    }

    @Disabled
    @Test
    public void shouldSetFortified() {
        unitProxy.setFortified(false);
        assertThat(servant.getLastMethodCalled(), is(OperationNames.UNIT_SET_FORTIFIED));
    }

    @Disabled
    @Test
    public void shouldGetMaxMove() {
        int maxMove = unitProxy.getMaxMove();
        assertThat(maxMove, is(666));
    }
}
