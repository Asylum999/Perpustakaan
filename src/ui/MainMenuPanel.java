package ui;

import Action.LibraryInterface;
import Auth.User;
import ui.panel.AdminMenuPanel;
import ui.panel.StudentMenuPanel;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(LibraryInterface lib, User user) {
        setLayout(new BorderLayout());
        if (user.isAdmin()) {
            add(new AdminMenuPanel(lib, user), BorderLayout.CENTER);
        } else {
            add(new StudentMenuPanel(lib, user), BorderLayout.CENTER);
        }
    }
}
