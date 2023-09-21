package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class ActionTool extends NullTool {

    private DrawingEditor editor;
    private Game game;

    public ActionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        if (e.isShiftDown()) {
            Position p = GfxConstants.getPositionFromXY(x,y);
            game.performUnitActionAt(p);
        }
    }
}
