package com.Application.FileManger;

import com.Graphics.Workspace.Sheet.Sheet;
import com.google.gson.Gson;
import javafx.scene.paint.Color;

public class ComponentData {
    // Informations génériques sur le composant
    public int id;
    public String name;
    public Color color;
    public int inputs;
    public int outputs;

    // Table de vérité du composant
    public Boolean[] truthTable;

    // Informations sur la feuille du composant
    public double width;
    public double height;
    public int[] components;
    /**
     * The array of the x coordinate of the center of the component
     */
    public double[] componentsX;
    /**
     * The array of the y coordinate of the center of the component
     */
    public double[] componentsY;
    /**
     * The list of the id of the start of the wires of the sheet
     */
    public int[] wiresStartNode;
    /**
     * The list of the id of the node of the component of the sheet
     */
    public int[] wiresStartComp;
    /**
     * The list of the id of the start of the wires of the sheet
     */
    public int[] wiresEndNode;
    /**
     * The list of the id of the node of the component of the sheet
     */
    public int[] wiresEndComp;
    /**
     * The value of the middle of the wire
     */
    public double[] wiresMiddle;

    /**
     * Base constructor
     * @param id the id of the component
     * @param name the name of the component
     * @param color the color of the component
     */
    public ComponentData(int id, String name, Color color, int inputs, int outputs) {
        this.id = id;
        this.name = name;
        this.color = color;

        this.inputs = inputs;
        this.outputs = outputs;
    }

    /**
     * Base constructor
     * @param id the id of the component
     * @param name the name of the component
     * @param color the color of the component
     */
    public ComponentData(int id, String name, Color color, Sheet sheet) {
        this.id = id;
        this.name = name;
        this.color = color;
        sheet.dataCollector(this);
    }

    public void setTruthTable(Boolean[] truthTable) {
        this.truthTable = truthTable;
    }

    String getFileContent() {
        String[] content = new String[15];
        content[0] = "id: " + id;
        content[1] = "name: " + name;
        content[2] = "color: " + color.toString();
        content[3] = "inputs: " + inputs;
        content[4] = "outputs: " + outputs;
        content[5] = "width: " + width;
        content[6] = "height: " + height;
        content[7] = "components: ";
        for (int i = 0; i < components.length; i++) {
            content[7] += components[i];
            if (i != components.length - 1) {
                content[7] += ", ";
            }
        }

        content[8] = "componentsX: ";
        for (int i = 0; i < componentsX.length; i++) {
            content[8] += componentsX[i];
            if (i != componentsX.length - 1) {
                content[8] += ", ";
            }
        }

        content[9] = "componentsY: ";
        for (int i = 0; i < componentsY.length; i++) {
            content[9] += componentsY[i];
            if (i != componentsY.length - 1) {
                content[9] += ", ";
            }
        }

        content[10] = "wiresStartComp: ";
        for (int i = 0; i < wiresStartComp.length; i++) {
            content[10] += wiresStartComp[i];
            if (i != wiresStartComp.length - 1) {
                content[10] += ", ";
            }
        }

        content[11] = "wiresStartNode: ";
        for (int i = 0; i < wiresStartNode.length; i++) {
            content[11] += wiresStartNode[i];
            if (i != wiresStartNode.length - 1) {
                content[11] += ", ";
            }
        }

        content[12] = "wiresEndComp: ";
        for (int i = 0; i < wiresEndComp.length; i++) {
            content[12] += wiresEndComp[i];
            if (i != wiresEndComp.length - 1) {
                content[12] += ", ";
            }
        }

        content[13] = "wiresEndNode: ";
        for (int i = 0; i < wiresEndNode.length; i++) {
            content[13] += wiresEndNode[i];
            if (i != wiresEndNode.length - 1) {
                content[13] += ", ";
            }
        }

        content[14] = "table: " + getTable();

        return String.join("\n", content);
    }

    String getTable() {
        StringBuilder table = new StringBuilder();
        for (Boolean b : truthTable) {
            if (b) {
                table.append("1");
            } else {
                table.append("0");
            }
        }
        return table.toString();
    }

    public static ComponentData parseData(String fileContent) {
        String[] content = fileContent.split("\n");
        int id = Integer.parseInt(content[0].split(": ")[1]);
        String name = content[1].split(": ")[1];
        Color color = Color.web(content[2].split(": ")[1]);

        int inputs = Integer.parseInt(content[3].split(": ")[1]);
        int outputs = Integer.parseInt(content[4].split(": ")[1]);

        double width = Double.parseDouble(content[5].split(": ")[1]);
        double height = Double.parseDouble(content[6].split(": ")[1]);

        ComponentData data = new ComponentData(id, name, color, inputs, outputs);
        data.width = width;
        data.height = height;

        String[] compId = content[7].split(": ")[1].split(", ");
        String[] compX = content[8].split(": ")[1].split(", ");
        String[] compY = content[9].split(": ")[1].split(", ");

        data.components = new int[compId.length];
        data.componentsX = new double[compX.length];
        data.componentsY = new double[compY.length];

        for (int i = 0; i < compId.length; i++) {
            data.components[i] = Integer.parseInt(compId[i]);
            data.componentsX[i] = Double.parseDouble(compX[i]);
            data.componentsY[i] = Double.parseDouble(compY[i]);
        }

        String[] wireStartComp = content[10].split(": ")[1].split(", ");
        String[] wireStartNode = content[11].split(": ")[1].split(", ");
        String[] wireEndComp = content[12].split(": ")[1].split(", ");
        String[] wireEndNode = content[13].split(": ")[1].split(", ");

        data.wiresStartComp = new int[wireStartComp.length];
        data.wiresStartNode = new int[wireStartNode.length];
        data.wiresEndComp = new int[wireEndComp.length];
        data.wiresEndNode = new int[wireEndNode.length];

        for (int i = 0; i < wireStartComp.length; i++) {
            data.wiresStartComp[i] = Integer.parseInt(wireStartComp[i]);
            data.wiresStartNode[i] = Integer.parseInt(wireStartNode[i]);
            data.wiresEndComp[i] = Integer.parseInt(wireEndComp[i]);
            data.wiresEndNode[i] = Integer.parseInt(wireEndNode[i]);
        }
        //TODO: parse the table
        data.truthTable = new Boolean[inputs];

        return data;
    }
}
