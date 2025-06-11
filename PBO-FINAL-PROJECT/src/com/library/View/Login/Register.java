package com.library.View.Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Register extends BorderPane {
private Label title;
private TextField usernameField;
private TextField idField;
private TextField majorField;
private TextField emailField;
private Button registerButton;
private Label notificationLabel;

    public Register(){
        // KIRI (Logo dan teks)
        VBox leftPane = new VBox(10);
        leftPane.setStyle("-fx-background-color: #800000;"); // warna maroon
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(20));

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/umm-logo.png").toExternalForm()));
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);

        Label ummLabel = new Label("UMM");
        ummLabel.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold");

        Label libraryLabel = new Label("LIBRARY");
        libraryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold");

        leftPane.getChildren().addAll(logo, ummLabel, libraryLabel);

        // KANAN (Form Login)
        VBox formPane = new VBox(30);
        formPane.setAlignment(Pos.CENTER);
        formPane.setPadding(new Insets(40));

        title = new Label("Register");
        title.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: rgb(77, 75, 75)");

        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setStyle("-fx-background-color: transparent; -fx-border-width: 0 0 2 0; -fx-border-color: black; -fx-prompt-text-fill: #212020;");

        idField = new PasswordField();
        idField.setPromptText("Enter id");
        idField.setStyle("-fx-background-color: transparent; -fx-border-width: 0 0 2 0; -fx-border-color: black; -fx-prompt-text-fill: #212020;");

        majorField = new PasswordField();
        majorField.setPromptText("Enter major");
        majorField.setStyle("-fx-background-color: transparent; -fx-border-width: 0 0 2 0; -fx-border-color: black; -fx-prompt-text-fill: #212020;");

        emailField = new PasswordField();
        emailField.setPromptText("Enter email");
        emailField.setStyle("-fx-background-color: transparent; -fx-border-width: 0 0 2 0; -fx-border-color: black; -fx-prompt-text-fill: #212020;");

        registerButton = new Button("Register");

        // Styling tombol merah
        String buttonStyle = "-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 20px; -fx-padding: 8 20; -fx-min-width: 150px;";
        registerButton.setStyle(buttonStyle);

        VBox buttonBox = new VBox(10,registerButton);
        buttonBox.setAlignment(Pos.CENTER);

        notificationLabel = new Label();
        notificationLabel.setTextFill(Color.RED);

        formPane.getChildren().addAll(title, usernameField, idField,majorField,emailField, buttonBox, notificationLabel);

        this.setLeft(leftPane);
        this.setCenter(formPane);

    }

    public Label getTitle() {
        return title;
    }

    public Label getNotificationLabel() {
        return notificationLabel;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getMajorField() {
        return majorField;
    }

    public TextField getIdField() {
        return idField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }
}
