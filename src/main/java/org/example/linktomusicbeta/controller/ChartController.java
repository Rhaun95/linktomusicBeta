package org.example.linktomusicbeta.controller;

import ch.qos.logback.classic.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.linktomusicbeta.component.ChartMusicCell;
import org.example.linktomusicbeta.model.Music;
import org.example.linktomusicbeta.service.MusicAPI;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/*
* TODO: 
*  Music List -> click + -> open convert tab to add
*  Musicplayer?
*  save/track Music
* */
public class ChartController {

    public static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(ChartController.class);


    @FXML private VBox chartView;
    @FXML private ListView<Music> musicListView;
    @FXML private BorderPane headerView;
    @FXML private Button toConvertBtn;

    /*TODO:
       - initialize with already exists music data
     */
    public void initialize() {
        ObservableList<Music> musicList = MusicAPI.fetchLatestMusic();

        musicListView.setItems(musicList);
        musicListView.setCellFactory(param -> new ChartMusicCell());

        musicListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // 더블 클릭 감지
                Music selectedMusic = musicListView.getSelectionModel().getSelectedItem();
                if (selectedMusic != null) {
                    openMusicPlayer(selectedMusic);
                }
            }
        });
    }


    private void openMusicPlayer(Music music) {
        try {
            logger.info("selected Music: {} - {}", music.getTitle(), music.getArtist());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/linktomusicbeta/fxml/musicPlayer.fxml"));
            Parent root = loader.load();
            logger.info("New Tab opened");

            MusicPlayerController controller = loader.getController();
            controller.setMusic(music);


            Stage stage = new Stage() ;
            stage.setScene(new Scene(root));
            stage.setTitle("Music Player");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}