package com.library.View.Login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;

public class LoginPanel extends BorderPane {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public Hyperlink registerLink, forgotPassword;

    public LoginPanel() {
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

        forgotPassword = new Hyperlink("Forgot Password");
        forgotPassword.setStyle("-fx-text-fill: #750205; -fx-font-weight: bold;");

        forgotPassword.setOnAction(e -> showForgotPasswordModal());

        loginForm.getChildren().addAll(loginTitle, infoText, usernameLabel, usernameField, passwordLabel, passwordField, forgotPassword, loginButton,registerRow);

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

    // Helper methods (you'll need to implement these according to your database)
            private boolean verifyStudent(String studentId, String email) {
        // Implement your verification logic here
        // Return true if student exists with this email
        return false;
    }

    private boolean updatePassword(String studentId, String newPassword) {
        // Implement password update logic here
        // Return true if successful
        return false;
    }

/*
    // Helper methods (you'll need to implement these according to your database)
    private boolean verifyStudent(String studentId, String email) {
        // Implement your verification logic here
        // Return true if student exists with this email
        return false;
    }

    private boolean updatePassword(String studentId, String newPassword) {
        // Implement password update logic here
        // Return true if successful
        return false;
    }
    */

    // Add this method to your LoginPanel class
    private void showForgotPasswordModal() {
        // Create the main dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Password Recovery");
        dialog.setHeaderText("Enter your student information");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().setStyle("-fx-background-color: white;");

        // Create form content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));

        // Student ID Field
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Enter your Student ID");
        studentIdField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Email Field
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your registered email");
        emailField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(studentIdField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType verifyButtonType = new ButtonType("Verify", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(verifyButtonType, ButtonType.CANCEL);

        // Style buttons
        Button verifyButton = (Button) dialog.getDialogPane().lookupButton(verifyButtonType);
        verifyButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold;");

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");

        // Handle verification
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == verifyButtonType) {
                if (studentIdField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                    showAlert("Error", "Please fill in all fields!", Alert.AlertType.ERROR);
                    return null;
                }

                // Here you would typically verify with your database
                boolean verified = verifyStudent(studentIdField.getText().trim(), emailField.getText().trim());

                if (verified) {
                    showChangePasswordDialog(studentIdField.getText().trim());
                } else {
                    showAlert("Error", "Student ID and email don't match our records", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Method to show change password dialog
    private void showChangePasswordDialog(String studentId) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter your new password");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().setStyle("-fx-background-color: white;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New password");
        newPasswordField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm new password");
        confirmPasswordField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        grid.add(new Label("New Password:"), 0, 0);
        grid.add(newPasswordField, 1, 0);
        grid.add(new Label("Confirm Password:"), 0, 1);
        grid.add(confirmPasswordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType changeButtonType = new ButtonType("Change Password", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(changeButtonType, ButtonType.CANCEL);

        // Style buttons
        Button changeButton = (Button) dialog.getDialogPane().lookupButton(changeButtonType);
        changeButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold;");

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == changeButtonType) {
                if (newPasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
                    showAlert("Error", "Please fill in both password fields!", Alert.AlertType.ERROR);
                    return null;
                }

                if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
                    showAlert("Error", "Passwords don't match!", Alert.AlertType.ERROR);
                    return null;
                }

                // Here you would typically update the password in your database
                boolean success = updatePassword(studentId, newPasswordField.getText());

                if (success) {
                    showAlert("Success", "Password changed successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to change password", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Alert helper method
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert
        alert.getDialogPane().setStyle("-fx-background-color: white;");

        // Style the button
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        if (type == Alert.AlertType.INFORMATION) {
            okButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        } else if (type == Alert.AlertType.ERROR) {
            okButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        }

        alert.showAndWait();
    }
}

