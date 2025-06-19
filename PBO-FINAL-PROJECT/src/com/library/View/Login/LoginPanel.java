package com.library.View.Login;

import com.library.Controller.ForgotPasswordController;
import com.library.Controller.LoginController;
import com.library.Controller.Navigator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


public class LoginPanel extends BorderPane {
    private TextField usernameField;
    private PasswordField idField;
    private Button loginButton;
    private Hyperlink registerLink, forgotPassword;
    private LoginController controller;

    public LoginPanel() {
        initializeUI();
        this.controller = new LoginController(this);
    }

    private void initializeUI() {
        // Left side - Logo and Library text
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

        // Username field
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 25px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        usernameField = new TextField();
        usernameField.setPrefWidth(400);
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #fa1e1e; -fx-background-radius: 10; -fx-border-radius: 10;");

        // Password field
        // ID field (previously password field)
        Label idLabel = new Label("ID:");
        idLabel.setStyle("-fx-text-fill: #0a0a0a; -fx-font-size: 25px; -fx-font-family: 'Georgia'; -fx-font-weight: bold;");
        idField = new PasswordField();
        idField.setPrefWidth(400);
        idField.setPrefHeight(40);
        idField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #fa1e1e; -fx-background-radius: 10; -fx-border-radius: 10;");


        // Forgot Password link
        forgotPassword = new Hyperlink("Forgot Password");
        forgotPassword.setStyle("-fx-text-fill: #750205; -fx-font-weight: bold;");
        forgotPassword.setOnAction(e -> showForgotPasswordModal());

        // Login button
        loginButton = new Button("Login");
        loginButton.setPrefWidth(150);
        loginButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        // Register link
        registerLink = new Hyperlink("Register Here");
        registerLink.setStyle("-fx-text-fill: #750205; -fx-font-weight: bold;");
        Label newUserLabel = new Label("Are you new? ");
        newUserLabel.setStyle("-fx-text-fill: #575454; -fx-font-size: 15px;");
        HBox registerRow = new HBox(newUserLabel, registerLink);

        loginForm.getChildren().addAll(
                loginTitle,
                infoText,
                usernameLabel,
                usernameField,
                idLabel,
                idField,
                forgotPassword,
                loginButton,
                registerRow

        );

        this.setLeft(leftPane);
        this.setCenter(loginForm);
    }

    private void showForgotPasswordModal() {
        ForgotPasswordController controller = new ForgotPasswordController();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Password Recovery");
        dialog.setHeaderText("Enter your email address");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your registered email");
        emailField.setPrefWidth(300);

        Label emailErrorLabel = new Label();
        emailErrorLabel.setStyle("-fx-text-fill: red;");
        emailErrorLabel.setVisible(false);

        grid.add(new Label("Email:"), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(emailErrorLabel, 1, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType verifyButtonType = new ButtonType("Verify", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(verifyButtonType, ButtonType.CANCEL);

        Node verifyButton = dialog.getDialogPane().lookupButton(verifyButtonType);
        verifyButton.setDisable(true);

        // Style the dialog buttons
        dialog.getDialogPane().lookupButton(verifyButtonType)
                .setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL)
                .setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");

        // Email validation
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            String errorMessage = controller.getErrorMessage(newValue, null);
            if (errorMessage != null) {
                emailErrorLabel.setText(errorMessage);
                emailErrorLabel.setVisible(true);
                verifyButton.setDisable(true);
            } else {
                emailErrorLabel.setVisible(false);
                verifyButton.setDisable(false);
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == verifyButtonType) {
                String email = emailField.getText().trim();
                if (controller.verifyEmail(email)) {
                    showResetPasswordDialog(email, controller);
                } else {
                    controller.showAlert("Error",
                            "No account found with this email address.",
                            Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showResetPasswordDialog(String email, ForgotPasswordController controller) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Reset Password");
        dialog.setHeaderText("Enter your new password");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        PasswordField newPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);

        newPasswordField.setPromptText("New password (min 6 characters)");
        confirmPasswordField.setPromptText("Confirm password");

        grid.add(new Label("New Password:"), 0, 0);
        grid.add(newPasswordField, 1, 0);
        grid.add(new Label("Confirm Password:"), 0, 1);
        grid.add(confirmPasswordField, 1, 1);
        grid.add(errorLabel, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType resetButtonType = new ButtonType("Reset Password", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(resetButtonType, ButtonType.CANCEL);

        // Style the dialog buttons
        dialog.getDialogPane().lookupButton(resetButtonType)
                .setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL)
                .setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");

        Node resetButton = dialog.getDialogPane().lookupButton(resetButtonType);
        resetButton.setDisable(true);

        // ID validation
        ChangeListener<String> idListener = (observable, oldValue, newValue) -> {
            String id1 = newPasswordField.getText();
            String id2 = confirmPasswordField.getText();

            if (id1.length() < 6) {
                errorLabel.setText("ID must be at least 6 characters");
                errorLabel.setVisible(true);
                resetButton.setDisable(true);
            } else if (!id1.equals(id2)) {
                errorLabel.setText("IDs don't match");
                errorLabel.setVisible(true);
                resetButton.setDisable(true);
            } else {
                errorLabel.setVisible(false);
                resetButton.setDisable(false);
            }
        };


        newPasswordField.textProperty().addListener(idListener);
        confirmPasswordField.textProperty().addListener(idListener);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == resetButtonType) {
                String newPassword = newPasswordField.getText();

                if (controller.updatePassword(email, newPassword)) {
                    controller.showAlert("Success",
                            "Password has been reset successfully. Please login with your new password.",
                            Alert.AlertType.INFORMATION);
                } else {
                    controller.showAlert("Error",
                            "Failed to reset password. Please try again.",
                            Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Getters
    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return idField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Hyperlink getRegisterLink() {
        return registerLink;
    }
}