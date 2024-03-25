module com.example.javatetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires com.almasb.fxgl.all;

    opens com.example.javatetris to javafx.fxml;
    exports com.example.javatetris;
    exports com.example.page;
    opens com.example.page to javafx.fxml;
}