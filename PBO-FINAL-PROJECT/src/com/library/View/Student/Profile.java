package com.library.View.Student;

import com.library.Controller.Navigator;
import com.library.Model.Connections;
import com.library.Model.Student;
import com.library.Model.User;
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

public class Profile extends BorderPane {

    public Profile() {
        VBox sidebar = createSidebar();
        VBox mainContent = createMainContent();

        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(0);
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #800000;");

        VBox headerSection = new VBox(-20);
        headerSection.setPadding(new Insets(20));
        headerSection.setAlignment(Pos.CENTER);
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/LogoUmm.png").toExternalForm()));
        logo.setFitWidth(150);
        logo.setFitHeight(150);
        logo.setPreserveRatio(true);
        Label labelUMM = new Label("LIBRARY");
        labelUMM.setTextFill(Color.WHITE);
        labelUMM.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelUMM.setAlignment(Pos.CENTER);
        labelUMM.setWrapText(true);
        headerSection.getChildren().addAll(logo, labelUMM);

        String[] menuItems = {"Home", "Search Book", "Borrowing History", "Notifications", "Profile", "Logout"};
        String[] menuIcons = {"/images/Home.png", "/images/Search.png", "/images/Borrowinghistory.png", "/images/Notifications.png", "/images/Profile.png", "/images/Logout.png"};

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

            int index = i;
            menuButton.setOnAction(e -> {
                for (Button btn : allMenuButtons) {
                    btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
                }
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");

                switch (menuItems[index]) {
                    case "Home":
                        Navigator.showStudentDashboard(Navigator.getCurrentUser().getUsername());
                        break;
                    case "Search Book":
                        Navigator.showSearchBook();
                        break;
                    case "Borrowing History":
                        Navigator.showBorrowingHistory();
                        break;
                    case "Notifications":
                        Navigator.showNotifications();
                        break;
                    case "Profile":
                        Navigator.showProfile();
                        break;
                    case "Logout":
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Logout Confirmation");
                        alert.setHeaderText("Do you really want to logout?");
                        alert.setContentText("Press OK to proceed or Cancel to stay.");

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                Navigator.showLogin();
                            }
                        });
                        break;
                }
            });

            allMenuButtons.add(menuButton);
            menuBox.getChildren().add(menuButton);

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

        User rawUser = Navigator.getCurrentUser();
        if (!(rawUser instanceof Student student)) {
            System.err.println("Current user is not a Student");
            return new VBox(); // kosongin UI jika bukan student
        }


        HBox profileHeader = new HBox(20);
        profileHeader.setPadding(new Insets(30));
        profileHeader.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        ImageView profileImage = new ImageView(new Image(getClass().getResource("/images/Profileimage.png").toExternalForm()));
        profileImage.setFitWidth(100);
        profileImage.setPreserveRatio(true);

        VBox profileDetails = new VBox(10);
        profileDetails.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(student.getUsername());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label idLabel = new Label(student.getId());
        idLabel.setFont(Font.font("Arial", 14));

        Label roleLabel = new Label("Student");
        roleLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 4 12; -fx-background-radius: 10;");
        roleLabel.setFont(Font.font("Arial", 13));

        Button editProfileBtn = new Button("Edit Profile");
        editProfileBtn.setStyle("-fx-background-color: #ff5e5e; -fx-text-fill: white; -fx-font-weight: bold;");
        editProfileBtn.setPadding(new Insets(8, 20, 8, 20));
        editProfileBtn.setOnAction(e -> showEditProfileModal());

        profileDetails.getChildren().addAll(nameLabel, idLabel, roleLabel, editProfileBtn);
        profileHeader.getChildren().addAll(profileImage, profileDetails);

        VBox generalBox = new VBox(15);
        generalBox.setPadding(new Insets(30));
        generalBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        Label generalTitle = new Label("General Information");
        generalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox infoList = new VBox(8);
        infoList.getChildren().addAll(
                createInfoRow("Full Name", student.getUsername()),
                createInfoRow("Student ID", student.getId()),
                createInfoRow("Email", student.getEmail()),
                createInfoRow("Faculty", student.getFaculty()),
                createInfoRow("Major", student.getMajor())
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

    // Modal methods (unchanged)
    private void showEditProfileModal() {
        User currentUser = Navigator.getCurrentUser();

        if (!(currentUser instanceof Student)) return;

        Student student = (Student) currentUser;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.initModality(Modality.APPLICATION_MODAL);

        // Input fields
        TextField nameField = new TextField(student.getUsername());
        TextField emailField = new TextField(student.getEmail());
        TextField facultyField = new TextField(student.getFaculty());
        TextField majorField = new TextField(student.getMajor());

        VBox form = new VBox(10,
                new Label("Name:"), nameField,
                new Label("Email:"), emailField,
                new Label("Faculty:"), facultyField,
                new Label("Major:"), majorField
        );
        form.setPadding(new Insets(20));
        form.setPrefWidth(400);

        dialog.getDialogPane().setContent(form);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                student.setUsername(nameField.getText().trim());
                student.setEmail(emailField.getText().trim());
                student.setFaculty(facultyField.getText().trim());
                student.setMajor(majorField.getText().trim());

                new Connections().updateUser(student);
                Navigator.setCurrentUser(student); // update session

                showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
                Navigator.showProfile(); // refresh tampilan
            }
        });
    }

    private void showChangePasswordModal() {
        User user = Navigator.getCurrentUser();

        if (!(user instanceof Student)) return;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Change Password");
        dialog.initModality(Modality.APPLICATION_MODAL);

        PasswordField newPassField = new PasswordField();
        PasswordField confirmPassField = new PasswordField();

        VBox form = new VBox(10,
                new Label("New Password:"), newPassField,
                new Label("Confirm Password:"), confirmPassField
        );
        form.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(form);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String newPass = newPassField.getText().trim();
                String confirm = confirmPassField.getText().trim();

                if (newPass.isEmpty() || !newPass.equals(confirm)) {
                    showAlert("Error", "Passwords do not match or are empty.", Alert.AlertType.ERROR);
                    return;
                }

                new Connections().updateStudentPassword(user.getId(), newPass);
                showAlert("Success", "Password changed successfully.", Alert.AlertType.INFORMATION);
            }
        });
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
