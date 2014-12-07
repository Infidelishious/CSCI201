package edu.usc.ianglow.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.Vector;

import edu.usc.ianglow.server.Message;
import edu.usc.ianglow.server.Recipe;

public class MessageManager{
	private Vector<Recipe> queue;
	private Vector<Message> messages;
	
	Socket s;
	Runnable input, output;
	Object msgLock = new Object(),
			teamLock = new Object();
	
	private boolean resetMsg = false;
	private boolean teamWon = true;

	public MessageManager(){
		queue = new Vector<Recipe>();
		messages = new Vector<Message>();
	}

	
	public void init(final Socket s)
	{
		this.s = s;
		
		input = new Runnable(){
			public void run() {
				try {
					ObjectInputStream br= new ObjectInputStream(s.getInputStream());
					int x = 2;
					while(x == 2)
					{
//						System.out.println( "Waiting for message" );
						try {
							Message msg = (Message) br.readObject();
							messages.add(msg);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						System.out.println("Message Object Receaved");
					}
					br.close();
					s.close();
				} catch (IOException ioe) {
					System.out.println( "IOExceptionin Client constructor: " + ioe.getMessage() );
				}
			}
		};
		Thread a = new Thread(input);
		a.setDaemon(true);
		a.start();
		
		output = new Runnable(){
			public void run() {
				try {
					ObjectOutputStream br = new ObjectOutputStream(s.getOutputStream());
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
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					br.close();
					s.close();
				} catch (IOException ioe) {
					System.out.println( "IOExceptionin Client constructor: " + ioe.getMessage() );
				}
			}


		};
		Thread b = new Thread(output);
		b.setDaemon(true);
		b.start();
	}
	
	public void sendRcpToServer(final Recipe msg)
	{
		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("sent rcp");
				queue.add(msg);
				
				synchronized(msgLock)
				{
					msgLock.notifyAll();
				}
			}}).start();
	}
	
	
	public boolean messageQueued()
	{
		return !queue.isEmpty();
	}
	
	public boolean hasMessage()
	{
		return !messages.isEmpty();
	}

	public Message getMessage()
	{
		if(!hasMessage())
			return null;
		
		Message temp = messages.get(0);
		messages.remove(0);
		return temp;
	}
	
}
