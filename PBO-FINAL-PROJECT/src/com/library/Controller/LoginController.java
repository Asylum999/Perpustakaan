package com.library.Controller;

import com.library.Model.Admin;
import com.library.Model.Connections;
import com.library.Model.Student;
import com.library.View.Login.LoginPanel;

public class LoginController {
    private final LoginPanel view;
    private final Connections connection;

    public LoginController(LoginPanel view) {
        this.view = view;
        this.connection = new Connections();

        view.getRegisterLink().setOnAction(e -> Navigator.showRegister());

        view.getLoginButton().setOnAction(e -> {
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText(); // password = ID

            Student student = connection.getStudentIfValid(username, password);
            Admin admin = connection.getAdminIfValid(username, password);

            if (student != null) {
                Navigator.showStudentDashboard(student); // Kirim objek Student
            } else if (admin != null) {
                Navigator.showAdminHomeDashboard(admin); // Kirim objek Admin
            } else {
                view.showAlert("Login Gagal", "Username atau ID tidak cocok!", javafx.scene.control.Alert.AlertType.ERROR);
            }
        });
    }
}
