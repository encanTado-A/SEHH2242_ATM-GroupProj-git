// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.Scanner;
// import javax.swing.*; // program uses Scanner to obtain user input


// public class ATM_interface extends JFrame {

//     private JButton CANCELKEY;
//     private JButton CLEARKEY;
//     private JButton ENTERKEY;

//     private JButton L1;
//     private JButton L2;
//     private JButton L3;
//     private JButton R1;
//     private JButton R2;
//     private JButton R3;

//     private JButton keys[];
//     private JPanel keyPadJPanel;
//     // private JTextArea textArea; //text area to display output 
//     // private JTextPane textPane;

//     public static JTextArea textArea;

//     private static JTextField textField;
//     private static String temp = "";
//     private static int tempInt = 0;
//     private String line1 = "";


//     public void CreateFrame(String title) {

//         JFrame mainFrame = new JFrame(title);
//         mainFrame.setSize(800, 600);
//         mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//         // JPanel textinput = new GUI();
//         // mainFrame.add(textinput, BorderLayout.NORTH);
        
//         // ================================
//         JPanel upperPanel = new JPanel();
//         textArea = new JTextArea(10, 30);
//         textArea.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(textArea);
//         // upperPanel.add(scrollPane, BorderLayout.CENTER);

//         JPanel upperLeftPanel = new JPanel();
//         upperLeftPanel.setLayout( new GridLayout( 3, 1 ) );
//         L1 = new JButton( "L1" );
//         upperLeftPanel.add( L1 );
//         L2 = new JButton( "L2" );
//         upperLeftPanel.add( L2 );
//         L3 = new JButton( "L3" );
//         upperLeftPanel.add( L3 );

//         JPanel upperRightPanel = new JPanel();
//         upperRightPanel.setLayout( new GridLayout( 3, 1 ) );
//         R1 = new JButton( "R1" );
//         upperRightPanel.add( R1 );
//         R2 = new JButton( "R2" );
//         upperRightPanel.add( R2 );
//         R3 = new JButton( "R3" );
//         upperRightPanel.add( R3 );
        
//         upperPanel.add(upperLeftPanel, BorderLayout.SOUTH);
//         upperPanel.add(scrollPane, BorderLayout.CENTER);
//         upperPanel.add(upperRightPanel, BorderLayout.EAST);

//         mainFrame.add(upperPanel, BorderLayout.NORTH);

//         // // Redirect console output to the JTextArea
//         // PrintStream printStream = new PrintStream(new OutputStream() {
//         //     @Override
//         //     public void write(int b) {
//         //         // Append the character to the JTextArea
//         //         textArea.append(String.valueOf((char) b));
//         //         // Scroll to the bottom
//         //         textArea.setCaretPosition(textArea.getDocument().getLength());
//         //     }
//         // });
//         // System.setOut(printStream);
//         // System.setErr(printStream); // Optional: Redirect error output as well


//         // ================================

//         // KeypadPanel keypadPanel = new KeypadPanel();
//         // add keypad panel
//         // mainFrame.add(keypadPanel, BorderLayout.SOUTH);

//        JPanel mainPanel = new JPanel( new BorderLayout() );

//         this.textField = new JTextField(20);
//         textField.setHorizontalAlignment(JTextField.RIGHT); // Right-align the text
//         textField.setEditable(false);    // set textArea not editable
//         textField.setText( "Testing" );  // display line1 in textArea 


//         keys = new JButton[ 16 ]; // array keys contains 16 JButtons 

//         // initialize all digit key buttons
//         for ( int i = 0; i <= 9; i++ )
//         {
//             keys[ i ] = new JButton( String.valueOf( i ) );
//         }

//         // initialize all function key buttons
//         this.CANCELKEY = new JButton( "Cancel" );
//         this.CLEARKEY = new JButton( "Clear" );
//         this.ENTERKEY = new JButton( "Enter" );

//         keys[ 10 ] = this.CANCELKEY; // cancel
//         keys[ 11 ] = this.CLEARKEY; // 
//         keys[ 12 ] = ENTERKEY;
//         keys[ 13 ] = new JButton( "" );
//         keys[ 14 ] = new JButton( "00" );
//         keys[ 15 ] = new JButton( "" );

//         // resize button size
//         for ( int i = 0; i < 16; i++ )
//         {
//             keys[ i ].setPreferredSize(new Dimension(20, 10));
//         }

//         // set keyPadJPanel layout to grid layout
//         this.keyPadJPanel = new JPanel();
//         keyPadJPanel.setLayout( new GridLayout( 4, 4 ) );

//         // add buttons to keyPadJPanel panel
//         // 7, 8, 9, Cancel
//         for ( int i = 7; i <= 10; i++ )
//             keyPadJPanel.add( keys[ i ] );

//         // 4, 5, 6
//         for ( int i = 4; i <= 6; i++ )
//             keyPadJPanel.add( keys[ i ] );

//         // Clear
//         keyPadJPanel.add( keys[ 11 ] );

//         // 1, 2, 3
//         for ( int i = 1; i <= 3; i++ )
//             keyPadJPanel.add( keys[ i ] );

