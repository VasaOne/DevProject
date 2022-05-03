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
    public boolean[][] truthTable;

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
    public int[] wiresStart;
    /**
     * The list of the id of the node of the component of the sheet
     */
    public int[] wiresStartId;
    /**
     * The list of the id of the end of the wires of the sheet
     */
    public int[] wiresEnd;
    /**
     * The list of the id of the node of the component of the sheet
     */
    public int[] wiresEndId;
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
        //TODO: add the truth table of the sheet
        this.truthTable = new boolean[][]{};
    }

    public String getJson() {
        String json = "{\n";
        json += "\t\"id\": " + id + ",\n";
        json += "\t\"name\": \"" + name + "\",\n";
        json += "\t\"color\": \"" + color.toString() + "\",\n";
        json += "\t\"inputs\": " + inputs + ",\n";
        json += "\t\"outputs\": " + outputs + ",\n";
        json += "\t\"truthTable\": [\n";
        for (int i = 0; i < truthTable.length; i++) {
            json += "\t[";
            for (int j = 0; j < truthTable[i].length; j++) {
                json += "\t\t" + truthTable[i][j];
                if (j != truthTable[i].length - 1) {
                    json += ", ";
                }
            }
            json += "]";
            if (i != truthTable.length - 1) {
                json += ",\n";
            }
        }
        json += "\t],\n";
        json += "\t\"width\": " + width + ",\n";
        json += "\t\"height\": " + height + ",\n";
        json += "\t\"components\": [";
        for (int i = 0; i < components.length; i++) {
            json += components[i];
            if (i != components.length - 1) {
                json += ", ";
            }
        }
        json += "],\n";
        json += "\t\"componentsX\": [";
        for (int i = 0; i < componentsX.length; i++) {
            json += componentsX[i];
            if (i != componentsX.length - 1) {
                json += ", ";
            }
        }
        json += "],\n";
        json += "\t\"componentsY\": [";
        for (int i = 0; i < componentsY.length; i++) {
            json += componentsY[i];
            if (i != componentsY.length - 1) {
                json += ", ";
            }
        }
        json += "],\n";
        json += "\t\"wiresStart\": [";
        for (int i = 0; i < wiresStart.length; i++) {
            json += wiresStart[i];
            if (i != wiresStart.length - 1) {
                json += ", ";
            }
        }
        json += "],\n";
        json += "\t\"wiresStartId\": [";
        for (int i = 0; i < wiresStartId.length; i++) {
            json += wiresStartId[i];
            if (i != wiresStartId.length - 1) {
                json += ", ";
            }
        }
        json += "],\n";
        json += "\t\"wiresEnd\": [";
        for (int i = 0; i < wiresEnd.length; i++) {
            json += wiresEnd[i];
            if (i != wiresEnd.length - 1) {
                json += ", ";
            }
        }
        json += "],\n";
        json += "\t\"wiresEndId\": [";
        for (int i = 0; i < wiresEndId.length; i++) {
            json += wiresEndId[i];
            if (i != wiresEndId.length - 1) {
                json += ", ";
            }
        }
        json += "]\n";
        json += "}";
        return json;
    }
}
