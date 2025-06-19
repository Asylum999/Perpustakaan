package com.library.View.Admin;

import com.library.Controller.Navigator;
import com.library.Model.Admin;
import com.library.Model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminBookManagement extends BorderPane {

    private final Admin admin;
    private TableView<BookTableData> bookTable;

    public static class BookTableData {
        private String no;
        private final Book book;
        private String status;

        public BookTableData(String no, Book book, String status) {
            this.no = no;
            this.book = book;
            this.status = status;
        }

        public String getNo() { return no; }
        public void setNo(String no) { this.no = no; }

        public String getIsbn() { return book.getIsbn(); }
        public String getTitle() { return book.getTitle(); }
        public String getAuthor() { return book.getAuthor(); }
        public String getCategory() { return book.getCategory(); }
        public String getStatus() { return status; }
        public Book getBook() { return book; }
        public void setStatus(String status) { this.status = status; }
    }


    public AdminBookManagement(Admin admin) {
        this.admin = admin;

        // Sidebar
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
        headerSection.getChildren().addAll(logo, labelUMM);

        String[] menuItems = {"Dashboard", "Book Management", "User Management", "Profile", "Logout"};
        String[] menuIcons = {"/images/Home.png", "/images/Search.png", "/images/userManagement.png", "/images/Profile.png", "/images/Logout.png"};

        VBox menuBox = new VBox();
        menuBox.setPadding(new Insets(10, 0, 0, 0));
        menuBox.setSpacing(5);
        sidebar.getChildren().addAll(headerSection, menuBox);

        List<Button> allMenuButtons = new ArrayList<>();

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
                        System.out.println("Unhandled: " + menuItems[index]);
                }
            });

            allMenuButtons.add(menuButton);
            menuBox.getChildren().add(menuButton);

            if (menuItems[i].equals("Book Management")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }

        // === Main Content - Book Management ===
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
        // You can add an icon here if you have one
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
        bookTable = new TableView<>();
        bookTable.setPrefHeight(500);
        bookTable.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        // Table Columns
        TableColumn<BookTableData, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));
        noCol.setPrefWidth(30);

        TableColumn<BookTableData, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnCol.setPrefWidth(150);

        TableColumn<BookTableData, String> titleCol = new TableColumn<>("TITLE");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(150);

        TableColumn<BookTableData, String> authorCol = new TableColumn<>("AUTHOR");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setPrefWidth(150);

        TableColumn<BookTableData, String> categoryCol = new TableColumn<>("CATEGORY");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setPrefWidth(120);

        TableColumn<BookTableData, String> statusCol = new TableColumn<>("STATUS");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(81);

        // Add action buttons to each row
        TableColumn<BookTableData, Void> actionCol = new TableColumn<>("");
        actionCol.setPrefWidth(100);

        actionCol.setCellFactory(col -> new TableCell<BookTableData, Void>() {
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
                    BookTableData selectedBook = getTableView().getItems().get(getIndex());
                    if (selectedBook != null) {
                        showDeleteConfirmation(selectedBook);
                    }
                });

                // Action for Edit Button (existing)
                editBtn.setOnAction(event -> {
                    BookTableData selectedBook = getTableView().getItems().get(getIndex());
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
        });

        bookTable.getColumns().addAll(noCol, isbnCol, titleCol, authorCol, categoryCol, statusCol, actionCol);

        // Sample data
        ObservableList<BookTableData> bookData = FXCollections.observableArrayList(
                new BookTableData("1", new Book("978-132-4567-09-34", "Java Programming", "James wan", "Programming"), "Available"),
                new BookTableData("2", new Book("890-543-1245-16-13", "AI for beginner", "Albert Einstein", "AI"), "Borrowed")
        );

        // Add sample rows
        for (int i = 3; i <= 15; i++) {
            bookData.add(new BookTableData(String.valueOf(i), new Book("Sample", "Sample", "Sample", "Sample"), "Sample"));
        }

        bookTable.setItems(bookData);
        leftSide.getChildren().add(bookTable);

        // Right Side - Search Form
        VBox rightSide = new VBox(15);
        rightSide.setPrefWidth(300);

        VBox searchCard = new VBox(15);
        searchCard.setPadding(new Insets(20));
        searchCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        // ISBN Field
        Label isbnLabel = new Label("ISBN :");
        isbnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        isbnLabel.setTextFill(Color.web("#2c3e50"));
        TextField isbnField = new TextField();
        isbnField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Title Field
        Label titleLabel = new Label("Title :");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        TextField titleField = new TextField();
        titleField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Author Field
        Label authorLabel = new Label("Author :");
        authorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        authorLabel.setTextFill(Color.web("#2c3e50"));
        TextField authorField = new TextField();
        authorField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Category Field
        Label categoryLabel = new Label("Category :");
        categoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        categoryLabel.setTextFill(Color.web("#2c3e50"));
        TextField categoryField = new TextField();
        categoryField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Search Button
        Button searchBtn = new Button("Search");
        searchBtn.setPrefWidth(270);
        searchBtn.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");

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

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    // Method to show Add Book Modal
    private void showAddBookModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Book");
        dialog.setHeaderText("Enter Book Information");
        dialog.initModality(Modality.APPLICATION_MODAL);

        // Create form content
        GridPane grid = new GridPane();  // Changed to GridPane for consistency
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));

        // ISBN Field
        Label isbnLabel = new Label("ISBN:");
        isbnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField isbnField = new TextField();
        isbnField.setPromptText("Enter ISBN");
        isbnField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Title Field
        Label titleLabel = new Label("Title:");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField titleField = new TextField();
        titleField.setPromptText("Enter book title");
        titleField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Author Field
        Label authorLabel = new Label("Author:");
        authorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField authorField = new TextField();
        authorField.setPromptText("Enter author name");
        authorField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Category Field
        Label categoryLabel = new Label("Category:");
        categoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter category");
        categoryField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Add fields to grid
        grid.add(isbnLabel, 0, 0);
        grid.add(isbnField, 1, 0);
        grid.add(titleLabel, 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(authorLabel, 0, 2);
        grid.add(authorField, 1, 2);
        grid.add(categoryLabel, 0, 3);
        grid.add(categoryField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType addButtonType = new ButtonType("Add Book", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, cancelButtonType);

        // Style the buttons
        Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold;");

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);
        cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");

        // Set dialog size
        dialog.getDialogPane().setPrefSize(400, 350);

        // Handle the result
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

                // Show confirmation dialog
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Add Book");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Are you sure you want to add this book?\n\n" +
                        "ISBN: " + isbnField.getText() + "\n" +
                        "Title: " + titleField.getText());
                styleAlert(confirmAlert);

                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Add new book to table
                    int newNo = bookTable.getItems().size() + 1;
                    Book newBook = new Book(
                            isbnField.getText().trim(),
                            titleField.getText().trim(),
                            authorField.getText().trim(),
                            categoryField.getText().trim()
                    );

                    BookTableData newBookData = new BookTableData(
                            String.valueOf(newNo),
                            newBook,
                            "Available"
                    );

                    bookTable.getItems().add(newBookData);
                    showAlert("Success", "Book has been successfully added!", Alert.AlertType.INFORMATION);
                }
                return dialogButton;
            }
            return null;
        });

        dialog.showAndWait();
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
    private void showEditBookModal(BookTableData bookData) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Book");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().setStyle("-fx-background-color: white;");

        // Create modal form fields
        TextField isbnField = new TextField(bookData.getIsbn());
        isbnField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField titleField = new TextField(bookData.getTitle());
        titleField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField authorField = new TextField(bookData.getAuthor());
        authorField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField categoryField = new TextField(bookData.getCategory());
        categoryField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Create form layout menggunakan GridPane seperti di UserEdit
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
                // Validasi field wajib (title dan author)
                if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty()) {
                    showAlert("Error", "Title and Author are required fields!", Alert.AlertType.ERROR);
                    return null;
                }

                // Show confirmation dialog (persis seperti di UserEdit)
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Changes");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Are you sure you want to save these changes?");
                styleAlert(confirmAlert); // Menggunakan styleAlert yang sama

                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Update book data
                    bookData.getBook().setTitle(titleField.getText().trim());
                    bookData.getBook().setAuthor(authorField.getText().trim());
                    bookData.getBook().setCategory(categoryField.getText().trim());

                    // Refresh table
                    bookTable.refresh();

                    showAlert("Success", "Book has been successfully updated!", Alert.AlertType.INFORMATION);
                }
                return dialogButton;
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Gunakan styleAlert yang sama dengan UserEdit
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

    // Helper method untuk styling confirmation alert
    private void styleConfirmationAlert(Alert alert) {
        alert.getDialogPane().setStyle("-fx-background-color: white;");

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold;");

        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold;");
    }

    private void showDeleteConfirmation(BookTableData bookToDelete) {
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
            // Remove from table
            bookTable.getItems().remove(bookToDelete);

            // Refresh table numbers
            refreshTableNumbers();

            showAlert("Success", "Book has been deleted successfully!", Alert.AlertType.INFORMATION);
        }
    }

    private void refreshTableNumbers() {
        ObservableList<BookTableData> items = bookTable.getItems();
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setNo(String.valueOf(i + 1));
        }
        bookTable.refresh();
    }
}