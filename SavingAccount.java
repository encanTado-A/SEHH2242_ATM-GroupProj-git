//newly added subclass of account type - saving account
import java.util.Scanner;
import java.util.InputMismatchException;

public class SavingAccount extends Account
{
    //private value
    private double interestRate;
    //constructor of the savinga account derived from account
    public SavingAccount(int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, double theInterestRate) {
          super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
          theInterestRate = setInterestRate(theInterestRate);
      }
    //overriding account getValue method
    @Override
    public double getValue() {
        return interestRate;
    }
    //method to set the interest rate
    public double setInterestRate(double rate) {
        interestRate = rate;
        return interestRate;
    }
}
