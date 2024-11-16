import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.*;

public class ATM_interface extends JFrame {

    private JButton CANCELKEY;
    private JButton CLEARKEY;
    private JButton ENTERKEY;

    private JButton keys[];
    private JPanel keyPadJPanel;
    // private JTextArea textArea; //text area to display output 
    // private JTextPane textPane;
    private JTextField textField;
    private String line1 = "";

    private JTextArea textArea;

    public void CreateFrame(String title) {

        JFrame mainFrame = new JFrame(title);
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // JPanel textinput = new GUI();
        // mainFrame.add(textinput, BorderLayout.NORTH);
        
// ================================
        this.textArea = new JTextArea(10, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainFrame.add(scrollPane, BorderLayout.NORTH);

        // Redirect console output to the JTextArea
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // Append the character to the JTextArea
                textArea.append(String.valueOf((char) b));
                // Scroll to the bottom
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
        System.setOut(printStream);
        System.setErr(printStream); // Optional: Redirect error output as well


// ================================

        // KeypadPanel keypadPanel = new KeypadPanel();
        // add keypad panel
        // mainFrame.add(keypadPanel, BorderLayout.SOUTH);

       JPanel mainPanel = new JPanel( new BorderLayout() );

        // textArea = new JTextArea(3,10);  // declaration of textArea for displaying output
        // textArea.setEditable(false);    // set textArea not editable
        // textArea.setText(line1);  // display line1 in textArea 

        // textArea.setLineWrap( false );
        // textArea.setWrapStyleWord( false );

        this.textField = new JTextField(20);
        textField.setHorizontalAlignment(JTextField.RIGHT); // Right-align the text
        textField.setEditable(false);    // set textArea not editable
        textField.setText( "Testing" );  // display line1 in textArea 


        keys = new JButton[ 16 ]; // array keys contains 16 JButtons 

        // initialize all digit key buttons
        for ( int i = 0; i <= 9; i++ )
        {
            keys[ i ] = new JButton( String.valueOf( i ) );
        }

        // initialize all function key buttons
        this.CANCELKEY = new JButton( "Cancel" );
        this.CLEARKEY = new JButton( "Clear" );
        this.ENTERKEY = new JButton( "Enter" );

        keys[ 10 ] = this.CANCELKEY; // cancel
        keys[ 11 ] = this.CLEARKEY; // 
        keys[ 12 ] = ENTERKEY;
        keys[ 13 ] = new JButton( "" );
        keys[ 14 ] = new JButton( "00" );
        keys[ 15 ] = new JButton( "" );

        // resize button size
        for ( int i = 0; i < 16; i++ )
        {
            keys[ i ].setPreferredSize(new Dimension(20, 10));
        }

        // set keyPadJPanel layout to grid layout
        this.keyPadJPanel = new JPanel();
        keyPadJPanel.setLayout( new GridLayout( 4, 4 ) );

        // add buttons to keyPadJPanel panel
        // 7, 8, 9, Cancel
        for ( int i = 7; i <= 10; i++ )
            keyPadJPanel.add( keys[ i ] );

        // 4, 5, 6
        for ( int i = 4; i <= 6; i++ )
            keyPadJPanel.add( keys[ i ] );

        // Clear
        keyPadJPanel.add( keys[ 11 ] );

        // 1, 2, 3
        for ( int i = 1; i <= 3; i++ )
            keyPadJPanel.add( keys[ i ] );

        // Enter
        keyPadJPanel.add( keys[ 12 ] );

        keyPadJPanel.add( keys[ 15 ] );
        
        // 0
        keyPadJPanel.add( keys[ 0 ] );

        // 00
        keyPadJPanel.add( keys[ 14 ] );
        
        // empty button
        keyPadJPanel.add( keys[ 13 ] );

        mainPanel.add(this.textField, BorderLayout.NORTH);
        // mainPanel.add(textArea, BorderLayout.NORTH);
        mainPanel.add(this.keyPadJPanel, BorderLayout.CENTER );
        
        // add for event listen
        ButtonHandler handler = new ButtonHandler();

        // stick the action to the button
        for (int i = 0; i <= 15; i++)
            keys[ i ].addActionListener(handler);

        mainFrame.add(mainPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
        mainFrame.setResizable( true );
    }

        private class ButtonHandler implements ActionListener 
   {
        @Override // in ActionListener
        public void actionPerformed ( ActionEvent event )
        {
            if ( event.getSource() == CANCELKEY ) 
            {
                line1 = "";
                textField.setText(line1);
            }
            else if ( event.getSource() == CLEARKEY )
            {
                line1 = "";
                textField.setText(line1);
            }
            else if ( event.getSource() == ENTERKEY )
            {
                line1 = line1.concat("\n");
                String input = textField.getText();
                textArea.append(input + "\n");
                // System.out.println(input); // Print the text field input to the console
                textField.setText("");      // Clear the text field
                textField.setText(line1); 
            }
            else
            {
                line1 = line1.concat( event.getActionCommand() );
                textField.setText(line1);        
            }
        }
   } // end ButtonHandler
} // end class ATM_interface

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