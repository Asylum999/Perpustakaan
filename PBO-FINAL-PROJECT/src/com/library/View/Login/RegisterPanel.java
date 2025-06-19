package com.library.View.Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class RegisterPanel extends BorderPane {
    private TextField nameField, studentIdField, facultyField, majorField, emailField;
    private Button registerButton;
    public Hyperlink backtoLogin;



    public RegisterPanel() {
        //left side
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
        // Right Side - Register Form
        VBox registerForm = new VBox(10);
        registerForm.setPadding(new Insets(40));
        registerForm.setAlignment(Pos.CENTER_LEFT);
        registerForm.setFillWidth(false);
        registerForm.setStyle("-fx-background-color: #FFFFFF;");

        Text registerTitle = new Text("REGISTER");
        registerTitle.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 60px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");

        Label nameLabel = new Label("Name :");
        nameLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 15px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        nameField = new TextField();
        nameField.setPrefWidth(400);
        nameField.setPrefHeight(25);
        nameField.setStyle("-fx-background-color: #ffffff  ; " + "-fx-border-color: #fa1e1e;" + "-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        Label studentIdLabel = new Label("Student ID :");
        studentIdLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 15px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        studentIdField = new TextField();
        studentIdField.setPrefWidth(400);
        studentIdField.setPrefHeight(25);
        studentIdField.setStyle("-fx-background-color: #ffffff  ; " + "-fx-border-color: #fa1e1e;" + "-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        HBox facultyMajorBox = new HBox(10);
        VBox facultyBox = new VBox(5);
        VBox majorBox = new VBox(5);

        Label facultyLabel = new Label("Faculty :");
        facultyLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 15px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        facultyField = new TextField();
        facultyField.setPrefWidth(195);
        facultyField.setPrefHeight(25);
        facultyField.setStyle("-fx-background-color: #ffffff  ; " + "-fx-border-color: #fa1e1e;" + "-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        Label majorLabel = new Label("Major :");
        majorLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 15px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        majorField = new TextField();
        majorField.setPrefWidth(195);
        majorField.setPrefHeight(25);
        majorField.setStyle("-fx-background-color: #ffffff  ; " + "-fx-border-color: #fa1e1e;" + "-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        facultyBox.getChildren().addAll(facultyLabel, facultyField);
        majorBox.getChildren().addAll(majorLabel, majorField);
        facultyMajorBox.getChildren().addAll(facultyBox, majorBox);

        Label emailLabel = new Label("Email :");
        emailLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 15px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        emailField = new TextField();
        emailField.setPrefWidth(400);
        emailField.setPrefHeight(25);
        emailField.setStyle("-fx-background-color: #ffffff  ; " + "-fx-border-color: #fa1e1e;" + "-fx-background-radius: 10;" + "-fx-border-radius: 10;");

        registerButton = new Button("Register");
        registerButton.setPrefWidth(150);
        registerButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        backtoLogin = new Hyperlink("Back to Login");
        backtoLogin.setStyle("-fx-text-fill: #750205; -fx-font-weight: bold;");
        Label newUserLabel = new Label("Already Have an Account? ");
        newUserLabel.setStyle("-fx-text-fill: #575454; -fx-font-size: 15px;");
        HBox registerRow = new HBox(newUserLabel, backtoLogin);

        registerForm.getChildren().addAll(registerTitle, nameLabel, nameField,
                studentIdLabel, studentIdField,
                facultyMajorBox, emailLabel, emailField,
                registerButton,registerRow);

        this.setLeft(leftPane);
        this.setCenter(registerForm);
    }

    private void addInputValidation() {
        studentIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                studentIdField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                emailField.setText(oldValue);
            }
        });
    }


    public void cleanup() {
        // Remove existing event handlers
        registerButton.setOnAction(null);
        backtoLogin.setOnAction(null);
        nameField.textProperty().unbind();
        studentIdField.textProperty().unbind();
        facultyField.textProperty().unbind();
        majorField.textProperty().unbind();
        emailField.textProperty().unbind();

    }


    public TextField getNameField() {
        return nameField;
    }

    public TextField getStudentIdField() {
        return studentIdField;
    }

    public TextField getFacultyField() {
        return facultyField;
    }

    public TextField getMajorField() {
        return majorField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }
}

