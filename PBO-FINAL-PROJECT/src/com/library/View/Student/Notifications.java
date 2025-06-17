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

public class Notifications extends BorderPane {

    public Notifications() {
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
        labelUMM.setAlignment(Pos.CENTER);

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

            if (menuItems[i].equals("Notifications")) {
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
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        Label title = new Label("Notifications");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#2c3e50"));

        VBox notifBox = new VBox(15);

        notifBox.getChildren().addAll(
                createNotifItem(
                        "Your book request \"Java Programming\" is now available.",
                        "Pick up before: 18 June 2025",
                        "12 Minute Ago"
                ),
                createNotifItem(
                        "\"AI for Beginner\" is due in 2 days.",
                        "Due Date: 20 June 2025",
                        "5 Minute Ago"
                ),
                createNotifItem(
                        "\"Data Structure\" is overdue! Please return it immediately.",
                        "Due Date: 21 June 2025",
                        "1 Minute Ago"
                )
        );

        mainContent.getChildren().addAll(title, notifBox);
        return mainContent;
    }

    private VBox createNotifItem(String message, String dateInfo, String timeAgo) {
        VBox notif = new VBox(5);
        notif.setPadding(new Insets(15));
        notif.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 6;");

        Label msgLabel = new Label(message);
        msgLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label dateLabel = new Label("• " + dateInfo);
        dateLabel.setFont(Font.font("Arial", 13));

        Label timeLabel = new Label(timeAgo);
        timeLabel.setFont(Font.font("Arial", 10));
        timeLabel.setTextFill(Color.GRAY);
        timeLabel.setAlignment(Pos.BOTTOM_RIGHT);

        notif.getChildren().addAll(msgLabel, dateLabel, timeLabel);
        return notif;
    }
}

