package edu.usc.ianglow;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public transient SavingsAccount savings;
	public transient CheckingAccount checking;
	public String username, password;
	public double checkingBalance, savingBalance;
	
	public User()
	{
		
	}
	
	public User(String name, String pass, double savemoney, double checkmoney){
		username = name;
		password = pass;
		checking = new CheckingAccount(checkmoney);
		savings = new Basic(savemoney);
		updateAccountType();
	}
	
	void updateAccountType()
	{
		double totalvalue = savings.getBalance() + checking.getBalance();
		if(totalvalue < 1000.0)
			savings = new Basic(savings.getBalance());
		else if(totalvalue < 10000.0)
			savings = new Premium(savings.getBalance());
		else
			savings = new Deluxe(savings.getBalance());
	}
	
	void postDerialize()
	{
		savings = new Basic(savingBalance);
		checking = new CheckingAccount(checkingBalance);
		updateAccountType();
	}

	void preSerialize()
	{
		savingBalance = savings.getBalance();
		checkingBalance = checking.getBalance();
	}
	
	public String toString(){
		return "Username: " + username + "\nPassword: " + password + "\nMoney: " + (checkingBalance + savingBalance);
	}

}
