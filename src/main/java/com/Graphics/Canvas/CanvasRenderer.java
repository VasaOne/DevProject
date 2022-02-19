package com.Graphics.Canvas;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
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
     * The default height of the canvas
     */
    private double height;
    /**
     * The default width of the canvas
     */
    private double width;

    private double scale;


    ///TEMPORARY :
    private Color nodeOnColor = Color.web("#F21C1C");
    private Color nodeOffColor = Color.web("#7F1010");

    public CanvasRenderer(Sheet displayedSheet, double defaultScale) {
        scale = defaultScale;
        width = displayedSheet.width * scale;
        height = displayedSheet.height * scale;
        this.displayedSheet = displayedSheet;
        setCanvas(new Canvas(width, height));
    }

    public void renderGraphicContext() {
        ArrayList<NodeInstance> onNodesToDraw = new ArrayList<>();
        ArrayList<NodeInstance> offNodesToDraw = new ArrayList<>();

        for (ComponentInstance component: displayedSheet.objects) {
            onNodesToDraw.addAll(component.getOnNodes());
            offNodesToDraw.addAll(component.getOffNodes());

            context.setFill(component.instanceOf.color);
            context.fillRect(
                    component.getOriginX() * scale, component.getOriginY() * scale,
                    component.instanceOf.getWidth() * scale, component.instanceOf.getHeight() * scale
            );
        }
        for (NodeInstance node: onNodesToDraw) {
            context.setFill(nodeOnColor);
            context.fillOval(
                    node.getOriginX() * scale,
                    node.getOriginY() * scale,
                    node.instanceOf.getWidth() * scale,
                    node.instanceOf.getHeight() * scale
            );
        }
        for (NodeInstance node: offNodesToDraw) {
            context.setFill(nodeOffColor);
            context.fillOval(
                    node.getOriginX() * scale,
                    node.getOriginY() * scale,
                    node.instanceOf.getWidth() * scale,
                    node.instanceOf.getHeight() * scale
            );
        }
    }

    public void setDisplayedSheet(Sheet displayedSheet) {
        this.displayedSheet = displayedSheet;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        context = canvas.getGraphicsContext2D();
        context.setFill(Color.LIGHTGRAY);
        context.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
    }
    public void setCanvasParent(ScrollPane parent) {
        parent.setContent(canvas);
    }
}
