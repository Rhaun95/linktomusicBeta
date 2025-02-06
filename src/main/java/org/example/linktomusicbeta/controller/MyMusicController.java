package org.example.linktomusicbeta.controller;

import ch.qos.logback.classic.Logger;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.example.linktomusicbeta.component.MyMusicCell;
import org.example.linktomusicbeta.model.Music;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyMusicController {

    public static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(MyMusicController.class);
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    @FXML private ListView<Music>  musicListView;
    @FXML private Button toConvert;
    @FXML private Button playPauseButton, stopButton, closeButton;
    @FXML private HBox musicPlayerContainer;
    private boolean isPlaying = false;

    /*
     * TODO:
     *  move to other pages, mediaPlayer stil playing but disaapered
     * */
    public void initialize() throws InvalidDataException, UnsupportedTagException, IOException {

        String musicDirectory = System.getProperty("user.home")+ File.separator+ "Music";
        logger.info(musicDirectory);
        File musicDir = new File(musicDirectory);
        if (!musicDir.exists() || !musicDir.isDirectory()) {
            logger.warn("Could not find Music folder.");
            return;
        }
        logger.info("Music directory exists.");

        File[] trackList = musicDir.listFiles((dir, name) -> name.endsWith(".mp3"));
        List<Music> musicFiles = new ArrayList<>();

        if (trackList != null) {
            for (File track : trackList) {
                musicFiles.add(extractMetadata(track));
            }
        }

        ObservableList<Music> musicList = FXCollections.observableArrayList(musicFiles);
        musicListView.setItems(musicList);
        musicListView.setCellFactory(param -> new MyMusicCell());

        musicListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // 더블 클릭 감지
                Music selectedMusic = musicListView.getSelectionModel().getSelectedItem();
                if (selectedMusic != null) {
                    openMusicPlayer(selectedMusic);
                }
            }
        });
    }

    public Music extractMetadata(File track) throws InvalidDataException, UnsupportedTagException, IOException {

        Mp3File mp3File = new Mp3File(track);
        if (!mp3File.hasId3v2Tag()) {
            return null;
        }
        ID3v2 tag = mp3File.getId3v2Tag();
        String title = tag.getTitle();
        String artist = tag.getArtist();
        String album = tag.getAlbum();
        String genre = tag.getGenreDescription();
        Image albumImageData = null;

        if (tag.getAlbumImage() != null) {
            byte[] imageData = tag.getAlbumImage();
            albumImageData = new Image(new ByteArrayInputStream(imageData));
        }
        return new Music(albumImageData,title, artist, track.toURI().toString());
    }

    @FXML
    public void changeToConvert() {
        try {
            // 새 FXML 파일을 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/linktomusicbeta/fxml/convert.fxml"));
            VBox newRoot = loader.load();

            ConvertController controller= loader.getController();
            controller.setPrimaryStage(primaryStage);
            // 새로운 Scene을 설정
            Scene newScene = new Scene(newRoot);
            primaryStage.setScene(newScene);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    private void openMusicPlayer(Music music) {

        logger.info("selected Music: {} - {}", music.getTitle(), music.getArtist());

        playMusic(music);


    }

    @FXML
    private void togglePlayPause() {
        if (mediaPlayer == null) return;

        if (isPlaying) {
            mediaPlayer.pause();
            playPauseButton.setText("▶");
        } else {
            mediaPlayer.play();
            playPauseButton.setText("⏸");
        }
        isPlaying = !isPlaying;
    }

    @FXML
    private void stopMusic() {
        mediaPlayer.stop();
        musicPlayerContainer.setVisible(false);
    }

    @FXML private MediaPlayer mediaPlayer;

    public void playMusic(Music music) {
        try {
            String mediaSource = music.getUrl();
            Media media = new Media(mediaSource);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            musicPlayerContainer.setVisible(true);
            isPlaying = true;
            logger.info("music playing");

        } catch (Exception e) {
            logger.info("music loading failed : Enter valid link");
        }
    }

}
