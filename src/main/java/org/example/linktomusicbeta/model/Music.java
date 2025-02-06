package org.example.linktomusicbeta.model;

import javafx.scene.image.Image;

public class Music {

    private String title;
    private String artist;
    private Image image;
    private String url;

    public Music(Image image, String title, String artist, String url) {
        this.image = image;
        this.title = title;
        this.artist = artist;
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
