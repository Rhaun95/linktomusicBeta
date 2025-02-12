package org.example.linktomusicbeta.service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.linktomusicbeta.controller.MusicPlayerController;

import java.io.File;

//SingleTon
public class MediaPlayerService {
    private static MediaPlayerService instance;
    private MediaPlayer mediaPlayer;
    private String currentFilePath;

    private MediaPlayerService() {
        // private 생성자 → 외부에서 직접 인스턴스 생성 불가
    }

    public static MediaPlayerService getInstance() {
        if (instance == null) {
            instance = new MediaPlayerService();
        }
        return instance;
    }

    public MediaPlayer getMediaPlayer(String filePath) {
        if (!filePath.equals(currentFilePath)) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();  // 기존 mediaPlayer 중지
                mediaPlayer.dispose();  // 기존 mediaPlayer 자원 해제
            }
            Media media = new Media(filePath);  // 새 Media 생성
            mediaPlayer = new MediaPlayer(media);  // 새 MediaPlayer 생성
            currentFilePath = filePath;  // 현재 경로 기록
        }

        return mediaPlayer;
    }

    public void play(String filePath) {
        mediaPlayer = getMediaPlayer(filePath);
        mediaPlayer.play();
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
    public void changeMusic(String filePath) {
       stop();
       mediaPlayer = new MediaPlayer(new Media(new File(filePath).toURI().toString()));
       mediaPlayer.play();
    }
}

