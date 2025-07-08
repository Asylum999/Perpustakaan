package com.library.View.Admin;

import com.library.Controller.Navigator;
import com.library.Model.Book;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.library.Model.Connections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminBookManagement extends BorderPane {
    private Connections connections = new Connections();
    private TableView<Book> bookTable;
    private ObservableList<Book> bookData;
    private TextField isbnField, titleField, authorField, categoryField;

    public AdminBookManagement() {
        // Initialize the book data
        bookData = FXCollections.observableArrayList(connections.getAllBooks());

        // === Sidebar (Same as Dashboard) ===
        VBox sidebar = createSidebar();

        // === Main Content - Book Management ===
        VBox mainContent = createMainContent();

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSidebar() {
        //===Sidebar===
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
                // Reset semua tombol ke style default
                for (Button btn : allMenuButtons) {
                    btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
                }
                // Highlight tombol yang diklik
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");

                // Aksi pindah halaman
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
                        handleLogout();
                        break;
                    default:
                        System.out.println("Menu belum ditangani: " + menuItems[index]);
                }
            });

            allMenuButtons.add(menuButton);
            menuBox.getChildren().add(menuButton);

            // Highlight "Book Management"
            if (menuItems[i].equals("Book Management")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }

        return sidebar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Header with title and Add Book button
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label pageTitle = new Label("Book Management");
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        pageTitle.setTextFill(Color.web("#2c3e50"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addBookBtn = new Button("Add Book");
        addBookBtn.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 20;");
        ImageView addIcon = new ImageView();
        addBookBtn.setGraphic(addIcon);

        // Add Book Modal Action
        addBookBtn.setOnAction(e -> showAddBookModal());

        header.getChildren().addAll(pageTitle, spacer, addBookBtn);

        // Content Layout
        HBox contentLayout = new HBox(20);
        contentLayout.setAlignment(Pos.TOP_LEFT);

        // Left Side - Table
        VBox leftSide = new VBox(15);
        leftSide.setPrefWidth(800);

        // Table
        bookTable = createTable();
        leftSide.getChildren().add(bookTable);

        // Right Side - Search Form
        VBox rightSide = new VBox(15);
        rightSide.setPrefWidth(300);

        VBox searchCard = new VBox(15);
        searchCard.setPadding(new Insets(20));
        searchCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        // Initialize search fields
        isbnField = new TextField();
        titleField = new TextField();
        authorField = new TextField();
        categoryField = new TextField();

        // ISBN Field
        Label isbnLabel = new Label("ISBN :");
        isbnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        isbnLabel.setTextFill(Color.web("#2c3e50"));
        isbnField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Title Field
        Label titleLabel = new Label("Title :");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        titleField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Author Field
        Label authorLabel = new Label("Author :");
        authorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        authorLabel.setTextFill(Color.web("#2c3e50"));
        authorField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Category Field
        Label categoryLabel = new Label("Category :");
        categoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        categoryLabel.setTextFill(Color.web("#2c3e50"));
        categoryField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Search Button
        Button searchBtn = new Button("Search");
        searchBtn.setPrefWidth(270);
        searchBtn.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        searchBtn.setOnAction(e -> searchBooks());

        searchCard.getChildren().addAll(
                isbnLabel, isbnField,
                titleLabel, titleField,
                authorLabel, authorField,
                categoryLabel, categoryField,
                searchBtn
        );

        rightSide.getChildren().add(searchCard);

        contentLayout.getChildren().addAll(leftSide, rightSide);
        mainContent.getChildren().addAll(header, contentLayout);

        return mainContent;
    }

    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Do you really want to logout?");
        alert.setContentText("Press OK to proceed or Cancel to stay.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Navigator.showLogin(); // back to login screen
            }
        });
    }

    private void searchBooks() {
        String isbn = isbnField.getText().trim().toLowerCase();
        String title = titleField.getText().trim().toLowerCase();
        String author = authorField.getText().trim().toLowerCase();
        String category = categoryField.getText().trim().toLowerCase();

        List<Book> filteredBooks = new ArrayList<>();

        for (Book book : connections.getAllBooks()) {
            boolean matches = true;

            if (!isbn.isEmpty() && !book.getIsbn().toLowerCase().contains(isbn)) {
                matches = false;
            }
            if (!title.isEmpty() && !book.getTitle().toLowerCase().contains(title)) {
                matches = false;
            }
            if (!author.isEmpty() && !book.getAuthor().toLowerCase().contains(author)) {
                matches = false;
            }
            if (!category.isEmpty() && !book.getCategory().toLowerCase().contains(category)) {
                matches = false;
            }

            if (matches) {
                filteredBooks.add(book);
            }
        }

        bookData.setAll(filteredBooks);
    }

    private TableView<Book> createTable() {
        TableView<Book> table = new TableView<>();
        table.setPrefHeight(500);
        table.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        table.setItems(bookData);

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

        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setPrefWidth(100);
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setPrefWidth(200);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setPrefWidth(150);
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setPrefWidth(90);
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
                        String formattedStatus = item.substring(0, 1).toUpperCase() +
                                item.substring(1).toLowerCase();

                        Label statusLabel = new Label(formattedStatus);
                        statusLabel.setPadding(new Insets(3, 12, 3, 12));
                        statusLabel.setStyle("-fx-background-radius: 10px; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");

                        if (item.equalsIgnoreCase("Available")) {
                            statusLabel.setStyle(statusLabel.getStyle() + "-fx-background-color: #28a745;");
                        } else if (item.equalsIgnoreCase("Borrowed")) {
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
                private final Button deleteBtn = new Button();
                private final Button editBtn = new Button();
                private final HBox buttonBox = new HBox(5);

                {
                    // Styling Delete Button
                    deleteBtn.setStyle("-fx-background-color: #dc3545; -fx-pref-width: 25; -fx-pref-height: 25;");
                    ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/images/deletebutton.png").toExternalForm()));
                    deleteIcon.setFitWidth(20);
                    deleteIcon.setFitHeight(20);
                    deleteBtn.setGraphic(deleteIcon);

                    // Styling Edit Button
                    editBtn.setStyle("-fx-background-color: #007bff; -fx-pref-width: 25; -fx-pref-height: 25;");
                    ImageView editIcon = new ImageView(new Image(getClass().getResource("/images/editbutton.png").toExternalForm()));
                    editIcon.setFitWidth(20);
                    editIcon.setFitHeight(20);
                    editBtn.setGraphic(editIcon);

                    // Action for Delete Button
                    deleteBtn.setOnAction(event -> {
                        Book selectedBook = getTableView().getItems().get(getIndex());
                        if (selectedBook != null) {
                            showDeleteConfirmation(selectedBook);
                        }
                    });

                    // Action for Edit Button
                    editBtn.setOnAction(event -> {
                        Book selectedBook = getTableView().getItems().get(getIndex());
                        if (selectedBook != null) {
                            showEditBookModal(selectedBook);
                        }
                    });

                    buttonBox.getChildren().addAll(deleteBtn, editBtn);
                    buttonBox.setAlignment(Pos.CENTER);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : buttonBox);
                }
            };
        });

        table.getColumns().addAll(noCol, isbnCol, titleCol, authorCol, categoryCol, statusCol, actionCol);

        return table;
    }

    private void showAddBookModal() {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Add New Book");
        dialog.setHeaderText("Enter Book Information");
        dialog.initModality(Modality.APPLICATION_MODAL);

        // Create form content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));

        // Form fields
        TextField isbnField = new TextField();
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField categoryField = new TextField();

        // Add fields to grid
        grid.add(new Label("ISBN:"), 0, 0);
        grid.add(isbnField, 1, 0);
        grid.add(new Label("Title:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Author:"), 0, 2);
        grid.add(authorField, 1, 2);
        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType addButtonType = new ButtonType("Add Book", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Style the buttons
        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold;");

        Node cancelButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");

        // Set dialog size
        dialog.getDialogPane().setPrefSize(400, 350);

        // Convert result to Book when Add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                // Validate fields
                if (isbnField.getText().trim().isEmpty() ||
                        titleField.getText().trim().isEmpty() ||
                        authorField.getText().trim().isEmpty() ||
                        categoryField.getText().trim().isEmpty()) {

                    showAlert("Error", "Please fill in all fields!", Alert.AlertType.ERROR);
                    return null;
                }

                // Create new book with Available status
                return new Book(
                        isbnField.getText().trim(),
                        titleField.getText().trim(),
                        authorField.getText().trim(),
                        categoryField.getText().trim(),
                        "Available"
                );
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();
        result.ifPresent(book -> {
            // Show confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Add Book");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to add this book?\n\n" +
                    "ISBN: " + book.getIsbn() + "\n" +
                    "Title: " + book.getTitle());
            styleAlert(confirmAlert);

            Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
            if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                // Add to database
                connections.addBook(book);

                // Add to table and refresh
                bookData.add(book);
                bookTable.refresh();

                showAlert("Success", "Book has been successfully added!", Alert.AlertType.INFORMATION);
            }
        });
    }

    private void showEditBookModal(Book selectedBook) { // Changed parameter name
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Edit Book");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().setStyle("-fx-background-color: white;");

        // Create modal form fields
        TextField isbnField = new TextField(selectedBook.getIsbn());
        isbnField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField titleField = new TextField(selectedBook.getTitle());
        titleField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField authorField = new TextField(selectedBook.getAuthor());
        authorField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField categoryField = new TextField(selectedBook.getCategory());
        categoryField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Create form layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));

        grid.add(new Label("ISBN:"), 0, 0);
        grid.add(isbnField, 1, 0);
        grid.add(new Label("Title:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Author:"), 0, 2);
        grid.add(authorField, 1, 2);
        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType saveButtonType = new ButtonType("Save Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Style buttons
        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold;");

        Node cancelButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");

        // Process result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                // Validate required fields
                if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty()) {
                    showAlert("Error", "Title and Author are required fields!", Alert.AlertType.ERROR);
                    return null;
                }

                // Create updated book
                return new Book(
                        isbnField.getText().trim(),
                        titleField.getText().trim(),
                        authorField.getText().trim(),
                        categoryField.getText().trim(),
                        selectedBook.getStatus() // Keep the original status
                );
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();
        result.ifPresent(updatedBook -> {
            // Show confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Changes");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to save these changes?");
            styleAlert(confirmAlert);

            Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
            if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                // Update in database
                connections.updateBook(updatedBook);

                // Update in table - FIXED: Now using the correct ObservableList
                int index = bookData.indexOf(selectedBook); // bookData is the ObservableList
                if (index >= 0) {
                    bookData.set(index, updatedBook); // Now calling set on ObservableList
                    bookTable.refresh();
                }

                showAlert("Success", "Book has been successfully updated!", Alert.AlertType.INFORMATION);
            }
        });
    }

    private void showDeleteConfirmation(Book bookToDelete) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Book");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this book?\n\n" +
                "Title: " + bookToDelete.getTitle() + "\n" +
                "ISBN: " + bookToDelete.getIsbn());

        // Apply consistent styling
        styleAlert(confirmAlert);

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete from database
            connections.deleteBook(bookToDelete.getIsbn());

            // Remove from table
            bookData.remove(bookToDelete);

            showAlert("Success", "Book has been deleted successfully!", Alert.AlertType.INFORMATION);
        }
    }

    // Method to show alerts/notifications
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert
        alert.getDialogPane().setStyle("-fx-background-color: white;");

        // Style the button
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        if (alertType == Alert.AlertType.INFORMATION) {
            okButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        } else if (alertType == Alert.AlertType.ERROR) {
            okButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        alert.showAndWait();
    }

    private void styleAlert(Alert alert) {
        alert.getDialogPane().setStyle("-fx-background-color: white;");

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        if (okButton != null) {
            okButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        if (cancelButton != null) {
            cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");
        }
    }
}