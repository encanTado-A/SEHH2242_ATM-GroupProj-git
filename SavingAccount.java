//newly added subclass of account type - saving account
import java.util.Scanner;
import java.util.InputMismatchException;

public class SavingAccount extends Account
{
    //private value
    private double interestRate = 0.005;
    //constructor of the savinga account derived from account
    public SavingAccount(int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, double theInterestRate) {
          super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
          theInterestRate = interestRate;
      }
    //overriding account getValue method
    @Override
    public double getValue() {
        return interestRate;
    }
    //method to set the interest rate
    public void setInterestRate(double newrate) {
        Scanner input = new Scanner(System.in);
        while (true) {
            try { 
                System.out.println("\nPlease enter the new interest rate per annum (50% = 50): \n ");
                newrate = input.nextDouble();
                newrate *= 0.01;
                break;
                } 
            catch (InputMismatchException e) { //if any non integer values are detected, throw error and restart the loop
                System.out.println("You have just entered a wrong value. Please try again.");
                input.nextLine(); //create a new line for user to reinput the values
                }
            }
        interestRate = newrate;
        input.close();
    }
}
