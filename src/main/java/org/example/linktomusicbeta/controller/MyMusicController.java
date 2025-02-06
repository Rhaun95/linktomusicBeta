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
import javafx.scene.layout.VBox;
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
        logger.info("Primary stage set: " + stage);
        this.primaryStage = stage;
    }
    @FXML private ListView<Music>  musicListView;
    @FXML private Button toConvert;

    public void initialize() throws InvalidDataException, UnsupportedTagException, IOException {
        logger.info("Primary stage is set: " + primaryStage);


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
        musicListView.getItems().forEach(music -> {
            logger.info("ListView 내용 확인 -> 제목: " + music.getTitle() + ", 아티스트: " + music.getArtist());
        });
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
        ID3v2 tag = mp3File.getId3v2Tag();

        if (tag == null) {
          return null;
        }


//            if (mp3File.hasId3v2Tag()) {
//                ID3v2 id3v2Tag = mp3File.getId3v2Tag();
//                logger.info("파일: " + track.getName());
//                logger.info("제목: " + id3v2Tag.getTitle());
//                logger.info("아티스트: " + id3v2Tag.getArtist());
//                logger.info("앨범: " + id3v2Tag.getAlbum());
//                logger.info("장르: " + id3v2Tag.getGenreDescription());
//                logger.info("커버 이미지 존재 여부: " + (id3v2Tag.getAlbumImage() != null));
//                logger.info("==================================");
//            } else {
//                logger.info("ID3v2 태그가 없는 파일: " + track.getName());
//            }


        String title = tag.getTitle();
        String artist = tag.getArtist();
        String album = tag.getAlbum();
        String genre = tag.getGenreDescription();
        byte[] imageData = tag.getAlbumImage();
        // 앨범 이미지 추출 (APIC 프레임)
        Image albumImageData = null;

        if (imageData != null) {
            albumImageData = new Image(new ByteArrayInputStream(imageData));
        }

        return new Music(albumImageData,title, artist, track.getPath());
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
//    public List<Music> loadMusicFiles(String path) throws InvalidDataException, UnsupportedTagException, IOException {
//        File folder = new File(path);
//        List<Music> musicFiles = new ArrayList<Music>();
//
//        if(folder.exists() && folder.isDirectory()) {
//            System.out.println("폴더 경로: " + path);
//            for(File file : Objects.requireNonNull(folder.listFiles())) {
////                if (file.isFile() && (file.getName().endsWith(".mp3") || file.getName().endsWith(".aac"))) {
//                if (file.isFile()&& !file.getName().equalsIgnoreCase("desktop.ini")
//                        && (file.getName().endsWith(".mp3") || file.getName().endsWith(".aac"))) {
//                    System.out.println("파일 경로: " + file.getAbsolutePath());
//                    musicFiles.add( extractMetadata(file));
//                }
//            }
//        }
//        return musicFiles;
//    }
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
