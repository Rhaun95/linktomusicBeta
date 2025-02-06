package org.example.linktomusicbeta.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.linktomusicbeta.model.Music;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MusicAPI {

    private static final String API_URL = "https://api.deezer.com/chart?limit=30";

    public static ObservableList<Music> fetchLatestMusic() {
        ObservableList<Music> musicList = FXCollections.observableArrayList();

        new Thread(() -> {
            try {
                JSONObject jsonResponse = getJsonObject();
                JSONArray tracks = jsonResponse.getJSONObject("tracks").getJSONArray("data");

                for (int i = 0; i < tracks.length(); i++) {
                    JSONObject track = tracks.getJSONObject(i);
                    String title = track.getString("title");
                    String artist = track.getJSONObject("artist").getString("name");
                    String imageUrl = track.getJSONObject("album").getString("cover_medium");
                    String link = track.getString("link");

//                    Music music = new Music(imageUrl, title, artist, link);
//                    Platform.runLater(()-> musicList.add(music));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return musicList;
    }

    private static JSONObject getJsonObject() throws IOException {
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

        return new JSONObject(response.toString());
    }
}