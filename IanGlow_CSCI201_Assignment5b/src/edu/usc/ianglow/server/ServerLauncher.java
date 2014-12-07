package edu.usc.ianglow.server;

import java.util.*;
import java.net.*;
import java.io.*;

public class ServerLauncher {

	public final static int WINSCORE = 10;
	static int PORT = 25565;
	
	Vector<Message> queue;
	Vector<Recipe> recipes;
	Vector<ServerThread> serverThreads;
	Object msgLock = new Object();
	Runnable think, output;
	int blueScore = 0, redScore = 0;

	public ServerLauncher (final int port)
	{
		System.out.println("****Server Starting****");
		try {
			System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			System.out.println("IP: N/A");
		}
		
		final ServerLauncher thiss = this;
		queue = new Vector<Message>();
		recipes = new Vector<Recipe>();
		serverThreads = new Vector<ServerThread>();

		output = new Runnable(){
			public void run() {
				int x = 2;
				while(x == 2)
				{
					if(queue.size() > 0)
					{
						Message msg = queue.get(0);
						if(msg != null)
						{
							synchronized(thiss)
							{
								for(ServerThread i : serverThreads)
								{
									//Was i.SendMessage(deepClone(msg));
									i.SendMessage(msg);
									
//									System.out.println("Message sent from : " + msg.from + " to " + i.user);
								}
							}
						}
						queue.remove(0);
					}

				}
			}
		};
		Thread a = new Thread(output);
		a.setDaemon(true);
		a.start();

//		think = new Runnable(){
//			@Override
//			public void run() {
//				while(true)
//				{
//					synchronized(this){
//						try {
//							this.wait(10);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					
////					System.out.println(serverThreads.size() + "usuers");
//					if(serverThreads.size() == 0) continue;
//					
//					while(hasBattleMessage())
//					{
//						sendMessage(getBattleMessage());
//					}
//					
//					while(hasTextMessage())
//					{
//						sendMessage(getTextMessage());
//					}
//					
//					while(hasEventMessage())
//					{
//						EventMessage msg = getEventMessage();
//						
//						if(msg.to.equals("server"))
//						{
//							if(msg.event.equalsIgnoreCase("b"))
//								blueScore++;
//							else
//								redScore++;
//							
//							if(redScore >= WINSCORE)
//							{
//								blueScore = 0;
//								redScore = 0;
//								
//								ResetMessage rs = new ResetMessage();
//								rs.team1win = false;
//								
//								sendMessage(rs);
//							}
//							else if(blueScore >= WINSCORE)
//							{
//								blueScore = 0;
//								redScore = 0;
//								
//								ResetMessage rs = new ResetMessage();
//								rs.team1win = true;
//								
//								sendMessage(rs);
//							}
//						}
//						else
//							sendMessage(msg);
//					}	
//
//					StatusMessage sm = new StatusMessage();
//					sm.from = "server";
//					sm.playerPosition = new Vector<MovmentMessage>();
//					
//					for(ServerThread i : serverThreads)
//					{
//						if(i.getLastMovmentMesssage() != null)
//							sm.playerPosition.add(i.getLastMovmentMesssage());
//					}
//					
//					sm.blueScore = blueScore;
//					sm.redScore = redScore;
//					
//					sendMessage(sm);
//				}
//			}
//		};
//		a = new Thread(think);
//		a.setDaemon(true);
//		a.start();

		a = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					ServerSocket ss = new ServerSocket(port);
					while (true)
					{
						Socket s = ss.accept();
						ServerThread st = new ServerThread(s, thiss);
						synchronized(thiss)
						{
							serverThreads.add(st);
						}
						st.start();
					}
				}
				catch (IOException ioe) { System.out.println("IOException in ServerLauncher Constructor: " + ioe.getMessage()); }
			}
		});
		a.setDaemon(true);
		a.start();
	}

	public void sendMessage(Message msg)
	{
		queue.add(msg);
		synchronized(msgLock)
		{
			msgLock.notifyAll();
		}
	}

	public Message deepClone(Message m) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(m);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Message) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public boolean messageQueued()
	{
		return !queue.isEmpty();
	}
	
	public boolean hasRecepe()
	{
		return !recipes.isEmpty();
	}

	
	public static void main (String[] args)
	{
		ServerLauncher gameServer = new ServerLauncher(PORT);
	}
}


















