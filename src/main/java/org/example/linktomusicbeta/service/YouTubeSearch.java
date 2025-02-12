package org.example.linktomusicbeta.service;

import ch.qos.logback.classic.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YouTubeSearch {

    public static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(YouTubeSearch.class);


    private static final String API_KEY = EnvLoader.get("YOUTUBE_API_KEY"); // 여기에 YouTube API 키 입력

    private static final String SEARCH_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&maxResults=10&q=";

    public static JSONArray searchYouTube(String query) throws Exception {
            // 검색어를 URL에 맞게 변환
            String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
            URL url = new URL(SEARCH_URL + encodedQuery + "&key=" + API_KEY);

            // HTTP 요청 보내기
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 응답 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // JSON 파싱
            JSONObject jsonResponse = new JSONObject(response.toString());

            return jsonResponse.getJSONArray("items");
    }

}

