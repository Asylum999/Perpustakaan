package ui;

import javax.swing.*;
import java.awt.*;

public class AestheticPanel extends JPanel {
    private Color backgroundColor;
    private int arc;
    private int shadowGap;
    private Color shadowColor;

    public AestheticPanel(LayoutManager layout, Color backgroundColor, int arc, int shadowGap, Color shadowColor) {
        super(layout);
        this.backgroundColor = backgroundColor;
        this.arc = arc;
        this.shadowGap = shadowGap;
        this.shadowColor = shadowColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (shadowColor != null) {
            g2.setColor(shadowColor);
            g2.fillRoundRect(shadowGap, shadowGap, getWidth() - shadowGap * 2, getHeight() - shadowGap * 2, arc, arc);
        }
        if (backgroundColor != null) {
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        }
        super.paintComponent(g);
    }
}
