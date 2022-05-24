package com.Graphics.Workspace.Wire;

import com.Config;
import com.Graphics.Workspace.Component.IOComponent;
import com.Graphics.Workspace.Node.InputNode;
import com.Graphics.Workspace.Node.OutputNode;
import com.Physics.Component;
import com.Physics.Wire;
import javafx.scene.canvas.GraphicsContext;

import static com.Graphics.GraphicsManager.sheet;

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

    /**
     * Updates the position of the wire.
     */
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
                sheet.addWire(physicWire);
                physicWire.connect(new Component[] {start.relativeTo.getPhysicComponent(), end.relativeTo.getPhysicComponent()});
            }
        }
    }

    /**
     * Draws the wire.
     * @param context the context to draw on.
     * @param scale the scale of the workspace.
     */
    public void drawWire(GraphicsContext context, double scale) {
        if (isReal) {
            updateWire();
            context.beginPath();

            boolean canBePlaced = this.canBePlaced;
            if (Objects.nonNull(start)) {
                canBePlaced &= start.relativeTo.canBePlaced();
            }
            if (Objects.nonNull(end)) {
                canBePlaced &= end.relativeTo.canBePlaced();
            }

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

    /**
     * Sets the start of the wire to the given node. Set itself as the start of the wire.
     * @param start the node to set as start
     */
    public void setStart(OutputNode start) {
        this.start = start;
        start.addWire(this);
        startX = start.getCenterX();
        startY = start.getCenterY();
        testMiddle();

        if (!(start.relativeTo instanceof IOComponent)) {
            start.relativeTo.getPhysicComponent().addWireOutput(physicWire, start.id);
        }


        /*
        //Connaître le numéro de la node
        IOComponent component = null;
        if (start.relativeTo instanceof IOComponent) { //Normalement ne sert à rien car on est sûr qu'il s'agit d'un omponent
            component = (IOComponent) start.relativeTo;
        }
        int s = 0;
        int l = 0;
        for (OutputNode node : component.startNodes) {
            if (node != start) {
                s++;
            } else {
                l = s; //On a récupéré le num de la node
            }
        }
        component.getPhysicComponent().addWireInput(physicWire, l);

         */
    }

    /**
     * Sets the end of the wire to the given node. Set itself as the end of the wire.
     * @param end the node to set as end
     */
    public void setEnd(InputNode end) {
        this.end = end;
        end.setWire(this);
        endX = end.getCenterX();
        endY = end.getCenterY();
        testMiddle();

        if (!(end.relativeTo instanceof IOComponent)) {
            end.relativeTo.getPhysicComponent().addWireInput(physicWire, end.id);
        }

        /*//Connaître le numéro de la node
        IOComponent component = null;
        if (end.relativeTo instanceof IOComponent) { //Normalement ne sert à rien car on est sûr qu'il s'agit d'un omponent
            component = (IOComponent) end.relativeTo;
        }
        int s = 0;
        int l = 0;
        for (InputNode node : component.endNodes) {
            if (node != end) {
                s++;
            } else {
                l = s; //On a récupéré le num de la node
            }
        }
        component.getPhysicComponent().addWireOutput(physicWire, l);

         */
    }

    public OutputNode getStart() {
        return start;
    }
    public InputNode getEnd() {
        return end;
    }

    /**
     * Sets the start of the wire to the give coordinates. Removes the start node if needed.
     * @param startX the x coordinate of the start.
     * @param startY the y coordinate of the start.
     */
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
    /**
     * Sets the end of the wire to the give coordinates. Removes the end node if needed.
     * @param endX the x coordinate of the end.
     * @param endY the y coordinate of the end.
     */
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

    /**
     * Test if the middle is in the right place
     */
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
    /**
     * Tests if the wire is large enough to be placed
     * @return true if the wire is large enough to be placed, false otherwise
     */
    public boolean isWidthLarge() {
        return startX + Config.WSWireMinWidth < endX;
    }

    /**
     * Removes the wire from its start and end.
     * /!\ Should only be called by the sheet itself ! Maybe call removeWire function from the sheet.
     */
    public void remove() {
        if (Objects.nonNull(start)) {
            start.removeWire(this);
        }
        if (Objects.nonNull(end)) {
            end.removeWire();
        }
    }
}
