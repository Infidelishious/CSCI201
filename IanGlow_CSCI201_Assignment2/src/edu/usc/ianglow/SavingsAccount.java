package edu.usc.ianglow;


public abstract class SavingsAccount extends BaseAccount{

	double intrestRate;
	
	public SavingsAccount(double balance) {
		super(balance);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double getBalanceAfterNumYears(int numYears) {
		return getBalance() * Math.pow(1 + intrestRate, numYears);
	}
}
