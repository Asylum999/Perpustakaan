package com.library.Controller;

import com.library.View.Login.LoginPanel;
import com.library.View.Login.RegisterPanel;
import com.library.View.Student.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator {
    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void showLogin() {
        LoginPanel loginPanel = new LoginPanel();
        Scene scene = new Scene(loginPanel, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
        new com.library.Controller.LoginController(loginPanel);
    }

    public static void showRegister() {
        RegisterPanel registerPanel = new RegisterPanel();
        Scene scene = new Scene(registerPanel, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
        new com.library.Controller.RegisterController(registerPanel);
    }
    public static void showStudentDashboard(String nama) {
         HomeDashboard dashboard = new HomeDashboard(nama);
        Scene scene = new Scene(dashboard, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showBorrowingHistory() {
        BorrowingHistory historyPage = new BorrowingHistory();
        Scene scene = new Scene(historyPage, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showSearchBook() {
        SearchBookCatalog searchBookCatalog = new SearchBookCatalog();
        Scene scene = new Scene(searchBookCatalog, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showNotifications() {
        Notifications notifications = new Notifications();
        Scene scene = new Scene(notifications, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showProfile() {
        Profile profile = new Profile();
        Scene scene = new Scene(profile, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

}

