package hotciv.standard;

import hotciv.framework.Game;

public class LogToggler {

    public static Game toggleDecorator(Game currentGame, Game decorateeGame) {
        if (currentGame == decorateeGame) {
            return new LoggingGame(decorateeGame);
        }
        else {
            return decorateeGame;
        }
    }

}
