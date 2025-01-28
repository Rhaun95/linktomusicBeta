package org.example.linktomusicbeta;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage)  {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/example/linktomusicbeta/fxml/home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 540);

            stage.setTitle("LinkToMusic");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
