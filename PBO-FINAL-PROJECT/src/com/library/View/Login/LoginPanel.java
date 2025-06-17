package com.library.View.Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class LoginPanel extends BorderPane {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public Hyperlink registerLink;

    public LoginPanel() {

        VBox leftPane = new VBox(10);
        leftPane.setStyle("-fx-background-color: #800000;");
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(20));
        leftPane.setPrefWidth(500);

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/umm-logo.png").toExternalForm()));
        logo.setFitWidth(300);
        logo.setFitHeight(500);
        logo.setPreserveRatio(true);

        Label ummLabel = new Label("UMM");
        ummLabel.setStyle("-fx-text-fill: white; -fx-font-size: 40px; -fx-font-weight: bold");

        Label libraryLabel = new Label("LIBRARY");
        libraryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 40px; -fx-font-weight: bold");

        leftPane.getChildren().addAll(logo, ummLabel, libraryLabel);

        // Right Side - Login Form
        VBox loginForm = new VBox(10);
        loginForm.setPadding(new Insets(40));
        loginForm.setAlignment(Pos.CENTER_LEFT);
        loginForm.setFillWidth(false);
        loginForm.setStyle("-fx-background-color: #FFFFFF;");

        Text loginTitle = new Text("Login");
        loginTitle.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 60px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");

        Label infoText = new Label("Login with your data that you entered during your registration");
        infoText.setStyle("-fx-text-fill: #575454; -fx-font-size: 15px;");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 25px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        usernameField = new TextField();
        usernameField.setPrefWidth(400);
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-background-color: #ffffff  ; " + "-fx-border-color: #fa1e1e;" + "-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 25px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        passwordField = new PasswordField();
        passwordField.setPrefWidth(400);
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-background-color: #ffffff; " + "-fx-border-color: #fa1e1e;" + "-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        registerLink = new Hyperlink("Register Here");
        registerLink.setStyle("-fx-text-fill: #750205; -fx-font-weight: bold;");
        Label newUserLabel = new Label("Are you new? ");
        newUserLabel.setStyle("-fx-text-fill: #575454; -fx-font-size: 15px;");
        HBox registerRow = new HBox(newUserLabel, registerLink);

        loginButton = new Button("Login");
        loginButton.setPrefWidth(150);
        loginButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        loginForm.getChildren().addAll(loginTitle, infoText, usernameLabel, usernameField, passwordLabel, passwordField, registerRow, loginButton);

        this.setLeft(leftPane);
        this.setCenter(loginForm);
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Hyperlink getRegisterLink() {
        return registerLink;
    }
}

