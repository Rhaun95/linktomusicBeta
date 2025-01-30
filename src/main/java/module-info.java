module linktomusicbeta {

    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires ch.qos.logback.classic;
    requires org.slf4j;

    opens org.example.linktomusicbeta to javafx.fxml;
    opens org.example.linktomusicbeta.controller to javafx.fxml;

    exports org.example.linktomusicbeta;
    exports org.example.linktomusicbeta.controller to javafx.fxml;



}