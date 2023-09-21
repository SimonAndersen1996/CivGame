package hotciv.standard.winning;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;

/**
 * Compute the winner, given AlphaCiv's rules:
 * Player RED wins at 3000BC
 */
public class WinningAlpha implements WinningStrategy {

    /**
     * @param game - what rules are in the game
     * @return Null if the age is before 3000BC, else return Player RED
     */
    @Override
    public Player getWinner(Game game) {
        if (game.getAge() < -3000){
            return null;
        }
        return Player.RED;
    }

    @Override
    public void incrementBattlesWon(Player p) {

    }

    @Override
    public void incrementRoundsPlayed() {

    }

    @Override
    public int getRounds() {
        return 0;
    }


}
