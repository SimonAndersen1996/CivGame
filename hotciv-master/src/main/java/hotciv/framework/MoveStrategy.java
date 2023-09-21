package hotciv.framework;

/**
 * The MoveStrategy is responsible for managing unit movement
 * in the game.
 */
public interface MoveStrategy {

    boolean isValidMove(Position from, Position to, Game game);

}
