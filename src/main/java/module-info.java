module com.example.javatetris {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.javatetris to javafx.fxml;
    exports com.example.javatetris;
}