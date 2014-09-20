//Ian Glow HW2 for CSCI 270
package edu.usc.ianglow;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank{
	private static final int WITHDRAW = 0, DEPOSIT = 1;
	
	ArrayList<User> users;
	User user;
	Scanner sc;
	boolean loop;
	
	public Bank()
	{
		sc = new Scanner(System.in);
		loop = false;
		loadUsers();
		
		while(true)
		{
			int in = 1;
			if(!loop)
				 in = getInt("\nWelcome to the bank.\n1) Existing Account Holder\n2) Open a New Account\nWhat would you like to do? ",1,2,true);

			loop = false;
			String username;
			
			if(in == 2)
			{
				while(true){
					System.out.print("Username: ");
					username = sc.next();
					if(usernameExists(username))
					{
						System.out.println("I’m sorry, but the username \"" + username + "\" is already associated\nwith an account. Please try again (or enter ‘q’ to return to\nthe main menu).");
					}
					else
					{
						if(!username.equalsIgnoreCase("q"))
							System.out.println("Great, that username is not in use!");
						break;
					}
				};
				if(username.equalsIgnoreCase("q")) continue;
				
				System.out.print("Password: ");
				String password = sc.next();
				
				double checking = getDouble("How much would you like to deposit in checking? ", DEPOSIT);
				double savings = getDouble("How much would you like to deposit in savings? ", DEPOSIT);
				
				users.add(new User(username, password, savings, checking));
			}
			else if(in == 1)
			{
				System.out.print("Username: ");
				username = sc.next();
				if(username.equalsIgnoreCase("q"))
					continue;
				
				System.out.print("Password: ");
				String password = sc.next();
				
				user = login(users,username,password);
				if(user != null) 
				{
					sc.nextLine();
					break;	
				}
				System.out.println("\nI’m sorry, but that username and password does not match any\nat our bank. "
						+ "Please try again (or enter ‘q’ to return to the\nmain menu).");
				sc.nextLine();
				loop = true;
			}
		}
		
		System.out.print("\nWelcome to your accounts, " + user.username);
		
		while(true)
		{
			System.out.print("\n1) View Account Information\n2) Make a Deposit\n3) Make a Withdrawal" +
						"\n4) Determine Balance in x Years\n5) Logout\nWhat would you like to do? ");
			int in = getInt("", 1, 5, true);
			if(in == 1){
					System.out.println("You have a " + user.checking.getAccountType() + " account with a balance of " + String.format("$%,.2f", user.checking.getBalance()));
					System.out.println("You have a " + user.savings.getAccountType() + " account with a balance of " + String.format("$%,.2f", user.savings.getBalance()));
			}
			else if(in == 2){
				depositMoney();
			}
			else if(in == 3){
				withdrawMoney();
			}
			else if(in == 4)
			{
				showIntrest();
			}
			else if(in == 5){
				break;
			}
		}
		
		saveUsers();
		System.out.println("Thank you for coming into the bank!");
	}

	private void saveUsers() {
		try {
			for(User i : users)
				i.preSerialize();
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.txt"));
			oos.writeObject(users);
			oos.close();
		} catch (Exception e) {
			System.out.println("Failed to save users");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadUsers() {
		try
		{
			ObjectInputStream oos = new ObjectInputStream( new FileInputStream("Users.txt"));
			users = (ArrayList<User>)oos.readObject();
			for(User i : users) i.postDerialize();
			oos.close();
		}
		catch(Exception e)
		{	
			users = new ArrayList<User>();
		}
	}

	private void showIntrest() {
		int in;
		in = getInt("In how many years?", 0, Integer.MAX_VALUE, false);
		
		//This is done to calculate the number of spaces between things
		user.savings.getBalanceAfterNumYears(in, user);
		int yearS = user.savings.yearSize;
		int amountS = user.savings.balnceSize;
		int	intrestS = user.savings.intrestSize;	
		
		System.out.println("Your " + user.savings.getAccountType() + " account will have the following:");
		
		System.out.println(addTrailingSpaces("Year", yearS) + addTrailingSpaces("Amount", amountS)  + "Intrest");
		System.out.println(addTrailingSpaces("----", yearS) + addTrailingSpaces("------", amountS)  + "-------");
		
		double current = user.savings.getBalance();
		for(int i = 0; i <= in; i++)
		{
			double next = user.savings.getBalanceAfterNumYears(i + 1, user);
			double intrest = next - current;
			
			if(i == in)
				System.out.println(addTrailingSpaces("" + i, yearS) + addTrailingSpaces(String.format("$%,.2f%n", current), amountS)); 
			else
				System.out.println(addTrailingSpaces("" + i, yearS) 
						+ addTrailingSpaces(String.format("$%,.2f", current), amountS)
						+ addTrailingSpaces(String.format("$%,.2f", intrest), intrestS)); 
			
			current = next;
		}
	}

	private void withdrawMoney() {
		int in;
		in = getInt("Here are the accounts you have:" + 
				"\n1) Checking\n2) " + user.savings.getAccountType() +
				"\nFrom which account would you like to withdraw? ", 1, 2, false);
		if(in == 1)
		{
			double money = getDouble("How much to withdraw? ", WITHDRAW);
			while(!user.checking.withdraw(money))
			{
				System.out.println("You do not have " + String.format("$%,.2f", money) + " in your checking account.");
				money = getDouble("How much to withdraw? ", WITHDRAW);
			}
			
			user.updateAccountType();
			System.out.println("" + String.format("$%,.2f", money) + " withdraw from your " + user.checking.getAccountType() + " account");
		}
		else if(in == 2)
		{
			double money = getDouble("How much to withdraw? ", WITHDRAW);
			if(!user.savings.withdraw(money))
				System.out.println("You do not have " + String.format("$%,.2f", money) +" in your " + user.savings.getAccountType() + " account.");
			{
				user.updateAccountType();
				System.out.println("" + String.format("$%,.2f", money) + " withdraw from your " + user.checking.getAccountType() + " account");
			}
		}
	}

	private void depositMoney() {
		int in;
		in = getInt("Here are the accounts you have:" + 
				"\n1) Checking\n2) " + user.savings.getAccountType() 
				+ "\nInto which account would you like to make a deposit? ", 1, 2, false);
		if(in == 1)
		{
			double money = getDouble("How much do you want to put into your checking? ", DEPOSIT);
			user.checking.deposit(money);
			user.updateAccountType();
			System.out.println("" + String.format("$%,.2f", money) + " deposited into your " + user.checking.getAccountType() + " account");
		}
		else if(in == 2)
		{
			double money = getDouble("How much do you want to put into your " + user.savings.getAccountType() + "? ", DEPOSIT);
			user.savings.deposit(money);
			user.updateAccountType();
			System.out.println("" + String.format("$%,.2f", money) + " deposited into your " + user.savings.getAccountType() + " account");
		}
	}
	
	private boolean usernameExists(String username)
	{
		for(User i : users)
		{
			if(i.username.equals(username)) return true;
		}
		return false;
	}
	
	private double getDouble(String prompt, int type)
	{
		double temp;
		while(true)
		{
			try
			{
				System.out.print(prompt);
				temp = sc.nextDouble();
				if(temp <= 0)
				{
					if(type == DEPOSIT)
						System.out.println("You are not allowed to deposit a negative amount.");
					else if (type == WITHDRAW)
						System.out.println("You are not allowed to withdraw a negative amount.");
					
					continue;
				}
				
				sc.nextLine();
				break;
			}
			catch(Exception c)
			{
				String failed = sc.nextLine();
				System.out.println("\"" + failed + "\" is not a valid amount.");
			}
		}

		return temp;
	}
	
	private int getInt(String prompt, int min, int max, boolean endline)
	{
		System.out.print(prompt);
		int temp;
		while(true)
		{
			try
			{
				temp = sc.nextInt();
				if(temp <= 0)
				{
					System.out.println("Number must be positive. Please try again");
					continue;
				}
				else if(temp > max || temp < min)
				{
					System.out.println("That is not an option. Please try again");
					continue;
				}
				
				sc.nextLine();
				
				if(endline)
					System.out.println();
				break;
			}
			catch(Exception c)
			{
				sc.nextLine();
				System.out.println("Error, not a number. Please try again");
			}
		}

		return temp;
	}

	private static User login(ArrayList<User> users, String username, String password)
	{
		for(User i : users)
		{
			if(i.username.equals(username) && i.password.equals(password)) return i;
		}
		return null;
	}
	
	private static String getSpaces(int num, char c)
	{
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < num + 2; i++) 
		{
			if(i > num - 1)
				temp.append(' ');
			else
				temp.append(c);
		}
		return temp.toString();
	}

	private static String addTrailingSpaces(String a, int num)
	{
		if(num < a.length())
			return a + getSpaces(2, ' ');
		return a + getSpaces(num - a.length() + 2, ' ');
	}
	
	public static void main(String[] args)
	{
		new Bank();
	}

}
