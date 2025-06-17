package com.library.Controller;

import com.library.View.Login.LoginPanel;
import com.library.View.Login.RegisterPanel;
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

}

