import java.awt.*;
import javax.swing.*;

public class ATM_interface extends JFrame {
    public void CreateFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setSize(1000, 1100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyPanel panel = new MyPanel();
        frame.add(panel);
        
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ATM_interface().CreateFrame("default");
    }
}

class MyPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the border
        drawLines(g);
    }

    void drawLines(Graphics g) {
        Graphics2D Lines = (Graphics2D) g;
        Lines.setColor(Color.BLACK); // Set the line color
        Lines.drawLine(150, 50, 850, 50); // Upper line of the ATM window
        Lines.drawLine(150, 50, 150, 600); // Left height of the ATM window
        Lines.drawLine(150, 600, 850, 600); // Lower line of the ATM window
        Lines.drawLine(850, 50, 850, 600); // Right height of the ATM window

        // Fill the area bounded by the lines
        Polygon polygon = new Polygon();
        polygon.addPoint(150, 50);
        polygon.addPoint(850, 50);
        polygon.addPoint(850, 600);
        polygon.addPoint(150, 600);
        
        g.setColor(Color.GRAY); // Set the color to gray
        g.fillPolygon(polygon); // Fill the polygon
    }
}