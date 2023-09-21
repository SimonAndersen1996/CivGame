package hotciv.Common;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.Client.GameProxy;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.tool.CompositionTool;
import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotCivClientGUITesting {

    private Game game;

    public static void main(String[] args) throws Exception{
        new HotCivClientGUITesting(args[0]);
    }

    public HotCivClientGUITesting(String hostname){
        System.out.println("=== HotCiv MANUAL TEST Client (Socket) (host:" + hostname + ") ===");

        //Set up Server
        int port = 37123;

        //Set up Broker part
        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(hostname, port);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy("Mogens", requestor);

        //Testing
        //testSimpleMethods();
        DrawingEditor editor =
                new MiniDrawApplication( "Click and/or drag any item to see all game actions",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Click and drag any item to see Game's proper response.");

        editor.setTool(new CompositionTool(editor, game));
    }

    private void testSimpleMethods(){
        System.out.println("=== Testing simple methods ===");
        Unit unitAt = game.getUnitAt(new Position(3, 8));
        System.out.println(" -> Game unit   " + unitAt.getOwner() + " " + unitAt.getTypeString());
        System.out.println(" -> Game unit   " + unitAt.getOwner() + " " + unitAt.getTypeString());
        System.out.println(" -> Game winner " + game.getWinner());
        System.out.println(" -> Game PIT    " + game.getPlayerInTurn());
        System.out.println(" -> Game move   " + game.moveUnit(new Position(2,0), new Position(3,3)));

        game.endOfTurn();
        System.out.println(" -> Now PIT after endOfTurn " + game.getPlayerInTurn());
    }
}