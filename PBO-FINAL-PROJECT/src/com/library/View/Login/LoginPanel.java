package com.library.View.Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class LoginPanel extends BorderPane {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Hyperlink registerLink, forgotPassword;

    public LoginPanel() {
        // Left Side
        VBox leftPane = new VBox(-30);
        leftPane.setStyle("-fx-background-color: #800000;");
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(20));
        leftPane.setPrefWidth(500);

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/LogoUmm.png").toExternalForm()));
        logo.setFitWidth(250);
        logo.setFitHeight(250);
        logo.setPreserveRatio(true);

        Label libraryLabel = new Label("LIBRARY");
        libraryLabel.setStyle("-fx-text-fill: #ebeae8; -fx-font-size: 50px; -fx-font-weight: bold");

        leftPane.getChildren().addAll(logo, libraryLabel);

        // Right Side - Login Form
        VBox loginForm = new VBox(10);
        loginForm.setPadding(new Insets(40));
        loginForm.setAlignment(Pos.CENTER_LEFT);
        loginForm.setFillWidth(false);
        loginForm.setStyle("-fx-background-color: #FFFFFF;");

        Text loginTitle = new Text("Login");
        loginTitle.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 60px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");

        Label infoText = new Label("Login with your username and ID");
        infoText.setStyle("-fx-text-fill: #575454; -fx-font-size: 15px;");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 25px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        usernameField = new TextField();
        usernameField.setPrefWidth(400);
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #fa1e1e; -fx-background-radius: 10; -fx-border-radius: 10;");

        Label passwordLabel = new Label("ID:");
        passwordLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 25px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        passwordField = new PasswordField();
        passwordField.setPrefWidth(400);
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #fa1e1e; -fx-background-radius: 10; -fx-border-radius: 10;");

        // Register link
        registerLink = new Hyperlink("Register Here");
        registerLink.setStyle("-fx-text-fill: #750205; -fx-font-weight: bold;");
        Label newUserLabel = new Label("Are you new? ");
        newUserLabel.setStyle("-fx-text-fill: #575454; -fx-font-size: 15px;");
        HBox registerRow = new HBox(newUserLabel, registerLink);
        registerRow.setAlignment(Pos.CENTER_LEFT);

        // Login button
        loginButton = new Button("Login");
        loginButton.setPrefWidth(150);
        loginButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        // Forgot password (disabled for ID-as-password)
        forgotPassword = new Hyperlink("Forgot Password");
        forgotPassword.setVisible(false);
        forgotPassword.setDisable(true);

        loginForm.getChildren().addAll(loginTitle, infoText, usernameLabel, usernameField,
                passwordLabel, passwordField, forgotPassword, loginButton, registerRow);

        this.setLeft(leftPane);
        this.setCenter(loginForm);
    }

    // Getter methods
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

    // Optional method to show error message
    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-background-color: white;");

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        if (type == Alert.AlertType.ERROR) {
            okButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        } else {
            okButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        }

        alert.showAndWait();
    }

    // If needed by controller to turn off forgot password
    public void disableForgotPassword() {
        forgotPassword.setDisable(true);
        forgotPassword.setVisible(false);
    }
}
