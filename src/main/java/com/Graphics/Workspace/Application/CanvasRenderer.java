package com.Graphics.Workspace.Application;

import com.Config;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Graphics.Workspace.Sheet.Sheet;
import com.Graphics.Workspace.Wire.WireInstance;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

/**
 * This class make the canvas render each frame.
 */
public class CanvasRenderer {
    /**
     * The canvas where sheet will be rendered
     */
    private Canvas canvas;
    private GraphicsContext context;
    /**
     * The sheet that will be displayed
     */
    private Sheet displayedSheet;

    /**
     * The diameter in pixel of a node, so that size(pixel) = size(NU) * scale
     */
    private final double scale;

    public CanvasRenderer(Sheet displayedSheet, double defaultScale) {
        scale = defaultScale;
        // The default width of the canvas
        double width = displayedSheet.getWidth() * scale;
        // The default height of the canvas
        double height = displayedSheet.getHeight() * scale;
        this.displayedSheet = displayedSheet;
        setCanvas(new Canvas(width, height));

        /*CanvasInteractions interactions = */
        new CanvasInteractions(displayedSheet, canvas, scale);
    }

    public void renderGraphicContext() {
        context.setFill(Config.WSBackgroundColor);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (WireInstance wire: displayedSheet.wires) {
            wire.drawWire(context, scale);
            context.stroke();
        }

        ArrayList<ComponentInstance> drawLast = new ArrayList<>();
        for (ComponentInstance component: displayedSheet.components) {
            if (component.isPlaced) {
                component.drawComponent(context, scale);
            }
            else {
                drawLast.add(component);
            }
        }
        for (ComponentInstance component: drawLast) {
            component.drawComponent(context, scale);
        }

        displayedSheet.ioComponent.drawComponent(context, scale);
    }

    public void setDisplayedSheet(Sheet displayedSheet) {
        this.displayedSheet = displayedSheet;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        context = canvas.getGraphicsContext2D();
        context.setFill(Config.WSBackgroundColor);
        context.setFont(Config.WSFont(scale));
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.setLineWidth(Config.WSWireSize * scale);
        context.setLineJoin(StrokeLineJoin.ROUND);

    }
    public void setCanvasParent(ScrollPane parent) {
        parent.setContent(canvas);
    }
}
