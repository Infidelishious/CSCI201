package edu.usc.ianglow;

public class Basic extends SavingsAccount{

	public Basic(double balance) {
		super(balance);
		intrestRate = 0.001;
	}

	@Override
	public String getAccountType() {
		return "Basic Savings";
	}

}
