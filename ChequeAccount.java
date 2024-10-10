//newly added subclass of account type - cheque account
import java.util.Scanner;
import java.util.InputMismatchException;

public class ChequeAccount extends Account
{
    //private value
    private int limitPerCheque = 10000;
    //constructor
    public ChequeAccount (int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, int theLimitPerCheque) {
          super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
          theLimitPerCheque = limitPerCheque;
      }
    //overriding account getValue method
    @Override
    public double getValue() {
        return (int)limitPerCheque;
    }
    //method to set the cheque limit
    public void setLimitPerCheque(int newlimit) {
        Scanner input = new Scanner(System.in);
        while (true) {
            try { 
                System.out.println("\nPlease enter the new limit per cheque: \n ");
                newlimit = input.nextInt();
                break;
                } 
            catch (InputMismatchException e) { //if any non integer values are detected, throw error and restart the loop
                System.out.println("You have just entered a wrong value. Please try again.");
                input.nextLine(); //create a new line for user to reinput the values
                }
            }
        limitPerCheque = newlimit;
        input.close();
    }
}
