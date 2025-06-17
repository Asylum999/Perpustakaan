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
    }
}
