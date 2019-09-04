package c482;

import c482.View.AddPartController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/MainApp.fxml"));
        Scene mainScene = new Scene(root);
        
        mainStage.setScene(mainScene);
        mainStage.setTitle("Inventory Management System");
        mainStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
