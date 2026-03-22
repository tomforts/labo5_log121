package org.example.labo5;

public class TranslateCommand extends PerspectiveCommand {

    private int dx;
    private int dy;
    private int oldX;
    private int oldY;

    public TranslateCommand(Perspective perspective, int dx, int dy) {
        super(perspective);
        this.dx   = dx;
        this.dy   = dy;
        this.oldX = perspective.getOffsetX();
        this.oldY = perspective.getOffsetY();
    }

    @Override
    public void execute() {
        perspective.translate(dx, dy);
    }

    @Override
    public void undo() {
        perspective.setOffsets(oldX, oldY);
    }
}
