module com.graphics.devproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;

    opens com.Graphics to javafx.fxml;
    exports com.Graphics;
    exports com.Graphics.Canvas;
    opens com.Graphics.Canvas to javafx.fxml;

    requires com.google.gson;
}