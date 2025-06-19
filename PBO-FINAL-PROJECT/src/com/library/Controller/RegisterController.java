package com.library.Controller;
import com.library.Model.*;

import com.library.View.Login.RegisterPanel;
import javafx.scene.control.Alert;


public class RegisterController implements AutoCloseable
{
    private RegisterPanel view;
    private final Connections connections;
    private volatile boolean isClosed = false;


    public RegisterController(RegisterPanel view) {
        this.view = view;
        this.connections = new Connections();
        initializeEventHandlers();


        view.backtoLogin.setOnAction(e -> Navigator.showLogin());
        view.getRegisterButton().setOnAction(e -> {
            System.out.println("Register button clicked"); // Add debug logging
            handleRegistration();
        });

    }

    private void initializeEventHandlers() {
        view.backtoLogin.setOnAction(e -> {
            cleanup();
            Navigator.showLogin();
        });
        view.getRegisterButton().setOnAction(e -> {
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
        // Implement your ID validation logic here
        // For example: must be numeric and certain length
        return id.matches("\\d{8,}"); // at least 8 digits
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void cleanup() {
        if (view != null) {
            view.backtoLogin.setOnAction(null);
            view.getRegisterButton().setOnAction(null);
        }
        if (connections != null) {
            try {
                connections.close(); // You'll need to implement this in your Connections class
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
                        if (view != null) {
                            view.cleanup(); // Use the panel's cleanup method
                        }
                        if (connections != null) {
                            connections.close();
                        }
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

