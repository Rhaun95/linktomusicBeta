package org.example.linktomusicbeta.model;

public class Music {

    private String title;
    private String artist;
    private String imagePath;

    public Music(String imagePath, String title, String artist) {
        this.imagePath = imagePath;
        this.title = title;
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
