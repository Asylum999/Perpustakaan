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
import java.util.UUID;

public class BorrowingHistory extends BorderPane {

    private TableView<BorrowingData> table;

    public BorrowingHistory() {
        VBox sidebar = createSidebar();

        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(40, 40, 40, 40));
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        Label title = new Label("Borrowing History");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#2c3e50"));

        VBox summaryBox = createSummaryBox();
        table = createTable();

        mainContent.getChildren().addAll(title, summaryBox, table);

        this.setLeft(sidebar);
        this.setCenter(mainContent);
    }

    private VBox createSidebar() {
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
                    default:
                        System.out.println("Menu belum ditangani: " + menuItems[index]);
                }
            });

            allMenuButtons.add(menuButton);
            menuBox.getChildren().add(menuButton);

            if (menuItems[i].equals("Borrowing History")) {
                menuButton.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14;");
            }
        }
        return sidebar;
    }

    private VBox createSummaryBox() {
        VBox summaryBox = new VBox(15);
        summaryBox.setPadding(new Insets(20));
        summaryBox.setStyle("-fx-background-color: #750205; -fx-background-radius: 10px;");
        summaryBox.setPrefWidth(800);

        Label summaryTitle = new Label("Summary");
        summaryTitle.setTextFill(Color.web("#f39c12"));
        summaryTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Hitung dari database
        Connections conn = new Connections();
        String currentId = Navigator.getCurrentUser().getId();
        List<Borrowing> borrowings = conn.getAllBorrowings();
        int total = 0, borrowed = 0, returned = 0, overdue = 0;

        LocalDate today = LocalDate.now();
        for (Borrowing b : borrowings) {
            if (!b.getStudentId().equals(currentId)) continue;

            total++;
            if ("Borrowed".equalsIgnoreCase(b.getStatus())) {
                if (b.getDueDate().isBefore(today)) {
                    overdue++;
                } else {
                    borrowed++;
                }
            } else if ("Returned".equalsIgnoreCase(b.getStatus())) {
                returned++;
            }
        }

        VBox summaryItems = new VBox(8);
        HBox totalBooksBox = createSummaryItem("Total Books", ": " + total);
        HBox borrowedBox = createSummaryItem("Borrowed", ": " + borrowed);
        HBox returnedBox = createSummaryItem("Returned", ": " + returned);
        HBox overdueBox = createSummaryItem("Overdue", ": " + overdue);

        summaryItems.getChildren().addAll(totalBooksBox, borrowedBox, returnedBox, overdueBox);
        summaryBox.getChildren().addAll(summaryTitle, summaryItems);

        return summaryBox;
    }


    private HBox createSummaryItem(String label, String value) {
        HBox itemBox = new HBox(10);
        itemBox.setAlignment(Pos.CENTER_LEFT);

        Label labelText = new Label(label);
        labelText.setTextFill(Color.WHITE);
        labelText.setFont(Font.font("Arial", 14));
        labelText.setPrefWidth(100);

        Label valueText = new Label(value);
        valueText.setTextFill(Color.WHITE);
        valueText.setFont(Font.font("Arial", 14));

        itemBox.getChildren().addAll(labelText, valueText);
        return itemBox;
    }

    private TableView<BorrowingData> createTable() {

        String currentStudentId = Navigator.getCurrentUser().getId();  // ambil ID user login

        TableView<BorrowingData> table = new TableView<>();
        table.setPrefHeight(300);
        table.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5px;");

        TableColumn<BorrowingData, String> noCol = new TableColumn<>("No");
        noCol.setPrefWidth(80);
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));
        noCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<BorrowingData, String> bookTitleCol = new TableColumn<>("Book Title");
        bookTitleCol.setPrefWidth(200);
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

        TableColumn<BorrowingData, String> borrowDateCol = new TableColumn<>("Borrow Date");
        borrowDateCol.setPrefWidth(150);
        borrowDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        borrowDateCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<BorrowingData, String> returnDateCol = new TableColumn<>("Return Date");
        returnDateCol.setPrefWidth(150);
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        returnDateCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<BorrowingData, String> statusCol = new TableColumn<>("Status");
        statusCol.setPrefWidth(120);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setStyle("-fx-alignment: CENTER;");

        statusCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-alignment: CENTER;");
                    if (item.equals("Returned")) {
                        setTextFill(Color.web("#28a745"));
                    } else if (item.equals("Overdue")) {
                        setTextFill(Color.web("#dc3545"));
                    } else if (item.equals("Borrowed")) {
                        setTextFill(Color.web("#007bff"));
                    }
                }
            }
        });

        table.getColumns().addAll(noCol, bookTitleCol, borrowDateCol, returnDateCol, statusCol);

        Connections conn = new Connections();
        List<Borrowing> borrowings = conn.getAllBorrowings();
        List<Book> books = conn.getAllBooks();
        ObservableList<BorrowingData> data = FXCollections.observableArrayList();

        int no = 1;
        for (Borrowing b : borrowings) {
            if (!b.getStudentId().equals(currentStudentId)) continue;

            String title = books.stream()
                    .filter(book -> book.getIsbn().equals(b.getIsbn()))
                    .map(Book::getTitle)
                    .findFirst()
                    .orElse("-");

            String returnDate = b.getDueDate().isBefore(LocalDate.now()) && b.getStatus().equalsIgnoreCase("Borrowed")
                    ? ""
                    : b.getDueDate().toString();

            String status = b.getStatus();

            data.add(new BorrowingData(
                    String.valueOf(no++),
                    title,
                    b.getBorrowDate().toString(),
                    returnDate,
                    status
            ));
        }


        table.setItems(data);
        return table;
    }

    public static class BorrowingData {
        private String no;
        private String bookTitle;
        private String borrowDate;
        private String returnDate;
        private String status;

        public BorrowingData(String no, String bookTitle, String borrowDate, String returnDate, String status) {
            this.no = no;
            this.bookTitle = bookTitle;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
            this.status = status;
        }

        public String getNo() { return no; }
        public String getBookTitle() { return bookTitle; }
        public String getBorrowDate() { return borrowDate; }
        public String getReturnDate() { return returnDate; }
        public String getStatus() { return status; }

        public void setNo(String no) { this.no = no; }
        public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
        public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }
        public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
        public void setStatus(String status) { this.status = status; }
    }
}
