package org.example.linktomusicbeta.model;

import javafx.scene.image.Image;

import java.io.File;

public class Music {

    private String title;
    private String artist;
    private Image image;
    private String filePath;
    private String ytVideoID;

    public Music(Image image, String title, String artist) {
        this.image = image;
        this.title = title;
        this.artist = artist;
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Image getImagePath() {
        return image;
    }

    public void setImagePath(Image image) {
        this.image = image;
    }
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getYtVideoID() {
        return ytVideoID;
    }

    public void setYtVideoID(String ytVideoID) {
        this.ytVideoID = ytVideoID;
    }


}

