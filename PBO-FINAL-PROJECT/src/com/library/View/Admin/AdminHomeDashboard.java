package com.library.View.Admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminHomeDashboard extends BorderPane {
    public AdminHomeDashboard() {
        // === Sidebar ===
        VBox sidebar = new VBox(0);
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #800000;");

        // Header section with logo and title
        VBox headerSection = new VBox(10);
        headerSection.setPadding(new Insets(20));
        headerSection.setAlignment(Pos.CENTER);

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/umm-logo.png").toExternalForm()));
        logo.setFitWidth(50);
        logo.setPreserveRatio(true);

        Label labelUMM = new Label("UMM LIBRARY");
        labelUMM.setTextFill(Color.WHITE);
        labelUMM.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelUMM.setAlignment(Pos.CENTER);
        labelUMM.setWrapText(true);

        headerSection.getChildren().addAll(logo, labelUMM);

        // Menu section
        VBox menuBox = new VBox(0);
        menuBox.setPadding(new Insets(20, 0, 0, 0));

        String[] menuItems = {"Home", "Book Management", "User Management", "Profile", "Logout"};
        String[] menuIcons = {"/images/Home.png", "/images/BookManagement.png", "/images/UserManagement.png",
                "/images/Profile.png", "/images/Logout.png"};

        for (int i = 0; i < menuItems.length; i++) {
            HBox menuItem = new HBox(15);
            menuItem.setPadding(new Insets(12, 20, 12, 20));
            menuItem.setAlignment(Pos.CENTER_LEFT);

            // Highlight Home menu item by default
            if (menuItems[i].equals("Home")) {
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

        // === Main Content ===
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        Label welcome = new Label("Welcome, Admin!");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcome.setTextFill(Color.web("#2c3e50"));

        Label subtitle = new Label("Administrator Dashboard");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setTextFill(Color.web("#7f8c8d"));

        // Admin Summary Cards
        HBox summaryBox = new HBox(25);
        summaryBox.setPadding(new Insets(30, 0, 20, 0));
        summaryBox.setAlignment(Pos.CENTER_LEFT);

        VBox booksCard = createSummaryCard("Total Books", "/images/Books.png", "1,245", "#34495e");
        VBox usersCard = createSummaryCard("Total Users", "/images/Users.png", "586", "#34495e");
        VBox loansCard = createSummaryCard("Active Loans", "/images/Loans.png", "342", "#34495e");

        summaryBox.getChildren().addAll(booksCard, usersCard, loansCard);

        // Quick Actions
        VBox quickActionsBox = new VBox(15);
        quickActionsBox.setPadding(new Insets(20));
        quickActionsBox.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                "-fx-border-radius: 8px; -fx-background-radius: 8px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        HBox quickActionsHeader = new HBox(10);
        quickActionsHeader.setAlignment(Pos.CENTER_LEFT);

        Label quickActionsIcon = new Label("⚡");
        quickActionsIcon.setFont(Font.font("Arial", 18));

        Label quickActionsTitle = new Label("Quick Actions");
        quickActionsTitle.setTextFill(Color.web("#2c3e50"));
        quickActionsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        quickActionsHeader.getChildren().addAll(quickActionsIcon, quickActionsTitle);

        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER_LEFT);

        Button addBookBtn = createQuickActionButton("Add New Book", "/images/AddBook.png");
        Button addUserBtn = createQuickActionButton("Add New User", "/images/AddUser.png");
        Button manageLoansBtn = createQuickActionButton("Manage Loans", "/images/ManageLoans.png");

        actionButtons.getChildren().addAll(addBookBtn, addUserBtn, manageLoansBtn);
        quickActionsBox.getChildren().addAll(quickActionsHeader, actionButtons);

        mainContent.getChildren().addAll(welcome, subtitle, summaryBox, quickActionsBox);

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSummaryCard(String title, String iconPath, String value, String backgroundColor) {
        VBox card = new VBox(15);
        card.setPrefSize(180, 120);
        card.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));

        ImageView icon = new ImageView(new Image(getClass().getResource(iconPath).toExternalForm()));
        icon.setFitWidth(30);
        icon.setFitHeight(30);
        icon.setPreserveRatio(true);

        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.LIGHTGRAY);
        titleLabel.setFont(Font.font("Arial", 14));

        Label valueLabel = new Label(value);
        valueLabel.setTextFill(Color.WHITE);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        card.getChildren().addAll(icon, titleLabel, valueLabel);
        return card;
    }

    private Button createQuickActionButton(String text, String iconPath) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #800000; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-background-radius: 8px; " +
                "-fx-padding: 10 15 10 15;");

        ImageView icon = new ImageView(new Image(getClass().getResource(iconPath).toExternalForm()));
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        icon.setPreserveRatio(true);

        button.setGraphic(icon);
        button.setContentDisplay(ContentDisplay.LEFT);
        button.setGraphicTextGap(10);

        return button;
    }
}