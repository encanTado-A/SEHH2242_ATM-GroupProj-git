// Screen.java
// Represents the screen of the ATM
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner; // program uses Scanner to obtain user input
import javax.swing.*;

public class Screen extends JFrame
{
   public static JTextArea textArea;
   private static JPasswordField inputField;
   private static boolean isPasswordInput = false;
   private static boolean keypadInputActivate = true;
   private static boolean availableCancelKey = false;

   private static int tempInt = 0;
   private static int option = -1;

   private JButton CANCELKEY;
   private JButton CLEARKEY;
   private JButton ENTERKEY;

   private JButton keys[];
   private static JButton panelkeys[];

   private JPanel keyPadJPanel;

   private String line1 = "";

   // get lower panel keypad button respond
   public static int getInputGUI ()
   {
      tempInt = -1;
      // Wait until a valid input is received
      while (tempInt == -1) {
         try {
               Thread.sleep(100); // Sleep briefly to avoid busy-waiting
         } catch (InterruptedException e) {
               e.printStackTrace();
         }
      }
      return tempInt;
   } // end static Method getInputGUI

   // get lower panel keypad button respond
   public static int getPasswordInputGUI ()
   {
      tempInt = -1;
      // Wait until a valid input is received
      while (tempInt == -1) {
         try {
               Thread.sleep(100); // Sleep briefly to avoid busy-waiting
         } catch (InterruptedException e) {
               e.printStackTrace();
         }
      }
      return tempInt;
   } // end static Method getPasswordInputGUI

   // get upper panel button respond
   public static int getMenuOptionInputGUI ()
   {
      option = -1;
      // Wait until a valid input is received
      while (option == -1) {
         try {
               Thread.sleep(100); // Sleep briefly to avoid busy-waiting
         } catch (InterruptedException e) {
               e.printStackTrace();
         }
      }
      return option;
   } // end static Method getMenuOptionInputGUI

   // set the keypad availability
   public static void setKeypadInputActivateGUI (boolean tmp)
   {
      keypadInputActivate = tmp;
   } // end Method setKeypadInputActivate

   // set inputField echo charactor: * or normal display
   public void setMask ( boolean tmp )
   {
      isPasswordInput = tmp;
      if ( tmp )
         inputField.setEchoChar('*'); // Mask input with '*'
      else
         inputField.setEchoChar( (char) 0 ); // Mask input with '*'
   } // end Method setMask

   //set when can switch on the function of cancel key
   public static void setCancelKeyActivateGUI (boolean switch_1){
      availableCancelKey = switch_1 ? true : false;
   }
   // displays a message without a carriage return
   public void cleanScreen () 
   {
      textArea.setText( null );

   } // end method cleanScreen

   // displays a message without a carriage return
   public void displayMessage( String message ) 
   {
      textArea.append( message );
   } // end Method displayMessage

   // display a message with a carriage return
   public void displayMessageLine( String message ) 
   {
      message = message.concat( "\n" );
      textArea.append( message );
   } // end Method displayMessageLine

   // display a dollar amount
   public void displayDollarAmount( double amount )
   {
      String tmp = new String();
      tmp = tmp.format("$%,.2f", amount);
      textArea.append( tmp ); 
   } // end method displayDollarAmount 

   // Automatically clean screen after <int seconds>
   public void promptExitInSeconds(int seconds) {
      // Create a timer for the countdown
      Timer timer = new Timer(1000, new ActionListener() {
          int timeLeft = seconds; // Initialize the countdown time
          @Override
          public void actionPerformed(ActionEvent e) {
              if (timeLeft > 0) {
                  displayMessageLine("This execution will exit in " + timeLeft + " seconds.");
                  timeLeft--; // Decrement the time left
              } else {
                  ((Timer)e.getSource()).stop(); // Stop the timer
                  cleanScreen(); // Clear the screen after the countdown ends
              }
          } // end Method actionPerformed
      }); // end class ActionListener
      timer.start(); // Start the timer
   } // end Method promptExitInSeconds

