package edu.usc.ianglow;

public class Deluxe extends SavingsAccount{

	public Deluxe (double balance) {
		super(balance);
		intrestRate = 0.05;
	}

	@Override
	public String getAccountType() {
		return "deluxe savings";
	}

}