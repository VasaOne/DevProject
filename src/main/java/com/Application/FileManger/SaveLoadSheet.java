package com.Application.FileManger;

import com.Graphics.GraphicsManager;
import com.Graphics.Workspace.Application.SheetObject;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Graphics.Workspace.Sheet.Sheet;
import com.Graphics.Workspace.Wire.WireInstance;
import com.Physics.Component;
import javafx.scene.paint.Color;
import com.Physics.*;

import java.util.ArrayList;
import java.util.List;

import com.Graphics.GraphicsManager;

import static com.Graphics.GraphicsManager.currentSheet;
import static com.Graphics.GraphicsManager.physicSheet;


public class SaveLoadSheet {

    public static ArrayList<SheetObject> loadedObjects = new ArrayList<>();
    public static ArrayList<Boolean[]> truthTables = new ArrayList<>();

    public static void saveSheet(int id, String name, Color color, Sheet sheet) {
        //String json = new ComponentData(id, name, color, sheet).getJson();
        //System.out.println(json);
        ComponentData componentData = new ComponentData(id, name, color, sheet);
        System.out.println(componentData.getFileContent());
        componentData.setTruthTable(new Boolean[]{true, true, false, true, false, true, true, true, false});
        System.out.println(componentData.getTable());


        //TODO: write in file
        String fileContent = componentData.getFileContent();
        String table = componentData.getTable();
    }

    public static Sheet loadSheet(String fileContent) {

        String[] content = fileContent.split("\n");
        int id = Integer.parseInt(content[0].split(": ")[1]);
        String name = content[1].split(": ")[1];
        Color color = Color.valueOf(content[2].split(": ")[1]);

        int inputs = Integer.parseInt(content[3].split(": ")[1]);
        int outputs = Integer.parseInt(content[4].split(": ")[1]);

        double width = Double.parseDouble(content[5].split(": ")[1]);
        double height = Double.parseDouble(content[6].split(": ")[1]);


        Sheet graphicSheet = new Sheet(width, height);
        com.Physics.Sheet physicSheet = new com.Physics.Sheet();

        for (int i = 0; i < inputs; i++) {
            graphicSheet.ioComponent.addStartNode(graphicSheet);
            physicSheet.addInput();
        }
        for (int i = 0; i < outputs; i++) {
            graphicSheet.ioComponent.addEndNode(graphicSheet);
            physicSheet.addOutput();
        }

        String[] compId = content[7].split(": ")[1].split(", ");
        String[] compX = content[8].split(": ")[1].split(", ");
        String[] compY = content[9].split(": ")[1].split(", ");

        for (int i = 0; i < compId.length; i++) {
            int compIdInt = Integer.parseInt(compId[i]);
            SheetObject abstractComponent = loadedObjects.get(compIdInt);
            Component physicComponent = new Component("", abstractComponent.inputs, abstractComponent.outputs, truthTables.get(compIdInt));
            graphicSheet.addObject(new ComponentInstance(
                    loadedObjects.get(Integer.parseInt(compId[i])),
                    Double.parseDouble(compX[i]),
                    Double.parseDouble(compY[i]),
                    physicComponent));
            physicSheet.addComponent(physicComponent);
        }

        String[] wireStartComp = content[10].split(": ")[1].split(", ");
        String[] wireStartNode = content[11].split(": ")[1].split(", ");
        String[] wireEndComp = content[12].split(": ")[1].split(", ");
        String[] wireEndNode = content[13].split(": ")[1].split(", ");

        for (int i = 0; i < wireStartComp.length; i++) {
            WireInstance wireInstance = new WireInstance();
            Wire physicWire = new Wire();

            int startComp = Integer.parseInt(wireStartComp[i]);
            int endComp = Integer.parseInt(wireEndComp[i]);

            if (startComp == -1) {
                wireInstance.setStart(graphicSheet.ioComponent.startNodes.get(Integer.parseInt(wireStartNode[i])));
                physicWire.setState(false);
            } else {
                wireInstance.setStart(graphicSheet.components.get(startComp).outputs[Integer.parseInt(wireStartNode[i])]);
                physicSheet.getComponents().get(startComp).addWireOutput(physicWire, Integer.parseInt(wireEndNode[i]));
            }

            if (endComp == -1) {
                wireInstance.setEnd(graphicSheet.ioComponent.endNodes.get(Integer.parseInt(wireEndNode[i])));
            } else {
                wireInstance.setEnd(graphicSheet.components.get(endComp).inputs[Integer.parseInt(wireEndNode[i])]);
                physicSheet.getComponents().get(endComp).addWireInput(physicWire, Integer.parseInt(wireStartNode[i]));
            }

            graphicSheet.addWire(wireInstance);
            physicSheet.addWire(physicWire);
        }

        return currentSheet;
    }
}
