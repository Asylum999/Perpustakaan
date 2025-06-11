package Main;

import javax.swing.*;
import java.awt.*;

public class MahasiswaFrame extends JFrame {
    public MahasiswaFrame(/* parameter */) {
        super("Menu Mahasiswa");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUndecorated(true);
        setResizable(false);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        setBounds(0, 0, bounds.width, bounds.height);

        setVisible(true);
    }
}