package edu.usc.ianglow;

public abstract class BaseAccount {
	
	private double balance;
	
	public BaseAccount(double balance) {
		setBalance(balance);
	}
	public double getBalance() {
		return this.balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	// returns the balance after numYears has passed
	// if the account has interest, this method will account for it
	protected abstract double getBalanceAfterNumYears(int numYears);
	
	// returns a string representing the type of account
	// such as “Checking”, “Deluxe Savings”, etc.
	// Note that the quotation marks will need to be changed if you
	// try to copy and paste from here into Eclipse
	public abstract String getAccountType();
	
	// you need to fill the content of this method
	public boolean withdraw(double amount) {
		if(balance < amount || amount < 0) return false;
		balance -= amount;
		return true;
	}
	
	public boolean deposit(double amount) {
		if(amount < 0) return false;
		balance += amount;
		return true;
	}
}
