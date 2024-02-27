package com.sunrisehms;

import com.sunrisehms.util.SessionFactoryConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
//        SessionFactoryConfiguration.getInstance().getSession();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/sunrisehms/view/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Sunrise Hotel:HMS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
