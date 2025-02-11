package org.example.linktomusicbeta.controller;

import ch.qos.logback.classic.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.example.linktomusicbeta.component.MusicCell;
import org.example.linktomusicbeta.model.Music;
import org.example.linktomusicbeta.service.AudioProxyServer;
import org.example.linktomusicbeta.service.ConvertService;
import org.example.linktomusicbeta.service.YouTubeSearch;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class SearchController {
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private ListView<Music> searchResultsView;
    @FXML private Button playPauseButton, stopButton, closeButton;
    @FXML private HBox musicPlayerContainer;
    private boolean isPlaying = false;
    private Stage primaryStage;
    public static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(MyMusicController.class);

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    private final ObservableList<Music> searchResults = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        searchResultsView.setItems(searchResults);
        searchResultsView.setCellFactory(param -> new MusicCell());
        searchResultsView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // 더블 클릭 감지
                Music selectedMusic = searchResultsView.getSelectionModel().getSelectedItem();
                if (selectedMusic != null) {
                    openMusicPlayer(selectedMusic);
                }
            }
        });
    }

    @FXML
    public void handleSearch() {
        String query = searchField.getText();
        if (query.isEmpty()) return;

        new Thread(() -> {
            try {
                JSONArray results = YouTubeSearch.searchYouTube(query);
                ObservableList<Music> musicList = FXCollections.observableArrayList();

                for (int i = 0; i < results.length(); i++) {
                    JSONObject track = results.getJSONObject(i);
//                    System.out.println("track : " + track);
                    String videoId = track.getJSONObject("id").getString("videoId");
                    String title = track.getJSONObject("snippet").getString("title");
                    String artist = track.getJSONObject("snippet").getString("channelTitle");
                    String thumbnail = track.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
//                    String youtubeUrl = "https://www.youtube.com/watch?v=" + videoId;
//                    String audioUrl = ConvertService.getAudioStreamUrl(youtubeUrl);
                    Music music =  new Music(new Image(thumbnail),title, artist);
                    music.setYtVideoID(videoId);
//                    music.setUrl(audioUrl);
                    musicList.add(music);
                }

                Platform.runLater(() -> searchResults.setAll(musicList));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
            String youtubeUrl = "https://www.youtube.com/watch?v=" + music.getYtVideoID();
            String audioUrl = ConvertService.getAudioStreamUrl(youtubeUrl);

//            if (audioUrl != null && !audioUrl.isEmpty()) {
//                Media media = new Media(audioUrl);
//                mediaPlayer = new MediaPlayer(media);
////                mediaPlayer.setVolume(0.5);
//                mediaPlayer.play();
//                System.out.println(mediaPlayer.getStatus());
//            } else {
//                logger.error("Failed to retrieve valid audio URL");
//            }

            AudioProxyServer.startProxyServer(audioUrl);
            Media media = new Media("http://localhost:8080/audio");
             mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(mediaPlayer::play);

//            mediaPlayer.setOnError(e -> {
//                logger.error("MediaPlayer error: " +);
//            });

//            musicPlayerContainer.setVisible(true);
            isPlaying = true;
//            AudioClip clip = new AudioClip(audioUrl);
//            clip.play();
//            String mediaSource = music.getUrl();

            logger.info("music playing");

        } catch (Exception e) {
            logger.info("music loading failed : Enter valid link " + "/n" + e.getMessage() );
        }
    }
//    private void playMusic(Music music)  {
//        new Thread(() -> {
//            String audioUrl;
//            try {
//                audioUrl = ConvertService.convertLinkToMusic(music.getYtVideoID());
//            } catch (IOException | InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            if (audioUrl != null) {
//                Platform.runLater(() -> {
//                    MusicPlayer player = new MusicPlayer();
//                    player.play(audioUrl);
//                });
//            }
//        }).start();
//    }


}
