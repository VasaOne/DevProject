package com.Application.FileManger;

import com.Graphics.Workspace.Application.SheetObject;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Graphics.Workspace.Sheet.Sheet;
import com.Graphics.Workspace.Wire.WireInstance;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SaveLoadSheet {

    public static ArrayList<SheetObject> loadedObjects = new ArrayList<>();

    public static void saveSheet(int id, String name, Color color, Sheet sheet) {
        //String json = new ComponentData(id, name, color, sheet).getJson();
        //System.out.println(json);
        ComponentData componentData = new ComponentData(id, name, color, sheet);
        System.out.println(componentData.getFileContent());
        componentData.setTruthTable(new boolean[][] {{true, true, false}, {true, false, true}, {true, true, false}});
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

        Sheet sheet = new Sheet(width, height);
        for (int i = 0; i < inputs; i++) {
            sheet.ioComponent.addStartNode(sheet);
        }
        for (int i = 0; i < outputs; i++) {
            sheet.ioComponent.addEndNode(sheet);
        }

        String[] compId = content[7].split(": ")[1].split(", ");
        String[] compX = content[8].split(": ")[1].split(", ");
        String[] compY = content[9].split(": ")[1].split(", ");

        for (int i = 0; i < compId.length; i++) {
            sheet.addObject(new ComponentInstance(
                    loadedObjects.get(Integer.parseInt(compId[i])),
                    Double.parseDouble(compX[i]),
                    Double.parseDouble(compY[i])));
        }

        String[] wireStartComp = content[10].split(": ")[1].split(", ");
        String[] wireStartNode = content[11].split(": ")[1].split(", ");
        String[] wireEndComp = content[12].split(": ")[1].split(", ");
        String[] wireEndNode = content[13].split(": ")[1].split(", ");

        for (int i = 0; i < wireStartComp.length; i++) {
            WireInstance wireInstance = new WireInstance();

            int startComp = Integer.parseInt(wireStartComp[i]);
            int endComp = Integer.parseInt(wireEndComp[i]);

            if (startComp == -1) {
                wireInstance.setStart(sheet.ioComponent.startNodes.get(Integer.parseInt(wireStartNode[i])));
            }
            else {
                wireInstance.setStart(sheet.components.get(startComp).outputs[Integer.parseInt(wireStartNode[i])]);
            }

            if (endComp == -1) {
                wireInstance.setEnd(sheet.ioComponent.endNodes.get(Integer.parseInt(wireEndNode[i])));
            }
            else {
                wireInstance.setEnd(sheet.components.get(endComp).inputs[Integer.parseInt(wireEndNode[i])]);
            }

            sheet.addWire(wireInstance);
        }

        return sheet;
    }
}
