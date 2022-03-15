package com.Graphics.Workspace;

import com.Config;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
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
    private final double height;
    /**
     * The default width of the canvas
     */
    private final double width;
    /**
     * The diameter in pixel of a node, so that size(pixel) = size(NU) * scale
     */
    private final double scale;

    public CanvasRenderer(Sheet displayedSheet, double defaultScale) {
        scale = defaultScale;
        width = displayedSheet.width * scale;
        height = displayedSheet.height * scale;
        this.displayedSheet = displayedSheet;
        setCanvas(new Canvas(width, height));

        /*CanvasInteractions interactions = */
        new CanvasInteractions(this, displayedSheet, canvas, scale);
        //canvas.setOnMouseClicked(mouseEvent -> System.out.println("Hi"));
    }

    public void renderGraphicContext() {
        ArrayList<ComponentInstance> drawLast = new ArrayList<>();
        for (ComponentInstance component: displayedSheet.components) {
            if (component.isPlaced) {
                component.drawComponent(context, scale);
            }
            else {
                drawLast.add(component);
            }
        }
        context.setGlobalAlpha(Config.WSMoveAlpha);
        for (ComponentInstance component: drawLast) {
            component.drawComponent(context, scale);
        }
        context.setGlobalAlpha(1);
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
    }
    public void setCanvasParent(ScrollPane parent) {
        parent.setContent(canvas);
    }
}
