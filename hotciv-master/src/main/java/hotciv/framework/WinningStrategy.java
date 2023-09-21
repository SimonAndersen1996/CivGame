package hotciv.framework;

/**
 * Represents the way of winning in the game
 * <p>
 * Responsibilities:
 * Know the current winning strategy
 * Compute who wins the game given different strategies
 *</p>
 */
public interface WinningStrategy {

    /**
     * @param game - what rules are in the game
     * @return the player who wins the game
     */
    Player getWinner(Game game);

    /**
     * Increases the battles won for player
     * @param p The winning player
     */
    void incrementBattlesWon(Player p);

    /**
     * Increments the number of rounds played which the WinningStrategy
     * keeps track of.
     */
    void incrementRoundsPlayed();

    /**
     *
     * @return number of rounds played.
     */
    int getRounds();
}
