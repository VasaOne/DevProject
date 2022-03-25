module com.devproject {
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
    exports com.Graphics.Workspace;
    opens com.Graphics.Workspace to javafx.fxml;

    requires com.google.gson;
}