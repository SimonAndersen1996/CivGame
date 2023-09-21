package hotciv.standard.winning;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;

import java.util.HashMap;

public class WinningEpsilon implements WinningStrategy {
    HashMap<Player, Integer> battlesWon = new HashMap();

    @Override
    public Player getWinner(Game game) {
        for(Player p : getBattlesWon().keySet()){
            int won = getBattlesWon().get(p);
            if(won >= 3){
                return p;
            }
        }
        return null;
    }

    @Override
    public void incrementBattlesWon(Player p) {
        if (getBattlesWon().get(p) == null) {
            getBattlesWon().put(p, 0);
        }
        int won = getBattlesWon().get(p);
        getBattlesWon().put(p,won+1);
    }

    @Override
    public void incrementRoundsPlayed() {

    }

    @Override
    public int getRounds() {
        return 0;
    }

    private HashMap<Player, Integer> getBattlesWon() {
        return battlesWon;
    }
}
