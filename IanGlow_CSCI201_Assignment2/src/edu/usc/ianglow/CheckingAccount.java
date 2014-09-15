package edu.usc.ianglow;

public class CheckingAccount extends BaseAccount{


	public CheckingAccount(double balance) {
		super(balance);
	}

	@Override
	protected double getBalanceAfterNumYears(int numYears) {
		return getBalance();
	}

	@Override
	public String getAccountType() {
		return "Checking";
	}

}
