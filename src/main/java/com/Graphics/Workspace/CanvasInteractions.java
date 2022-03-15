package com.Graphics.Workspace;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/**
 * This class allows the user to interact with the canvas
 */
public class CanvasInteractions {
    private CanvasRenderer renderer;
    private Sheet sheet;
    private Canvas canvas;
    private double scale;

    private boolean isNodeSelected;
    private NodeInstance selectedNode;
    private boolean isComponentSelected;
    private ComponentInstance selectedComponent;

    private double xFromOrigin;
    private double yFromOrigin;

    private double firstXPosition;
    private double firstYPosition;

    public CanvasInteractions(CanvasRenderer renderer, Sheet sheet, Canvas canvas, double scale) {
        this.renderer = renderer;
        this.sheet = sheet;
        this.canvas = canvas;
        this.scale = scale;
        canvas.setOnMousePressed(mouseEvent -> OnMousePressed(mouseEvent));
        canvas.setOnMouseReleased(mouseEvent -> OnMouseReleased(mouseEvent));
        canvas.setOnMouseDragged(mouseEvent -> OnMouseDragged(mouseEvent));
    }

    private void OnMousePressed(MouseEvent event) {
        NodeInstance node = sheet.getNodeAt(event.getX() / scale, event.getY() / scale);
        if (Objects.nonNull(node)) {
            isNodeSelected = true;
            selectedNode = node;
            //System.out.println();
        }
        else {
            ComponentInstance component = sheet.getComponentAt(event.getX() / scale, event.getY() / scale);
            if (Objects.nonNull(component)) {
                isComponentSelected = true;
                selectedComponent = component;

                firstXPosition = selectedComponent.getOriginX();
                firstYPosition = selectedComponent.getOriginY();

                xFromOrigin = event.getX() / scale - selectedComponent.getOriginX();
                yFromOrigin = event.getY() / scale - selectedComponent.getOriginY();
                //System.out.println("Component selected, " + selectedComponent.instanceOf.name);
            }
        }

    }
    private void OnMouseReleased(MouseEvent event) {
        isNodeSelected = false;
        if (isComponentSelected) {
            if (!selectedComponent.canBePlaced) {
                selectedComponent.moveComponent(firstXPosition, firstYPosition);
                selectedComponent.canBePlaced = true;
            }
            selectedComponent.isPlaced = true;
        }
        isComponentSelected = false;
        //System.out.println("Released");
    }
    private void OnMouseDragged(MouseEvent event) {
        if (isNodeSelected) {

        }
        else if (isComponentSelected) {
            selectedComponent.isPlaced = false;
            double posX = event.getX() / scale;
            double posY = event.getY() / scale;

            ComponentInstance override = sheet.isOverriding(selectedComponent);
            if (Objects.nonNull(override)) {
                //System.out.println("Overriding : " + override.instanceOf.name);
                selectedComponent.canBePlaced = false;
            }
            else {
                selectedComponent.canBePlaced = true;
            }
/*            //(xx)
            //(xx)
            if (Objects.nonNull(upLeft) && Objects.nonNull(upRight) && Objects.nonNull(bottomLeft) && Objects.nonNull(bottomRight)) {
                posY = sheet.spaceBetween + yFromOrigin +
                        Math.max(bottomLeft.getOriginY() + bottomLeft.instanceOf.getHeight(), bottomRight.getOriginY() + bottomRight.instanceOf.getHeight());
            }
            //(xx)
            //(xo)
            else if (Objects.nonNull(upLeft) && Objects.nonNull(upRight) && Objects.nonNull(bottomLeft)) {
                posX = sheet.spaceBetween + xFromOrigin + bottomLeft.getOriginX() + bottomLeft.instanceOf.getWidth();
                posY = sheet.spaceBetween + yFromOrigin + upRight.getOriginY() + upRight.instanceOf.getHeight();
            }
            //(xx)
            //(ox)
            else if (Objects.nonNull(upLeft) && Objects.nonNull(upRight) && Objects.nonNull(bottomRight)) {
                posX = bottomRight.originX - sheet.spaceBetween - selectedComponent.instanceOf.getWidth() + xFromOrigin;
                posY = sheet.spaceBetween + yFromOrigin + upLeft.getOriginY() + upLeft.instanceOf.getHeight();
            }
            //(xo)
            //(xx)
            else if (Objects.nonNull(upLeft) && Objects.nonNull(bottomLeft) && Objects.nonNull(bottomRight)) {
                posX = sheet.spaceBetween + xFromOrigin + bottomRight.getOriginX() + bottomRight.instanceOf.getWidth();
                posY = bottomRight.getOriginY() - sheet.spaceBetween - selectedComponent.instanceOf.getHeight() + yFromOrigin;
            }
            //(ox)
            //(xx)
            else if (Objects.nonNull(upRight) && Objects.nonNull(bottomLeft) && Objects.nonNull(bottomRight)) {
                posX = bottomLeft.getOriginX() - sheet.spaceBetween - selectedComponent.instanceOf.getWidth() + xFromOrigin;
                posY = bottomLeft.getOriginY() - sheet.spaceBetween - selectedComponent.instanceOf.getHeight() + yFromOrigin;
            }
            //(xx)
            //(oo)
            else if (Objects.nonNull(upLeft) && Objects.nonNull(upRight)) {
                posY = sheet.spaceBetween + xFromOrigin +
                        Math.max(upLeft.getOriginY() + upLeft.instanceOf.getHeight(), upRight.getOriginY() + upRight.instanceOf.getHeight());
            }
            //(oo)
            //(xx)
            else if (Objects.nonNull(bottomLeft) && Objects.nonNull(bottomRight)) {
                posY = xFromOrigin - sheet.spaceBetween - selectedComponent.instanceOf.getHeight() +
                        Math.min(bottomLeft.getOriginY(), bottomRight.getOriginY());
            }
            //(xo)
            //(xo)
            else if (Objects.nonNull(upLeft) && Objects.nonNull(bottomLeft)) {
                posX = xFromOrigin + sheet.spaceBetween +
                        Math.max(upLeft.getOriginX() + upLeft.instanceOf.getWidth(), bottomLeft.getOriginX() + bottomLeft.instanceOf.getWidth());
            }
            //(ox)
            //(ox)
            else if (Objects.nonNull(upRight) && Objects.nonNull(bottomRight)) {
                posX = xFromOrigin - sheet.spaceBetween - selectedComponent.instanceOf.getWidth() +
                        Math.min(upLeft.getOriginX(), bottomLeft.getOriginX());
            }
            else {
                if (Objects.nonNull(upLeft)) {
                    posY = sheet.spaceBetween + yFromOrigin + upLeft.getOriginY() + upLeft.instanceOf.getHeight();
                }
                if (Objects.nonNull(upRight)) {
                    posY = sheet.spaceBetween + yFromOrigin + upRight.getOriginY() + upRight.instanceOf.getHeight();
                }
                if (Objects.nonNull(bottomLeft)) {
                    posY = sheet.spaceBetween + yFromOrigin + bottomLeft.getOriginY() + bottomLeft.instanceOf.getHeight();
                }
                if (Objects.nonNull(bottomRight)) {
                    posY = sheet.spaceBetween + yFromOrigin + bottomRight.getOriginY() + bottomRight.instanceOf.getHeight();
                }
            }*/
            selectedComponent.moveComponent(posX - xFromOrigin, posY - yFromOrigin);
        }
    }
}
