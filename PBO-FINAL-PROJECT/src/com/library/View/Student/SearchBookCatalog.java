package com.library.View.Student;

import com.library.Controller.Navigator;
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

import java.util.ArrayList;
import java.util.List;

public class SearchBookCatalog extends BorderPane {

    private TableView<BookData> table;

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
        table = createTable();

        mainContent.getChildren().addAll(title, searchForm, table);

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox();
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

        // Menu section
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
                        Navigator.showStudentDashboard("Nama Mahasiswa");
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

            if (menuItems[i].equals("Search Book")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }
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
        isbnField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
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
        titleField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
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
        authorField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
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
        categoryField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
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

        // Define columns with adjusted widths
        TableColumn<BookData, String> noCol = new TableColumn<>("No");
        noCol.setPrefWidth(40);
        noCol.setStyle("-fx-alignment: CENTER;");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<BookData, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setPrefWidth(150);
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<BookData, String> titleCol = new TableColumn<>("Title");
        titleCol.setPrefWidth(250);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookData, String> authorCol = new TableColumn<>("Author");
        authorCol.setPrefWidth(150);
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookData, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setPrefWidth(120);
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<BookData, String> statusCol = new TableColumn<>("Status");
        statusCol.setPrefWidth(100);
        statusCol.setStyle("-fx-alignment: CENTER;");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Improved status column
        statusCol.setCellFactory(column -> {
            return new TableCell<BookData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Label statusLabel = new Label(item);
                        statusLabel.setPadding(new Insets(3, 12, 3, 12));
                        statusLabel.setStyle("-fx-background-radius: 10px; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");

                        if (item.equals("Available")) {
                            statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #28a745;");
                        } else if (item.equals("Borrowed")) {
                            statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #dc3545;");
                        } else {
                            statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #6c757d;");
                        }
                        setGraphic(statusLabel);
                        setText(null);
                    }
                }
            };
        });

        // Action column
        TableColumn<BookData, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(100);
        actionCol.setStyle("-fx-alignment: CENTER;");
        actionCol.setCellFactory(column -> {
            return new TableCell<BookData, Void>() {
                private final Button borrowButton = new Button("Borrow");
                private final HBox container = new HBox();

                {
                    borrowButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 12px; " +
                            "-fx-background-radius: 5px; -fx-cursor: hand; -fx-padding: 5 10;");
                    borrowButton.setOnAction(event -> {
                        BookData book = getTableView().getItems().get(getIndex());
                        handleBorrowAction(book);
                    });

                    container.setAlignment(Pos.CENTER);
                    container.getChildren().add(borrowButton);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        BookData book = getTableView().getItems().get(getIndex());
                        if (book.getStatus().equals("Available")) {
                            setGraphic(container);
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
                new BookData("1", "978-128-409-07-14", "Java Programming: From Beginner to Expert", "James Wan", "Programming", "Available"),
                new BookData("2", "810-541-748-19-15", "Artificial Intelligence Fundamentals", "Albert Einstein", "AI", "Borrowed"),
                new BookData("3", "978-013-468-599-1", "Effective Java", "Joshua Bloch", "Programming", "Available"),
                new BookData("4", "978-149-195-035-7", "Designing Data-Intensive Applications", "Martin Kleppmann", "Database", "Available")
        );

        table.setItems(data);
        return table;
    }

    private void handleBorrowAction(BookData book) {
        // Create confirmation dialog
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Borrow");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Are you sure you want to borrow:\n" +
                book.getTitle() + " by " + book.getAuthor() + "?");

        // Style the dialog
        confirmDialog.getDialogPane().setStyle("-fx-background-color: white;");
        Button okButton = (Button) confirmDialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");

        // Show dialog and wait for response
        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Update book status
                book.setStatus("Borrowed");

                // Refresh table
                table.refresh();

                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Book borrowed successfully!");
                successAlert.show();
            }
        });
    }

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