package com.library.View;

import com.library.View.Login.LoginPanel;
import com.library.View.Login.RegisterPanel;
import com.library.View.Student.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        HomeDashboard homeDashboard = new HomeDashboard("farrel");
        LoginPanel loginPanel = new LoginPanel();
        RegisterPanel registerPanel = new RegisterPanel();
        SearchBookCatalog searchBookCatalog = new SearchBookCatalog();
        BorrowingHistory borrowingHistory = new BorrowingHistory();
        Notifications notifications = new Notifications();
        Profile profile = new Profile();

        Scene scene = new Scene(profile,800,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Dashboard");
        primaryStage.setMaximized(true);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
