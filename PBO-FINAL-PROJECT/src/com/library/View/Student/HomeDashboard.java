package com.library.View.Student;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HomeDashboard extends BorderPane {
    public HomeDashboard(String nama) {
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

        String[] menuItems = {"Home", "Search Book", "Borrowing History", "Notifications", "Profile", "Logout"};
        String[] menuIcons = {"/images/Home.png", "/images/Search.png", "/images/Borrowinghistory.png", "/images/Notifications.png", "/images/Profile.png", "/images/Logout.png"};

        for (int i = 0; i < menuItems.length; i++) {
            HBox menuItem = new HBox(15);
            menuItem.setPadding(new Insets(12, 20, 12, 20));
            menuItem.setAlignment(Pos.CENTER_LEFT);

            // Highlight Home menu item
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

        Label welcome = new Label("Welcome, " + nama + "!");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcome.setTextFill(Color.web("#2c3e50"));

        Label subtitle = new Label("Here's a quick summary of your activity");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setTextFill(Color.web("#7f8c8d"));

        // Summary Cards
        HBox summaryBox = new HBox(25);
        summaryBox.setPadding(new Insets(30, 0, 20, 0));
        summaryBox.setAlignment(Pos.CENTER_LEFT);

        VBox borrowedCard = createSummaryCard("Borrowed", "/images/Borrowedsummary.png", "3", "#34495e");
        VBox overdueCard = createSummaryCard("Overdue", "/images/Overduesummary.png", "1", "#34495e");
        VBox fineCard = createSummaryCard("Fine", "/images/Finesummary.png", "Rp5,000", "#34495e");

        summaryBox.getChildren().addAll(borrowedCard, overdueCard, fineCard);

        // Reminder Box
        VBox reminderBox = new VBox(15);
        reminderBox.setPadding(new Insets(20));
        reminderBox.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        HBox reminderHeader = new HBox(10);
        reminderHeader.setAlignment(Pos.CENTER_LEFT);

        Label reminderIcon = new Label("🔔");
        reminderIcon.setFont(Font.font("Arial", 18));

        Label reminderTitle = new Label("Reminders");
        reminderTitle.setTextFill(Color.web("#2c3e50"));
        reminderTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        reminderHeader.getChildren().addAll(reminderIcon, reminderTitle);

        VBox reminderItems = new VBox(8);

        HBox reminder1 = new HBox(8);
        reminder1.setAlignment(Pos.CENTER_LEFT);
        Label bullet1 = new Label("•");
        bullet1.setTextFill(Color.web("#7f8c8d"));
        bullet1.setFont(Font.font("Arial", 16));
        Label r1 = new Label("1 book due in 3 days");
        r1.setFont(Font.font("Arial", 14));
        r1.setTextFill(Color.web("#2c3e50"));
        reminder1.getChildren().addAll(bullet1, r1);

        HBox reminder2 = new HBox(8);
        reminder2.setAlignment(Pos.CENTER_LEFT);
        Label bullet2 = new Label("•");
        bullet2.setTextFill(Color.web("#7f8c8d"));
        bullet2.setFont(Font.font("Arial", 16));
        Label r2 = new Label("Return overdue book to avoid more fines");
        r2.setFont(Font.font("Arial", 14));
        r2.setTextFill(Color.web("#2c3e50"));
        reminder2.getChildren().addAll(bullet2, r2);

        reminderItems.getChildren().addAll(reminder1, reminder2);
        reminderBox.getChildren().addAll(reminderHeader, reminderItems);

        mainContent.getChildren().addAll(welcome, subtitle, summaryBox, reminderBox);

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSummaryCard(String title, String iconPath, String value, String backgroundColor) {
        VBox card = new VBox(15);
        card.setPrefSize(180, 120);
        card.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
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
}