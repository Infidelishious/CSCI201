package edu.usc.ianglow;


public abstract class SavingsAccount extends BaseAccount{

	public int intrestSize, balnceSize, yearSize;
	protected double intrestRate;
	
	public SavingsAccount(double balance) {
		super(balance);
	}

	//MUST pass in user or you cannot calculate
	@Override
	protected double getBalanceAfterNumYears(int numYears, User user) {
		
		User temp = new User("Bloop","Bloop",getBalance(), user.checking.getBalance());
		temp.savings.setBalance(getBalance());
		temp.checking.setBalance(user.checking.getBalance());
		double intrest = 0;
		
		for(int i = 0; i < numYears; i++)
		{
			intrest = temp.savings.getBalance() * temp.savings.intrestRate;
			temp.savings.deposit(intrest);
			temp.updateAccountType();
		}
			
		balnceSize = String.format("$%,.2f", temp.savings.getBalance()).length();
		if(balnceSize < 6) balnceSize = 6;
		
		intrestSize = String.format("$%,.2f", intrest).length();
		
		yearSize = ("" + numYears).length();
		if(yearSize < 4) yearSize = 4;
		return temp.savings.getBalance();
	}
}
