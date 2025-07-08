package com.library.View.Admin;

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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;

import java.time.format.TextStyle;
import java.util.*;
import java.time.Month;
import java.util.stream.Collectors;

public class AdminHomeDashboard extends BorderPane {
    private Map<String, VBox> monthDropdowns = new HashMap<>();
    private Map<String, Label> monthArrows = new HashMap<>();

    public AdminHomeDashboard(String nama) {
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
        labelUMM.setWrapText(true);
        headerSection.getChildren().addAll(logo, labelUMM);

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
                for (Button btn : allMenuButtons) {
                    btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
                }
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");

                switch (menuItems[index]) {
                    case "Dashboard":
                        Navigator.showAdminHomeDashboard(Navigator.getCurrentUser().getUsername());
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
                                Navigator.showLogin();
                            }
                        });
                        break;
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
        Label welcome = new Label("Welcome, " + nama + "!");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcome.setTextFill(Color.web("#2c3e50"));

        HBox contentLayout = new HBox(20);
        contentLayout.setAlignment(Pos.TOP_LEFT);
        VBox leftSide = new VBox(20);
        leftSide.setPrefWidth(650);

        VBox borrowChartCard = new VBox(15);
        borrowChartCard.setPadding(new Insets(20));
        borrowChartCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label borrowChartTitle = new Label("Book Borrowing Statistics");
        borrowChartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        borrowChartTitle.setTextFill(Color.web("#2c3e50"));

        CategoryAxis borrowXAxis = new CategoryAxis();
        NumberAxis borrowYAxis = new NumberAxis();
        borrowYAxis.setLabel("Books Borrowed");
        LineChart<String, Number> borrowChart = new LineChart<>(borrowXAxis, borrowYAxis);
        borrowChart.setCreateSymbols(true);
        borrowChart.setLegendVisible(false);
        borrowChart.setPrefHeight(250);

        XYChart.Series<String, Number> borrowSeries = new XYChart.Series<>();
        Connections conn = new Connections();
        Map<String, Integer> stats = conn.getMonthlyBorrowingStats();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        for (String month : months) {
            borrowSeries.getData().add(new XYChart.Data<>(month, stats.getOrDefault(month, 0)));
        }

        borrowChart.getData().add(borrowSeries);
        borrowChartCard.getChildren().addAll(borrowChartTitle, borrowChart);
        leftSide.getChildren().add(borrowChartCard);

        // Return chart
        VBox returnChartCard = new VBox(15);
        returnChartCard.setPadding(new Insets(20));
        returnChartCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Label returnChartTitle = new Label("Book Return Statistics");
        returnChartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        returnChartTitle.setTextFill(Color.web("#2c3e50"));

        CategoryAxis returnXAxis = new CategoryAxis();
        NumberAxis returnYAxis = new NumberAxis();
        returnYAxis.setLabel("Books Returned");
        LineChart<String, Number> returnChart = new LineChart<>(returnXAxis, returnYAxis);
        returnChart.setCreateSymbols(true);
        returnChart.setLegendVisible(false);
        returnChart.setPrefHeight(250);

        XYChart.Series<String, Number> returnSeries = new XYChart.Series<>();
        Map<String, Integer> returnStats = conn.getAllBorrowings().stream()
                .filter(b -> "Returned".equalsIgnoreCase(b.getStatus()))
                .collect(Collectors.groupingBy(
                        b -> b.getBorrowDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                        Collectors.reducing(0, e -> 1, Integer::sum)));

        for (String month : months) {
            returnSeries.getData().add(new XYChart.Data<>(month, returnStats.getOrDefault(month, 0)));
        }

        returnChart.getData().add(returnSeries);
        returnChartCard.getChildren().addAll(returnChartTitle, returnChart);
        leftSide.getChildren().add(returnChartCard);

        VBox reportCard = new VBox(10);
        reportCard.setPadding(new Insets(20));
        reportCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Label reportLabel = new Label("Currently Borrowed Books Report");
        reportLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        reportLabel.setTextFill(Color.web("#2c3e50"));

        List<Borrowing> borrowings = conn.getAllBorrowings();
        long borrowedCount = borrowings.stream().filter(b -> "Borrowed".equalsIgnoreCase(b.getStatus())).count();

        Label reportText = new Label("Total books currently borrowed: " + borrowedCount);
        reportText.setFont(Font.font("Arial", 14));
        reportText.setTextFill(Color.web("#34495e"));

        reportCard.getChildren().addAll(reportLabel, reportText);
        leftSide.getChildren().add(reportCard);

        VBox rightSide = new VBox(20);
        rightSide.setPrefWidth(350);
        VBox finesCard = new VBox(15);
        finesCard.setPadding(new Insets(20));
        finesCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label finesTitle = new Label("Fines Collected");
        finesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        finesTitle.setTextFill(Color.web("#2c3e50"));

        VBox monthsList = new VBox(8);
        String[] fineMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        for (String month : fineMonths) {
            VBox monthContainer = new VBox(0);
            HBox monthRow = new HBox();
            monthRow.setAlignment(Pos.CENTER_LEFT);
            monthRow.setSpacing(10);
            Label monthLabel = new Label(month);
            monthLabel.setFont(Font.font("Arial", 14));
            monthLabel.setTextFill(Color.web("#2c3e50"));
            Label arrow = new Label("↓");
            arrow.setFont(Font.font("Arial", 16));
            arrow.setTextFill(Color.web("#7f8c8d"));
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            monthRow.getChildren().addAll(monthLabel, spacer, arrow);

            VBox dropdown = new VBox(10);
            dropdown.setPadding(new Insets(10));
            dropdown.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");
            dropdown.setVisible(false);
            dropdown.setManaged(false);

            Label collected = new Label("Collected Fines :");
            Label collectedAmount = new Label("Rp. 0");
            HBox collectedRow = new HBox(collected, new Region(), collectedAmount);
            HBox.setHgrow(collectedRow.getChildren().get(1), Priority.ALWAYS);

            TextField addField = new TextField();
            addField.setPromptText("Rp.");
            Button addButton = new Button("Add");
            addButton.setOnAction(ev -> {
                try {
                    int added = Integer.parseInt(addField.getText().replaceAll("[^0-9]", ""));
                    int current = Integer.parseInt(collectedAmount.getText().replaceAll("[^0-9]", ""));
                    collectedAmount.setText("Rp. " + (added + current));
                    addField.clear();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                }
            });

            HBox addRow = new HBox(8, new Label("Add Fines:"), addField, addButton);
            dropdown.getChildren().addAll(collectedRow, addRow);

            monthRow.setOnMouseClicked(e -> toggleDropdown(month, dropdown, arrow));

            monthContainer.getChildren().addAll(monthRow, dropdown);
            monthsList.getChildren().add(monthContainer);
        }

        finesCard.getChildren().addAll(finesTitle, monthsList);
        rightSide.getChildren().add(finesCard);

        contentLayout.getChildren().addAll(leftSide, rightSide);
        mainContent.getChildren().addAll(welcome, contentLayout);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: #f8f9fa; -fx-background: #f8f9fa;");

        this.setLeft(sidebar);
        this.setCenter(scrollPane);
    }

    private void toggleDropdown(String name, VBox dropdown, Label arrow) {
        boolean open = dropdown.isVisible();
        dropdown.setVisible(!open);
        dropdown.setManaged(!open);
        arrow.setText(open ? "↓" : "↑");
    }
}
