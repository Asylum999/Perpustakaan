package com.library.Controller;

import com.library.Model.Student;
import com.library.View.Admin.AdminHomeDashboard;
import com.library.View.Login.LoginPanel;
import com.library.View.Login.RegisterPanel;
import com.library.View.Student.HomeDashboard;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthController {
    private Stage primaryStage;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    public AuthController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initialize();
    }

    private void initialize() {
        // Initialize panels
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();

        // Setup login panel actions
        setupLoginActions();

        // Setup register panel actions
        setupRegisterActions();

        // Show login panel initially
        showLoginPanel();
    }

    private void setupLoginActions() {
        // Login button action
        loginPanel.getLoginButton().setOnAction(e -> handleLogin());

        // Register link action - hide for admin
        loginPanel.getRegisterLink().setOnAction(e -> showRegisterPanel());
    }

    private void setupRegisterActions() {
        // Register button action
        registerPanel.getRegisterButton().setOnAction(e -> handleRegistration());

        // Back to login link action
        registerPanel.backtoLogin.setOnAction(e -> showLoginPanel());
    }

    private void handleLogin() {
        String username = loginPanel.getUsernameField().getText();
        String password = loginPanel.getPasswordField().getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password");
            return;
        }

        // Check for admin login first
        if (username.equals(ADMIN_ID)){
            if (password.equals(ADMIN_PASSWORD)) {
                showAdminDashboard();
                return;
            } else {
                showAlert("Error", "Invalid admin credentials");
                return;
            }
        }

        // Regular student login
        try {
            List<Student> students = readStudentsFromDB();
            for (Student student : students) {
                if (student.getUsername().equals(username) && student.getId().equals(password)) {
                    // Login successful
                    showHomeDashboard(student);
                    return;
                }
            }
            showAlert("Error", "Invalid username or password");
        } catch (IOException e) {
            showAlert("Error", "Failed to access database");
            e.printStackTrace();
        }
    }

    private void handleRegistration() {
        String name = registerPanel.getNameField().getText();
        String studentId = registerPanel.getStudentIdField().getText();
        String faculty = registerPanel.getFacultyField().getText();
        String major = registerPanel.getMajorField().getText();
        String email = registerPanel.getEmailField().getText();

        if (name.isEmpty() || studentId.isEmpty() || faculty.isEmpty() || major.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        try {
            // Check if student already exists
            List<Student> students = readStudentsFromDB();
            for (Student student : students) {
                if (student.getId().equals(studentId)) {
                    showAlert("Error", "Student ID already registered");
                    return;
                }
            }

            // Create new student
            Student newStudent = new Student(name, studentId, faculty, major, email);

            // Save to database
            saveStudentToDB(newStudent);

            showAlert("Success", "Registration successful!");
            showLoginPanel();
        } catch (IOException e) {
            showAlert("Error", "Failed to save registration");
            e.printStackTrace();
        }
    }

    private List<Student> readStudentsFromDB() throws IOException {
        List<Student> students = new ArrayList<>();
        File file = new File(DB_FILE);

        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Skip header if exists
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    // Skip admin record (we handle admin separately)
                    if (parts[1].trim().equals(ADMIN_ID)) continue;

                    students.add(new Student(
                            parts[0].trim(), // name
                            parts[1].trim(), // id
                            parts[2].trim(), // faculty
                            parts[3].trim(), // major
                            parts[4].trim()
                    ));
                }
            }
        }
        return students;
    }

    private void saveStudentToDB(Student student) throws IOException {
        File file = new File(DB_FILE);
        boolean fileExists = file.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (!fileExists) {
                // Write header if file is new
                writer.println("name;id;faculty;major;email");
            }
            writer.println(String.join(";",
                    student.getUsername(),
                    student.getId(),
                    student.getFaculty(),
                    student.getMajor(),
                    student.getEmail()
            ));
        }
    }

    private void showLoginPanel() {
        // Clear fields
        loginPanel.getUsernameField().clear();
        loginPanel.getPasswordField().clear();

        Scene scene = new Scene(loginPanel, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UMM Library - Login");
        primaryStage.show();
    }

    private void showRegisterPanel() {
        // Clear fields
        registerPanel.getNameField().clear();
        registerPanel.getStudentIdField().clear();
        registerPanel.getFacultyField().clear();
        registerPanel.getMajorField().clear();
        registerPanel.getEmailField().clear();

        Scene scene = new Scene(registerPanel, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UMM Library - Register");
        primaryStage.show();
    }

    private void showHomeDashboard(Student student) {
        HomeDashboard homeDashboard = new HomeDashboard(student.getUsername());
        Scene scene = new Scene(homeDashboard, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UMM Library - Dashboard");
        primaryStage.show();
    }

    private void showAdminDashboard() {
        AdminHomeDashboard adminDashboard = new AdminHomeDashboard();
        Scene scene = new Scene(adminDashboard, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UMM Library - Admin Dashboard");
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}