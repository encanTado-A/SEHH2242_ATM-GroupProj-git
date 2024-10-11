// Transfer.java
// Represents a transfer ATM transcation

// import java.time;

public class Transfer extends Transaction {

    // private Account ownerAccount;
    // private BankDatabase bankDatabaseSub;
    private Keypad keypad;
    // private CashDispenser cashDispenser; // reference to cash dispenser

    private final int MAXINPUTCOUNT = 3;

    // Transfer constractor
    public Transfer( int userAccountNumber, Screen atmScreen,
         BankDatabase atmBankDatabase, Keypad atmKeypad )
    {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);

        this.keypad = atmKeypad;
    } // end Transfer

    // return false if inputted account number does not exist
    public boolean checkAccountExist( int tmp )
    {
        BankDatabase bankDatabase = getBankDatabase();
        
        return bankDatabase.authenticateUserExist(tmp) ;
    } // end checkAccountExist

    // return false if no available balance to transfer
    private boolean checkAvailableBalance()
    {
        BankDatabase bankDatabase = getBankDatabase();

        return bankDatabase.getAvailableBalance(this.getAccountNumber()) > 0;
    } // end checkAvailableBalance

    // perform transfer
    private void transfer(int tmpAccountNo, int amount)
    {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // start transfering the aount
        // from owner to designated account
        bankDatabase.debit(getAccountNumber(), amount);

        // debit the same amount to the target transfer account
        bankDatabase.debit(tmpAccountNo, amount);

        // completed transfer
        int tmpReferenceNo = 0; // format: time + account number (only show first 1 + last 1 digit)

        screen.displayMessageLine("\nTransfer in progress...\n");
        screen.displayMessageLine("\nTransfer has completed.");
        screen.displayMessageLine("Reference no.: " + tmpReferenceNo + "\n");
    }

    @Override
    public void execute()
    {
        // get references to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // int noOfTry = 0;
        int tmpConfirmation = 0;
        int tmpAccountNo = 0;
        int amount = 0;

        boolean flagAllClear = false;
        boolean CANCELED = false;
        boolean flagTransferSuccess = false;
        boolean flagUserExist = false;
        boolean flagConfirmTranscation = false;
        boolean flagReinput = false;
        boolean flagAmountValid = false;

        while (!flagAllClear) {

            while (!flagUserExist)
            {
                screen.displayMessageLine("\nPlease enter the account number that you want to transfer, or enter 0 to cancel the operation");
                tmpAccountNo = keypad.getInput();

                // user wants to exit transfer action
                if (tmpAccountNo == 0) {
                    CANCELED = true;
                    break;
                } // end if

                // check if user have not enough amount to transfer
                if (checkAvailableBalance() == false)
                {
                    screen.displayMessageLine("\nInsufficient balance to transfer.");
                    break;
                } // end if

                // check if the account not exist
                flagUserExist = checkAccountExist(tmpAccountNo);
            } // end while

            if (flagUserExist == false || tmpAccountNo == this.getAccountNumber() )
            {
                screen.displayMessageLine("\nInvalid account number."
                                        + "\nplease try again.\n");
                continue;
            } // end if
            else
            {
                while (flagUserExist && !flagAmountValid)
                {
                    // diplay the presented account amount
                    screen.displayMessage("\nAvailable balance: ");
                    screen.displayDollarAmount(bankDatabase.getAvailableBalance(this.getAccountNumber()));
                    screen.displayMessageLine("\n");

                    // get transfer amount
                    screen.displayMessageLine("Please enter the amount you wish to transfer, or enter 0 to cancel the operation");
                    amount = keypad.getInput();

                    // user wants to exit transfer action
                    if (amount == 0)
                    {
                        CANCELED = true;
                        break;
                    }

                    if (bankDatabase.getAvailableBalance(this.getAccountNumber()) < amount)
                    {
                        screen.displayMessageLine("\nInsufficient balance. Please try again");
                        // re-prompt again to let user input
                        continue;
                    }

                    // exit loop
                    flagAmountValid = true;
                    // flagTransferSuccess = true;
                } // end while (!flagTransferSuccess)
            } // end else

            while (!flagTransferSuccess && ((flagUserExist && flagAmountValid) && !CANCELED))
            {
                screen.displayMessageLine("Confirm meun:");
                screen.displayMessageLine("Account number: ");
                screen.displayMessageLine("Transfer amount: " + amount);

                screen.displayMessageLine("Enter 1 to confirm, "
                                + "enter 2 to re-input the amount, "
                                + "or enter 0 to cancel the operation");
                tmpConfirmation = keypad.getInput();

                switch (tmpConfirmation)
                {
                    case 0:
                        CANCELED = true;                        
                        break;
                    case 1:
                        flagConfirmTranscation = true;
                        break;
                    case 2:
                        flagAmountValid = false;
                        break;
                    default:
                        screen.displayMessageLine("Invalid option."
                                                    + "Please try again");
                        continue;
                        // throw new AssertionError();
                } // end switch

                flagTransferSuccess = flagAmountValid && flagConfirmTranscation;
            } // end while

            if (CANCELED)
                break;

            if (flagTransferSuccess)
                flagAllClear = true;
        } // end while (flagAllClear)

        // proceed to complete transaction 
        if (flagConfirmTranscation)
        {
            transfer(tmpAccountNo, amount);
            return;
        } // end if

        // exit transaction
        screen.displayMessageLine("\nCanceling transaction...");
    } // end execute
} // end Transfer