package hotciv.standard.impls;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

public class ObserverSpy implements GameObserver {

    private String lastMethod = "none";

    @Override
    public void worldChangedAt(Position pos) {
        lastMethod = "worldChangedAt";
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        lastMethod = "turnEnds";
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        lastMethod = "tileFocusChangedAt";
    }

    public String lastMethodCalled() {
        return lastMethod;
    }

}
