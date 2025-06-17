package com.library.View;

import com.library.Controller.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Navigator.setStage(primaryStage);
        Navigator.showLogin();

        primaryStage.setTitle("Library System");
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
