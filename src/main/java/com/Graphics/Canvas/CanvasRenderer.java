package com.Graphics.Canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * This class make the canvas render each frame.
 */
public class CanvasRenderer {
    /**
     * The graphicsContext that will render our sheet
     */
    private GraphicsContext gc;
    /**
     * The sheet that will be displayed
     */
    private Sheet displayedSheet;

    /**
     * The height of the canvas
     */
    private double height;
    /**
     * The width of the canvas
     */
    private double width;

    /**
     * The position on the sheet of the upper-left angle
     */
    private double originX;
    /**
     * The position on the sheet of the upper-left angle
     */
    private double originY;

    /**
     * The ratio of the zoom : sheet size = zoomRatio * canvas size
     */
    private double zoomRatio;

    public CanvasRenderer(Canvas canvas, Sheet displayedSheet) {
        gc = canvas.getGraphicsContext2D();
        this.displayedSheet = displayedSheet;
    }

    public void renderGraphicContext() {
        ArrayList<NodeInstance> onNodesToDraw = new ArrayList<>();
        ArrayList<NodeInstance> offNodesToDraw = new ArrayList<>();

        for (ComponentInstance instance: displayedSheet.objects) {
            onNodesToDraw.addAll(instance.getOnNodes());
            offNodesToDraw.addAll(instance.getOffNodes());

            gc.setFill(instance.instanceOf.color);
        }
    }

    public void setDisplayedSheet(Sheet displayedSheet) {
        this.displayedSheet = displayedSheet;
    }

    public void setCanvas(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        height = canvas.getHeight();
        width = canvas.getWidth();
    }

    public void setPositionAndZoom(double originX, double originY, double zoomRatio) {
        this.originX = originX;
        this.originY = originY;
        this.zoomRatio = zoomRatio;
    }

    public double pixelToSheetX(double pixelX) {
        return (pixelX + originX) * zoomRatio;
    }
    public double pixelToSheetY(double pixelY) {
        return (pixelY + originY) * zoomRatio;
    }

    public double sheetToPixelX(double sheetX) {
        return sheetX / zoomRatio - originX;
    }
    public double sheetToPixelY(double sheetY) {
        return sheetY / zoomRatio - originY;
    }
}
