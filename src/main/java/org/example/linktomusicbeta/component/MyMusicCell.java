package org.example.linktomusicbeta.component;

import ch.qos.logback.classic.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.linktomusicbeta.model.Music;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyMusicCell extends ListCell<Music> {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(MyMusicCell.class);
    private static final Map<String, Image> imageCache = new HashMap<>();

    @FXML private HBox trackContainer;
    @FXML private ImageView image;
    @FXML private VBox labels;
    @FXML private Label title;
    @FXML private Label artist;
    @FXML private Button optionBtn;
    private FXMLLoader loader;

    public MyMusicCell() {
        try {
            loader = new FXMLLoader(getClass().getResource("/org/example/linktomusicbeta/fxml/musicCell.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            logger.error("❌ FXML 로드 실패: " + e.getMessage());
        }
    }

   @Override
    protected void updateItem(Music music, boolean isEmpty) {
        super.updateItem(music, isEmpty);

        if(isEmpty || music == null) {
            setGraphic(null);
            return;
        }
       title.setText(music.getTitle());
       artist.setText(music.getArtist());
       image.setImage(music.getImagePath());
       setGraphic(trackContainer);
//        String imagePath =  music.getImagePath();
//        Image image = imageCache.get(imagePath);
//
//        if (image == null) {
//            Image newImage = new Image(imagePath);
//            imageCache.put(imagePath, newImage);
//            albumImage.setImage(newImage);
//
//            if (!albumImage.isCache()) {
//                albumImage.setCache(true); // 이미 로드된 이미지에 대해서만 캐시 활성화
//            }
//        }
//
//        if (image != null) {
//            albumImage.setImage(image);  // 캐시된 이미지 설정
//        }

//        setGraphic(hbox); // 셀 UI 적용

   }
}
