package com.library.View.Admin;

import com.library.Controller.Navigator;
import com.library.Model.Book;
import com.library.Model.Borrowing;
import com.library.Model.Connections;
import com.library.Model.Student;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;

import java.util.List;

public class AdminUserEdit extends BorderPane {

    private Student currentStudent;
    private TextField nimField;
    private TextField nameField;
    private TextField majorField;
    private TextField facultyField;
    private TextField emailField;
    private TableView<BookTableData> borrowedBooksTable;
    private final Connections connections = new Connections();

    public static class BookTableData {
        private final String no;
        private final String isbn;
        private final String title;
        private final String author;
        private final String category;
        private final String status;

        public BookTableData(String no, String isbn, String title, String author, String category, String status) {
            this.no = no;
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.category = category;
            this.status = status;
        }

        public String getNo() { return no; }
        public String getIsbn() { return isbn; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getCategory() { return category; }
        public String getStatus() { return status; }
    }

    public AdminUserEdit(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        this.currentStudent = student;
        initializeComponents();
        loadStudentData();
    }

    private void initializeComponents() {
        // === Simplified Sidebar with Back button ===
        VBox sidebar = createSidebar();

        // === Main Content - Edit Student ===
        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Header with title
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label pageTitle = new Label("Edit Student Profile");
        pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        pageTitle.setTextFill(Color.web("#2c3e50"));

        header.getChildren().add(pageTitle);

        // Content Layout
        HBox contentLayout = new HBox(30);
        contentLayout.setAlignment(Pos.TOP_LEFT);

        // Left Side - Student Form
        VBox leftSide = createStudentForm();

        // Right Side - Borrowed Books Table
        VBox rightSide = createBorrowedBooksTable();

        contentLayout.getChildren().addAll(leftSide, rightSide);
        mainContent.getChildren().addAll(header, contentLayout);

        // Layout Setup
        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #800000;");
        sidebar.setPadding(new Insets(20, 0, 20, 0));
        sidebar.setAlignment(Pos.TOP_CENTER);

        // Back button
        Button backButton = new Button("Back");
        backButton.setGraphic(new ImageView(new Image(getClass().getResource("/images/back.png").toExternalForm())));
        backButton.setContentDisplay(ContentDisplay.LEFT);
        backButton.setAlignment(Pos.CENTER_LEFT);
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
        backButton.setPadding(new Insets(10, 20, 10, 20));
        backButton.setMaxWidth(Double.MAX_VALUE);

        backButton.setOnAction(e -> Navigator.showAdminUserManagement());

        sidebar.getChildren().add(backButton);
        return sidebar;
    }

    private VBox createStudentForm() {
        VBox leftSide = new VBox(20);
        leftSide.setPrefWidth(400);

        // Student Form Card
        VBox studentFormCard = new VBox(15);
        studentFormCard.setPadding(new Insets(30));
        studentFormCard.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        // Create form fields
        nimField = createFormField("NIM :", "Enter NIM");
        nameField = createFormField("Name :", "Enter Name");
        majorField = createFormField("Major :", "Enter Major");
        facultyField = createFormField("Faculty:", "Enter Faculty");
        emailField = createFormField("Email :", "Enter Email");

        // Edit Profile Button
        Button editProfileBtn = new Button("Edit Profile");
        editProfileBtn.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16; -fx-padding: 15 30; -fx-border-radius: 5; -fx-background-radius: 5;");
        editProfileBtn.setOnAction(e -> showEditProfileModal());

        // Add all components to form card
        studentFormCard.getChildren().addAll(
                createLabelFieldPair("NIM :", nimField),
                createLabelFieldPair("Name :", nameField),
                createLabelFieldPair("Major :", majorField),
                createLabelFieldPair("Faculty:", facultyField),
                createLabelFieldPair("Email :", emailField),
                editProfileBtn
        );

        leftSide.getChildren().add(studentFormCard);
        return leftSide;
    }

