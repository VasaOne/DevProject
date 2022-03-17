module pro {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires transitive javafx.graphics;
	requires transitive javafx.base;
	opens application to javafx.fxml;
	exports application to javafx.graphics;
}
