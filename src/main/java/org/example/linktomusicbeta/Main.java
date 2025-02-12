package org.example.linktomusicbeta;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.linktomusicbeta.controller.*;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/example/linktomusicbeta/fxml/main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 420);
//            MainController controller = fxmlLoader.getController();
//            ConvertController controller = fxmlLoader.getController();
//            MyMusicController controller = fxmlLoader.getController();
//            ChartController controller = fxmlLoader.getController();
//            SearchController controller = fxmlLoader.getController();
//            controller.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Link To Music Beta");
            primaryStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
