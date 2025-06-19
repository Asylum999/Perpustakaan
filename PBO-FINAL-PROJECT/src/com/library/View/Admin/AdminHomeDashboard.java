package com.library.View.Admin;

import com.library.Controller.Navigator;
import com.library.Model.Admin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

public class AdminHomeDashboard extends BorderPane {
    private Map<String, VBox> monthDropdowns = new HashMap<>();
    private Map<String, Label> monthArrows = new HashMap<>();

    public AdminHomeDashboard(Admin admin) {
        // === Sidebar ===
        VBox sidebar = new VBox(0);
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #800000;");

        VBox headerSection = new VBox(-20);
        headerSection.setPadding(new Insets(20));
        headerSection.setAlignment(Pos.CENTER);
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/LogoUmm.png").toExternalForm()));
        logo.setFitWidth(150);
        logo.setPreserveRatio(true);
        Label labelUMM = new Label("LIBRARY");
        labelUMM.setTextFill(Color.WHITE);
        labelUMM.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelUMM.setAlignment(Pos.CENTER);
        headerSection.getChildren().addAll(logo, labelUMM);

        String[] menuItems = {"Dashboard", "Book Management", "User Management", "Profile", "Logout"};
        String[] menuIcons = {"/images/Home.png", "/images/Search.png", "/images/userManagement.png", "/images/Profile.png", "/images/Logout.png"};
        List<Button> allMenuButtons = new ArrayList<>();
        VBox menuBox = new VBox(5);
        menuBox.setPadding(new Insets(10, 0, 0, 0));
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
                    case "Dashboard":
                        Navigator.showAdminHomeDashboard(admin);
                        break;
                    case "Book Management":
                        Navigator.showAdminBookManagement(admin);
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
                                Navigator.showLogin();
                            }
                        });
                        break;
                    default:
                        System.out.println("Unhandled menu: " + menuItems[index]);
                }
            });

            allMenuButtons.add(menuButton);
            menuBox.getChildren().add(menuButton);

            if (menuItems[i].equals("Dashboard")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        Label welcome = new Label("Welcome, " + admin.getUsername() + "!");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcome.setTextFill(Color.web("#2c3e50"));

        HBox contentLayout = new HBox(20);
        contentLayout.setAlignment(Pos.TOP_LEFT);

        VBox leftSide = new VBox(20);
        leftSide.setPrefWidth(650);
        leftSide.getChildren().addAll(
                createLineChartCard("Book Borrowing Statistics", "Books Borrowed"),
                createLineChartCard("Book Returns Statistics", "Books Returned"),
                createLogStatusCard()
        );

        VBox rightSide = new VBox(20);
        rightSide.setPrefWidth(350);
        rightSide.getChildren().add(createFinesCard());

        contentLayout.getChildren().addAll(leftSide, rightSide);
        mainContent.getChildren().addAll(welcome, contentLayout);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: #f8f9fa;");

        this.setLeft(sidebar);
        this.setCenter(scrollPane);
    }

    private VBox createLineChartCard(String title, String yAxisLabel) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Label chartTitle = new Label(title);
        chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        chartTitle.setTextFill(Color.web("#2c3e50"));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setPrefHeight(250);
        chart.setCreateSymbols(true);

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int[] values = {60, 55, 75, 58, 50, 40, 38, 85, 80, 88, 65, 90};
        for (int i = 0; i < months.length; i++) {
            dataSeries.getData().add(new XYChart.Data<>(months[i], values[i]));
        }

        chart.getData().add(dataSeries);
        card.getChildren().addAll(chartTitle, chart);
        return card;
    }

    private VBox createLogStatusCard() {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px;");

        Label title = new Label("Log Status");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setTextFill(Color.web("#2c3e50"));

        VBox logItems = new VBox(10);
        logItems.getChildren().addAll(createLog("Farhan borrowed a book"), createLog("Boabao returned a book"));

        card.getChildren().addAll(title, logItems);
        return card;
    }

    private HBox createLog(String text) {
        HBox log = new HBox(8);
        log.setAlignment(Pos.CENTER_LEFT);
        Label bullet = new Label("•");
        bullet.setTextFill(Color.web("#7f8c8d"));
        bullet.setFont(Font.font("Arial", 16));
        Label logText = new Label(text);
        logText.setFont(Font.font("Arial", 14));
        logText.setTextFill(Color.web("#2c3e50"));
        log.getChildren().addAll(bullet, logText);
        return log;
    }

    private VBox createFinesCard() {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px;");

        Label title = new Label("Fines Collected");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setTextFill(Color.web("#2c3e50"));

        VBox monthsList = new VBox(8);
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        String[] fineAmounts = {"Rp. 75.000", "Rp. 82.000", "Rp. 90.000", "Rp. 65.000",
                "Rp. 70.000", "Rp. 95.000", "Rp. 88.000", "Rp. 120.000",
                "Rp. 110.000", "Rp. 105.000", "Rp. 85.000", "Rp. 100.000"};

        for (int i = 0; i < months.length; i++) {
            VBox monthContainer = new VBox();
            HBox monthRow = new HBox(10);
            monthRow.setAlignment(Pos.CENTER_LEFT);
            Label monthLabel = new Label(months[i]);
            monthLabel.setFont(Font.font("Arial", 14));
            monthLabel.setTextFill(Color.web("#2c3e50"));
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            Label arrow = new Label("↓");
            arrow.setFont(Font.font("Arial", 16));
            arrow.setTextFill(Color.web("#7f8c8d"));
            monthRow.getChildren().addAll(monthLabel, spacer, arrow);

            VBox dropdownContent = new VBox(10);
            dropdownContent.setPadding(new Insets(10, 15, 10, 15));
            dropdownContent.setStyle("-fx-background-color: #f8f9fa;");
            dropdownContent.setVisible(false);
            dropdownContent.setManaged(false);

            HBox collectedRow = new HBox();
            collectedRow.setAlignment(Pos.CENTER_LEFT);
            Label collectedLabel = new Label("Collected Fines:");
            collectedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            collectedLabel.setTextFill(Color.web("#2c3e50"));
            Region spacer1 = new Region();
            HBox.setHgrow(spacer1, Priority.ALWAYS);
            Label amount = new Label(fineAmounts[i]);
            amount.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            amount.setTextFill(Color.web("#2c3e50"));
            collectedRow.getChildren().addAll(collectedLabel, spacer1, amount);

            dropdownContent.getChildren().add(collectedRow);

            monthContainer.getChildren().addAll(monthRow, dropdownContent);
            monthDropdowns.put(months[i], dropdownContent);
            monthArrows.put(months[i], arrow);
            int finalI = i;
            monthRow.setOnMouseClicked(e -> toggleDropdown(months[finalI]));

            monthsList.getChildren().add(monthContainer);
        }

        card.getChildren().addAll(title, monthsList);
        return card;
    }

    private void toggleDropdown(String monthName) {
        VBox dropdown = monthDropdowns.get(monthName);
        Label arrow = monthArrows.get(monthName);
        boolean visible = dropdown.isVisible();
        dropdown.setVisible(!visible);
        dropdown.setManaged(!visible);
        arrow.setText(visible ? "↓" : "↑");
    }
}
