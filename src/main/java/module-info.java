module com.example.landfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.landfx to javafx.fxml;
    exports com.example.landfx;
    exports com.example.landfx.Enteties.AbstractEnteties;
    opens com.example.landfx.Enteties.AbstractEnteties to javafx.fxml;
}