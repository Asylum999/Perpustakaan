package com.library.View.Student;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

        VBox headerSection = new VBox(10);
        headerSection.setPadding(new Insets(20));
        headerSection.setAlignment(Pos.CENTER);

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/umm-logo.png").toExternalForm()));
        logo.setFitWidth(50);
        logo.setPreserveRatio(true);

        Label labelUMM = new Label("UMM LIBRARY");
        labelUMM.setTextFill(Color.WHITE);
        labelUMM.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelUMM.setWrapText(true);

        headerSection.getChildren().addAll(logo, labelUMM);

        VBox menuBox = new VBox(0);
        menuBox.setPadding(new Insets(20, 0, 0, 0));

        String[] menuItems = {"Home", "Search Book", "Borrowing History", "Notifications", "Profile", "Logout"};
        String[] menuIcons = {"/images/Home.png", "/images/Search.png", "/images/Borrowinghistory.png",
                "/images/Notifications.png", "/images/Profile.png", "/images/Logout.png"};

        for (int i = 0; i < menuItems.length; i++) {
            HBox menuItem = new HBox(15);
            menuItem.setPadding(new Insets(12, 20, 12, 20));
            menuItem.setAlignment(Pos.CENTER_LEFT);

            if (menuItems[i].equals("Profile")) {
                menuItem.setStyle("-fx-background-color: rgba(255,255,255,0.1);");
            }

            ImageView icon = new ImageView(new Image(getClass().getResource(menuIcons[i]).toExternalForm()));
            icon.setFitWidth(20);
            icon.setFitHeight(20);
            icon.setPreserveRatio(true);

            Label menu = new Label(menuItems[i]);
            menu.setTextFill(Color.WHITE);
            menu.setFont(Font.font("Arial", 14));

            menuItem.getChildren().addAll(icon, menu);
            menuBox.getChildren().add(menuItem);
        }

        sidebar.getChildren().addAll(headerSection, menuBox);
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

        Label nameLabel = new Label("Farrel Anizar Razzani");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label idLabel = new Label("202410370110165");
        idLabel.setFont(Font.font("Arial", 14));

        Label roleLabel = new Label("Student");
        roleLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 4 12; -fx-background-radius: 10;");
        roleLabel.setFont(Font.font("Arial", 13));

        Button editProfileBtn = new Button("Edit Profile");
        editProfileBtn.setStyle("-fx-background-color: #ff5e5e; -fx-text-fill: white; -fx-font-weight: bold;");
        editProfileBtn.setPadding(new Insets(8, 20, 8, 20));

        profileDetails.getChildren().addAll(nameLabel, idLabel, roleLabel, editProfileBtn);
        profileHeader.getChildren().addAll(profileImage, profileDetails);

        // === General Information ===
        VBox generalBox = new VBox(15);
        generalBox.setPadding(new Insets(30));
        generalBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        Label generalTitle = new Label("General Information");
        generalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox infoList = new VBox(8);
        infoList.getChildren().addAll(
                createInfoRow("Full Name", "Farrel Anizar Razzani"),
                createInfoRow("Student ID", "202410370110165"),
                createInfoRow("Email", "farrelq@students.umm.ac.id"),
                createInfoRow("Faculty", "Engineering"),
                createInfoRow("Major", "Informatics")
        );

        Button changePasswordBtn = new Button("Change Password");
        changePasswordBtn.setStyle("-fx-background-color: transparent; -fx-border-color: #ff5e5e; -fx-text-fill: #ff5e5e; -fx-font-weight: bold;");
        changePasswordBtn.setPadding(new Insets(8, 16, 8, 16));

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
}

