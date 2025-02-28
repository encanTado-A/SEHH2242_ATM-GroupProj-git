// Transfer.java
// Represents a transfer ATM transcation

import java.util.ArrayList;
import java.util.UUID;

public class Transfer extends Transaction {
    private static ArrayList<UUID> transactionRecord = new ArrayList<>();
    private Keypad keypad; // reference to keypad
    private final int CANCEL = -9;

    // Transfer constractor
    public Transfer( int userAccountNumber, Screen atmScreen,
            BankDatabase atmBankDatabase, Keypad atmKeypad )
    {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);

        this.keypad = atmKeypad;
    } // end construster Transfer

    // return false if inputted account number does not exist
    private boolean checkAccountExist( int tmp )
    {
        BankDatabase bankDatabase = getBankDatabase();
        
        // return true if user exist in BankDatabase, else false
        return bankDatabase.authenticateUserExist(tmp) ;
    } // end checkAccountExist

    // return false if no available balance to transfer
    private boolean checkAvailableBalance()
    {
        BankDatabase bankDatabase = getBankDatabase();

        // return true if user have funds in the account, else false
        return bankDatabase.getAvailableBalance(this.getAccountNumber()) > 0;
    } // end checkAvailableBalance

    // generate a unique reference number to transaction record
    private UUID generateUUID ()
    {
        UUID tmp = UUID.randomUUID();
        return tmp;
    } // end generateUUID

    // perform transfer
    private void transfer(int tmpAccountNo, double amount)
    {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // start transfering the aount
        // from owner to designated account
        bankDatabase.debit(getAccountNumber(), amount);

        // debit the same amount to the target transfer account
        bankDatabase.credit(tmpAccountNo, amount);

        // completed transfer
        UUID tmpReferenceNo = generateUUID(); // format: time + account number (only show first 1 + last 1 digit)

        // store reference number
        addATMTransferRecord(tmpReferenceNo);

        screen.dynamicText("Transfer in progress" , 50 , false);
        screen.dynamicText("..." , 150 , false);
        screen.stopRunning(3, false);
        screen.cleanScreen();
        screen.displayMessageLine("\nTransfer has completed.");
        screen.displayMessageLine("From: \tAccount number:\t" + this.getAccountNumber());
        screen.displayMessageLine("To: \tAccount number:\t" + tmpAccountNo);
        screen.displayMessageLine("Transfer amount: " + amount);
        screen.displayMessageLine("Reference no.: " + tmpReferenceNo + "\n");

        // getATMTransferRecord();
    } // end transfer


    public void addATMTransferRecord (UUID tmpReferenceNumber) 
    {
        this.transactionRecord.add(tmpReferenceNumber);
    } // end addATMTransferRecord

    // perform record checking (Assume for Admin / maintenance use only )
    public void getATMTransferRecord ()
    {
        Screen screen = getScreen();

        // print out the transactionRecord
        screen.displayMessageLine("Transfer transaction record thus far");
        screen.displayMessageLine("Last update: NULL"); // no time implemented

        // top bar
        screen.displayMessageLine("\trecord count\treference number");
        int index = 0;
        for ( UUID record :  this.transactionRecord )
        {
            screen.displayMessageLine("\t" + index++ + "\t\t"+ record);
        }

        screen.displayMessageLine("All record has been displayed");
    } // end getATMTransferRecord

    @Override
    // run when called
    public void execute()
    {
        // get references to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        
        // temporary integer variable for storing user input
        int tmpConfirmation = 0;
        int tmpAccountNo = 0;
        double amount = 0;

        // flag variables for conditions
        boolean canceled = false;
        boolean flagAllClear = false;
        boolean flagUserExist = false;
        boolean flagAmountValid = false;
        boolean flagTransferAvailable = false;
        boolean flagConfirmTranscation = false;
        boolean flagTransferSuccess = false;

        screen.cleanScreen();

        while (!flagAllClear) {

            while (!flagTransferAvailable)
            {
                screen.displayMessageLine("\nPlease enter the account number that you want to transfer");
                screen.displayMessageLine("\nOr press CANCEL to cancel the operation");
                keypad.setKeypadInputActivate( true );
                tmpAccountNo = keypad.getInput();
                keypad.setKeypadInputActivate( false );

                // user wants to exit transfer action
                if (tmpAccountNo == CANCEL) {
                    canceled = true;
                    break;
                } // end if

                // check if user have not enough amount to transfer
                if (checkAvailableBalance() == false)
                {
                    screen.displayMessageLine("\nInsufficient balance to transfer.\n");
                    canceled = true;
                    screen.stopRunning(3, false);
                    screen.cleanScreen();
                    break;
                } // end if

                // check if the recipient account does not exist
                flagUserExist = checkAccountExist( tmpAccountNo );

                if (flagUserExist == false || tmpAccountNo == this.getAccountNumber() )
                {
                    screen.displayMessageLine("\nInvalid account number."
                                            + "\nplease try again.");
                    screen.stopRunning(3, false);
                    screen.cleanScreen();
                    continue;
                } // end if

                flagTransferAvailable = true;
            } // end while (!flagTransferAvailable)

            while (flagTransferAvailable && !flagAmountValid)
            {
                screen.cleanScreen();
                // diplay the presented account amount
                screen.displayMessage("\nAvailable balance: ");
                screen.displayDollarAmount(bankDatabase.getAvailableBalance(this.getAccountNumber()));
                screen.displayMessageLine("\n");

                // get transfer amount
                screen.displayMessageLine("Please enter the amount (in cent) you wish to transfer");
                screen.displayMessageLine("E.g. amount 100 = 1.00");
                screen.displayMessageLine("\nOr press CANCEL to cancel the operation");
                keypad.setKeypadInputActivate( true );
                amount = keypad.getInput();
                keypad.setKeypadInputActivate( false );

                // user wants to exit transfer action
                if (amount == CANCEL)
                {
                    canceled = true;
                    break; // exit the while loop and exited
                } else if (amount <= 0) 
                {
                    screen.displayMessageLine("\nInvaild amount. Please try again");
                    screen.stopRunning(3, false);
                    continue;
                } else{
                    amount = amount / 100;
                }

                if (bankDatabase.getAvailableBalance( this.getAccountNumber() ) < amount)
                {
                    screen.displayMessageLine("\nInsufficient balance. Please try again");
                    screen.stopRunning(3, false);
                    continue;
                }

                // exit loop
                flagAmountValid = true;
            } // end while (flagTransferAvailable && !flagAmountValid)

            while (!flagTransferSuccess && ((flagTransferAvailable && flagAmountValid) && !canceled))
            {
                screen.cleanScreen();
                screen.displayMessageLine("\nConfirm meun:");
                screen.displayMessageLine("From: \tAccount number:\t" + this.getAccountNumber());
                screen.displayMessageLine("To: \tAccount number:\t" + tmpAccountNo);
                screen.displayMessageLine("Transfer amount: " + amount);

                screen.displayMessageLine("\nEnter panel key 1 to confirm, "
                                + "\nEnter panel key 2 to re-input the amount, "
                                + "\n\nOr press CANCEL to cancel the operation");

                keypad.setCancelKeyActivate( true );
                tmpConfirmation = keypad.getMenuOptionInput();
                
                switch (tmpConfirmation)
                {
                    case CANCEL:
                        // cancel transaction and exit
                        canceled = true;                        
                        break;
                    case 1:
                        flagConfirmTranscation = true;
                        break;
                    case 2:
                        // re-enter transfer amount
                        flagAmountValid = false;
                        break;
                    default:
                        screen.displayMessageLine("Invalid option."
                                                    + "Please try again");
                        screen.stopRunning(3, false);
                        continue;
                        // throw new AssertionError();
                } // end switch

                flagTransferSuccess = flagAmountValid && flagConfirmTranscation;
            } // end while (!flagTransferSuccess && ((flagTransferAvailable && flagAmountValid) && !canceled))

            if (canceled)
                break;

            if (flagTransferSuccess)
                flagAllClear = true;
        } // end while (flagAllClear)

        // proceed to complete transaction 
        if (flagConfirmTranscation)
        {
            transfer(tmpAccountNo, amount);
            screen.stopRunning(5, false);
            return;
        } // end if

        // exit transaction
        screen.dynamicText("\nCanceling transaction" , 50 , false);
        screen.dynamicText("...", 150, false);
        screen.stopRunning(3, false);
    } // end execute
} // end Transfer