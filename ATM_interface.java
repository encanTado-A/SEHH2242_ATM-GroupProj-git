import java.awt.*;
// import java.io.OutputStream;
// import java.io.PrintStream;
import javax.swing.*;

public class ATM_interface extends JFrame {
    
    
    public void CreateFrame(String title) {
        JFrame mainFrame = new JFrame(title);
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel panel1 = new JPanel();
        // mainFrame.add(panel1, BorderLayout.NORTH);
        
        // JPanel textinput = new GUI();
        // mainFrame.add(textinput, BorderLayout.NORTH);
        
        // MyPanel panel = new MyPanel();
        // mainFrame.add(panel);

        // JTextArea textArea = new JTextArea();
        // textArea.setEditable(false);
        // JScrollPane scrollPane = new JScrollPane(textArea);
        // mainFrame.add(scrollPane, BorderLayout.CENTER);

        // // Redirect console output to the JTextArea
        // PrintStream printStream = new PrintStream(new OutputStream() {
        //     @Override
        //     public void write(int b) {
        //         // Append the character to the JTextArea
        //         textArea.append(String.valueOf((char) b));
        //         // Scroll to the bottom
        //         textArea.setCaretPosition(textArea.getDocument().getLength());
        //     }
        // });
        // System.setOut(printStream);
        // System.setErr(printStream); // Optional: Redirect error output as well

        KeypadPanel keypadPanel = new KeypadPanel();
        // add keypad panel
        mainFrame.add(keypadPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
        mainFrame.setResizable( false );
    }

    // public static void main(String[] args) {
    //     new ATM_interface().CreateFrame("ATM");
    // }
}

// class MyPanel extends JPanel {
//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         // Draw the border
//         drawLines(g);
//     }

//     void drawLines(Graphics g) {
//         Graphics2D Lines = (Graphics2D) g;
//         Lines.setColor(Color.BLACK); // Set the line color
//         Lines.drawLine(150, 50, 850, 50); // Upper line of the ATM window
//         Lines.drawLine(150, 50, 150, 600); // Left height of the ATM window
//         Lines.drawLine(150, 600, 850, 600); // Lower line of the ATM window
//         Lines.drawLine(850, 50, 850, 600); // Right height of the ATM window

//         // Fill the area bounded by the lines
//         Polygon polygon = new Polygon();
//         polygon.addPoint(150, 50);
//         polygon.addPoint(850, 50);
//         polygon.addPoint(850, 600);
//         polygon.addPoint(150, 600);
        
//         g.setColor(Color.GRAY); // Set the color to gray
//         g.fillPolygon(polygon); // Fill the polygon
//     }
// }