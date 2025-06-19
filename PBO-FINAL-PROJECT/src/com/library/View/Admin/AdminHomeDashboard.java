package com.library.View.Admin;

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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class AdminHomeDashboard extends BorderPane {
    private Map<String, VBox> monthDropdowns = new HashMap<>();
    private Map<String, Label> monthArrows = new HashMap<>();

    public AdminHomeDashboard(String nama) {
        // === Sidebar ===
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
                    default:
                        System.out.println("Menu belum ditangani: " + menuItems[index]);
                }
            });

            allMenuButtons.add(menuButton);
            menuBox.getChildren().add(menuButton);

            // Highlight default "Dashboard"
            if (menuItems[i].equals("Dashboard")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }

        // === Main Content (Scrollable) ===
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        Label welcome = new Label("Welcome, " + nama + "!");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcome.setTextFill(Color.web("#2c3e50"));

        // Main Content Layout
        HBox contentLayout = new HBox(20);
        contentLayout.setAlignment(Pos.TOP_LEFT);

        // In the leftSide section, replace the single chart with two charts
        VBox leftSide = new VBox(20);
        leftSide.setPrefWidth(650);

        // Book Borrowing Chart
        VBox borrowChartCard = new VBox(15);
        borrowChartCard.setPadding(new Insets(20));
        borrowChartCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label borrowChartTitle = new Label("Book Borrowing Statistics");
        borrowChartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        borrowChartTitle.setTextFill(Color.web("#2c3e50"));

        // Create Borrowing Line Chart
        CategoryAxis borrowXAxis = new CategoryAxis();
        NumberAxis borrowYAxis = new NumberAxis();
        borrowYAxis.setLabel("Books Borrowed");
        LineChart<String, Number> borrowChart = new LineChart<>(borrowXAxis, borrowYAxis);
        borrowChart.setCreateSymbols(true);
        borrowChart.setLegendVisible(false);
        borrowChart.setPrefHeight(250);

        XYChart.Series<String, Number> borrowSeries = new XYChart.Series<>();
        borrowSeries.getData().add(new XYChart.Data<>("Jan", 65));
        borrowSeries.getData().add(new XYChart.Data<>("Feb", 59));
        borrowSeries.getData().add(new XYChart.Data<>("Mar", 80));
        borrowSeries.getData().add(new XYChart.Data<>("Apr", 60));
        borrowSeries.getData().add(new XYChart.Data<>("May", 55));
        borrowSeries.getData().add(new XYChart.Data<>("Jun", 45));
        borrowSeries.getData().add(new XYChart.Data<>("Jul", 40));
        borrowSeries.getData().add(new XYChart.Data<>("Aug", 90));
        borrowSeries.getData().add(new XYChart.Data<>("Sep", 85));
        borrowSeries.getData().add(new XYChart.Data<>("Oct", 90));
        borrowSeries.getData().add(new XYChart.Data<>("Nov", 70));
        borrowSeries.getData().add(new XYChart.Data<>("Dec", 95));

        borrowChart.getData().add(borrowSeries);
        borrowChartCard.getChildren().addAll(borrowChartTitle, borrowChart);

        // Book Returns Chart
        VBox returnChartCard = new VBox(15);
        returnChartCard.setPadding(new Insets(20));
        returnChartCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label returnChartTitle = new Label("Book Returns Statistics");
        returnChartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        returnChartTitle.setTextFill(Color.web("#2c3e50"));

        // Create Returns Line Chart
        CategoryAxis returnXAxis = new CategoryAxis();
        NumberAxis returnYAxis = new NumberAxis();
        returnYAxis.setLabel("Books Returned");
        LineChart<String, Number> returnChart = new LineChart<>(returnXAxis, returnYAxis);
        returnChart.setCreateSymbols(true);
        returnChart.setLegendVisible(false);
        returnChart.setPrefHeight(250);

        XYChart.Series<String, Number> returnSeries = new XYChart.Series<>();
        returnSeries.getData().add(new XYChart.Data<>("Jan", 60));
        returnSeries.getData().add(new XYChart.Data<>("Feb", 55));
        returnSeries.getData().add(new XYChart.Data<>("Mar", 75));
        returnSeries.getData().add(new XYChart.Data<>("Apr", 58));
        returnSeries.getData().add(new XYChart.Data<>("May", 50));
        returnSeries.getData().add(new XYChart.Data<>("Jun", 40));
        returnSeries.getData().add(new XYChart.Data<>("Jul", 38));
        returnSeries.getData().add(new XYChart.Data<>("Aug", 85));
        returnSeries.getData().add(new XYChart.Data<>("Sep", 80));
        returnSeries.getData().add(new XYChart.Data<>("Oct", 88));
        returnSeries.getData().add(new XYChart.Data<>("Nov", 65));
        returnSeries.getData().add(new XYChart.Data<>("Dec", 90));

        returnChart.getData().add(returnSeries);
        returnChartCard.getChildren().addAll(returnChartTitle, returnChart);

        // Log Status
        VBox logStatusCard = new VBox(15);
        logStatusCard.setPadding(new Insets(20));
        logStatusCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label logStatusTitle = new Label("Log Status");
        logStatusTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        logStatusTitle.setTextFill(Color.web("#2c3e50"));

        VBox logItems = new VBox(10);

        HBox log1 = new HBox(8);
        log1.setAlignment(Pos.CENTER_LEFT);
        Label bullet1 = new Label("•");
        bullet1.setTextFill(Color.web("#7f8c8d"));
        bullet1.setFont(Font.font("Arial", 16));
        Label logText1 = new Label("Farhan Has Been Borrow Blablabla Book");
        logText1.setFont(Font.font("Arial", 14));
        logText1.setTextFill(Color.web("#2c3e50"));
        log1.getChildren().addAll(bullet1, logText1);

        HBox log2 = new HBox(8);
        log2.setAlignment(Pos.CENTER_LEFT);
        Label bullet2 = new Label("•");
        bullet2.setTextFill(Color.web("#7f8c8d"));
        bullet2.setFont(Font.font("Arial", 16));
        Label logText2 = new Label("Boabao Has Returned The Book");
        logText2.setFont(Font.font("Arial", 14));
        logText2.setTextFill(Color.web("#2c3e50"));
        log2.getChildren().addAll(bullet2, logText2);

        logItems.getChildren().addAll(log1, log2);
        logStatusCard.getChildren().addAll(logStatusTitle, logItems);

        leftSide.getChildren().addAll(borrowChartCard, returnChartCard, logStatusCard);

        // Right Side - Fines Collected with Dropdown
        VBox rightSide = new VBox(20);
        rightSide.setPrefWidth(350);

        VBox finesCard = new VBox(15);
        finesCard.setPadding(new Insets(20));
        finesCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label finesTitle = new Label("Fines Collected");
        finesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        finesTitle.setTextFill(Color.web("#2c3e50"));

        // Months list with dropdown functionality
        VBox monthsList = new VBox(8);
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        // Sample fine amounts for each month
        String[] fineAmounts = {"Rp. 75.000", "Rp. 82.000", "Rp. 90.000", "Rp. 65.000",
                "Rp. 70.000", "Rp. 95.000", "Rp. 88.000", "Rp. 120.000",
                "Rp. 110.000", "Rp. 105.000", "Rp. 85.000", "Rp. 100.000"};

        for (int i = 0; i < months.length; i++) {
            VBox monthContainer = new VBox(0);

            // Month header (clickable)
            HBox monthRow = new HBox();
            monthRow.setAlignment(Pos.CENTER_LEFT);
            monthRow.setSpacing(10);
            monthRow.setPadding(new Insets(5, 0, 5, 0));
            monthRow.setStyle("-fx-cursor: hand;");

            Label monthLabel = new Label(months[i]);
            monthLabel.setFont(Font.font("Arial", 14));
            monthLabel.setTextFill(Color.web("#2c3e50"));
            monthLabel.setPrefWidth(100);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label arrow = new Label("↓");
            arrow.setFont(Font.font("Arial", 16));
            arrow.setTextFill(Color.web("#7f8c8d"));

            monthRow.getChildren().addAll(monthLabel, spacer, arrow);

            // Dropdown content (initially hidden)
            VBox dropdownContent = new VBox(10);
            dropdownContent.setPadding(new Insets(10, 15, 10, 15));
            dropdownContent.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 5;");
            dropdownContent.setVisible(false);
            dropdownContent.setManaged(false);

            HBox collectedRow = new HBox();
            collectedRow.setAlignment(Pos.CENTER_LEFT);
            Label collectedLabel = new Label("Collected Fines :");
            collectedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            collectedLabel.setTextFill(Color.web("#2c3e50"));
            Region spacer1 = new Region();
            HBox.setHgrow(spacer1, Priority.ALWAYS);
            Label collectedAmount = new Label(fineAmounts[i]);
            collectedAmount.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            collectedAmount.setTextFill(Color.web("#2c3e50"));
            collectedRow.getChildren().addAll(collectedLabel, spacer1, collectedAmount);

            HBox addRow = new HBox(8);
            addRow.setAlignment(Pos.CENTER_LEFT);
            Label addLabel = new Label("Add Fines :");
            addLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            addLabel.setTextFill(Color.web("#2c3e50"));
            TextField addField = new TextField();
            addField.setPromptText("Rp.");
            addField.setPrefWidth(70);
            Button addButton = new Button("Add");
            addButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10;");
            addButton.setPrefWidth(50);
            addButton.setPrefHeight(25);
            addRow.getChildren().addAll(addLabel, addField, addButton);

            dropdownContent.getChildren().addAll(collectedRow, addRow);

            monthContainer.getChildren().addAll(monthRow, dropdownContent);

            // Store references
            monthDropdowns.put(months[i], dropdownContent);
            monthArrows.put(months[i], arrow);

            // Add click event
            final String monthName = months[i];
            monthRow.setOnMouseClicked(e -> toggleDropdown(monthName));

            monthsList.getChildren().add(monthContainer);
        }

        finesCard.getChildren().addAll(finesTitle, monthsList);
        rightSide.getChildren().add(finesCard);

        contentLayout.getChildren().addAll(leftSide, rightSide);
        mainContent.getChildren().addAll(welcome, contentLayout);

        // Membuat ScrollPane untuk main content
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: #f8f9fa; -fx-background: #f8f9fa;");

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(scrollPane);
    }

    private void toggleDropdown(String monthName) {
        VBox dropdown = monthDropdowns.get(monthName);
        Label arrow = monthArrows.get(monthName);

        if (dropdown.isVisible()) {
            // Close dropdown
            dropdown.setVisible(false);
            dropdown.setManaged(false);
            arrow.setText("↓");
        } else {
            // Open dropdown
            dropdown.setVisible(true);
            dropdown.setManaged(true);
            arrow.setText("↑");
        }
    }
}