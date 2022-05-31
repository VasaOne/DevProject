package com.Application.FileManger;

import com.Graphics.GraphicsManager;
import com.Graphics.Workspace.Application.SheetObject;
import com.Graphics.Workspace.Component.ComponentInstance;
import com.Graphics.Workspace.Sheet.Sheet;
import com.Graphics.Workspace.Wire.WireInstance;
import com.Physics.Component;
import javafx.scene.paint.Color;
import com.Physics.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;

import static com.Graphics.GraphicsManager.currentSheet;
import static com.Graphics.GraphicsManager.physicSheet;
import static java.lang.Math.exp;

public class SaveLoadSheet {

    public static ComponentData[] componentData;
    public static SheetObject[] loadedObjects;
    public static Boolean[][][] truthTables;

    private static String defaultPath = "C:\\Users\\larde\\Bureau\\Elec";

    public static void saveSheet(int id, String name, Color color, Sheet sheet) {
        ComponentData componentData = new ComponentData(id, name, color, sheet);
        //System.out.println(componentData.getFileContent());
        //TODO: Change setTruthTable argument to "compiled version" of the truthTable of the sheet
        componentData.setTruthTable(new Boolean[] {true, true, false, true, false, true, true, true, false});
        Boolean[] truthTable = new Boolean[sheet.ioComponent.startNodes.size()];
        for (int i=0;i<Math.pow(2, sheet.ioComponent.startNodes.size());i++) {

        }
        System.out.println(componentData.getTable());

        String fileContent = componentData.getFileContent();

        try {
            File creator = new File(defaultPath + name + ".comp");
            creator.createNewFile();
            FileWriter saveCompo = new FileWriter(defaultPath + name + ".comp");
            saveCompo.write(fileContent);
            saveCompo.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void loadSheet(String fileContent) throws ComponentNotFoundException {
        ComponentData data = ComponentData.parseData(fileContent);

        physicSheet = new com.Physics.Sheet();
        currentSheet = new Sheet(data.width, data.height);

        for (int i = 0; i < data.inputs; i++) {
            currentSheet.ioComponent.addStartNode(currentSheet);
        }
        for (int i = 0; i < data.outputs; i++) {
            currentSheet.ioComponent.addEndNode(currentSheet);
        }

        for (int i = 0; i < data.components.length; i++) {
            int compIdInt = data.components[i];
            SheetObject abstractComponent = loadedObjects[compIdInt];
            Component physicComponent = new Component(
                    data.name, abstractComponent.inputs, abstractComponent.outputs, truthTables[compIdInt]);
            currentSheet.addObject(new ComponentInstance(
                    loadedObjects[compIdInt], data.componentsX[i], data.componentsY[i], physicComponent));
            physicSheet.addComponent(physicComponent);
        }

        for (int i = 0; i < data.wiresStartComp.length; i++) {
            WireInstance wireInstance = new WireInstance();
            //Wire physicWire = new Wire();
            Wire physicWire = wireInstance.getPhysicWire();

            int startComp = data.wiresStartComp[i];
            int endComp = data.wiresEndComp[i];

            if (startComp == -1) {
                wireInstance.setStart(currentSheet.ioComponent.startNodes.get(data.wiresStartNode[i]));
                physicWire.setState(false);
                physicSheet.addWireInput(physicWire);
            } else {
                wireInstance.setStart(currentSheet.components.get(startComp).outputs[data.wiresStartNode[i]]);
                physicSheet.getComponents().get(startComp).addWireOutput(physicWire, data.wiresEndNode[i]);
                physicWire.addConnection(physicSheet.getComponents().get(startComp), 0);
            }

            if (endComp == -1) {
                wireInstance.setEnd(currentSheet.ioComponent.endNodes.get(data.wiresEndNode[i]));
                physicSheet.addOutput(wireInstance.getPhysicWire());
                System.out.println("Output");
            } else {
                wireInstance.setEnd(currentSheet.components.get(endComp).inputs[data.wiresEndNode[i]]);
                physicSheet.getComponents().get(endComp).addWireInput(physicWire, data.wiresStartNode[i]);
                physicWire.addConnection(physicSheet.getComponents().get(endComp), 1);
            }

            currentSheet.addWire(wireInstance);
            if (startComp != -1) {
                physicSheet.addWire(physicWire);
            }
        }
    }

    /**
     * Look for components in files and tries to load them until the id id
     * @return the id of the last loaded element
     */
    public static String loadObjectUntil(File[] compFiles) throws IOException {
        int lastId = compFiles.length;

        File[] fileNames = new File[lastId];
        ComponentData[] componentData = new ComponentData[lastId + 3];
        SheetObject[] tempArray = new SheetObject[lastId + 3];
        Boolean[][][] truthTables = new Boolean[lastId + 3][][];

        tempArray[0] = new SheetObject(0, "not", Color.BROWN, 1, 1);
        tempArray[1] = new SheetObject(1, "and", Color.GREEN, 2, 1);
        tempArray[2] = new SheetObject(2, "or", Color.RED, 2, 1);

        componentData[0] = new ComponentData(0, "not", Color.BROWN, 1, 1);
        componentData[1] = new ComponentData(1, "and", Color.GREEN, 2, 1);
        componentData[2] = new ComponentData(2, "or", Color.RED, 2, 1);

        truthTables[0] = new Boolean[][] {{true}, {false}};
        truthTables[1] = new Boolean[][] {{false}, {false}, {false}, {true}};
        truthTables[2] = new Boolean[][] {{false}, {true}, {true}, {true}};

        for (File file: compFiles) {
            try {
                String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                String[] split = fileContent.split("\n");
                String rawId = split[0].split(": ")[1];
                int id = Integer.parseInt(rawId);
                fileNames[id - 3] = file;

                if (id >= lastId) {
                    tempArray[id] = new SheetObject(id,
                            split[1].split(": ")[1],
                            Color.web(split[2].split(": ")[1]),
                            Integer.parseInt(split[3].split(": ")[1]),
                            Integer.parseInt(split[4].split(": ")[1])
                    );
//                    String rawTable = split[14].split(": ")[1];
//                    truthTables[id] = new Boolean[rawTable.length()][];
//                    for (int i = 0; i < rawTable.length(); i++) {
//                        if (rawTable.charAt(i) == '1') truthTables[id][i][0] = true;
//                        else truthTables[id][i][0] = false;
//                    }
                    fileNames[id - 3] = file;
                    componentData[id] = ComponentData.parseData(fileContent);
                }
            }
            catch (IOException e) {
                System.err.println("IOException");
            }
            catch (NumberFormatException | PatternSyntaxException | IndexOutOfBoundsException e) {
                System.err.println("File corrupted : " + file.getPath());
            }
        }

        if (fileNames.length>0 && fileNames[0] != null) {
            int i = 0;
            while (i < fileNames.length && fileNames[i] != null) i++;

            SaveLoadSheet.componentData = Arrays.copyOfRange(componentData, 0, i + 3);
            loadedObjects = Arrays.copyOfRange(tempArray, 0, i + 3);
            SaveLoadSheet.truthTables = Arrays.copyOfRange(truthTables, 0, i + 3);
            return Files.readString(fileNames[i - 1].toPath(), StandardCharsets.UTF_8);
        }
        else {
            loadedObjects = tempArray;
            SaveLoadSheet.truthTables = truthTables;
            return null;
        }
    }

    public static void loadAll() throws IOException, ComponentNotFoundException {
        System.out.println("entered");
        File[] compFiles = new File(defaultPath).listFiles();
        if (compFiles != null) {
            System.out.println("file found " + compFiles.length);
            String rawSheet = loadObjectUntil(compFiles);
            if (rawSheet != null) {
                System.out.println("rawSheet : " + rawSheet);
                loadSheet(rawSheet);
            }
            else {
                System.out.println("rawSheet is null");
                physicSheet = new com.Physics.Sheet();
                currentSheet = new Sheet(30, 20);
            }
        }
        else {
            System.out.println("file not found");
            loadObjectUntil(new File[0]);
            physicSheet = new com.Physics.Sheet();
            currentSheet = new Sheet(30, 20);
        }
    }

    public static void createNewSheet() throws IOException {
        File[] files = new File(defaultPath).listFiles();
        if (files == null) {
            files = new File[0];
        }
        loadObjectUntil(files);

        physicSheet = new com.Physics.Sheet();
        currentSheet = new Sheet(30, 20);
    }
}
