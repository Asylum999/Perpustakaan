package com.library.View.Student;

import com.library.Controller.Navigator;
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
import java.util.stream.Collectors;

public class HomeDashboard extends BorderPane {
    public HomeDashboard(String nama) {
        // === Sidebar ===
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

            if (menuItems[i].equals("Home")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }

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

        HBox summaryBox = new HBox(25);
        summaryBox.setPadding(new Insets(30, 0, 20, 0));
        summaryBox.setAlignment(Pos.CENTER_LEFT);

        Connections conn = new Connections();
        String currentId = Navigator.getCurrentUser().getId();

        int borrowed = 0;
        int overdue = 0;
        double fineAmount = 0.0;

        for (Borrowing b : conn.getAllBorrowings()) {
            if (b.getStudentId().equals(currentId)) {
                if ("Borrowed".equalsIgnoreCase(b.getStatus())) {
                    if (b.getDueDate().isBefore(LocalDate.now())) {
                        overdue++;
                    } else {
                        borrowed++;
                    }
                }
            }
        }

        for (var f : conn.getAllFines()) {
            if (f.getStudentId().equals(currentId) && "Unpaid".equalsIgnoreCase(f.getStatus())) {
                fineAmount += f.getAmount();
            }
        }

        VBox borrowedCard = createSummaryCard("Borrowed", "/images/Borrowedsummary.png", String.valueOf(borrowed), "#34495e");
        VBox overdueCard = createSummaryCard("Overdue", "/images/Overduesummary.png", String.valueOf(overdue), "#34495e");
        VBox fineCard = createSummaryCard("Fine", "/images/Finesummary.png", "Rp" + String.format("%,.0f", fineAmount), "#34495e");

        summaryBox.getChildren().addAll(borrowedCard, overdueCard, fineCard);

        // === Reminder Section ===
        VBox reminderCard = new VBox(15);
        reminderCard.setPadding(new Insets(20));
        reminderCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label reminderTitle = new Label("\uD83D\uDD14 Reminders");
        reminderTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        reminderTitle.setTextFill(Color.web("#2c3e50"));

        LocalDate today = LocalDate.now();
        List<Borrowing> dueSoon = conn.getAllBorrowings().stream()
                .filter(b -> b.getStudentId().equals(currentId))
                .filter(b -> b.getStatus().equalsIgnoreCase("Borrowed"))
                .filter(b -> !b.getDueDate().isBefore(today))
                .filter(b -> !b.getDueDate().isAfter(today.plusDays(7)))
                .collect(Collectors.toList());

        VBox reminderList = new VBox(5);
        if (dueSoon.isEmpty()) {
            Label emptyLabel = new Label("No upcoming due dates.");
            emptyLabel.setFont(Font.font("Arial", 13));
            emptyLabel.setTextFill(Color.web("#7f8c8d"));
            reminderList.getChildren().add(emptyLabel);
        } else {
            for (Borrowing b : dueSoon) {
                String title = conn.getAllBooks().stream()
                        .filter(book -> book.getIsbn().equals(b.getIsbn()))
                        .map(book -> book.getTitle())
                        .findFirst()
                        .orElse("Unknown Title");

                Label item = new Label("- " + title + " due " + b.getDueDate());
                item.setFont(Font.font("Arial", 13));
                item.setTextFill(Color.web("#2c3e50"));
                reminderList.getChildren().add(item);
            }
        }

        reminderCard.getChildren().addAll(reminderTitle, reminderList);

        // Tambahkan semua ke main content
        mainContent.getChildren().addAll(welcome, subtitle, summaryBox, reminderCard);

        // Set layout
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSummaryCard(String title, String iconPath, String value, String backgroundColor) {
        VBox card = new VBox(15);
        card.setPrefSize(180, 120);
        card.setStyle("-fx-background-color: #750205 " + backgroundColor + "; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
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
