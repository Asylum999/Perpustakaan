package com.library.Controller;
import com.library.Model.*;
import com.library.View.Login.RegisterPanel;
import javafx.scene.control.Alert;

public class RegisterController implements AutoCloseable {
    private RegisterPanel view;
    private final Connections connections;
    private volatile boolean isClosed = false;

    public RegisterController(RegisterPanel view) {
        this.view = view;
        this.connections = new Connections();
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        // Hanya satu event handler untuk backtoLogin
        view.getBacktoLogin().setOnAction(e -> {
            System.out.println("Back to Login clicked"); // Debug log
            try {
                Navigator.showLogin();
            } catch (Exception ex) {
                System.err.println("Error navigating to login: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        view.getRegisterButton().setOnAction(e -> {
            System.out.println("Register button clicked"); // Debug log
            handleRegistration();
        });
    }

    private void handleRegistration() {
        String username = view.getNameField().getText();
        String id = view.getStudentIdField().getText();
        String faculty = view.getFacultyField().getText();
        String major = view.getMajorField().getText();
        String email = view.getEmailField().getText();

        System.out.println("Attempting registration with: " + username);

        if (!validateInputs(username, id, faculty, major, email)) {
            return;
        }

        try {
            Student newStudent = new Student(username, id, faculty, major, email);
            connections.addUser(newStudent);

            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Registration successful! You can now login.");

            // Navigate to login setelah registrasi berhasil
            Navigator.showLogin();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Registration failed: " + e.getMessage());
        }
    }

    private boolean validateInputs(String username, String id, String faculty,
                                   String major, String email) {
        if (username.isEmpty() || id.isEmpty() || faculty.isEmpty() ||
                major.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields!");
            return false;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Please enter a valid email address!");
            return false;
        }

        if (connections.findUserById(id) != null) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "A user with this ID already exists!");
            return false;
        }

        if (!isValidId(id)) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "ID must be in a valid format!");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean isValidId(String id) {
        return id.matches("\\d{8,}"); // at least 8 digits
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method cleanup yang dipanggil saat controller akan ditutup
    public void cleanup() {
        if (view != null && !isClosed) {
            // Hanya set ke null tanpa memanggil Navigator
            view.getBacktoLogin().setOnAction(null);
            view.getRegisterButton().setOnAction(null);
        }
        if (connections != null) {
            try {
                connections.close();
            } catch (Exception e) {
                System.err.println("Error closing connections: " + e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        if (!isClosed) {
            synchronized (this) {
                if (!isClosed) {
                    try {
                        cleanup();
                        view = null;
                        isClosed = true;
                    } catch (Exception e) {
                        System.err.println("Error during RegisterController cleanup: " + e.getMessage());
                    }
                }
            }
        }
    }
}