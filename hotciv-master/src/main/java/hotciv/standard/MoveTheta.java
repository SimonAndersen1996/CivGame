package hotciv.standard;

import hotciv.framework.*;

public class MoveTheta implements MoveStrategy {

    @Override
    public boolean isValidMove(Position from, Position to, Game game) {
        Unit unitToMove = game.getUnitAt(from);
        if(unitToMove == null) {return false;}

        boolean isPassableTile = isPassableTile(to, game, unitToMove);
        if(!isPassableTile) return false;

        boolean isOwnUnit = unitToMove.getOwner() == game.getPlayerInTurn();
        if(!isOwnUnit) return false;

        boolean unitHasMoved = unitToMove.getMoved();
        if(unitHasMoved) return false;

        boolean isStacking = (game.getUnitAt(to) != null
                && game.getUnitAt(to).getOwner() == game.getPlayerInTurn());
        if(isStacking) return false;

        boolean isWithingMoveRange = isWithingMoveRange(from, to, unitToMove);
        if(isWithingMoveRange) return false;

        return true;
    }

    private boolean isWithingMoveRange(Position from, Position to, Unit unitToMove) {
        return Math.abs(from.getColumn() - to.getColumn()) > unitToMove.getMoveCount()
                || Math.abs(from.getRow() - to.getRow()) > unitToMove.getMoveCount();
    }

    private boolean isPassableTile(Position to, Game game, Unit unit) {
        boolean isPassableTile;
        switch (unit.getTypeString()) {
            case GameConstants.SANDWORM:
                isPassableTile = game.getTileAt(to).getTypeString().equals(GameConstants.DESERT);
                return isPassableTile;
            default:
                isPassableTile = !game.getTileAt(to).getTypeString().equals(GameConstants.OCEANS)
                        && !game.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
                return isPassableTile;
        }
    }
}
