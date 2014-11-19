package Server;

import java.util.Random;
import java.util.concurrent.Semaphore;


public class NumberServer 
{

	Semaphore connectionPermits = new Semaphore(5);
	Random rand = new Random(System.currentTimeMillis());
	boolean isAllowed = true;
	int counter = 0;
	
	public NumberServer()
	{
		
	}
	
	public void ban()
	{
		isAllowed = false;
		
	}

	public boolean isAllowed() 
	{
		return isAllowed;
	}
	
	
	public Integer getNumber()
	{
		try 
		{
			//If you have not been banned, and there is currently a permit available
			connectionPermits.acquire();
			
			//get the next number
			int val = counter++;
			// wait .1 - 1 second
			Thread.sleep(rand.nextInt(10) * 100);
			//release the permit
			connectionPermits.release();
			//return the number
			return val;
		
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return null;
		
		
	}
	
	
	
}