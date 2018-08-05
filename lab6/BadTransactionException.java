/* BadTransactionException.java */

/**
 *  Implements an exception that should be thrown for bad transaction.
 **/
public class BadTransactionException extends Exception {

	public int money;
  

  /**
   *  Creates an exception object for BadTransactionException.
   **/
  public BadTransactionException(int badMoney) {
    super("Invalid amount of money: " + badMoney);
	money = badMoney;
  }
}