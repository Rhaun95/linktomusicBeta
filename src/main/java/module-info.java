module linktomusicbeta {

    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics;

    opens linktomusicbeta.controller to javafx.fxml;

//    opens linktomusicbeta.model;

    exports linktomusicbeta.controller to javafx.fxml;
    exports linktomusicbeta.gui;
//    opens linktomusicbeta.model.god;

}