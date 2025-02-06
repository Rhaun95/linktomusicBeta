package org.example.linktomusicbeta.controller;

import ch.qos.logback.classic.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.example.linktomusicbeta.model.Music;
import org.slf4j.LoggerFactory;


public class MusicPlayerController {

    private static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(MusicPlayerController.class);

    @FXML private Button playButton, pauseButton, stopButton, closeButton;
    @FXML private MediaPlayer mediaPlayer;

    public void playMusic(Music music) {
        if (music == null) {
          return;
        }
        try {
            String mediaSource = music.getUrl();
            Media media = new Media(mediaSource);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            logger.info("music playing");

        } catch (Exception e) {
            logger.info("music loading failed : Enter valid link");
        }
    }

    @FXML
    private void playMusic() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    @FXML
    private void pauseMusic() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    @FXML
    private void stopMusic() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
    @FXML
    private void closePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
