package com.library.View;

import com.library.Controller.AuthController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialize the authentication controller with the primary stage
        new AuthController(primaryStage);

        // Set application window properties
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("UMM Library Management System");

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}