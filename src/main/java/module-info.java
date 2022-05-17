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
    exports com.Graphics.Workspace.Sheet;
    opens com.Graphics.Workspace.Sheet to javafx.fxml;
    exports com.Graphics.Workspace.Application;
    opens com.Graphics.Workspace.Application to javafx.fxml;
    exports com.Graphics.Workspace.Node;
    opens com.Graphics.Workspace.Node to javafx.fxml;
    exports com.Graphics.Workspace.Component;
    opens com.Graphics.Workspace.Component to javafx.fxml;
    exports com.Graphics.Workspace.Wire;
    opens com.Graphics.Workspace.Wire to javafx.fxml;

    exports com.Application.FileManger;

    requires com.google.gson;
}