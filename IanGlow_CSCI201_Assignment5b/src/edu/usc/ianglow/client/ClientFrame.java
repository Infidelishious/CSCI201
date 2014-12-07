package edu.usc.ianglow.client;

import java.awt.BorderLayout;
//import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.usc.ianglow.server.Action;
import edu.usc.ianglow.server.MainFrame;
import edu.usc.ianglow.server.Message;
import edu.usc.ianglow.server.Recipe;

public class ClientFrame extends JFrame implements ActionListener{

	public Socket socket;
	public ItemPanel itemPanel;
	public JPanel mainPanel, bottomPanel, responsePanel;
	public JButton button;
	public JTextField resp;
	public ArrayList<InstructionPanel> instructions;
	public MessageManager messageManager;
	public ScrollPane scroll;
	private Thread painter;
	final ClientFrame thiss = this;
	Object drawLock = new Object();

	public ClientFrame()
	{
		super("Order Frame");
		setLayout(new BorderLayout());
		

	    messageManager = new MessageManager();
		mainPanel = new JPanel();
		new CustomDialog(this, this).setVisible(true);
		messageManager.init(socket);

		instructions = new ArrayList<InstructionPanel>();


		button = new JButton("Send Request");
		button.addActionListener(this);

		itemPanel = new ItemPanel(this);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		bottomPanel.add(button);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		scroll = new ScrollPane();
		scroll.add(mainPanel);

		adificate();

		addInt();
		revalidate();
		repaint();
		
		painter = new Thread(new Runnable(){
			@Override
			public void run() {
				
				synchronized (this) {
					while(true)
					{
						try {
							wait(20);
						} catch (InterruptedException e) {
							System.out.println("Painter killed");
							return;
						}
						
						synchronized (drawLock)
						{
//							thiss.repaint(); //Might Be unessisarry
							thiss.revalidate();
						}
					}
				}
			}});
		painter.setDaemon(true);
		painter.start();

	}

	private void adificate() {
		add(itemPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void addInt() {
		InstructionPanel inst = new InstructionPanel(this);
		instructions.add(inst);
		mainPanel.add(inst);
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Recipe rcp = getRecipe();
		
		if(rcp == null)
		{
			JOptionPane.showMessageDialog(
					this,
					"Invalid Recipe");
			return;
		}
		
		messageManager.sendRcpToServer(rcp);
		responsePanel = new JPanel();
		
		
		resp = new JTextField("Waiting for repsonse...");
		resp.setEditable(false);
		resp.setBorder(null);
		responsePanel.add(resp);
		

		getContentPane().removeAll();
		revalidate();
		add(responsePanel, BorderLayout.CENTER);
		
		getContentPane().repaint();
		
		
		Thread th = new Thread(new Runnable(){
			@Override
			public void run() {
				while(true)
				{
					while(!messageManager.hasMessage())
					{
						try 
						{
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					
					Message msg = messageManager.getMessage();
					if(msg.type == Message.DENIED)
					{
						resp.setText("Request Denied!");
						JPanel bot = new JPanel();
						
						button = new JButton("Back");
						button.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent arg0) {
								thiss.getContentPane().removeAll();
								adificate();
								
							}});
						
						bot.setLayout(new GridBagLayout());
						bot.add(button);
						thiss.add(bot, BorderLayout.SOUTH);
						return;
					}
					else if(msg.type == Message.ACCEPTED)
					{
						resp.setText("Request accepted!");
					}
					else
					{
//						painter.interrupt();
						resp.setText("Order Complete!");
						JPanel bot = new JPanel();
						JPanel middle = new JPanel();
						
						button = new JButton("Done!");
						
						JLabel leb = new JLabel("");
						Image image = null;
						
						System.out.println("FIDFS");
						try {
						    URL url = new URL(msg.url);
						    image = ImageIO.read(url);
						    leb.setIcon(new ImageIcon(image));
						} catch (IOException ee) {
							ee.printStackTrace();
						}
						
						
						button.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent arg0) {
								thiss.dispose();
							}});
						
						bot.setLayout(new GridBagLayout());
						bot.add(button);
						
						middle.setLayout(new GridBagLayout());
						middle.add(leb);
						
						responsePanel = new JPanel();
						responsePanel.setLayout(new GridBagLayout()); 
						responsePanel.add(resp);
						
						synchronized (drawLock)
						{
							thiss.add(bot, BorderLayout.SOUTH);
							thiss.add(middle, BorderLayout.CENTER);
							thiss.add(responsePanel, BorderLayout.NORTH);
						}
						
//						thiss.revalidate();
//						thiss.repaint();
//						
						return;
					}
				}
			}});
//		
		th.setDaemon(true);
		th.start();


	}

	private Recipe getRecipe() {
		Recipe rcp = new Recipe();
		try{
			try{
				rcp.metal = Integer.parseInt(itemPanel.metalF.getText());
			}
			catch(Exception e)
			{
				rcp.metal = 0;
			}
			
			try{
				rcp.plastic = Integer.parseInt(itemPanel.plasticF.getText());
			}
			catch(Exception e)
			{
				rcp.plastic = 0;
			}
			
			try{
				rcp.wood = Integer.parseInt(itemPanel.woodF.getText());
			}
				catch(Exception e)
			{
				rcp.wood = 0;
			}
			
			rcp.cost = Integer.parseInt(toNums(itemPanel.costF.getText()));
			rcp.name = itemPanel.itemF.getText();
			
			for(InstructionPanel i : instructions)
			{
				Action act = i.getAction();
				if(act == null)
					return null;
				rcp.actions.add(act);
			}
		}
		catch(Exception e)
		{
			return null;
		}

		return rcp;
	}

	private String toNums(String text) {
		return text.replaceAll("[^0-9]", "");
	}

	public static void main(String[] args)
	{
		ClientFrame mainFrame = new ClientFrame();
		mainFrame.setVisible(true);
		mainFrame.setSize(800,600);
		mainFrame.setResizable(false);
		mainFrame.setMinimumSize(new Dimension(50*9 + 280,50*9 + 110));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void removeInt() {
		if(instructions.size() < 2)
			return;
		
		InstructionPanel inst = instructions.get(instructions.size() - 1);
		instructions.remove(inst);
		mainPanel.remove(inst);
		revalidate();

	}

}
