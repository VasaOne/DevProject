package com.Application.FileManger;

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
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;

import static com.Graphics.GraphicsManager.currentSheet;
import static com.Graphics.GraphicsManager.physicSheet;
import static java.lang.Math.pow;

public class SaveLoadSheet {

    public static SheetObject[] loadedObjects;
    public static Boolean[][][] truthTables;

    private static String defaultPath = System.getProperty("user.home") + "/Documents/SimulateurElectronique/";

    public static void saveSheet(int id, String name, Color color, Sheet sheet) {
        ComponentData componentData = new ComponentData(id, name, color, sheet);
        System.out.println(componentData.getFileContent());
        //TODO: Change setTruthTable argument to "compiled version" of the truthTable of the sheet
        componentData.setTruthTable(new Boolean[] {true, true, false, true, false, true, true, true, false});
        Boolean[] truthTable = new Boolean[sheet.ioComponent.startNodes.size()];
        for (int i=0;i<pow(2, sheet.ioComponent.startNodes.size());i++) {

        }
        System.out.println(componentData.getTable());


        //TODO: write in file
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
        String[] content = fileContent.split("\n");
        int id = Integer.parseInt(content[0].split(": ")[1]);
        String name = content[1].split(": ")[1];
        Color color = Color.web(content[2].split(": ")[1]);

        int inputs = Integer.parseInt(content[3].split(": ")[1]);
        int outputs = Integer.parseInt(content[4].split(": ")[1]);

        double width = Double.parseDouble(content[5].split(": ")[1]);
        double height = Double.parseDouble(content[6].split(": ")[1]);

        physicSheet = new com.Physics.Sheet();
        currentSheet = new Sheet(width, height);

        for (int i = 0; i < inputs; i++) {
            currentSheet.ioComponent.addStartNode(currentSheet);
        }
        for (int i = 0; i < outputs; i++) {
            currentSheet.ioComponent.addEndNode(currentSheet);
        }

        String[] compId = content[7].split(": ")[1].split(", ");
        String[] compX = content[8].split(": ")[1].split(", ");
        String[] compY = content[9].split(": ")[1].split(", ");

        for (int i = 0; i < compId.length; i++) {
            int compIdInt = Integer.parseInt(compId[i]);
            SheetObject abstractComponent = loadedObjects[compIdInt];
            Component physicComponent = new Component("", abstractComponent.inputs, abstractComponent.outputs, truthTables[compIdInt]);
            currentSheet.addObject(new ComponentInstance(
                    loadedObjects[compIdInt],
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
            //Wire physicWire = new Wire();
            Wire physicWire = wireInstance.getPhysicWire();

            int startComp = Integer.parseInt(wireStartComp[i]);
            int endComp = Integer.parseInt(wireEndComp[i]);

            if (startComp == -1) {
                wireInstance.setStart(currentSheet.ioComponent.startNodes.get(Integer.parseInt(wireStartNode[i])));
                physicWire.setState(false);
                physicSheet.addWireInput(physicWire);
            } else {
                wireInstance.setStart(currentSheet.components.get(startComp).outputs[Integer.parseInt(wireStartNode[i])]);
                physicSheet.getComponents().get(startComp).addWireOutput(physicWire, Integer.parseInt(wireEndNode[i]));
                physicWire.addConnection(physicSheet.getComponents().get(startComp), 0);
            }

            if (endComp == -1) {
                wireInstance.setEnd(currentSheet.ioComponent.endNodes.get(Integer.parseInt(wireEndNode[i])));
                physicSheet.addOutput(wireInstance.getPhysicWire());
                System.out.println("Output");
            } else {
                wireInstance.setEnd(currentSheet.components.get(endComp).inputs[Integer.parseInt(wireEndNode[i])]);
                physicSheet.getComponents().get(endComp).addWireInput(physicWire, Integer.parseInt(wireStartNode[i]));
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
        SheetObject[] tempArray = new SheetObject[lastId + 3];
        Boolean[][][] truthTables = new Boolean[lastId + 3][][];

        tempArray[0] = new SheetObject(0, "not", Color.BROWN, 1, 1);
        tempArray[1] = new SheetObject(1, "and", Color.GREEN, 2, 1);
        tempArray[2] = new SheetObject(2, "or", Color.RED, 2, 1);

        truthTables[0] = new Boolean[][] {{true}, {false}};
        truthTables[1] = new Boolean[][] {{false}, {false}, {false}, {true}};
        truthTables[2] = new Boolean[][] {{false}, {true}, {true}, {true}};

        for (File file: compFiles) {
            try {
                String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                String[] split = fileContent.split("\n");
                String rawId = split[0].split(": ")[1];
                int id = Integer.parseInt(rawId);

                if (id >= lastId) {
                    tempArray[id] = new SheetObject(id,
                            split[1].split(": ")[1],
                            Color.web(split[2].split(": ")[1]),
                            Integer.parseInt(split[3].split(": ")[1]),
                            Integer.parseInt(split[4].split(": ")[1])
                    );
                    String rawTable = split[14].split(": ")[1];
                    truthTables[id] = new Boolean[rawTable.length()][];
                    for (int i = 0; i < rawTable.length(); i++) {
                        if (rawTable.charAt(i) == '1') truthTables[id][i][0] = true;
                        else truthTables[id][i][0] = false;
                    }
                    fileNames[id - 3] = file;
                }
            }
            catch (IOException e) {
                System.err.println("IOException");
            }
            catch (NumberFormatException | PatternSyntaxException | IndexOutOfBoundsException e) {
                System.err.println("File corrupted : " + file.getPath());
            }
        }

        if (fileNames.length > 0) {
            int i = 0;
            while (fileNames[i] != null) i++;
            i--;

            loadedObjects = Arrays.copyOfRange(tempArray, 0, i + 3);
            SaveLoadSheet.truthTables = Arrays.copyOfRange(truthTables, 0, i + 3);
            return Files.readString(fileNames[i].toPath(), StandardCharsets.UTF_8);
        }
        else {
            loadedObjects = tempArray;
            SaveLoadSheet.truthTables = truthTables;
            return null;
        }
    }

    public static void loadAll() throws IOException, ComponentNotFoundException {
        File[] compFiles = new File(defaultPath).listFiles();
        String rawSheet = null;
        if (compFiles != null) {
            rawSheet = loadObjectUntil(compFiles);
            loadSheet(rawSheet);
        }
        else {
            loadObjectUntil(new File[0]);
            currentSheet = new Sheet(500, 500);
        }
    }
}
