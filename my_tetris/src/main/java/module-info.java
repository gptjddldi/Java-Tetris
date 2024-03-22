module org.example.myjavafxtetris {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.myjavafxtetris to javafx.fxml;
    exports org.example.myjavafxtetris;
}