package com.library.Controller;

import com.library.Model.Student;
import com.library.View.Admin.*;
import com.library.View.Login.LoginPanel;
import com.library.View.Login.RegisterPanel;
import com.library.View.Student.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Navigator {
    private static Stage mainStage;
    private static RegisterController currentRegisterController;
    private static LoginController currentLoginController;


    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void showLogin() {

        if (currentRegisterController != null) {
            currentRegisterController.close();
            currentRegisterController = null;
        }
            LoginPanel loginPanel = new LoginPanel();
        Scene scene = new Scene(loginPanel, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
        new com.library.Controller.LoginController(loginPanel);

        if (currentLoginController != null) {
            currentLoginController.close();
        }
        currentLoginController = new LoginController(loginPanel);

    }

    public static void showRegister() {
        if (currentLoginController != null) {
            currentLoginController.close();
            currentLoginController = null;
        }

        RegisterPanel registerPanel = new RegisterPanel();
        Scene scene = new Scene(registerPanel, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);

        if (currentRegisterController != null) {
            currentRegisterController.close();
        }
        currentRegisterController = new RegisterController(registerPanel);

        new com.library.Controller.RegisterController(registerPanel);

        registerPanel.cleanup();
    }
    public static void showStudentDashboard(String nama) {
        try {
            HomeDashboard dashboard = new HomeDashboard(nama);
            Scene scene = new Scene(dashboard, mainStage.getWidth(), mainStage.getHeight());
            mainStage.setScene(scene);
        } catch (Exception e) {
            showError("Error loading dashboard: " + e.getMessage());
        }

    }

    public static void showBorrowingHistory() {
        BorrowingHistory historyPage = new BorrowingHistory();
        Scene scene = new Scene(historyPage, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showSearchBook() {
        SearchBookCatalog searchBookCatalog = new SearchBookCatalog();
        Scene scene = new Scene(searchBookCatalog, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showNotifications() {
        Notifications notifications = new Notifications();
        Scene scene = new Scene(notifications, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showProfile() {
        Profile profile = new Profile();
        Scene scene = new Scene(profile, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showAdminHomeDashboard(String nama) {
        AdminHomeDashboard adminHomeDashboard = new AdminHomeDashboard("Admin");
        Scene scene = new Scene(adminHomeDashboard, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showAdminBookManagement() {
        AdminBookManagement adminBookManagement = new AdminBookManagement();
        Scene scene = new Scene(adminBookManagement, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }
    public static void showAdminUserManagement() {
        AdminUserManagement adminUserManagement = new AdminUserManagement();
        Scene scene = new Scene(adminUserManagement, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showAdminUserEdit(Student student) {
        if (student == null) {
            System.err.println("Error: Cannot edit null student");
            return;
        }

        AdminUserEdit adminUserEdit = new AdminUserEdit(student); // Pass parameter
        Scene scene = new Scene(adminUserEdit, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showAdminProfile() {
        ProfileAdmin profileAdmin = new ProfileAdmin();
        Scene scene = new Scene(profileAdmin, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

