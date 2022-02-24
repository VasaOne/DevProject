package com;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Config {
    //Colors of the workspace :
    public static Color WSBackgroundColor = Color.LIGHTGRAY;
    public static Color WSTextColor = Color.BLACK;
    public static Color WSOnNodesColor = Color.web("#F21C1C");
    public static Color WSOffNodesColor = Color.web("#7F1010");
    public static Color WSDisabledColor = Color.GRAY;
    public static double WSMoveAlpha = 0.7;

    //Texts of the workspace :
    public static double WSFontSize;
    public static Font WSFont() { return Font.font(WSFontSize); }
    public static Font WSFont(double size) {
        WSFontSize = size;
        return Font.font(size);
    }
}
