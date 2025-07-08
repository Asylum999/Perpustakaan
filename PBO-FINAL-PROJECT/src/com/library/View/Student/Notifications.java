package com.library.View.Student;

import com.library.Controller.Navigator;
import com.library.Model.Book;
import com.library.Model.Borrowing;
import com.library.Model.Connections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
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

        String userId = Navigator.getCurrentUser().getId();
        Connections conn = new Connections();
        List<Borrowing> borrowings = conn.getAllBorrowings();
        List<Book> books = conn.getAllBooks();

        LocalDate today = LocalDate.now();

        for (Borrowing b : borrowings) {
            if (!b.getStudentId().equals(userId)) continue;

            String bookTitle = books.stream()
                    .filter(book -> book.getIsbn().equals(b.getIsbn()))
                    .map(Book::getTitle)
                    .findFirst()
                    .orElse("Unknown Title");

            long daysToDue = java.time.temporal.ChronoUnit.DAYS.between(today, b.getDueDate());

            if ("Borrowed".equalsIgnoreCase(b.getStatus())) {
                // ✅ 1. Notifikasi umum setelah peminjaman
                notifBox.getChildren().add(
                        createNotifItem(
                                "You borrowed \"" + bookTitle + "\".",
                                "Due Date: " + b.getDueDate(),
                                "Just borrowed"
                        )
                );

                // ❌ 2. Sudah jatuh tempo
                if (daysToDue < 0) {
                    notifBox.getChildren().add(
                            createNotifItem(
                                    "\"" + bookTitle + "\" is overdue! Please return it immediately.",
                                    "Due Date: " + b.getDueDate(),
                                    "Overdue"
                            )
                    );
                }
                // ⚠️ 3. Akan jatuh tempo dalam 3 hari
                else if (daysToDue <= 3) {
                    notifBox.getChildren().add(
                            createNotifItem(
                                    "\"" + bookTitle + "\" is due in " + daysToDue + " days.",
                                    "Due Date: " + b.getDueDate(),
                                    daysToDue + " day(s) left"
                            )
                    );
                }
            }
        }

        if (notifBox.getChildren().isEmpty()) {
            Label noNotif = new Label("You have no notifications.");
            noNotif.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            notifBox.getChildren().add(noNotif);
        }

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
