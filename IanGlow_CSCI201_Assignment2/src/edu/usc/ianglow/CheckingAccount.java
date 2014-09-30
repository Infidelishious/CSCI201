package edu.usc.ianglow;

public class CheckingAccount extends BaseAccount{


	public CheckingAccount(double balance) {
		super(balance);
	}

	@Override
	protected double getBalanceAfterNumYears(int numYears, User user) {
		return getBalance();
	}

	@Override
	public String getAccountType() {
		return "checking";
	}

}
