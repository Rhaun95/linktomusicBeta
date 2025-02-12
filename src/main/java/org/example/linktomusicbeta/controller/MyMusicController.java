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
import org.example.linktomusicbeta.service.MediaPlayerService;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
 * TODO:
 *  move to other pages, mediaPlayer stil playing but disaapered
 *  플레이어와 리스트 화면 겹치게
 *  delete function
 * */
public class MyMusicController {

    public static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(MyMusicController.class);

    @FXML private ListView<Music>  musicListView;


    private Stage primaryStage;
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void initialize() throws InvalidDataException, UnsupportedTagException, IOException {

        String musicDirectory = System.getProperty("user.home")+ File.separator+ "Music";
        logger.info(musicDirectory);
        File musicDir = new File(musicDirectory);
        if (!musicDir.exists() || !musicDir.isDirectory()) {
            logger.warn("Could not find Music folder.");
            return;
        }
        logger.info("Music directory exists.");

        File[] fileList = musicDir.listFiles((dir, name) -> name.endsWith(".mp3"));
        List<Music> musicFiles = new ArrayList<>();

        if (fileList != null) {
            for (File musicFile : fileList) {
                musicFiles.add(extractMetadata(musicFile));
            }
        }

        ObservableList<Music> musicList = FXCollections.observableArrayList(musicFiles);
        musicListView.setItems(musicList);
        musicListView.setCellFactory(param -> new MyMusicCell());

        musicListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // 더블 클릭 감지
                String selectedMusic = musicListView.getSelectionModel().getSelectedItem().getFilePath();
                if (selectedMusic != null) {
                    MediaPlayerService.getInstance().play(selectedMusic);
                    MusicPlayerController.getInstance().setIsPlaying(selectedMusic);
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
        Music music = new Music(albumImageData,title, artist);
        music.setFilePath(track.toURI().toString());
        return music;
    }

}
