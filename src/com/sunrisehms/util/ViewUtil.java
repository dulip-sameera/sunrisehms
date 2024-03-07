package com.sunrisehms.util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewUtil {

    public static void loadView(Class cls, String view, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(cls.getResource("/com/sunrisehms/view/" + view));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