   // pause program in <int seconds> or milisecond if <Mili == true>
   // please make sure that the disrupted time is greater than the prompt exit time at least 2 secs
   public void stopRunning ( int seconds, boolean Mili )
   {
      if (Mili) {
         try {
            Thread.sleep(seconds); // count as Milisecond
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      } else {
         try {
            Thread.sleep(seconds * 1000); // count as second
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   } // end Method stopRunning

   // display <String message> in a "word by word" style, in the speed <int Miliseconds>. 
   // next line by <switchLine == true>
   public void dynamicText(String message, int Miliseconds , boolean switchLine) {
      char[] charArray = message.toCharArray();
      for (char c : charArray) {
         textArea.append(String.valueOf(c));
         stopRunning(Miliseconds, true);
      }
      if(switchLine){
         displayMessageLine(" ");
      }
   } // end Method dynamicText
   
   // main GUI component creation
   public void CreateFrame(String title) {

         JFrame mainFrame = new JFrame(title);
         mainFrame.setSize( 960, 720 ); // width , height
         mainFrame.setLayout( new BorderLayout() );
         mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         mainFrame.setLocationRelativeTo(null); // centre window on run
         
         // ================================
         /*
         * 
         * UPPER PANEL
         * 
         * upperPanel holds:
         *     upperLeftPanel:
         *        Left three JButton object
         *     scrollPane:
         *        Screen: JTextArea object
         *     upperRightPanel:
         *        Right three JButton object
         * 
         */

         JPanel upperPanel = new JPanel(  );
         textArea = new JTextArea( 20, 50 );
         textArea.setEditable( false );
         JScrollPane scrollPane = new JScrollPane( textArea );
         upperPanel.add( scrollPane, BorderLayout.CENTER );
         upperPanel.setPreferredSize( new Dimension( 450 , 300 ) );

         // set textArea color
         textArea.setBackground(Color.decode("#242526"));
         textArea.setForeground(Color.green);

         PanelHandler panelHandler = new PanelHandler();
         panelkeys = new JButton[6];
         for ( int i = 0; i < 5; i++ )
         {
            panelkeys[ i ] = new JButton( String.valueOf( i+1 ) );
            panelkeys[ i ].setPreferredSize( new Dimension(60, 60) ); // width, height
            panelkeys[ i ].addActionListener( panelHandler );
         }

         panelkeys[ 5 ] = new JButton( "" );

         // left three buttons
         JPanel upperLeftPanel = new JPanel();
         upperLeftPanel.setLayout( new GridLayout( 3 , 1 ) );
         upperLeftPanel.add( panelkeys[0] );
         upperLeftPanel.add( panelkeys[1] );
         upperLeftPanel.add( panelkeys[2] );
         
         // right three buttons
         JPanel upperRightPanel = new JPanel();
         upperRightPanel.setLayout( new GridLayout( 3 , 1 ) );
         upperRightPanel.add( panelkeys[3] );
         upperRightPanel.add( panelkeys[4] );
         upperRightPanel.add( panelkeys[5] );
         
         upperPanel.add( upperLeftPanel, BorderLayout.SOUTH );
         upperPanel.add( scrollPane, BorderLayout.CENTER );
         upperPanel.add( upperRightPanel, BorderLayout.EAST );

         mainFrame.add( upperPanel, BorderLayout.NORTH );

         // ================================
         /*
         * 
         * FIELD PANEL
         * 
         * fieldPanel holds:
         *     inputField: JPasswordField object
         * 
         */

         JPanel middlePanel = new JPanel(  );
         JPanel fieldPanel = new JPanel( new BorderLayout() );
         
         fieldPanel.setPreferredSize(new Dimension( 450 , 20 )); // width, height
         inputField = new JPasswordField( 10 );
         
         // general setting of the inputField
         inputField.setHorizontalAlignment( JTextField.RIGHT ); // Right-align the text
         inputField.setEchoChar( (char) 0 ); // normal text display
         inputField.setEditable( false );
         inputField.setText( "Active input via keypad" );  // display line1 in textArea 

         // set textArea color
         inputField.setBackground( Color.decode("#242526") );
         inputField.setForeground( Color.green );

         fieldPanel.add( inputField, BorderLayout.CENTER );
         middlePanel.add( fieldPanel );
         mainFrame.add( middlePanel, BorderLayout.CENTER );

         // ================================
         /*
         * 
         * LOWER PANEL
         * 
         * lowerPanel holds:
         *     keyPadJPanel:
         *        16 JBotton object
         * 
         */

         JPanel lowerPanel = new JPanel(  );
         keys = new JButton[ 16 ]; // array keys contains 16 JButtons 

         // initialize all digit key buttons
         for ( int i = 0; i <= 9; i++ )
         {
            keys[ i ] = new JButton( String.valueOf( i ) );
         }

         // initialize all function key buttons
         this.CANCELKEY = new JButton( "CANCEL" );
         this.CLEARKEY = new JButton( "CLEAR" );
         this.ENTERKEY = new JButton( "ENTER" );

         keys[ 10 ] = this.CANCELKEY; // cancel
         keys[ 11 ] = this.CLEARKEY; // 
         keys[ 12 ] = this.ENTERKEY;
         keys[ 13 ] = new JButton( "" );
         keys[ 14 ] = new JButton( "00" );
         keys[ 15 ] = new JButton( "" );

         // resize button size
         for ( int i = 0; i < 16; i++ )
         {
            keys[ i ].setPreferredSize( new Dimension( 100, 100 ) ); // width, height
         }

         this.keyPadJPanel = new JPanel();

         // general setting of the keypad
         keyPadJPanel.setPreferredSize( new Dimension( 350 , 350 ) ); // width, height
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
         
         // event listener for each button in keypad
         KeypadHandler handler = new KeypadHandler();
         EnterButtonHandler enterHandler = new EnterButtonHandler();

         // stick the action to the button
         for (int i = 0; i <= 15; i++)
         {
            if ( i == 12 )
                  continue;
            keys[ i ].addActionListener(handler);
         }

         // 
         keys[ 12 ].addActionListener(enterHandler);

         lowerPanel.add(this.keyPadJPanel, BorderLayout.CENTER);
         mainFrame.add(lowerPanel, BorderLayout.SOUTH);
         // ================================
         // final adjustment of the frame
         mainFrame.setVisible( true );
         mainFrame.setResizable( false ); // set fixed windows size
    } // end Method CreateFrame

   // Listener classes
   /*
   * upperLeftPanel
   * upperrightPanel
   *     PanelHandler
   * 
   * keyPadJPanel
   *     KeypadHandler
   *     EnterButtonHandler
   * 
   */
   private class KeypadHandler implements ActionListener 
   {
        @Override // in ActionListener
         public void actionPerformed ( ActionEvent event )
         {
            if ( event.getSource() == CANCELKEY ) 
            {
               if ( availableCancelKey )
               {
                  tempInt = -9;
                  option = -9;
               }
            }
            else if ( event.getSource() == CLEARKEY )
            {
                line1 = "";
                inputField.setText(line1);
            }
            else
            {
               if ( keypadInputActivate )
               {
                  line1 = line1.concat( event.getActionCommand() );
                  inputField.setText(line1);
               }
               else
               {
               inputField.setText( "Please input via panel button" );  // display line1 in textArea
               }
            }
        } // end Method actionPerformed
   } // end class ButtonHandler

   private class PanelHandler implements ActionListener 
   {
        @Override // in ActionListener
         public void actionPerformed ( ActionEvent event )
         {
            if ( event.getSource() == panelkeys[0] ) 
            {
               option = 1;
            }
            else if ( event.getSource() == panelkeys[1] )
            {
               option = 2;
            }
            else if ( event.getSource() == panelkeys[2] )
            {
               option = 3;

            }
            else if ( event.getSource() == panelkeys[3] )
            {
               option = 4;

            }
            else if ( event.getSource() == panelkeys[4] )
            {
               option = 5;
            }
            // else if ( event.getSource() == panelkeys[5] )
            // {
            //    option = 9;

            // }
            // else
            // {
            //    // throw new Exception("No this fking option okay");  
            // }
        } // end Method actionPerformed
   } // end class ButtonHandler

    private class EnterButtonHandler implements ActionListener 
   {

      @Override // in ActionListener
      public void actionPerformed ( ActionEvent event )
      {
        String input = new String(inputField.getPassword());
         if ( event.getSource() == ENTERKEY )
         {
            if ( line1.length() > 0 )
            {
               System.out.println("Input received: " + input); // This simulates sending to the Scanner
               if ( !isPasswordInput && keypadInputActivate ) {
                  displayMessage( input );
               }
               
               Scanner scanner = new Scanner(input); // Create a new Scanner with the input
               tempInt = Integer.parseInt(input);

               while (scanner.hasNext()) {
                  scanner.next();
               }

               // Clear the text field for new input
               line1 = "";
               inputField.setText(line1);
            }
            else if ( !keypadInputActivate )
            {
               inputField.setText( "Please input via panel button" );  // display line1 in textArea
               line1 = "";
            }
            else if ( line1.length() == 0 )
            {
               inputField.setText( "Please input via keypad" );  // display line1 in textArea
               line1 = "";
            }
         }
      } // end Method actionPerformed
   } // end class ButtonHandler

} // end class Screen

/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/