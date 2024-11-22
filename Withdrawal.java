// Withdrawal.java
// Represents a withdrawal ATM transaction

import java.util.Optional;

public class Withdrawal extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = -9;

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen,
         BankDatabase atmBankDatabase, Keypad atmKeypad,
         CashDispenser atmCashDispenser) {
      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);

      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   // perform transaction
   public void execute() {
      boolean cashDispensed = false; // cash was not dispensed yet
      double availableBalance; // amount available for withdrawal

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();
   
      // loop until cash is dispensed or the user cancels
      do {
         // obtain a chosen withdrawal amount from the user
         amount = displayMenuOfAmounts();

         // check whether user chose a withdrawal amount or canceled
         if (amount != CANCELED) {
            // get available balance of account involved
            availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

            // check whether the user has enough money in the account
            if (amount <= availableBalance) {
               // check whether the cash dispenser has enough money
               if (cashDispenser.isSufficientCashAvailable(amount)) {
                  // update the account involved to reflect withdrawal
                  bankDatabase.debit(getAccountNumber(), amount);

                  cashDispenser.dispenseCash(amount); // dispense cash
                  cashDispensed = true; // cash was dispensed

                  // instruct user to take cash
               screen.dynamicText("\nProcesssing", 50, false);
               screen.dynamicText("...", 150, false);
               screen.stopRunning(3, false);
               screen.cleanScreen();
                  screen.displayMessageLine(
                        "\nPlease take your cash now.");
               } // end if
               else // cash dispenser does not have enough cash
                  screen.displayMessageLine(
                        "\nInsufficient cash available in the ATM." +
                              "\n\nPlease choose a smaller amount.");
            } // end if
            else // not enough money available in user's account
            {
               screen.displayMessageLine(
                     "\nInsufficient funds in your account." +
                           "\n\nPlease choose a smaller amount.");
            } // end else
         } // end if
         else // user chose cancel menu option
         {
            screen.dynamicText("Canceling transaction", 50 , false);
            screen.dynamicText("..." , 150 ,true);
            screen.stopRunning(3, false);
            return; // return to main menu because user canceled
         } // end else
         screen.stopRunning(5, false);
      } while (!cashDispensed);

   } // end method execute

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts() {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      screen.cleanScreen();
      // array of amounts to correspond to menu numbers
      int amounts[] = { 0, 100, 500, 1000 };

      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - $100");// HK$100
         screen.displayMessageLine("2 - $500");// HK$500
         screen.displayMessageLine("3 - $1000");// HK$1000
         screen.displayMessageLine("4 - Custom amount");
         screen.displayMessageLine("CANCEL - Cancel transaction");
         screen.displayMessage("\nChoose a withdrawal amount: ");

         keypad.setKeypadInputActivate( false );
         int input = keypad.getMenuOptionInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch (input) {
            case 1: // if the user chose a withdrawal amount
            case 2: // (i.e., chose option 1, 2, 3), return the
            case 3: // corresponding amount from amounts array
               screen.cleanScreen();
               userChoice = amounts[input]; // save user's choice
               break;
            case 4:
               screen.cleanScreen();
               screen.displayMessageLine("\nInput the multiples of HKD100 for withdrawal (maximum: $20000)");
               screen.displayMessageLine("\nOr press CANCEL to cancel the operation");
               keypad.setKeypadInputActivate(true);
               int input_2 = keypad.getInput();
               userChoice = Optional.of(input_2)
                     .filter(x -> x > 0 && x <= 20000 && x % 100 == 0) // checking valid withdrawal
                     .orElseGet(() -> Optional.of(input_2) // Using nested Optional checks to verify valid case "0" or invalid case
                           .filter(x -> x == -9) // check whether is 0
                           .map(x -> -9) // if -9, return -9 to userChoice to go back withdrawal menu
                           .orElseGet(() -> {
                              screen.dynamicText("\nInvalid input value, operation cancel process launching" , 50 , false);// prompt user 
                              screen.dynamicText("..." , 150 , true);
                              return -9; // return 0 to userChoice to go back withdrawal menu
                           }));
               break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.cleanScreen();
               screen.displayMessageLine("\nInvalid selection. Try again.");
         } // end switch
      } // end while
      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts
} // end class Withdrawal

/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and *
 * Pearson Education, Inc. All Rights Reserved. *
 * *
 * DISCLAIMER: The authors and publisher of this book have used their *
 * best efforts in preparing the book. These efforts include the *
 * development, research, and testing of the theories and programs *
 * to determine their effectiveness. The authors and publisher make *
 * no warranty of any kind, expressed or implied, with regard to these *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or *
 * consequential damages in connection with, or arising out of, the *
 * furnishing, performance, or use of these programs. *
 *************************************************************************/