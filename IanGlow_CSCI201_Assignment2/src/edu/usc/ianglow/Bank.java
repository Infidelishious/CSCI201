package edu.usc.ianglow;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank{
	ArrayList<User> users;
	User user;
	Scanner sc;
	
	@SuppressWarnings("unchecked")
	public Bank()
	{
		sc = new Scanner(System.in);
		
		try
		{
			ObjectInputStream oos = new ObjectInputStream( new FileInputStream("Users.txt"));
			users = (ArrayList<User>)oos.readObject();
			for(User i : users) i.postDerialize();
			oos.close();
		}
		catch(Exception e)
		{	
			e.printStackTrace();
			System.out.println("No File found, creating arraylist");
			users = new ArrayList<User>();
		}
		
		while(true)
		{
			System.out.print("Welcome to the bank.\n1) Existing Account Holder\n2) Open a New Account\nWhat would you like to do?");
			int in = getInt("",1,2);
			String username;
			if(in == 2)
			{
				do{
					System.out.println("Username:");
					username = sc.nextLine();
					if(usernameExists(username))
					{
						System.out.println("I’m sorry, but the username \"" + username + "\" is already associated\nwith an account. Please try again (or enter ‘q’ to return to\nthe main menu)");
						username = sc.nextLine();
					}
					else
					{
						break;
					}
				}while(!username.equalsIgnoreCase("q"));
				if(username.equalsIgnoreCase("q")) continue;
				
				System.out.println("Password:");
				String password = sc.next();
				
				double checking = getDouble("How much would you like to deposit in checking?");
				double savings = getDouble("How much would you like to deposit in savings?");
				
//				System.out.println("Username: " + username + "\nPassword: " + password + "\nMoney: " + (checking + savings));
				users.add(new User(username, password, savings, checking));
			}
			else if(in == 1)
			{
				//login
				System.out.println("Username:");
				username = sc.next();
				System.out.println("Password:");
				String password = sc.next();
				System.out.println("Username: " + username + "\nPassword: " + password);
				user = login(users,username,password);
				if(user != null) 
				{
					System.out.println("Logged in!");
					sc.nextLine();;
					break;	
				}
				System.out.println("\n*Username or password incorect*\n");
				sc.nextLine();;
			}
		}
		
		while(true)
		{
			System.out.print("Welcome to your accounts, " + user.username + "\n1) View Account Information\n2) Make a Deposit\n3) Make a Withdrawal" +
						"\n4) Determine Balance in x Years\n5) Logout\nWhat would you like to do? ");
			int in = getInt("", 1, 5);
			if(in == 1){
					System.out.println("You have a checking account with a balance of " + user.checking.getBalance());
					System.out.println("You have a " + user.savings.getAccountType() + " account with a balance of " + user.savings.getBalance());
			}
			else if(in == 2){
				System.out.println("Here are the accounts you have:" + 
						"\n1) Checking\n2) " + user.savings.getAccountType());
				in = getInt("", 1, 2);
				if(in == 1)
				{
					double money = getDouble("How much do you want to put into checking?");
					user.checking.deposit(money);
					System.out.println("" + money + " deposited");
					sc.nextLine();;
				}
				else if(in == 2)
				{
					double money = getDouble("How much do you want to put into savings?");
					user.savings.deposit(money);
					user.updateAccountType();
					System.out.println("" + money + " deposited");
					sc.nextLine();;
				}
			}
			else if(in == 3){
				System.out.println("Here are the accounts you have:" + 
						"\n1) Checking\n2) " + user.savings.getAccountType());
				in = getInt("", 1, 2);
				if(in == 1)
				{
					double money = getDouble("How much do you want to withdraw from checking?");
					if(!user.checking.withdraw(money))
						System.out.println("You do not have that amount to withdraw");
					else
					{
						user.updateAccountType();
						System.out.println("" + money + " withdraw");
					}
					sc.nextLine();;
				}
				else if(in == 2)
				{
					double money = getDouble("How much do you want to withdraw from savings?");
					if(!user.savings.withdraw(money))
						System.out.println("You do not have that amount to withdraw");
					{
						user.updateAccountType();
						System.out.println("" + money + " withdraw");
					}
					sc.nextLine();;
				}
			}
			else if(in == 4)
			{
				System.out.println("In how many years?");
				in = getInt("", 0, Integer.MAX_VALUE);
				System.out.println("Year    Amount    Intrest");
				System.out.println("----    ------    -------");
				double current = user.savings.getBalance();
				for(int i = 0; i <= in; i++)
				{
					double next = user.savings.getBalanceAfterNumYears(i + 1);
					double intrest = next - current;
					if(i == in)
						System.out.printf(i + "    $%,.2f%n", current); 
					else
						System.out.printf(i + "    $%,.2f    $%,.2f%n", current, intrest); 
					
					current = next;
				}
			}
			else if(in == 5){
				break;
			}
		}
		
		System.out.println("Thank you for coming into the bank!");
		sc.nextLine();;
		
		try {
			for(User i : users){
				i.preSerialize();
//				System.out.println(i.toString());
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.txt"));
			oos.writeObject(users);
			oos.close();
		} catch (Exception e) {
			System.out.println("Failed to save users");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new Bank();
	}
	
	boolean usernameExists(String username)
	{
		for(User i : users)
		{
			if(i.username.equals(username)) return true;
		}
		return false;
	}
	
	private double getDouble(String prompt)
	{
		System.out.println(prompt);
		double temp;
		while(true)
		{
			try
			{
				temp = sc.nextDouble();
				if(temp <= 0)
				{
					System.out.println("Number must be positive. Please try again");
					continue;
				}
				
				sc.nextLine();
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
	
	private int getInt(String prompt, int min, int max)
	{
		System.out.println(prompt);
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

	static User login(ArrayList<User> users, String username, String password)
	{
		for(User i : users)
		{
			if(i.username.equals(username) && i.password.equals(password)) return i;
		}
		return null;
	}
	
}
