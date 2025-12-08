module com.example.koniary {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires java.naming;
    requires java.xml.bind;

    requires org.slf4j;

    opens com.example.koniary to javafx.fxml, org.hibernate.orm.core;
    opens com.example.koniary.controllers to javafx.fxml, org.hibernate.orm.core;
    opens com.example.koniary.model to javafx.fxml, org.hibernate.orm.core;

    exports com.example.koniary;
    exports com.example.koniary.controllers;
    exports com.example.koniary.model;
}
