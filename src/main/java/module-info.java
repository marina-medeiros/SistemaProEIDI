module org.example.sistemaproeidi {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.incubator.vector;
    requires java.logging;


    opens br.ufrn.imd.sistemaproeidi to javafx.fxml;
    exports br.ufrn.imd.sistemaproeidi;
    exports br.ufrn.imd.sistemaproeidi.controller;
    exports br.ufrn.imd.sistemaproeidi.model;
    exports br.ufrn.imd.sistemaproeidi.model.enums;
    opens br.ufrn.imd.sistemaproeidi.controller to javafx.fxml;
}