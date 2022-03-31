package com.Graphics.Workspace;

import com.Config;
import com.Physics.Wire;
import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

public class WireInstance {
    private OutputNode start;
    private InputNode end;

    public boolean isReal = true;
    public boolean canBePlaced = true;

    com.Physics.Wire physicWire = new Wire();

    private boolean state;
    public boolean getState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
        if (Objects.nonNull(start)) {
            start.setState(state);
        }
        if (Objects.nonNull(end)) {
            end.setState(state);
        }
    }

    private double startX;
    public double getStartX() {
        return startX;
    }
    private double startY;
    public double getStartY() {
        return startY;
    }

    private double middle;
    public double getMiddle() {
        return middle;
    }
    private boolean useDefaultMiddle = true;

    private double endX;
    public double getEndX() {
        return endX;
    }
    private double endY;
    public double getEndY() {
        return endY;
    }

    public WireInteraction wireInteraction;

    public WireInstance() {
        wireInteraction = new WireInteraction(this);
    }
    public WireInstance(OutputNode start, InputNode end, boolean isReal) {
        this();
        this.start = start;
        this.end = end;
        this.isReal = isReal;
        testMiddle();
    }

    public void updateWire() {
        if (Objects.nonNull(start)) {
            startX = start.getCenterX();
            startY = start.getCenterY();
        }
        if (Objects.nonNull(end)) {
            endX = end.getCenterX();
            endY = end.getCenterY();
            testMiddle();
            if (Objects.nonNull(start)) {
                physicWire.connect(start.relativeTo.getPhysicComponent(), end.relativeTo.getPhysicComponent());
            }
        }
    }

    public void drawWire(GraphicsContext context, double scale) {
        if (isReal) {
            updateWire();
            context.beginPath();
            if (!canBePlaced) {
                context.setStroke(Config.WSDisabledColor);
            }
            else if (state) {
                context.setStroke(Config.WSOnWiresColor);
            }
            else {
                context.setStroke(Config.WSOffWiresColor);
            }
            context.moveTo(startX * scale, startY * scale);
            context.lineTo(middle * scale, startY * scale);
            context.lineTo(middle * scale, endY * scale);
            context.lineTo(endX * scale, endY * scale);
        }
    }

    public void setStart(OutputNode start) {
        this.start = start;
        start.addWire(this);
        startX = start.getCenterX();
        startY = start.getCenterY();
        testMiddle();
    }
    public void setEnd(InputNode end) {
        this.end = end;
        end.setWire(this);
        endX = end.getCenterX();
        endY = end.getCenterY();
        testMiddle();
    }

    public OutputNode getStart() {
        return start;
    }
    public InputNode getEnd() {
        return end;
    }

    public void setStart(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
        //Si start était déjà assigné à un node
        if (Objects.nonNull(start)) {
            start.removeWire(this);
            start = null;
        }
        testMiddle();
    }
    public void setEnd(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
        //Si end était déjà assigné à un fil
        if (Objects.nonNull(end)) {
            end.removeWire();
            end = null;
        }
        testMiddle();
    }

    public double getDefaultMiddle() {
        return (startX + endX) / 2;
    }

    void testMiddle() {
        if (middle < startX) {
            useDefaultMiddle = true;
        }
        else if (middle > endX) {
            useDefaultMiddle = true;
        }
        if (useDefaultMiddle) {
            middle = getDefaultMiddle();
        }
    }
    boolean isWidthLarge() {
        return startX + Config.WSWireMinWidth < endX;
    }
}
