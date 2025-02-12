package org.example.linktomusicbeta.controller;

import ch.qos.logback.classic.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.linktomusicbeta.service.ConvertService;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ConvertController {

    private final ConvertService convertService = new ConvertService();
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ConvertController.class);
    private Stage primaryStage;
    @FXML
    private TextField linkField;
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    protected void handleLinkSubmit() throws IOException, InterruptedException {
        String link = linkField.getText();

        if(link ==null || link.isEmpty()){
            logger.warn("Please enter a valid link");
            return;
        }
        convertService.convertLinkToMusic(link);
    }

    @FXML
    public void changeScene() {
        try {
            // 새 FXML 파일을 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/linktomusicbeta/fxml/myMusic.fxml"));
            VBox  newRoot = loader.load();

            MyMusicController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            // 새로운 Scene을 설정
            Scene newScene = new Scene(newRoot);
            primaryStage.setScene(newScene);
            logger.info("Scene changed successfully");
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

}