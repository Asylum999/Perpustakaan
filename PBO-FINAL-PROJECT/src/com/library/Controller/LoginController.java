package com.library.Controller;
import com.library.View.Login.LoginPanel;

public class LoginController {
    private final LoginPanel view;

    public LoginController(LoginPanel view) {
        this.view = view;

        view.getRegisterLink().setOnAction(e -> Navigator.showRegister());
        view.getLoginButton().setOnAction(e -> {
            // Tambahkan validasi login di sini
            System.out.println("Login clicked");
        });

        view.getLoginButton().setOnAction(e -> {
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText();

            // Contoh validasi sederhana
            if (username.equals("student") && password.equals("123")) {
                Navigator.showStudentDashboard(username); // Ganti dengan nama pengguna yang sebenarnya
            } else {
                System.out.println("Login gagal!");
            }
        });
    }

}
