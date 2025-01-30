package org.example.linktomusicbeta.controller;

import ch.qos.logback.classic.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.linktomusicbeta.service.LinkService;
import org.slf4j.LoggerFactory;

/*
* TODO: 
*  Music List -> click + -> open convert tab to add
*  Musicplayer?
*  save/track Music
* */
public class ConvertController {

    private final LinkService linkService = new LinkService();
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ConvertController.class);

    @FXML
    private TextField linkField;

    @FXML
    protected void handleLinkSubmit() {
        String link = linkField.getText();

        if(link ==null || link.isEmpty()){
            logger.warn("Please enter a valid link");
            return;
        }
        linkService.convertLinkToMusic(link);
    }

}