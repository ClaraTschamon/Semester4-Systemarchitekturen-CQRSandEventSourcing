module at.fhv.cts.fxclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;
    requires spring.webflux;
    requires spring.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires spring.beans;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires reactor.core;
    requires CQRSandEventSourcing.Share.main;

    exports at.fhv.cts.fxclient;

    opens at.fhv.cts.fxclient.domainModel to javafx.base;

    exports at.fhv.cts.fxclient.domainModel to com.fasterxml.jackson.databind;
    opens at.fhv.cts.fxclient to javafx.base, javafx.fxml;
    exports at.fhv.cts.fxclient.rest;
    opens at.fhv.cts.fxclient.rest to javafx.base, javafx.fxml;

}