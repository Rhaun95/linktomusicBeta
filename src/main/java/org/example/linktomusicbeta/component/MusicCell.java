package org.example.linktomusicbeta.component;

import ch.qos.logback.classic.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import org.example.linktomusicbeta.controller.MusicPlayerController;
import org.example.linktomusicbeta.controller.MusicPlayerController;
import org.example.linktomusicbeta.model.Music;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MusicCell extends ListCell<Music> {

    private static final ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(MusicCell.class);
    private static final Map<String, Image> imageCache = new HashMap<>();
//    private final SimpleObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    private final HBox hbox = new HBox(10);
    private final ImageView albumImage = new ImageView();
    private final Label titleLabel = new Label();
    private final Label artistLabel = new Label();
    VBox infoVBox = new VBox(10);
//    private Music currentMusic;

    public MusicCell() {
        albumImage.setFitWidth(50);
        albumImage.setFitHeight(50);
        // 세로로 5px 간격
        infoVBox.getChildren().addAll(titleLabel, artistLabel);

        artistLabel.setStyle("-fx-font-size: 10px;");
        infoVBox.setAlignment(Pos.CENTER_LEFT);

        Button optionButton = new Button("@");
        HBox optionHbox = new HBox(10);

        optionHbox.getChildren().add(optionButton);
        optionHbox.setAlignment(Pos.CENTER);
        optionButton.setFocusTraversable(false);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hbox.getChildren().addAll(albumImage, infoVBox ,spacer , optionHbox);
        hbox.setSpacing(10);
        hbox.setPadding(new javafx.geometry.Insets(5, 8, 5, 8));

        hbox.setPickOnBounds(false);

    }

    @Override
    protected void updateItem(Music music, boolean isEmpty) {
        super.updateItem(music, isEmpty);
    logger.info("updateItem");
        if (isEmpty || music == null) {
            setGraphic(null);
//            currentMusic = null;
            return;
        }
//        currentMusic = music;
        titleLabel.setText(music.getTitle());
        artistLabel.setText(music.getArtist());

        String imagePath = "/org/example/linktomusicbeta/images/" + music.getImagePath();
        Image image = imageCache.get(imagePath);

        if (image == null) {
            InputStream imageStream = getClass().getResourceAsStream(imagePath);

            if (imageStream != null) {
                Image newImage = new Image(imageStream);
                imageCache.put(imagePath, newImage);
                albumImage.setImage(newImage);
                albumImage.setCache(true); // 이미 로드된 이미지에 대해서만 캐시 활성화
                logger.info("new Music is cached");
            }
        }

        albumImage.setImage(image);  // 캐시된 이미지 설정
        setGraphic(hbox); // 셀 UI 적용

//        this.setOnMouseClicked(null);
//        this.setOnMouseClicked(event -> {
//            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
//                logger.info("music clicked!");
//                if (currentMusic != null) {
//                    openMusicPlayer(currentMusic);
//                }
//            }
//        });
    }


}



