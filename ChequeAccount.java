//newly added subclass of account type - cheque account

public class ChequeAccount extends Account
{
    //private value
    private int limitPerCheque;
    //constructor
    public ChequeAccount (int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, int theLimitPerCheque) {
          super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
          theLimitPerCheque = setLimitPerCheque(theLimitPerCheque);
      }
    //overriding account getValue method
    @Override
    public double getValue() {
        return (int)limitPerCheque;
    }
    //method to set the cheque limit
    public int setLimitPerCheque(int limit) {
        limitPerCheque = limit;
        return limitPerCheque;
    }
}
