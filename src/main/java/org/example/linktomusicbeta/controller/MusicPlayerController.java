package org.example.linktomusicbeta.controller;
//
//import ch.qos.logback.classic.Logger;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.stage.Stage;

import ch.qos.logback.classic.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.example.linktomusicbeta.service.MediaPlayerService;
import org.slf4j.LoggerFactory;


public class MusicPlayerController {
    private static MusicPlayerController musicPlayerController;
    private static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(MusicPlayerController.class);
    private String selectedMusic;

    @FXML private Button playPauseButton, stopButton, closeButton;
    @FXML private HBox musicPlayerContainer;

    private boolean isPlaying = false;


    public MusicPlayerController() {
        musicPlayerController = this;
    }
    public static MusicPlayerController getInstance() {
        return musicPlayerController;
    }

    public void setIsPlaying(String filePath) {
        this.selectedMusic = filePath;
        isPlaying = true;
        musicPlayerContainer.setVisible(true);
    }

    @FXML
    private void togglePlayPause() {

        if (isPlaying) {
            MediaPlayerService.getInstance().pause();
            playPauseButton.setText("▶");
        } else {
            MediaPlayerService.getInstance().play(selectedMusic);
            playPauseButton.setText("⏸");
        }
        isPlaying = !isPlaying;
    }

    @FXML
    private void stopMusic() {
        MediaPlayerService.getInstance().stop();
        musicPlayerContainer.setVisible(false);
    }
}
