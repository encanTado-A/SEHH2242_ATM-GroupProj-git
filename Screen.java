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
   private static String temp = "";
   private static int tempInt = 0;
   private static int option = -1;

   private JButton CANCELKEY;
   private JButton CLEARKEY;
   private JButton ENTERKEY;

   private JButton keys[];
   private static JButton panelkeys[];

   private JPanel keyPadJPanel;

   private String line1 = "";

   private static boolean isPasswordInput = false;

   public void CreateFrame(String title) {

         JFrame mainFrame = new JFrame(title);
         mainFrame.setSize(800, 600);
         mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         // ================================

         JPanel upperPanel = new JPanel();
         textArea = new JTextArea(20, 50);
         textArea.setEditable(false);
         JScrollPane scrollPane = new JScrollPane(textArea);
         // upperPanel.add(scrollPane, BorderLayout.CENTER);

         JPanel upperLeftPanel = new JPanel();
         PanelHandler panelHandler = new PanelHandler();
         panelkeys = new JButton[6];
         for ( int i = 0; i < 5; i++ )
         {
            panelkeys[ i ] = new JButton( String.valueOf( i+1 ) );
            panelkeys[ i ].setPreferredSize(new Dimension(60, 60));
            panelkeys[ i ].addActionListener(panelHandler);
         }

         panelkeys[ 5 ] = new JButton( String.valueOf( 9 ) );
         panelkeys[ 5 ].setPreferredSize(new Dimension(60, 60));
         panelkeys[ 5 ].addActionListener(panelHandler);

         upperLeftPanel.setLayout( new GridLayout( 3 , 1 ) );
         upperLeftPanel.add( panelkeys[0] );
         upperLeftPanel.add( panelkeys[1] );
         upperLeftPanel.add( panelkeys[2] );

         JPanel upperRightPanel = new JPanel();
         upperRightPanel.setLayout( new GridLayout( 3 , 1 ) );
         upperRightPanel.add( panelkeys[3] );
         upperRightPanel.add( panelkeys[4] );
         upperRightPanel.add( panelkeys[5] );
         
         upperPanel.add(upperLeftPanel, BorderLayout.SOUTH);
         upperPanel.add(scrollPane, BorderLayout.CENTER);
         upperPanel.add(upperRightPanel, BorderLayout.EAST);

         mainFrame.add(upperPanel, BorderLayout.NORTH);

         // ================================

         JPanel mainPanel = new JPanel( new BorderLayout() );

         inputField = new JPasswordField(20);
         inputField.setHorizontalAlignment(JTextField.RIGHT); // Right-align the text
         inputField.setEchoChar((char) 0);
         inputField.setEditable(false);    // set textArea not editable
         inputField.setText( "Testing" );  // display line1 in textArea 


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

         mainPanel.add(inputField, BorderLayout.NORTH);
         // mainPanel.add(textArea, BorderLayout.NORTH);
         mainPanel.add(this.keyPadJPanel, BorderLayout.CENTER );
         
         // add for event listen
         KeypadHandler handler = new KeypadHandler();
         EnterButtonHandler enterHandler = new EnterButtonHandler();

         // stick the action to the button
         for (int i = 0; i <= 15; i++)
         {
            if ( i == 12 )
                  continue;
            keys[ i ].addActionListener(handler);
         }

         keys[ 12 ].addActionListener(enterHandler);

         // ================================

         mainFrame.add(mainPanel, BorderLayout.CENTER);

         mainFrame.setVisible(true);
         mainFrame.setResizable( true );
    }

    private class KeypadHandler implements ActionListener 
   {
        @Override // in ActionListener
         public void actionPerformed ( ActionEvent event )
         {
            if ( event.getSource() == CANCELKEY ) 
            {
                line1 = "";
                inputField.setText(line1);
            }
            else if ( event.getSource() == CLEARKEY )
            {
                line1 = "";
                inputField.setText(line1);
            }
            else
            {
                line1 = line1.concat( event.getActionCommand() );
                inputField.setText(line1);        
            }
        }
   } // end ButtonHandler

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
            else if ( event.getSource() == panelkeys[5] )
            {
               option = 9;

            }
            else
            {
               // throw new Exception("No this fking option okay");  
            }
        }
   } // end ButtonHandler

    private class EnterButtonHandler implements ActionListener 
   {

      @Override // in ActionListener
      public void actionPerformed ( ActionEvent event )
      {
        String input = inputField.getText();
         if ( event.getSource() == ENTERKEY )
         {
            if ( line1.length() > 0 )
            {
               System.out.println("Input received: " + input); // This simulates sending to the Scanner
               if ( !isPasswordInput ) {
                  displayMessage( input );
               }

               Scanner scanner = new Scanner(input); // Create a new Scanner with the input
               tempInt = Integer.parseInt(input);

               while (scanner.hasNext()) {
                  String command = scanner.next();
                  // // Do something with the command
                  // System.out.println("Command: " + command);
               }

               // Clear the text field for new input
               line1 = "";
               inputField.setText(line1);
            }
         }
      } 
   } // end ButtonHandler

   public static int getInputGUI ()
   {
      tempInt = 0;
      // Wait until a valid input is received
      while (tempInt == 0) {
         try {
               Thread.sleep(100); // Sleep briefly to avoid busy-waiting
         } catch (InterruptedException e) {
               e.printStackTrace();
         }
      }
      return tempInt;
   }

   public static int getPasswordInputGUI ()
   {
      tempInt = 0;
      // Wait until a valid input is received
      while (tempInt == 0) {
         try {
               Thread.sleep(100); // Sleep briefly to avoid busy-waiting
         } catch (InterruptedException e) {
               e.printStackTrace();
         }
      }
      return tempInt;
   }

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
   }

   public void setMask ( boolean tmp )
   {
      isPasswordInput = tmp;
      if ( tmp )
         inputField.setEchoChar('*'); // Mask input with '*'
      else
         inputField.setEchoChar( (char) 0 ); // Mask input with '*'
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
   } // end method displayMessage

   // display a message with a carriage return
   public void displayMessageLine( String message ) 
   {
      message = message.concat( "\n" );
      textArea.append( message );
   } // end method displayMessageLine

   // display a dollar amount
   public void displayDollarAmount( double amount )
   {
      String tmp = new String();
      tmp = tmp.format("$%,.2f", amount);
      textArea.append( tmp ); 
   } // end method displayDollarAmount 
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