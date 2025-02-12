package org.example.linktomusicbeta.controller;

import ch.qos.logback.classic.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.linktomusicbeta.component.ChartMusicCell;
import org.example.linktomusicbeta.component.MusicCell;
import org.example.linktomusicbeta.model.Music;
import org.example.linktomusicbeta.service.MusicAPI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void initialize() throws IOException {

        fetchLatestMusic();
        musicListView.setCellFactory(param -> new MusicCell());

        musicListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // 더블 클릭 감지
                Music selectedMusic = musicListView.getSelectionModel().getSelectedItem();
                if (selectedMusic != null) {
//                    openMusicPlayer(selectedMusic);
                }
            }
        });
    }
    private static final String API_URL = "https://api.deezer.com/chart?limit=30";

    public void fetchLatestMusic() throws IOException {
        new Thread(() -> {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                ObservableList<Music> musicList = getMusics(new JSONObject(response.toString()));

                // UI 스레드에서 ListView 업데이트
                Platform.runLater(() -> {
                    musicListView.setItems(musicList);
                });

            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }).start();
    }


    public static ObservableList<Music> getMusics(JSONObject jsonObject) {
        ObservableList<Music> musicList = FXCollections.observableArrayList();
            JSONArray tracks = jsonObject.getJSONObject("tracks").getJSONArray("data");

            for (int i = 0; i < tracks.length(); i++) {
                JSONObject track = tracks.getJSONObject(i);
                String title = track.getString("title");
                String artist = track.getJSONObject("artist").getString("name");
                String imageUrl = track.getJSONObject("album").getString("cover_medium");
                String link = track.getString("link");

                Music music = new Music(new Image(imageUrl), title, artist);
                music.setFilePath(link);
                Platform.runLater(()-> musicList.add(music));
            }
        return musicList;
    }

//    private static JSONObject getJsonObject() throws IOException {
//        URL url = new URL(API_URL);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        StringBuilder response = new StringBuilder();
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//            response.append(line);
//        }
//        reader.close();
//
//        return new JSONObject(response.toString());
//    }


//    private void openMusicPlayer(Music music) {
//        try {
//            logger.info("selected Music: {} - {}", music.getTitle(), music.getArtist());
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/linktomusicbeta/fxml/musicPlayer.fxml"));
//            Parent root = loader.load();
//            logger.info("New Tab opened");
//
//            MusicPlayerController controller = loader.getController();
//            controller.setMusic(music);
//
//
//            Stage stage = new Stage() ;
//            stage.setScene(new Scene(root));
//            stage.setTitle("Music Player");
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}