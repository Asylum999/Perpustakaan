package com.library.View.Admin;

import com.library.Controller.Navigator;
import com.library.Model.Student;
import com.library.Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class AdminUserManagement extends BorderPane {

    private TableView<UserTableData> userTable; // Instance variable

    // Wrapper class for table display
    public static class UserTableData {
        private final String no;
        private final Student user;

        public UserTableData(String no, Student user) {
            this.no = no;
            this.user = user;
        }

        // Getters for table columns
        public String getNo() {
            return no; }
        public String getStudentId() {
            return user.getId(); }
        public String getName() {
            return user.getUsername(); }
        public String getMajor() {
            return user.getMajor(); }
        public String getFaculty() {
            return user.getFaculty(); }
        public String getEmail() {
            return user.getEmail(); }
        public Student getUser() {
            return user; }
    }

    public AdminUserManagement() {
        // === Sidebar (Same as Dashboard) ===
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

        // Menu section - Admin Menu Items
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

            int index = i;
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
                        break;
                    default:
                        System.out.println("Menu belum ditangani: " + menuItems[index]);
                }
            });

            allMenuButtons.add(menuButton);
            menuBox.getChildren().add(menuButton);

            // Highlight "User Management"
            if (menuItems[i].equals("User Management")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }

        // === Main Content - User Management ===
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Header with title
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label pageTitle = new Label("User Management");
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        pageTitle.setTextFill(Color.web("#2c3e50"));

        header.getChildren().add(pageTitle);

        // Content Layout
        HBox contentLayout = new HBox(20);
        contentLayout.setAlignment(Pos.TOP_LEFT);

        // Left Side - Table
        VBox leftSide = new VBox(15);
        leftSide.setPrefWidth(800);

        // Table
        userTable = new TableView<>();
        userTable.setPrefHeight(500);
        userTable.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        // Table Columns - Fixed column headers and data binding
        TableColumn<UserTableData, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));
        noCol.setPrefWidth(30);

        TableColumn<UserTableData, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(120);

        TableColumn<UserTableData, String> studentIdCol = new TableColumn<>("STUDENT ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentIdCol.setPrefWidth(150);

        TableColumn<UserTableData, String> majorCol = new TableColumn<>("MAJOR");
        majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));
        majorCol.setPrefWidth(120);

        TableColumn<UserTableData, String> facultyCol = new TableColumn<>("FACULTY");
        facultyCol.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        facultyCol.setPrefWidth(100);

        TableColumn<UserTableData, String> emailCol = new TableColumn<>("EMAIL");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(153);

        TableColumn<UserTableData, Void> actionCol = new TableColumn<>("");
        actionCol.setPrefWidth(100);

        // Add action buttons to each row
        actionCol.setCellFactory(col -> new javafx.scene.control.TableCell<UserTableData, Void>() {
            private final Button deleteBtn = new Button();
            private final Button editBtn = new Button();
            private final HBox buttonBox = new HBox(5);

            {
                deleteBtn.setStyle("-fx-background-color: #750205; -fx-pref-width: 25; -fx-pref-height: 25;");
                editBtn.setStyle("-fx-background-color: #750205; -fx-pref-width: 25; -fx-pref-height: 25;");

                // Add images from your images folder
                ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/images/deletebutton.png").toExternalForm()));
                deleteIcon.setFitWidth(20);
                deleteIcon.setFitHeight(20);
                deleteBtn.setGraphic(deleteIcon);

                ImageView editIcon = new ImageView(new Image(getClass().getResource("/images/editbutton.png").toExternalForm()));
                editIcon.setFitWidth(20);
                editIcon.setFitHeight(20);
                editBtn.setGraphic(editIcon);

                // Delete button action
                deleteBtn.setOnAction(e -> {
                    UserTableData selectedUser = getTableView().getItems().get(getIndex());
                    if (selectedUser != null) {
                        showDeleteConfirmation(selectedUser);
                    }
                });

                // Edit button action - Navigate to User Edit scene
                editBtn.setOnAction(e -> {
                    UserTableData selectedUser = getTableView().getItems().get(getIndex());
                    if (selectedUser != null) {
                        // Pass the Student object to the edit scene
                        Navigator.showAdminUserEdit(selectedUser.getUser());
                    }
                });

                buttonBox.getChildren().addAll(deleteBtn, editBtn);
                buttonBox.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        });

        userTable.getColumns().addAll(noCol, nameCol, studentIdCol, majorCol, facultyCol, emailCol, actionCol);

        // Sample data
        ObservableList<UserTableData> userData = FXCollections.observableArrayList(
                new UserTableData("1", new Student("202410370110024", "POPO", "Informatics", "Technic", "POPO")),
                new UserTableData("2", new Student("202410370110165", "LALA", "Informatics", "Technic", "LALA"))
        );

        // Add sample rows
        for (int i = 3; i <= 15; i++) {
            userData.add(new UserTableData(String.valueOf(i), new Student("Sample", "Sample", "Sample", "Sample", "Sample")));
        }

        userTable.setItems(userData);
        leftSide.getChildren().add(userTable);

        // Right Side - Search Form
        VBox rightSide = new VBox(15);
        rightSide.setPrefWidth(300);

        VBox searchCard = new VBox(15);
        searchCard.setPadding(new Insets(20));
        searchCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        // Student ID Field (changed from NIM)
        Label studentIdLabel = new Label("Student ID :");
        studentIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        studentIdLabel.setTextFill(Color.web("#2c3e50"));
        TextField studentIdField = new TextField();
        studentIdField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Name Field
        Label nameLabel = new Label("Name :");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.web("#2c3e50"));
        TextField nameField = new TextField();
        nameField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Major Field
        Label majorLabel = new Label("Major :");
        majorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        majorLabel.setTextFill(Color.web("#2c3e50"));
        TextField majorField = new TextField();
        majorField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Faculty Field
        Label facultyLabel = new Label("Faculty :");
        facultyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        facultyLabel.setTextFill(Color.web("#2c3e50"));
        TextField facultyField = new TextField();
        facultyField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Email Field
        Label emailLabel = new Label("Email :");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        emailLabel.setTextFill(Color.web("#2c3e50"));
        TextField emailField = new TextField();
        emailField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Search Button
        Button searchBtn = new Button("Search");
        searchBtn.setPrefWidth(270);
        searchBtn.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");

        // Search functionality - updated parameter order
        searchBtn.setOnAction(e -> performSearch(studentIdField.getText(), nameField.getText(), majorField.getText(), facultyField.getText(), emailField.getText()));

        searchCard.getChildren().addAll(
                studentIdLabel, studentIdField,
                nameLabel, nameField,
                majorLabel, majorField,
                facultyLabel, facultyField,
                emailLabel, emailField,
                searchBtn
        );

        rightSide.getChildren().add(searchCard);

        contentLayout.getChildren().addAll(leftSide, rightSide);
        mainContent.getChildren().addAll(header, contentLayout);

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    // Method to perform search - updated parameter order
    private void performSearch(String studentId, String name, String major, String faculty, String email) {
        // Filter the table based on search criteria
        ObservableList<UserTableData> allUsers = FXCollections.observableArrayList(
                new UserTableData("1", new Student("202410370110024", "POPO", "Informatics", "Technic", "POPO")),
                new UserTableData("2", new Student("202410370110165", "LALA", "Informatics", "Technic", "LALA"))
        );

        // Add sample rows
        for (int i = 3; i <= 15; i++) {
            allUsers.add(new UserTableData(String.valueOf(i), new Student("Sample", "Sample", "Sample", "Sample", "Sample")));
        }

        ObservableList<UserTableData> filteredUsers = FXCollections.observableArrayList();

        for (UserTableData user : allUsers) {
            boolean matches = true;

            if (!studentId.isEmpty() && !user.getStudentId().toLowerCase().contains(studentId.toLowerCase())) {
                matches = false;
            }
            if (!name.isEmpty() && !user.getName().toLowerCase().contains(name.toLowerCase())) {
                matches = false;
            }
            if (!major.isEmpty() && !user.getMajor().toLowerCase().contains(major.toLowerCase())) {
                matches = false;
            }
            if (!faculty.isEmpty() && !user.getFaculty().toLowerCase().contains(faculty.toLowerCase())) {
                matches = false;
            }
            if (!email.isEmpty() && !user.getEmail().toLowerCase().contains(email.toLowerCase())) {
                matches = false;
            }

            if (matches) {
                filteredUsers.add(user);
            }
        }

        userTable.setItems(filteredUsers);
    }

    // Method to show delete confirmation dialog
    private void showDeleteConfirmation(UserTableData user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete user: " + user.getName() + "?");

        // Style the alert
        alert.getDialogPane().setStyle("-fx-background-color: white;");

        // Style the buttons
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        okButton.setText("Delete");

        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Remove user from table
                userTable.getItems().remove(user);
                showAlert("Success", "User has been successfully deleted!", Alert.AlertType.INFORMATION);
            }
        });
    }

    // Method to show alerts/notifications
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert
        alert.getDialogPane().setStyle("-fx-background-color: white;");

        // Style the button
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        if (alertType == Alert.AlertType.INFORMATION) {
            okButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        } else if (alertType == Alert.AlertType.ERROR) {
            okButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        alert.showAndWait();
    }
}