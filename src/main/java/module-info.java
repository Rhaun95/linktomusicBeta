module org.example.linktomusicbeta {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;

    requires ch.qos.logback.classic;
    requires org.slf4j;
    requires org.json;
    requires jaudiotagger;
    requires mp3agic;
    requires uk.co.caprica.vlcj;
    requires jdk.httpserver;


    opens org.example.linktomusicbeta to javafx.fxml;
    exports org.example.linktomusicbeta;

}