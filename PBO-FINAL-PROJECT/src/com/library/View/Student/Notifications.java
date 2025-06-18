package com.library.View.Student;

import com.library.Controller.Navigator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

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

        // Header section with logo and title
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
                    case "Home":
                        Navigator.showStudentDashboard("Nama Mahasiswa"); // ganti dengan variabel nama
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

            // Highlight default misalnya "Home"
            if (menuItems[i].equals("Notifications")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }
        return sidebar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        Label title = new Label("\uD83D\uDCAC Notifications");
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

