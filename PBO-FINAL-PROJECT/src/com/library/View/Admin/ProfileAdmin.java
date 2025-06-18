package com.library.View.Admin;

import com.library.Controller.Navigator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdmin extends BorderPane {

    public ProfileAdmin() {
        VBox sidebar = createSidebar();
        VBox mainContent = createMainContent();

        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #800000;");

        // Header section with logo and title
        VBox headerSection = new VBox(-20);
        headerSection.setPadding(new Insets(20));
        headerSection.setAlignment(Pos.CENTER);
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/LogoUmm.png").toExternalForm()));
        logo.setFitWidth(150);
        logo.setFitWidth(150);
        logo.setPreserveRatio(true);
        Label labelUMM = new Label("LIBRARY");
        labelUMM.setTextFill(Color.WHITE);
        labelUMM.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelUMM.setAlignment(Pos.CENTER);
        labelUMM.setWrapText(true);
        headerSection.getChildren().addAll(logo, labelUMM);
        
        String[] menuItems = {"Dashboard", "Book Management", "User Management", "Profile", "Logout"};
        String[] menuIcons = {"/images/Home.png", "/images/Search.png", "/images/userManagement.png", "/images/Profile.png", "/images/Logout.png"};

        List<Button> allMenuButtons = new ArrayList<>();

        VBox menuBox = new VBox();
        menuBox.setPadding(new Insets(10, 0, 0, 0));
        menuBox.setSpacing(5);
        sidebar.getChildren().addAll(headerSection, menuBox);

        for (int i = 0; i < menuItems.length; i++) {
            Button menuButton = new Button(menuItems[i]);
            menuButton.setGraphic(new ImageView(new Image(getClass().getResource(menuIcons[i]).toExternalForm())));
            menuButton.setContentDisplay(ContentDisplay.LEFT);
            menuButton.setAlignment(Pos.CENTER_LEFT);
            menuButton.setPadding(new Insets(10, 20, 10, 20));
            menuButton.setMaxWidth(Double.MAX_VALUE);
            menuButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");

            int index = i; // karena lambda butuh final atau effectively final
            menuButton.setOnAction(e -> {
                // Reset semua tombol ke style default
                for (Button btn : allMenuButtons) {
                    btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
                }
                // Highlight tombol yang diklik
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");

                // Aksi pindah halaman
                switch (menuItems[index]) {
                    case "Dashboard":
                        Navigator.showAdminHomeDashboard("Admin Name");
                        break;
                    case "Book Management":
                        Navigator.showAdminBookManagement();
                        break;
                    case "User Management":
                        Navigator.showAdminUserManagement();
                        break;
                    case "Profile":
                        Navigator.showAdminProfile();
                        break;
                    case "Logout":
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Logout Confirmation");
                        alert.setHeaderText("Do you really want to logout?");
                        alert.setContentText("Press OK to proceed or Cancel to stay.");

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                Navigator.showLogin(); // back to login screen
                            }
                        });
                    default:
                        System.out.println("Menu belum ditangani: " + menuItems[index]);
                }
            });

            allMenuButtons.add(menuButton);
            menuBox.getChildren().add(menuButton);

            // Highlight default misalnya "Home"
            if (menuItems[i].equals("Profile")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }
        return sidebar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // === Profile Header ===
        HBox profileHeader = new HBox(20);
        profileHeader.setPadding(new Insets(30));
        profileHeader.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        ImageView profileImage = new ImageView(new Image(getClass().getResource("/images/Profileimage.png").toExternalForm()));
        profileImage.setFitWidth(100);
        profileImage.setPreserveRatio(true);

        VBox profileDetails = new VBox(10);
        profileDetails.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label("Admin");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label idLabel = new Label("123");
        idLabel.setFont(Font.font("Arial", 14));

        Label roleLabel = new Label("Admin");
        roleLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 4 12; -fx-background-radius: 10;");
        roleLabel.setFont(Font.font("Arial", 13));

        profileDetails.getChildren().addAll(nameLabel, idLabel, roleLabel);
        profileHeader.getChildren().addAll(profileImage, profileDetails);

        // === General Information ===
        VBox generalBox = new VBox(15);
        generalBox.setPadding(new Insets(30));
        generalBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        Label generalTitle = new Label("General Information");
        generalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox infoList = new VBox(8);
        infoList.getChildren().addAll(
                createInfoRow("Name", "Admin"),
                createInfoRow("ID", "123")
        );

        Button changePasswordBtn = new Button("Change Password");
        changePasswordBtn.setStyle("-fx-background-color: transparent; -fx-border-color: #ff5e5e; -fx-text-fill: #ff5e5e; -fx-font-weight: bold;");
        changePasswordBtn.setPadding(new Insets(8, 16, 8, 16));
        changePasswordBtn.setOnAction(e -> showChangePasswordModal());

        generalBox.getChildren().addAll(generalTitle, infoList, changePasswordBtn);

        mainContent.getChildren().addAll(profileHeader, generalBox);
        return mainContent;
    }

    private HBox createInfoRow(String label, String value) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.BASELINE_LEFT);

        Label labelNode = new Label(label + " :");
        labelNode.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        labelNode.setPrefWidth(100);

        Label valueNode = new Label(value);
        valueNode.setFont(Font.font("Arial", 13));

        row.getChildren().addAll(labelNode, valueNode);
        return row;
    }

    // Add these methods to your ProfileAdmin class

    private void showChangePasswordModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Change Admin Password");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().setStyle("-fx-background-color: white;");

        // Create form fields
        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Create form layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));

        grid.add(new Label("Current Password:"), 0, 0);
        grid.add(currentPasswordField, 1, 0);
        grid.add(new Label("New Password:"), 0, 1);
        grid.add(newPasswordField, 1, 1);
        grid.add(new Label("Confirm Password:"), 0, 2);
        grid.add(confirmPasswordField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType changeButtonType = new ButtonType("Change Password", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(changeButtonType, ButtonType.CANCEL);

        // Style buttons
        Node changeButton = dialog.getDialogPane().lookupButton(changeButtonType);
        changeButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold;");

        Node cancelButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");

        // Process result
        dialog.setResultConverter(buttonType -> {
            if (buttonType == changeButtonType) {
                // Validate fields
                if (currentPasswordField.getText().isEmpty() ||
                        newPasswordField.getText().isEmpty() ||
                        confirmPasswordField.getText().isEmpty()) {

                    showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
                    return null;
                }

                if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
                    showAlert("Error", "New passwords don't match", Alert.AlertType.ERROR);
                    return null;
                }

                if (newPasswordField.getText().length() < 8) {
                    showAlert("Error", "Password must be at least 8 characters", Alert.AlertType.ERROR);
                    return null;
                }
                if (newPasswordField.getText().equals(currentPasswordField.getText())) {
                    showAlert("Error", "New password must be different from current password", Alert.AlertType.ERROR);
                    return null;
                }

                showAlert("Success", "Admin password changed successfully!", Alert.AlertType.INFORMATION);
            }
            return null;
        });

        dialog.showAndWait();
    }

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


