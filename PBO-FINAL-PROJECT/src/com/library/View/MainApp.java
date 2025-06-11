package com.library.View;

import com.library.View.Login.LoginPane;
import com.library.View.Login.Register;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainApp extends Application{


    @Override
    public void start(Stage stage){
        LoginPane loginPane = new LoginPane();
        Register registerPane = new Register();

        Scene scene = new Scene(loginPane,600,450);
        stage.setScene(scene);
        stage.setTitle("Loginpane");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
