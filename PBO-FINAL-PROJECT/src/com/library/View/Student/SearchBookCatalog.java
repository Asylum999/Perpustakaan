package com.library.View.Student;

import com.library.Controller.Navigator;
import com.library.Model.Book;
import com.library.Model.Borrowing;
import com.library.Model.Connections;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchBookCatalog extends BorderPane {

    private TableView<Book> table;

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

        searchButton.setOnAction(e -> {
            String isbnInput = isbnField.getText().toLowerCase().trim();
            String titleInput = titleField.getText().toLowerCase().trim();
            String authorInput = authorField.getText().toLowerCase().trim();
            String categoryInput = categoryField.getText().toLowerCase().trim();

            Connections conn = new Connections();
            List<Book> allBooks = conn.getAllBooks();

            List<Book> filtered = new ArrayList<>();
            for (Book book : allBooks) {
                boolean matchIsbn = isbnInput.isEmpty() || book.getIsbn().toLowerCase().contains(isbnInput);
                boolean matchTitle = titleInput.isEmpty() || book.getTitle().toLowerCase().contains(titleInput);
                boolean matchAuthor = authorInput.isEmpty() || book.getAuthor().toLowerCase().contains(authorInput);
                boolean matchCategory = categoryInput.isEmpty() || book.getCategory().toLowerCase().contains(categoryInput);

                if (matchIsbn && matchTitle && matchAuthor && matchCategory) {
                    filtered.add(book);
                }
            }

            table.setItems(FXCollections.observableArrayList(filtered));
        });


        searchForm.getChildren().addAll(leftForm, rightForm);
        return searchForm;
    }

    private TableView<Book> createTable() {
        TableView<Book> table = new TableView<>();
        table.setPrefHeight(400);
        table.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5px;");

        // Define columns with adjusted widths
        TableColumn<Book, String> noCol = new TableColumn<>("No");
        noCol.setPrefWidth(40);
        noCol.setStyle("-fx-alignment: CENTER;");
        noCol.setCellFactory(column -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && !empty) {
                    setText(String.valueOf(this.getIndex() + 1));
                } else {
                    setText(null);
                }
            }
        });

        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));

        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setPrefWidth(150);
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setPrefWidth(250);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setPrefWidth(150);
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setPrefWidth(120);
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, String> statusCol = new TableColumn<>("Status");
        statusCol.setPrefWidth(100);
        statusCol.setStyle("-fx-alignment: CENTER;");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Improved status column
        statusCol.setCellFactory(column -> {
            return new TableCell<Book, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Capitalize first letter (e.g. "available" -> "Available")
                        String capitalized = item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase();
                        Label statusLabel = new Label(capitalized);
                        statusLabel.setPadding(new Insets(3, 12, 3, 12));
                        statusLabel.setStyle("-fx-background-radius: 10px; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");

                        if (capitalized.equals("Available")) {
                            statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #28a745;");
                        } else if (capitalized.equals("Borrowed")) {
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
        TableColumn<Book, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(100);
        actionCol.setStyle("-fx-alignment: CENTER;");
        actionCol.setCellFactory(column -> {
            return new TableCell<Book, Void>() {
                private final Button borrowButton = new Button("Borrow");
                private final HBox container = new HBox();

                {
                    borrowButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 12px; " +
                            "-fx-background-radius: 5px; -fx-cursor: hand; -fx-padding: 5 10;");
                    borrowButton.setOnAction(event -> {
                        Book book = getTableView().getItems().get(getIndex());
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
                        Book book = getTableView().getItems().get(getIndex());
                        if ("available".equalsIgnoreCase(book.getStatus())) {
                            setGraphic(container);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
        });

        table.getColumns().addAll(noCol, isbnCol, titleCol, authorCol, categoryCol, statusCol, actionCol);

        Connections conn = new Connections();
        List<Book> booksFromCsv = conn.getAllBooks();
        int no = 1;
        for (Book book : booksFromCsv) {
            book.setNo(String.valueOf(no++));
        }

        ObservableList<Book> data = FXCollections.observableArrayList(booksFromCsv);
        table.setItems(data);
        return table;
    }

    private void handleBorrowAction(Book book) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Borrow");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Are you sure you want to borrow:\n" +
                book.getTitle() + " by " + book.getAuthor() + "?");

        confirmDialog.getDialogPane().setStyle("-fx-background-color: white;");
        Button okButton = (Button) confirmDialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Connections conn = new Connections();

                // Get current user
                String studentId = Navigator.getCurrentUser().getId(); // Pastikan ini tersedia

                // Generate borrowId (misalnya pakai timestamp + isbn)
                String borrowId = "BRW-" + System.currentTimeMillis();

                // Set tanggal hari ini dan dueDate (misal 7 hari)
                LocalDate borrowDate = LocalDate.now();
                LocalDate dueDate = borrowDate.plusDays(7);

                Borrowing borrowing = new Borrowing(
                        borrowId,
                        studentId,
                        book.getIsbn(),
                        borrowDate,
                        dueDate,
                        "Borrowed"
                );

                conn.addBorrowing(borrowing);                // Tambahkan ke Borrowing.csv
                conn.updateBookStatus(book.getIsbn(), "Borrowed"); // Ubah status buku

                // Update tampilan
                book.setStatus("Borrowed");
                table.refresh();

                // Sukses
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Book borrowed successfully!");
                successAlert.show();
            }
        });
    }

}