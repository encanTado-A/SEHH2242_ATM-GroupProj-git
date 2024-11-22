// ATM.java
// Represents an automated teller machine

public class ATM 
{
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private BankDatabase bankDatabase; // account information database

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int ACCFUNC = 3; //newly added to do specific account functions
   private static final int TRANSFER = 4;
   private static final int EXIT = -9;

   // no-argument ATM constructor initializes instance variables
   public ATM() 
   {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
   } // end no-argument ATM constructor

   // start ATM 
   public void run()
   {
      // welcome and authenticate user; perform transactions
      while ( true )
      {
         // loop while user is not yet authenticated
         while ( !userAuthenticated ) 
         {
            screen.cleanScreen();
            screen.displayMessageLine( "\nWelcome!" );       
            authenticateUser(); // authenticate user
         } // end while

         performTransactions(); // user is now authenticated 
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session 
         screen.dynamicText("\nThank you! Goodbye!\nRemember to take your card!\n" , 50 , true);
         screen.promptExitInSeconds(3);
         screen.stopRunning(5, false);
      } // end while   
   } // end method run

   // attempts to authenticate user against database
   private void authenticateUser() 
   {
      keypad.setCancelKeyActivate(false);
      screen.displayMessage( "\nPlease insert your card and enter your account number: " );
      keypad.setKeypadInputActivate( true );
      int accountNumber = keypad.getInput(); // input account number
      screen.displayMessage( "\nEnter your PIN: " ); // prompt for PIN
      screen.setMask( true );
      int pin = keypad.getPasswordInput(); // input PIN
      screen.setMask( false );

      // set userAuthenticated to boolean value returned by database
      userAuthenticated = 
         bankDatabase.authenticateUser( accountNumber, pin );
      
      // check whether authentication succeeded
      if ( userAuthenticated )
      {
         currentAccountNumber = accountNumber; // save user's account #
         screen.dynamicText("Logging in", 50, false);
         screen.dynamicText("..." , 150 , false);
         screen.stopRunning(2, false);
      } // end if
      else{
         screen.dynamicText("Invalid account number or PIN. Please try again", 30 , false);
         screen.dynamicText("..." , 150 , true);
         screen.promptExitInSeconds(3);
         screen.stopRunning(5, false);
      }
   } // end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions() 
   {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while ( !userExited )
      {     
         // show main menu and get user selection
         keypad.setKeypadInputActivate( false );
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch ( mainMenuSelection )
         {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY: 
            case WITHDRAWAL: 
            case ACCFUNC: //newly added case for specific account type function'
            case TRANSFER: // newly added case for transfer fund function'

               // initialize as new object of chosen type
               currentTransaction = createTransaction( mainMenuSelection );
               currentTransaction.execute(); // execute transaction
               break; 
            case EXIT: // user chose to terminate session
               screen.dynamicText("\nExiting the system", 30 , false);
               screen.dynamicText("...", 150 , false);
               screen.stopRunning(3, false);
               screen.cleanScreen();
               userExited = true; // this ATM session should end
               break;
            default: // user did not enter an integer from 1-4
               screen.displayMessageLine( 
                  "\nYou did not enter a valid selection. Try again." );
               break;
         } // end switch
      } // end while
   } // end method performTransactions
   
   // display the main menu and return an input selection
   private int displayMainMenu()
   {
      int accountType = bankDatabase.getAccountType(currentAccountNumber);
      screen.cleanScreen();
      keypad.setCancelKeyActivate( true );
      screen.displayMessageLine( "\nMain menu:" );
      screen.displayMessageLine( "1 - View my balance" );
      screen.displayMessageLine( "2 - Withdraw cash" );
      //newly added a few lines of code to determine the type of account a show which operation to complete
      if (accountType == 1) { 
          screen.displayMessageLine( "3 - View interest rate" );
      }
      else {
          screen.displayMessageLine( "3 - View cheque limit" );
      }
      screen.displayMessageLine( "4 - Transfer funds" );
      screen.displayMessageLine( "CANCEL - Exit\n" );
      screen.displayMessage( "Enter a choice in panel button: " );
      return keypad.getMenuOptionInput(); // return user's selection
   } // end method displayMainMenu
         
   // return object of specified Transaction subclass
   private Transaction createTransaction( int type )
   {
      Transaction temp = null; // temporary Transaction variable
      
      // determine which type of Transaction to create     
      switch ( type )
      {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
            temp = new BalanceInquiry( 
               currentAccountNumber, screen, bankDatabase );
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            keypad.setKeypadInputActivate( true );
            temp = new Withdrawal( currentAccountNumber, screen, 
               bankDatabase, keypad, cashDispenser );
            break; 
         case ACCFUNC: //create new specific Account Function transaction
            temp = new SpecAccountFunction( currentAccountNumber, screen, bankDatabase);
            break;
         case TRANSFER:
            keypad.setKeypadInputActivate( true );
            temp = new Transfer( currentAccountNumber, screen, bankDatabase, keypad );
            break;
      } // end switch

      return temp; // return the newly created object
   } // end method createTransaction
} // end class ATM



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