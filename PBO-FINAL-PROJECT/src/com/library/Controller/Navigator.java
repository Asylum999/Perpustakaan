package com.library.Controller;

import com.library.Model.Student;
import com.library.Model.User;
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
    private static User currentUser;


    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void showLogin() {
        System.out.println("Navigator: Showing Login"); // Debug log

        // Cleanup current register controller if exists
        if (currentRegisterController != null) {
            currentRegisterController.close();
            currentRegisterController = null;
        }

        // Cleanup current login controller if exists
        if (currentLoginController != null) {
            currentLoginController.close();
            currentLoginController = null;
        }

        try {
            LoginPanel loginPanel = new LoginPanel();
            Scene scene = new Scene(loginPanel, mainStage.getWidth(), mainStage.getHeight());
            mainStage.setScene(scene);

            // Create new login controller
            currentLoginController = new LoginController(loginPanel);

            System.out.println("Navigator: Login panel displayed successfully");
        } catch (Exception e) {
            System.err.println("Error showing login: " + e.getMessage());
            e.printStackTrace();
            showError("Error loading login page: " + e.getMessage());
        }
    }

    public static void showRegister() {
        System.out.println("Navigator: Showing Register"); // Debug log

        // Cleanup current login controller if exists
        if (currentLoginController != null) {
            currentLoginController.close();
            currentLoginController = null;
        }

        // Cleanup current register controller if exists
        if (currentRegisterController != null) {
            currentRegisterController.close();
            currentRegisterController = null;
        }

        try {
            RegisterPanel registerPanel = new RegisterPanel();
            Scene scene = new Scene(registerPanel, mainStage.getWidth(), mainStage.getHeight());
            mainStage.setScene(scene);

            // Create new register controller - HANYA SATU KALI!
            currentRegisterController = new RegisterController(registerPanel);

            System.out.println("Navigator: Register panel displayed successfully");
        } catch (Exception e) {
            System.err.println("Error showing register: " + e.getMessage());
            e.printStackTrace();
            showError("Error loading register page: " + e.getMessage());
        }
    }

    public static void showStudentDashboard(String nama) {
        // Cleanup controllers when leaving login/register
        cleanupAuthControllers();

        try {
            HomeDashboard dashboard = new HomeDashboard(nama);
            Scene scene = new Scene(dashboard, mainStage.getWidth(), mainStage.getHeight());
            mainStage.setScene(scene);
        } catch (Exception e) {
            showError("Error loading dashboard: " + e.getMessage());
        }
    }

    public static void showBorrowingHistory() {
        cleanupAuthControllers();

        BorrowingHistory historyPage = new BorrowingHistory();
        Scene scene = new Scene(historyPage, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showSearchBook() {
        cleanupAuthControllers();

        SearchBookCatalog searchBookCatalog = new SearchBookCatalog();
        Scene scene = new Scene(searchBookCatalog, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showNotifications() {
        cleanupAuthControllers();

        Notifications notifications = new Notifications();
        Scene scene = new Scene(notifications, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showProfile() {
        cleanupAuthControllers();

        Profile profile = new Profile();
        Scene scene = new Scene(profile, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showAdminHomeDashboard(String nama) {
        cleanupAuthControllers();

        AdminHomeDashboard adminHomeDashboard = new AdminHomeDashboard(nama);
        Scene scene = new Scene(adminHomeDashboard, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showAdminBookManagement() {
        cleanupAuthControllers();

        AdminBookManagement adminBookManagement = new AdminBookManagement();
        Scene scene = new Scene(adminBookManagement, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showAdminUserManagement() {
        cleanupAuthControllers();

        AdminUserManagement adminUserManagement = new AdminUserManagement();
        Scene scene = new Scene(adminUserManagement, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showAdminUserEdit(Student student) {
        cleanupAuthControllers();

        if (student == null) {
            System.err.println("Error: Cannot edit null student");
            return;
        }

        AdminUserEdit adminUserEdit = new AdminUserEdit(student);
        Scene scene = new Scene(adminUserEdit, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    public static void showAdminProfile() {
        cleanupAuthControllers();

        ProfileAdmin profileAdmin = new ProfileAdmin();
        Scene scene = new Scene(profileAdmin, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
    }

    // Helper method to cleanup authentication controllers
    private static void cleanupAuthControllers() {
        if (currentLoginController != null) {
            currentLoginController.close();
            currentLoginController = null;
        }
        if (currentRegisterController != null) {
            currentRegisterController.close();
            currentRegisterController = null;
        }
    }

    public static void loginSuccess(User user) {
        currentUser = user;
        showStudentDashboard(user.getUsername());
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Navigator.currentUser = currentUser;
    }
}