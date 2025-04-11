module org.fipp.gabryelborges.animacaoradixsort {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.fipp.gabryelborges.animacaoradixsort to javafx.fxml;
    exports org.fipp.gabryelborges.animacaoradixsort;
}