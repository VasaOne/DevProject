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
    private boolean isWireSelected;
    private WireInstance selectedWire;

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
            //System.out.println("Click on node");
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
        if (isWireSelected) {
            if (!selectedWire.canBePlaced) {
                sheet.removeWire(selectedWire);
            }
            isWireSelected = false;
        }
        else if (isNodeSelected) {
            if (Objects.equals(sheet.getNodeAt(event.getX() / scale, event.getY() / scale), selectedNode) && selectedNode.isGlobalInput) {
                selectedNode.getWire().setState(!selectedNode.getState());
            }
        }
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
        //Cas où on sélectionne un node
        if (isNodeSelected) {
            NodeInstance secondNode = sheet.getNodeAt(event.getX() / scale, event.getY() / scale);
            //Si la souris est sortie du node que l'on a sélectionné
            if (!Objects.equals(secondNode, selectedNode)) {
                //Si on a pas de fil
                if (!isWireSelected) {
                    selectedWire = new WireInstance();
                    sheet.addWire(selectedWire);
                    isWireSelected = true;
                    if (selectedNode.isInput) {
                        selectedWire.setStart(selectedNode);
                    }
                    else {
                        selectedWire.setEnd(selectedNode);
                    }
                }
                //Si la node sélectionnée est en entrée du fil
                if (selectedNode.isInput) {
                    if (Objects.nonNull(secondNode) && !secondNode.isInput) {
                        selectedWire.setEnd(secondNode);
                    }
                    else {
                        selectedWire.setEnd(event.getX() / scale, event.getY() / scale);
                    }
                }
                //Sinon
                else {
                    if (Objects.nonNull(secondNode) && secondNode.isInput) {
                        selectedWire.setStart(secondNode);
                    }
                    else {
                        selectedWire.setStart(event.getX() / scale, event.getY() / scale);
                    }
                }
                selectedWire.canBePlaced = !sheet.isWireOverriding(selectedWire);
                System.out.println(sheet.isWireOverriding(selectedWire));
            }
        }
        else if (isComponentSelected) {
            selectedComponent.isPlaced = false;
            double posX = event.getX() / scale;
            double posY = event.getY() / scale;

            if (!selectedComponent.isOnSheet(sheet.width, sheet.height)) {
                selectedComponent.canBePlaced = false;
                System.out.println("Not on sheet");
            }
            else {
                ComponentInstance override = sheet.isOverriding(selectedComponent);
                if (Objects.nonNull(override) || !selectedComponent.areWiresFacing()) {
                    selectedComponent.canBePlaced = false;
                    System.out.println("Wire inverted");
                }
                else if (sheet.areWiresOverriding()) {
                    selectedComponent.canBePlaced = false;
                    System.out.println("Wire overriding");
                }
                else {
                    selectedComponent.canBePlaced = true;
                }
            }
            selectedComponent.moveComponent(posX - xFromOrigin, posY - yFromOrigin);
        }
    }
}
