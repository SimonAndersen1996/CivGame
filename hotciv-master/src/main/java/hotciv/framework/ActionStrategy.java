package hotciv.framework;

public interface ActionStrategy {

    /**
     * Perform a special action.
     * @param unit the performer.
     * @param position of the performing unit.
     * @param game the running game.
     */
    void action(Unit unit, Position position, Game game);

}
