package org.example.linktomusicbeta;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.linktomusicbeta.controller.ChartController;
import org.example.linktomusicbeta.controller.ConvertController;
import org.example.linktomusicbeta.controller.MyMusicController;
import org.example.linktomusicbeta.controller.SearchController;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage)  {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/example/linktomusicbeta/fxml/serviceView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 420);

//            ConvertController controller = fxmlLoader.getController();
            MyMusicController controller = fxmlLoader.getController();
//            ChartController controller = fxmlLoader.getController();
//            SearchController controller = fxmlLoader.getController();
            controller.setPrimaryStage(stage);
//

            stage.setTitle("LinkToMusic");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
