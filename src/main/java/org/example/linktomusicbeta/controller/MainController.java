package org.example.linktomusicbeta.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML private StackPane contentArea;  // main.fxml의 contentArea

    @FXML
    public void changeToMyMusic() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/linktomusicbeta/fxml/myMusic.fxml"));
            VBox myMusicPage = loader.load();

            // myMusic.fxml 페이지를 contentArea에 로드
            contentArea.getChildren().setAll(myMusicPage);

        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @FXML
    public void changeToConvert() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/linktomusicbeta/fxml/convert.fxml"));
            VBox convertPage = loader.load();

            // convert.fxml 페이지를 contentArea에 로드
            contentArea.getChildren().setAll(convertPage);

        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @FXML
    public void emptyAction() {
        // 아무 작업 안 함
    }

}
