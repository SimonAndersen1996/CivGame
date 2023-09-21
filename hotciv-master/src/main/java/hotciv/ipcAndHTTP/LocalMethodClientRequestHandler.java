package hotciv.ipcAndHTTP;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;

public class LocalMethodClientRequestHandler implements ClientRequestHandler {
    private Invoker invoker;
    private String lastRequest;
    private String lastReply;

    public LocalMethodClientRequestHandler(Invoker invoker) {
    this.invoker = invoker;
    }

    @Override
    public String sendToServerAndAwaitReply(String request) {
        lastRequest = request;
        String reply = invoker.handleRequest(request);
        lastReply = reply;
        return reply;
    }

    @Override
    public void setServer(String hostname, int port) {

    }

    @Override
    public void close() {

    }
}