//         // Enter
//         keyPadJPanel.add( keys[ 12 ] );

//         keyPadJPanel.add( keys[ 15 ] );
        
//         // 0
//         keyPadJPanel.add( keys[ 0 ] );

//         // 00
//         keyPadJPanel.add( keys[ 14 ] );
        
//         // empty button
//         keyPadJPanel.add( keys[ 13 ] );

//         mainPanel.add(textField, BorderLayout.NORTH);
//         // mainPanel.add(textArea, BorderLayout.NORTH);
//         mainPanel.add(this.keyPadJPanel, BorderLayout.CENTER );
        
//         // add for event listen
//         keypadHandler handler = new keypadHandler();
//         EnterButtonHandler enterHandler = new EnterButtonHandler();

//         // stick the action to the button
//         for (int i = 0; i <= 15; i++)
//         {
//             if ( i == 12 )
//                 continue;
//             keys[ i ].addActionListener(handler);
            
//         }

//         keys[ 12 ].addActionListener(enterHandler);


//         // ================================

//         mainFrame.add(mainPanel, BorderLayout.CENTER);

//         mainFrame.setVisible(true);
//         mainFrame.setResizable( true );
//     }

//     public void displayMessageGUI ( String message )
//     {
//         textArea.append( message );        
//     } // end 

//     public void displayMessageLineGUI ( String message )
//     {
//         message = message.concat( "\n" );
//         textArea.append( message );
//     }
//     private class keypadHandler implements ActionListener 
//    {
//         @Override // in ActionListener
//         public void actionPerformed ( ActionEvent event )
//         {
//             if ( event.getSource() == CANCELKEY ) 
//             {
//                 line1 = "";
//                 textField.setText(line1);
//             }
//             else if ( event.getSource() == CLEARKEY )
//             {
//                 line1 = "";
//                 textField.setText(line1);
//             }
//             else if ( event.getSource() == ENTERKEY )
//             {
//                 if ( line1.length() > 0 )
//                 {
//                     String input = textField.getText();
//                     System.out.println("Input received: " + input); // This simulates sending to the Scanner
//                     Scanner scanner = new Scanner(input); // Create a new Scanner with the input
//                     temp = input;

//                     // Process the input as needed
//                     // Here you can add your logic to handle the input
//                     // For example, you could read numbers or commands from the scanner:
//                     while (scanner.hasNext()) {
//                         String command = scanner.next();
//                         // Do something with the command
//                         System.out.println("Command: " + command);
//                     }

//                     // Clear the text field for new input
//                     textField.setText("");
                    
//                     // line1 = line1.concat( "\n" );
//                     // String input = textField.getText();
//                     // textArea.append( input + "\n" );
//                     // // System.out.println(input); // Print the text field input to the console
//                     // textField.setText( "" );      // Clear the text field
//                     // // textField.setText(line1); }
//                 }
//             }
//             else
//             {
//                 line1 = line1.concat( event.getActionCommand() );
//                 textField.setText(line1);        
//             }
//         }
//    } // end ButtonHandler

//     private class EnterButtonHandler implements ActionListener 
//    {

//       @Override // in ActionListener
//       public void actionPerformed ( ActionEvent event )
//       {
//         String input = textField.getText();
//         if ( event.getSource() == ENTERKEY )
//         {
//             // Parse the input as an integer
//             tempInt = Integer.parseInt(input);
//             textArea.append("Input received: " + tempInt + "\n"); // Display the input in the text area
//             textField.setText(""); // Clear the text field for new input
//             // GUI
//             line1 = line1.concat("\n");
//             textField.setText(line1); 
//         }

//       }
//    } // end ButtonHandler

//     public static int getInputGUI ()
//     {
//         // Wait until a valid input is received
//         while (tempInt == 0) {
//             try {
//                 Thread.sleep(100); // Sleep briefly to avoid busy-waiting
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }
//         return tempInt;
//     }


// } // end class ATM_interface

// // class MyPanel extends JPanel {
// //     @Override
// //     protected void paintComponent(Graphics g) {
// //         super.paintComponent(g);
// //         // Draw the border
// //         drawLines(g);
// //     }

// //     void drawLines(Graphics g) {
// //         Graphics2D Lines = (Graphics2D) g;
// //         Lines.setColor(Color.BLACK); // Set the line color
// //         Lines.drawLine(150, 50, 850, 50); // Upper line of the ATM window
// //         Lines.drawLine(150, 50, 150, 600); // Left height of the ATM window
// //         Lines.drawLine(150, 600, 850, 600); // Lower line of the ATM window
// //         Lines.drawLine(850, 50, 850, 600); // Right height of the ATM window

// //         // Fill the area bounded by the lines
// //         Polygon polygon = new Polygon();
// //         polygon.addPoint(150, 50);
// //         polygon.addPoint(850, 50);
// //         polygon.addPoint(850, 600);
// //         polygon.addPoint(150, 600);
        
// //         g.setColor(Color.GRAY); // Set the color to gray
// //         g.fillPolygon(polygon); // Fill the polygon
// //     }
// // }