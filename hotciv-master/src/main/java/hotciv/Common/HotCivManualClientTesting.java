package hotciv.Common;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.Client.GameProxy;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.tool.CompositionTool;
import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotCivManualClientTesting {

    private Game game;

    public static void main(String[] args) throws Exception{
        new HotCivManualClientTesting(args[0]);
    }

    public HotCivManualClientTesting(String hostname){
        System.out.println("=== HotCiv MANUAL TEST Client (Socket) (host:" + hostname + ") ===");

        //Set up Server
        int port = 37123;


        //Set up Broker part
        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(hostname, port);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy("Mogens", requestor);

        //Testing
        testSimpleMethods();
    }

    private void testSimpleMethods(){
        System.out.println("=== Testing simple methods ===");
        System.out.println(" -> Game age    " + game.getAge());
        System.out.println(" -> Game unit   " + game.getUnitAt(new Position(3,8)).getTypeString());
        System.out.println(" -> Game winner " + game.getWinner());
        System.out.println(" -> Game PIT    " + game.getPlayerInTurn());
        System.out.println(" -> Game move   " + game.moveUnit(new Position(2,0), new Position(3,3)));

    }
}