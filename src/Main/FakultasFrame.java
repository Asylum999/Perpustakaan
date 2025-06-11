package Main;

import javax.swing.*;
import java.awt.*;

public class FakultasFrame extends JFrame {
    public FakultasFrame(/* params */) {
        super("Pilih Fakultas");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUndecorated(true);
        setResizable(false);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        setBounds(0, 0, bounds.width, bounds.height);

        setVisible(true);
    }
}