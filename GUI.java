// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.io.OutputStream;
// import java.io.PrintStream;
// import javax.swing.*;

// public class GUI extends JPanel
// {

//     public GUI ()
//     {
//         JPanel mainGUI = new JPanel();

//         JTextArea textArea = new JTextArea();
//         textArea.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(textArea);
//         mainGUI.add(scrollPane, BorderLayout.CENTER);

//         // Redirect console output to the JTextArea
//         PrintStream printStream = new PrintStream(new OutputStream() {
//             @Override
//             public void write(int b) {
//                 // Append the character to the JTextArea
//                 textArea.append(String.valueOf((char) b));
//                 // Scroll to the bottom
//                 textArea.setCaretPosition(textArea.getDocument().getLength());
//             }
//         });

//         System.setOut(printStream);
//         System.setErr(printStream); // Optional: Redirect error output as well
//     }

    
//     private class handler implements ActionListener
//     {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             String input = KeypadPanel.textField.getText();
//             System.out.println(input); // Print the text field input to the console
//             KeypadPanel.textField.setText("");      // Clear the text field
//         }
//     }
// }