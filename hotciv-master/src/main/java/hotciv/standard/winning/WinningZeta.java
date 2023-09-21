package hotciv.standard.winning;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;

public class WinningZeta implements WinningStrategy {
    private WinningStrategy winningStrategyPost20;
    private int noOfRounds = 0;
    private WinningStrategy currentWinningStrat = new WinningBeta();;

    public WinningZeta(WinningStrategy pre20, WinningStrategy post20) {
        currentWinningStrat = pre20;
        winningStrategyPost20 = post20;
    }

    @Override
    public Player getWinner(Game game) {
        return currentWinningStrat.getWinner(game);
    }

    @Override
    public void incrementBattlesWon(Player p) {
        currentWinningStrat.incrementBattlesWon(p);
    }

    @Override
    public void incrementRoundsPlayed() {
        noOfRounds++;
        if (getRounds() > 20) currentWinningStrat = new WinningEpsilon();
    }

    @Override
    public int getRounds() {
        return noOfRounds;
    }
}
