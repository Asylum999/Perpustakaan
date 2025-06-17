package com.library.View.Student;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchBookCatalog extends BorderPane {

    public SearchBookCatalog() {
        // === Sidebar ===
        VBox sidebar = createSidebar();

        // === Main Content ===
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title
        Label title = new Label("Search Book Catalog");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#2c3e50"));

        // Search Form
        HBox searchForm = createSearchForm();

        // Table
        TableView<BookData> table = createTable();

        mainContent.getChildren().addAll(title, searchForm, table);

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSidebar() {
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

        Label labelUMM = new Label("UMM\nLIBRARY");
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

            // Highlight Search Book menu item
            if (menuItems[i].equals("Search Book")) {
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

    private HBox createSearchForm() {
        HBox searchForm = new HBox(20);
        searchForm.setAlignment(Pos.CENTER_LEFT);

        // Left side - search fields
        VBox leftForm = new VBox(15);
        leftForm.setPrefWidth(400);

        // ISBN field
        HBox isbnBox = new HBox(10);
        isbnBox.setAlignment(Pos.CENTER_LEFT);
        Label isbnLabel = new Label("Isbn");
        isbnLabel.setPrefWidth(80);
        isbnLabel.setFont(Font.font("Arial", 14));
        TextField isbnField = new TextField();
        isbnField.setPrefWidth(250);
        isbnField.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5px; -fx-padding: 8px;");
        Label isbnColon = new Label(":");
        isbnBox.getChildren().addAll(isbnLabel, isbnColon, isbnField);

        // Title field
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label("Title");
        titleLabel.setPrefWidth(80);
        titleLabel.setFont(Font.font("Arial", 14));
        TextField titleField = new TextField();
        titleField.setPrefWidth(250);
        titleField.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5px; -fx-padding: 8px;");
        Label titleColon = new Label(":");
        titleBox.getChildren().addAll(titleLabel, titleColon, titleField);

        // Author field
        HBox authorBox = new HBox(10);
        authorBox.setAlignment(Pos.CENTER_LEFT);
        Label authorLabel = new Label("Author");
        authorLabel.setPrefWidth(80);
        authorLabel.setFont(Font.font("Arial", 14));
        TextField authorField = new TextField();
        authorField.setPrefWidth(250);
        authorField.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5px; -fx-padding: 8px;");
        Label authorColon = new Label(":");
        authorBox.getChildren().addAll(authorLabel, authorColon, authorField);

        // Category field
        HBox categoryBox = new HBox(10);
        categoryBox.setAlignment(Pos.CENTER_LEFT);
        Label categoryLabel = new Label("Category");
        categoryLabel.setPrefWidth(80);
        categoryLabel.setFont(Font.font("Arial", 14));
        TextField categoryField = new TextField();
        categoryField.setPrefWidth(250);
        categoryField.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5px; -fx-padding: 8px;");
        Label categoryColon = new Label(":");
        categoryBox.getChildren().addAll(categoryLabel, categoryColon, categoryField);

        leftForm.getChildren().addAll(isbnBox, titleBox, authorBox, categoryBox);

        // Right side - search button
        VBox rightForm = new VBox();
        rightForm.setAlignment(Pos.CENTER);
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(100, 40);
        searchButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-cursor: hand;");
        rightForm.getChildren().add(searchButton);

        searchForm.getChildren().addAll(leftForm, rightForm);
        return searchForm;
    }

    private TableView<BookData> createTable() {
        TableView<BookData> table = new TableView<>();
        table.setPrefHeight(400);
        table.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5px;");

        // Define columns
        TableColumn<BookData, String> noCol = new TableColumn<>("No");
        noCol.setPrefWidth(50);
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<BookData, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setPrefWidth(120);
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<BookData, String> titleCol = new TableColumn<>("TITLE");
        titleCol.setPrefWidth(200);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookData, String> authorCol = new TableColumn<>("AUTHOR");
        authorCol.setPrefWidth(120);
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookData, String> categoryCol = new TableColumn<>("CATEGORY");
        categoryCol.setPrefWidth(100);
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<BookData, String> statusCol = new TableColumn<>("STATUS");
        statusCol.setPrefWidth(80);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Custom cell factory for status column to show colored labels
        statusCol.setCellFactory(column -> {
            return new TableCell<BookData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        Label statusLabel = new Label(item);
                        statusLabel.setPadding(new Insets(3, 8, 3, 8));
                        statusLabel.setStyle("-fx-background-radius: 10px; -fx-text-fill: white; -fx-font-size: 10px;");

                        if (item.equals("Available")) {
                            statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #28a745;");
                        } else if (item.equals("Borrowed")) {
                            statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #dc3545;");
                        }

                        setGraphic(statusLabel);
                    }
                }
            };
        });

        // Action column for borrow button
        TableColumn<BookData, Void> actionCol = new TableColumn<>("ACTION");
        actionCol.setPrefWidth(80);
        actionCol.setCellFactory(column -> {
            return new TableCell<BookData, Void>() {
                private final Button borrowButton = new Button("Borrow");

                {
                    borrowButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 10px; -fx-background-radius: 5px; -fx-cursor: hand;");
                    borrowButton.setPadding(new Insets(3, 8, 3, 8));
                    borrowButton.setOnAction(event -> {
                        BookData book = getTableView().getItems().get(getIndex());
                        // Handle borrow action here
                        System.out.println("Borrowing book: " + book.getTitle());
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        BookData book = getTableView().getItems().get(getIndex());
                        if (book.getStatus().equals("Available")) {
                            setGraphic(borrowButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
        });

        table.getColumns().addAll(noCol, isbnCol, titleCol, authorCol, categoryCol, statusCol, actionCol);

        // Sample data
        ObservableList<BookData> data = FXCollections.observableArrayList(
                new BookData("1", "978-128-409-07-14", "Java Programming", "James Wan", "Programming", "Available"),
                new BookData("2", "810-541-748-19-15", "AI for Beginner", "Albert Einstein", "AI", "Borrowed")
        );

        // Add sample rows
        for (int i = 3; i <= 15; i++) {
            data.add(new BookData(String.valueOf(i), "Sample", "Sample", "Sample", "Sample", "Sample"));
        }

        table.setItems(data);
        return table;
    }

    // Data class for table
    public static class BookData {
        private String no;
        private String isbn;
        private String title;
        private String author;
        private String category;
        private String status;

        public BookData(String no, String isbn, String title, String author, String category, String status) {
            this.no = no;
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.category = category;
            this.status = status;
        }

        // Getters
        public String getNo() { return no; }
        public String getIsbn() { return isbn; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getCategory() { return category; }
        public String getStatus() { return status; }

        // Setters
        public void setNo(String no) { this.no = no; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
        public void setTitle(String title) { this.title = title; }
        public void setAuthor(String author) { this.author = author; }
        public void setCategory(String category) { this.category = category; }
        public void setStatus(String status) { this.status = status; }
    }
}