package com.Application.FileManger;

import com.Graphics.Workspace.Sheet.Sheet;
import com.google.gson.Gson;
import javafx.scene.paint.Color;

public class SaveLoadSheet {
    public static Sheet loadSheet(String json) {
        String[] data = json.split(",\n");
        Gson gson = new Gson();
        Sheet sheet = new Sheet(gson.fromJson(data[3], double.class), gson.fromJson(data[4], double.class));
                /*gson.fromJson(data[0].split("\n")[1], int.class),
                gson.fromJson(data[1], String.class),
                gson.fromJson(data[2], Color.class),*/

        return sheet;
    }
    public static void saveSheet(int id, String name, Color color, Sheet sheet) {
        String json = new ComponentData(id, name, color, sheet).getJson();
        loadSheet(json);
        //System.out.println(json);
    }
}
