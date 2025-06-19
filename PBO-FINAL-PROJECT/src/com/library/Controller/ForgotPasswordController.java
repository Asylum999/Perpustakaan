package com.library.Controller;

import com.library.Model.Connections;
import com.library.Model.Student;
import javafx.scene.control.Alert;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



public class ForgotPasswordController {
    private final Connections connections;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    public ForgotPasswordController() {
        this.connections = new Connections();
    }
    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean verifyEmail(String email) {
        if (!isValidEmail(email)) {
            return false;
        }

        try {
            Student student = connections.findStudentByEmail(email);
            return student != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updatePassword(String email, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            return false;
        }

        try {
            Student student = connections.findStudentByEmail(email);
            if (student != null) {
                connections.updateStudentPassword(student.getId(), newPassword);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getErrorMessage(String email, String password) {
        if (!isValidEmail(email)) {
            return "Please enter a valid email address";
        }
        if (password != null && password.length() < 6) {
            return "Password must be at least 6 characters long";
        }
        return null;
    }

    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
