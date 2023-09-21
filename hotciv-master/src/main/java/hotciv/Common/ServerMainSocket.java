package hotciv.Common;

import com.google.gson.Gson;
import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.Domain.NullObserver;
import hotciv.MarshallAndJSON.HotCivGameInvoker;
import hotciv.MarshallAndJSON.HotCivRootInvoker;
import hotciv.Service.InMemoryNameService;
import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.standard.factories.FactoryAlpha;
import hotciv.standard.factories.FactorySemi;
import hotciv.standard.impls.GameImpl;
import hotciv.stub.FakeBrokerGame;

import java.util.Observer;

public class ServerMainSocket {
    private Thread deamon;
    private Game gameServant;

    public ServerMainSocket(String arg) {
        int port = 37123;
        gameServant = new GameImpl(new FactorySemi());
        Invoker invoker = new HotCivRootInvoker(gameServant);
        GameObserver observer = new NullObserver();
        gameServant.addObserver(observer);

        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(arg, port);

        // Configure a socket based server request handler
        SocketServerRequestHandler ssrh = new SocketServerRequestHandler();
        ssrh.setPortAndInvoker(port, invoker);

        // Welcome
        System.out.println("=== HotCiv Socket based Server Request Handler (port:"
                + port + ") ===");
        System.out.println(" Use ctrl-c to terminate!");
        ssrh.start();
    }

}
