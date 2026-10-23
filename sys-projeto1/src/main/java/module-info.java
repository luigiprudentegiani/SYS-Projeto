module org.sysprojeto {
    requires javafx.controls;
    requires javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.base;
    exports app;
}