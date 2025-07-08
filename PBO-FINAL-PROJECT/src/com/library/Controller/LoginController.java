package com.library.Controller;
import com.library.Model.*;
import com.library.View.Login.LoginPanel;

import javafx.scene.control.Alert;


public class LoginController implements AutoCloseable {
    private LoginPanel view;
    private final Connections connections;
    private volatile boolean isClosed = false;


    public LoginController(LoginPanel view) {
        this.view = view;
        this.connections = new Connections();
        initializeEventHandlers();


        view.getRegisterLink().setOnAction(e -> Navigator.showRegister());
        view.getLoginButton().setOnAction(e -> handleLogin());
    }

    private void initializeEventHandlers() {
        if (view != null) {
            view.getRegisterLink().setOnAction(e -> Navigator.showRegister());
            view.getLoginButton().setOnAction(e -> handleLogin());
        };
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText().trim();
        String id = view.getPasswordField().getText().trim();

        System.out.println("Attempting login with - Username: " + username + ", ID: " + id);


        if (username.isEmpty() || id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields!");
            return;
        }

        try (Connections connections = new Connections()) {  // Ensure proper resource handling
            User user = connections.verifyLogin(id, username);

            System.out.println("User found: " + (user != null)); // Debug line


            if (user != null) {
                Navigator.setCurrentUser(user); // ✅ Tambahkan ini

                if (user instanceof Admin) {
                    Navigator.showAdminHomeDashboard(user.getUsername());
                } else if (user instanceof Student) {
                    Navigator.showStudentDashboard(user.getUsername());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed",
                        "Invalid username or ID. Please try again.");
            }

        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error",
                    "An error occurred during login. Please try again.");
        }
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
            view.getRegisterLink().setOnAction(null);
            view.getLoginButton().setOnAction(null);
        }
        if (connections != null) {
            // Close any open connections
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
                            view.getRegisterLink().setOnAction(null);
                            view.getLoginButton().setOnAction(null);
                        }
                        if (connections != null) {
                            connections.close();
                        }
                        view = null;
                        isClosed = true;
                    } catch (Exception e) {
                        System.err.println("Error during LoginController cleanup: " + e.getMessage());
                    }
                }
            }
        }
    }
}