    private VBox createLabelFieldPair(String labelText, TextField field) {
        VBox pair = new VBox(5);

        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#2c3e50"));

        pair.getChildren().addAll(label, field);
        return pair;
    }

    private TextField createFormField(String labelText, String promptText) {
        TextField field = new TextField();
        field.setStyle("-fx-border-color: #800000; -fx-border-width: 0 0 2 0; -fx-background-color: transparent; -fx-padding: 8; -fx-font-size: 14;");
        field.setPromptText(promptText);
        return field;
    }

    private VBox createBorrowedBooksTable() {
        VBox rightSide = new VBox(15);
        rightSide.setPrefWidth(700);

        Label tableTitle = new Label("Borrowed Books History");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableTitle.setTextFill(Color.web("#2c3e50"));

        // Borrowed Books Table
        borrowedBooksTable = new TableView<>();
        borrowedBooksTable.setPrefHeight(500);
        borrowedBooksTable.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        // Create table columns
        createTableColumns();

        rightSide.getChildren().addAll(tableTitle, borrowedBooksTable);
        return rightSide;
    }

    private void createTableColumns() {
        TableColumn<BookTableData, String> noCol = new TableColumn<>("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));
        noCol.setPrefWidth(30);

        TableColumn<BookTableData, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnCol.setPrefWidth(120);

        TableColumn<BookTableData, String> titleCol = new TableColumn<>("TITLE");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(150);

        TableColumn<BookTableData, String> authorCol = new TableColumn<>("AUTHOR");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setPrefWidth(120);

        TableColumn<BookTableData, String> categoryCol = new TableColumn<>("CATEGORY");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setPrefWidth(100);

        TableColumn<BookTableData, String> statusCol = new TableColumn<>("STATUS");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(80);

        TableColumn<BookTableData, Void> actionCol = new TableColumn<>("ACTION");
        actionCol.setPrefWidth(100);

        // Add action buttons to each row
        actionCol.setCellFactory(col -> new TableCell<BookTableData, Void>() {
            private final Button changeStatusBtn = new Button("Change Status");

            {
                changeStatusBtn.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 10; -fx-padding: 5 10;");
                changeStatusBtn.setOnAction(e -> {
                    BookTableData selectedBook = getTableView().getItems().get(getIndex());
                    if (selectedBook != null) {
                        changeBookStatus(selectedBook);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    BookTableData rowData = getTableView().getItems().get(getIndex());
                    if ("Returned".equalsIgnoreCase(rowData.getStatus())) {
                        setGraphic(null); // hide button if already returned
                    } else {
                        setGraphic(changeStatusBtn);
                    }
                }
            }
        });


        borrowedBooksTable.getColumns().addAll(noCol, isbnCol, titleCol, authorCol, categoryCol, statusCol, actionCol);
    }

    // Improved method name for clarity
    private void loadStudentData() {
        if (currentStudent != null) {
            // Map Student properties to form fields
            nimField.setText(currentStudent.getId()); // Assuming ID is NIM
            nameField.setText(currentStudent.getUsername()); // Assuming username is full name
            majorField.setText(currentStudent.getMajor());
            facultyField.setText(currentStudent.getFaculty());
            emailField.setText(currentStudent.getEmail());
        }

        // Load borrowed books data
        loadBorrowedBooks();
    }

    private void loadBorrowedBooks() {
        ObservableList<BookTableData> booksData = FXCollections.observableArrayList();
        String studentId = currentStudent.getId();

        int no = 1;
        for (Borrowing b : connections.getAllBorrowings()) {
            if (b.getStudentId().equals(studentId)) {
                // Ambil detail buku dari ISBN
                Book book = connections.getAllBooks().stream()
                        .filter(bk -> bk.getIsbn().equals(b.getIsbn()))
                        .findFirst()
                        .orElse(null);

                if (book != null) {
                    booksData.add(new BookTableData(
                            String.valueOf(no++),
                            book.getIsbn(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getCategory(),
                            b.getStatus() // status dari transaksi peminjaman
                    ));
                }
            }
        }

        borrowedBooksTable.setItems(booksData);
    }

    private void changeBookStatus(BookTableData bookData) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Change Book Status");
        alert.setHeaderText(null);
        alert.setContentText("Mark this book as returned and change status to 'Available'?");

        styleAlert(alert);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // 1. Update status buku ke "Available"
                connections.updateBookStatus(bookData.getIsbn(), "Available");

                // 2. Update status peminjaman ke "Returned" (bukan dihapus!)
                List<Borrowing> borrowings = connections.getAllBorrowings();
                for (Borrowing b : borrowings) {
                    if (b.getStudentId().equals(currentStudent.getId()) &&
                            b.getIsbn().equals(bookData.getIsbn()) &&
                            b.getStatus().equalsIgnoreCase("Borrowed")) {

                        b.setStatus("Returned");
                        break; // hanya update satu transaksi saja
                    }
                }
                connections.writeBorrowingsToCsv(borrowings);

                // 3. Tampilkan alert dan refresh table
                showAlert("Success", "Book status updated and borrowing marked as returned.", Alert.AlertType.INFORMATION);
                loadBorrowedBooks(); // Refresh tampilan
            }
        });
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

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.getDialogPane().setStyle("-fx-background-color: white;");

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        if (okButton != null) {
            if (alertType == Alert.AlertType.INFORMATION) {
                okButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
            } else if (alertType == Alert.AlertType.ERROR) {
                okButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
            }
        }

        alert.showAndWait();
    }

