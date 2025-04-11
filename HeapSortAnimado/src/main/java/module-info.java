module com.example.heapsortanimado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.heapsortanimado to javafx.fxml;
    exports com.example.heapsortanimado;
}