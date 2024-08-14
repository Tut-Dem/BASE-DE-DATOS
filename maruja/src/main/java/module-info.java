module com.prototype.maruja {
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
    requires java.sql;

    opens com.prototype.maruja to javafx.fxml;
    opens com.prototype.maruja.controllers to javafx.fxml;
    opens com.prototype.maruja.utils to javafx.fxml;
    opens com.prototype.maruja.models to javafx.fxml;
    opens com.prototype.maruja.database to javafx.fxml;
    exports com.prototype.maruja;
    exports com.prototype.maruja.models;
    exports com.prototype.maruja.database;
    exports com.prototype.maruja.controllers;
    exports com.prototype.maruja.utils;
}