    private void showEditProfileModal() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Student Profile");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.getDialogPane().setStyle("-fx-background-color: white;");

        // Create modal form fields
        TextField studentIdModalField = new TextField(currentStudent.getId());
        studentIdModalField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField nameModalField = new TextField(currentStudent.getUsername());
        nameModalField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField majorModalField = new TextField(currentStudent.getMajor());
        majorModalField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField facultyModalField = new TextField(currentStudent.getFaculty());
        facultyModalField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");
        TextField emailModalField = new TextField(currentStudent.getEmail());
        emailModalField.setStyle("-fx-border-color: #800000; -fx-border-radius: 5px; -fx-padding: 8;");

        // Create form layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));

        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(studentIdModalField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameModalField, 1, 1);
        grid.add(new Label("Major:"), 0, 2);
        grid.add(majorModalField, 1, 2);
        grid.add(new Label("Faculty:"), 0, 3);
        grid.add(facultyModalField, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(emailModalField, 1, 4);

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
        dialog.showAndWait().ifPresent(result -> {
            if (result == saveButtonType) {
                saveStudentChanges(studentIdModalField, nameModalField, majorModalField, facultyModalField, emailModalField);
            }
        });
    }

    private void saveStudentChanges(TextField nimField, TextField nameField, TextField majorField, TextField facultyField, TextField emailField) {
        // Validate input
        if (nimField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
            showAlert("Error", "NIM and Name are required fields!", Alert.AlertType.ERROR);
            return;
        }

        // Show confirmation
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Changes");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to save these changes?");

        styleAlert(confirmAlert);

        confirmAlert.showAndWait().ifPresent(confirmResult -> {
            if (confirmResult == ButtonType.OK) {
                // Update student data
                currentStudent.setId(nimField.getText().trim());
                currentStudent.setUsername(nameField.getText().trim());
                currentStudent.setMajor(majorField.getText().trim());
                currentStudent.setFaculty(facultyField.getText().trim());
                currentStudent.setEmail(emailField.getText().trim());

                // Update main form fields
                loadStudentData();

                showAlert("Success", "Student profile has been successfully updated!", Alert.AlertType.INFORMATION);
            }
        });
    }
}