package hotciv.standard.winning;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinningStrategy;
import hotciv.framework.City;

/**
 * Compute the winner, given BetaCiv's rules:
 * The player who has conquered all cities win
 */
public class WinningBeta implements WinningStrategy {

    /**
     * @param game - what rules are in the game
     * @return the player who owns all cities, else return null
     */
    @Override
    public Player getWinner(Game game) {
        int bluecount = 0;
        int redcount = 0;

        // Count how many cities red and blue has
        for(City city : game.getCities().values()){
            if(city.getOwner() == Player.RED){
                redcount++;
            }
            else{
                bluecount++;
            }
        }

        //Check to see if a Player has all the cities
        if(redcount == game.getCities().size()){
            return Player.RED;
        }
        else if(bluecount == game.getCities().size()){
            return Player.BLUE;
        }
        else return null;
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
