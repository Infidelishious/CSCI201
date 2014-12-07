package edu.usc.ianglow.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

class ServerThread extends Thread {
	
	String user;
	Socket mySocket;
	ServerLauncher sl;
	Vector<Message> queue;
	Timer timer;
	TimerTask timerTask;
	private Thread output, input;
	final ServerThread thiss = this;
	Object msgLock = new Object();
	private Recipe recipe;

	public ServerThread (Socket initSocket, final ServerLauncher sl)
	{
		mySocket = initSocket;
		this.sl = sl;

		queue = new Vector<Message>();
//		movementMessages = new Vector<MovmentMessage>(); 

		timerTask = new TimerTask(){
			@Override
			public void run() {
				output.interrupt();
				input.interrupt();
				synchronized(sl)
				{
					sl.serverThreads.remove(thiss);
				} 
			}};
	}

	public void run()
	{		
		output = new Thread(new Runnable(){
			public void run() {
				try {
					ObjectOutputStream br = new ObjectOutputStream(mySocket.getOutputStream());
					int x = 2;
					while(x == 2)
					{
						if(queue.size() > 0)
						{
							br.writeObject(queue.get(0));
							queue.remove(0);
						}
						else
						{
							synchronized(msgLock)
							{
								try {
									msgLock.wait();
								} catch (InterruptedException e) {
									break;
								}
							}
						}
					}
					br.close();
				} catch (IOException ioe) {
					System.out.println( "IOExceptionin Client constructor: " + ioe.getMessage() );
				}
			}
		});
		output.setDaemon(true);
		output.start();

		input = new Thread(new Runnable(){

			@Override
			public void run() {
				ObjectInputStream is;
				
				try
				{	
					is = new ObjectInputStream(mySocket.getInputStream());
				}
				catch (Exception ioe) { 
						System.out.println("Exception in ServerThread.input.run(): "); 
						ioe.printStackTrace();
						timerTask.run();
						return;
					};
				

				while (true)
				{
					Recipe rcp;
					try {
						rcp = (Recipe) is.readObject();
						rcp.st = thiss;
						recipe = rcp;
						sl.recipes.add(rcp);
					}catch (SocketException e) {
						System.out.println("Socket Closed");
						timerTask.run();
						break;
					} 
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		input.setDaemon(true);
		input.start();
	}

	public void SendMessage(Message msg)
	{
		queue.add(msg);
		synchronized(msgLock)
		{
			msgLock.notifyAll();
		}
	}

	public Recipe getRecipe()
	{
//		if(movementMessages.isEmpty()) return null;

		return recipe;
	}
}