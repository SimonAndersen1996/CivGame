package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.ImageFigure;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class UnitMoveTool extends NullTool {
    private final DrawingEditor editor;
    private final Game game;
    private Position from;
    private Position to;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        from = GfxConstants.getPositionFromXY(x,y);
        Unit toUnit = game.getUnitAt(from);
        if (toUnit != null) {
            System.out.println("From Unit color: " + game.getUnitAt(from).getOwner());
        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        editor.showStatus("Dragging over: "+ GfxConstants.getPositionFromXY(x,y));
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        to = GfxConstants.getPositionFromXY(x,y);
        Unit fromUnit = game.getUnitAt(to);
        if (fromUnit != null) {
            System.out.println("To Unit color : " + game.getUnitAt(to).getOwner());
        }
        boolean validMove = game.moveUnit(from, to);
        System.out.println("Valid move: " + validMove);
        if (!validMove) {editor.showStatus("Invalid move");}
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {

    }

    @Override
    public void keyDown(KeyEvent e, int key) {

    }
}
