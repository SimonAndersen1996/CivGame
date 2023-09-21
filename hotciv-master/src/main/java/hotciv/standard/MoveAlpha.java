package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.MoveStrategy;
import hotciv.framework.Position;

public class MoveAlpha implements MoveStrategy {
    @Override
    public boolean isValidMove(Position from, Position to, Game game) {
        if(game.getUnitAt(from) == null) {
            System.out.println("No Unit at from Position");
            return false;}

        boolean isPassableTile = !game.getTileAt(to).getTypeString().equals(GameConstants.OCEANS)
                && !game.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
        if(!isPassableTile) {
            System.out.println("Impassable Tile");
            return false;}

        boolean isOwnUnit = game.getUnitAt(from).getOwner() == game.getPlayerInTurn();
        if(!isOwnUnit) {
            System.out.println("Not own Unit");
            return false;}

        boolean unitHasMoved = game.getUnitAt(from).getMoved();
        if(unitHasMoved) {
            System.out.println("Unit has already moved");
            return false;}

        boolean isStacking = (game.getUnitAt(to) != null
                && game.getUnitAt(to).getOwner() == game.getPlayerInTurn());
        if(isStacking) {
            System.out.println("Stacking on friendly Unit");
            return false;}

        boolean isWithingMoveRange = Math.abs(from.getColumn() - to.getColumn()) > game.getUnitAt(from).getMoveCount()
                || Math.abs(from.getRow() - to.getRow()) > game.getUnitAt(from).getMoveCount();
        if(isWithingMoveRange) {
            System.out.println("Out of move range");
            return false;}

        return true;
    }
}
