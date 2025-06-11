package Main;

import Action.LibraryInterface;
import Auth.User;
import ui.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    public MainMenuFrame(LibraryInterface lib, User user) {
        super("Menu Utama Perpustakaan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(780, 580));
        setSize(820, 600);
        setLocationRelativeTo(null);

        // Fullscreen benar-benar penuh
        setUndecorated(true);
        setResizable(false);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        setBounds(0, 0, bounds.width, bounds.height);

        // Tambahkan panel utama dari package ui
        setContentPane(new MainMenuPanel(lib, user));
        setVisible(true);
    }
}