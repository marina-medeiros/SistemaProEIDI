module org.example.sistemaproeidi {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.incubator.vector;


    opens br.ufrn.imd.sistemaproeidi to javafx.fxml;
    exports br.ufrn.imd.sistemaproeidi;
    exports br.ufrn.imd.sistemaproeidi.controller;
    opens br.ufrn.imd.sistemaproeidi.controller to javafx.fxml;
}