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
    private JTextArea textArea; //text area to display output 
    private String line1 = "";

    public KeypadPanel() {
        // super( "My Calculator" );

        textArea =new JTextArea(3,10);  // declaration of textArea for displaying output
        textArea.setEditable(false);    // set textArea not editable
        textArea.setText(line1);  // display line1 in textArea 
        
        keys = new JButton[ 16 ]; // array keys contains 16 JButtons 

        // initialize all digit key buttons
        for ( int i = 0; i <= 9; i++ )
        {
            keys[ i ] = new JButton( String.valueOf( i ) );
        }

        // initialize all function key buttons
        CANCELKEY = new JButton( "Cancel" );
        CLEARKEY = new JButton( "Clear" );
        ENTERKEY = new JButton( "Enter" );

        keys[ 10 ] = CANCELKEY; // cancel
        keys[ 11 ] = CLEARKEY; // 
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
        keyPadJPanel = new JPanel();
        keyPadJPanel.setLayout( new GridLayout( 4, 4 ) );

        // add buttons to keyPadJPanel panel
        // 7, 8, 9, divide
        for ( int i = 7; i <= 10; i++ )
            keyPadJPanel.add( keys[ i ] );

        // 4, 5, 6
        for ( int i = 4; i <= 6; i++ )
            keyPadJPanel.add( keys[ i ] );

        // multiply ->
        keyPadJPanel.add( keys[ 11 ] );

        // 1, 2, 3
        for ( int i = 1; i <= 3; i++ )
            keyPadJPanel.add( keys[ i ] );

        // subtract
        keyPadJPanel.add( keys[ 12 ] );

        keyPadJPanel.add( keys[ 15 ] );
        
        // 0
        keyPadJPanel.add( keys[ 0 ] );

        keyPadJPanel.add( keys[ 14 ] );

        keyPadJPanel.add( keys[ 13 ] );

        // // ., =, add
        // for ( int i = 15; i >= 13; i-- )
        //     keyPadJPanel.add( keys[ i ] );

        // button.setPreferredSize(new Dimension(40, 40));
        add(textArea, BorderLayout.NORTH);
        add( keyPadJPanel, BorderLayout.CENTER );

        // self work
        // add for event listen
        ButtonHandler handler = new ButtonHandler();
        // stick the action to the button
        // keys.addActionListener(handler);

        // correction
        for (int i = 0; i <= 15; i++)
            keys[ i ].addActionListener(handler);
    } // end constructor Keypad_interface
    
       // event handler
   private class ButtonHandler implements ActionListener 
   {

      @Override // in ActionListener
      public void actionPerformed ( ActionEvent event )
      {
        if ( event.getSource() == CANCELKEY ) 
        {
            line1 = "";
            textArea.setText(line1);
        }
        else if ( event.getSource() == CLEARKEY )
        {
            line1 = "";
            textArea.setText(line1);
        }
        else if ( event.getSource() == ENTERKEY )
        {
            line1 = line1.concat("\n");
            textArea.setText(line1); 
        }
        else
        {
            line1 = line1.concat( event.getActionCommand() );
            textArea.setText(line1);        
        }
      }
   } // end ButtonHandler

} // end class KeypadFrame
