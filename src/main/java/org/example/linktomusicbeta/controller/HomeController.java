package org.example.linktomusicbeta.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.linktomusicbeta.model.Music;
import org.example.linktomusicbeta.component.MusicCell;
/*
* TODO: 
*  Music List -> click + -> open convert tab to add
*  Musicplayer?
*  save/track Music
* */
public class HomeController {

    @FXML
    private ListView<Music> musicListView;

    /*TODO:
       - initialize with already exists music data
     */
    public void initialize() {
        ObservableList<Music> musicList = FXCollections.observableArrayList(
                new Music("groovebox.png","제목1", "가수 1"),
                new Music("hot-air-balloon.png","제목2", "가수 2"),
                new Music("tool.jpg","제목3", "가수 3")
        );

        musicListView.setItems(musicList);
        musicListView.setCellFactory(param -> new MusicCell());

    }

}