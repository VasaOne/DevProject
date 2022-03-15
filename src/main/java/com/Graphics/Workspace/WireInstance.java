package com.Graphics.Workspace;

import com.Config;
import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

public class WireInstance {
    NodeInstance start;
    NodeInstance end;

    boolean state;
    public boolean getState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
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
    private boolean useDefaultMiddle;

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

    public void updateWire() {
        if (Objects.nonNull(start)) {
            startX = start.getCenterX();
            startY = start.getCenterY();
            testMiddle();
        }
        if (Objects.nonNull(end)) {
            endX = end.getCenterX();
            endY = end.getCenterY();
            testMiddle();
        }
    }

    public void drawWire(GraphicsContext context, double scale) {
        updateWire();
        context.beginPath();
        if (state) {
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

    public void setStart(NodeInstance start) {
        this.start = start;
        startX = start.getCenterX();
        startY = start.getCenterY();
        testMiddle();
    }
    public void setEnd(NodeInstance end) {
        this.end = end;
        endX = end.getCenterX();
        endY = end.getCenterY();
        testMiddle();
    }

    public NodeInstance getStart() {
        return start;
    }
    public NodeInstance getEnd() {
        return end;
    }

    public void setStart(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
        if (Objects.nonNull(start)) {
            start.removeWire();
            start = null;
        }
        testMiddle();
    }
    public void setEnd(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
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
}