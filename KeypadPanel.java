import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class KeypadPanel extends JPanel 
{
    private JButton CANCELKEY;
    private JButton CLEARKEY;
    private JButton ENTERKEY;

    private JButton keys[];
    private JPanel keyPadJPanel;
    // private JTextArea textArea; //text area to display output 
    // private JTextPane textPane;
    private JTextField textField;
    private String line1 = "";

    public KeypadPanel()
    {
        // super( "My Calculator" );

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
            keys[ i ].setPreferredSize(new Dimension(80, 60));
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

        add(mainPanel, BorderLayout.CENTER);
        
    } // end constructor KeypadPanel
    
    // event handler
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
                textField.setText(line1); 
            }
            else
            {
                line1 = line1.concat( event.getActionCommand() );
                textField.setText(line1);        
            }
        }
   } // end ButtonHandler


//    event handler
//    private class ButtonHandler implements ActionListener 
//    {

//       @Override // in ActionListener
//       public void actionPerformed ( ActionEvent event )
//       {
//         if ( event.getSource() == CANCELKEY ) 
//         {
//             line1 = "";
//             textArea.setText(line1);
//         }
//         else if ( event.getSource() == CLEARKEY )
//         {
//             line1 = "";
//             textArea.setText(line1);
//         }
//         else if ( event.getSource() == ENTERKEY )
//         {
//             line1 = line1.concat("\n");
//             textArea.setText(line1); 
//         }
//         else
//         {
//             line1 = line1.concat( event.getActionCommand() );
//             textArea.setText(line1);        
//         }
//       }
//    } // end ButtonHandler

} // end class KeypadFrame
