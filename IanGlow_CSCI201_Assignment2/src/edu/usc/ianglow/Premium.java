package edu.usc.ianglow;

public class Premium extends SavingsAccount{

	public Premium(double balance) {
		super(balance);
		intrestRate = 0.01;
	}

	@Override
	public String getAccountType() {
		return "premium savings";
	}

}
