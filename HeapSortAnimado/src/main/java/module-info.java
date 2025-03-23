module com.example.heapsortanimado {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.heapsortanimado to javafx.fxml;
    exports com.example.heapsortanimado;
}