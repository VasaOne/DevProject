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
}
