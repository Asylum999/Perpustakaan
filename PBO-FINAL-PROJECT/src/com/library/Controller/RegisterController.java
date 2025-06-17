package com.library.Controller;
import com.library.View.Login.RegisterPanel;

public class RegisterController {
    private final RegisterPanel view;

    public RegisterController(RegisterPanel view) {
        this.view = view;

        view.backtoLogin.setOnAction(e -> Navigator.showLogin());
        view.getRegisterButton().setOnAction(e -> {
            // Tambahkan logika pendaftaran di sini
            System.out.println("Register clicked");
        });
    }
}
