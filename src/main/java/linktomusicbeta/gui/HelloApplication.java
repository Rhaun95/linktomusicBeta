package linktomusicbeta.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage)  {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(
                    HelloApplication.class.getResource("/fxml/home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 640);

            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
                System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}