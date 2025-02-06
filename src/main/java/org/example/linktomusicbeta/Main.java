package org.example.linktomusicbeta;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.linktomusicbeta.controller.ConvertController;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage)  {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/example/linktomusicbeta/fxml/convert.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 420);

            ConvertController controller = fxmlLoader.getController();
            controller.setPrimaryStage(stage);

            stage.setTitle("LinkToMusic");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
