//newly added transaction subclass for displaying specific account type values (Interest rate or Cheque limit)
public class SpecAccountFunction extends Transaction
{
   //constructor
   public SpecAccountFunction( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase )
   {
      super( userAccountNumber, atmScreen, atmBankDatabase );
   }
   public void execute() { 
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();
      int userAccountNumber = getAccountNumber();
      int accountType = bankDatabase.getAccountType(userAccountNumber); //get account type
      
      if (accountType == 1) { //shows the interest rate if its a saving account
          double interestRate = bankDatabase.getInterestRate(userAccountNumber);
          String shownRate = Double.toString(interestRate) + "% per annum";
          screen.displayMessageLine( "\nAccount Type: Saving Account" );
          screen.displayMessageLine( "\nInterest Rate: ");
          screen.displayMessageLine( shownRate );
          screen.displayMessageLine( " ");
      } 
      else { //shows the cheque limit if its a cheque account
          int chequelimit = bankDatabase.getChequeLimit(userAccountNumber);
          String shownLimit = "$" + chequelimit;
          screen.displayMessageLine( "\nAccount Type: Cheque Account" );
          screen.displayMessageLine( "\nCheque Limit: ");
          screen.displayMessageLine( shownLimit );
          screen.displayMessageLine( " ");          
      }
   }
}
