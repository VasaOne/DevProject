package com.Graphics.Canvas;

import com.Config;

public class WireInteraction {
    WireInstance wire;

    private WireInteraction() {}
    public WireInteraction(WireInstance wire) {
        this.wire = wire;
    }

    public boolean hasTouchedStartPart(double posX, double posY) {
        return posX > wire.getStartX() && posX < wire.getMiddle() && Math.abs(posY - wire.getStartY()) < Config.WSWireSize / 2;
    }
    public boolean hasTouchedMiddlePart(double posX, double posY) {
        return ((posY > wire.getStartY() && posY < wire.getEndY()) || (posY > wire.getEndY() && posY < wire.getStartY())) && Math.abs(posX - wire.getMiddle()) < Config.WSWireSize / 2;
    }
    public boolean hasTouchedEndPart(double posX, double posY) {
        return posX > wire.getMiddle() && posX < wire.getEndX() && Math.abs(posY - wire.getEndY()) < Config.WSWireSize / 2;
    }
    public boolean hasTouchedWire(double posX, double posY) {
        return hasTouchedStartPart(posX, posY) || hasTouchedMiddlePart(posX, posY) || hasTouchedEndPart(posX, posY);
    }
}
