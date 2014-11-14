package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.usc.ianglow.Recipe.Action;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public static Color SWING_GRAY = UIManager.getColor ("Panel.background");

	ArrayList<String[]> data;
	ArrayList<Worktable> tabels;
	ArrayList<File> rpcFiles;
	ArrayList<File> factoryFiles;
	OutPanel panel;
	
	public JMenuItem openButton;
	public MainFrame thiss;
	public Thread painter;
	TaskBoard board;
	
	Square wood, metal, plastic, tasks;
	
	Square screwdriver, hammer, paintbrush, pliers, scissors;
	
	Worktable anvil1, anvil2, wb1, wb2, wb3, furn1, furn2, ts1, ts2, ts3, ps1, ps2, ps3, ps4, press;
	
	ToolShead toolshead;
	ResourcePile resPile;
	
	int workers;
	
	public MainFrame()
	{
		super("Factory");
		RecipeManager.getInstance().init(this);
		rpcFiles = new ArrayList<File>();
		factoryFiles = new ArrayList<File>();
		tabels = new ArrayList<Worktable>();
		toolshead = new ToolShead(this);
		resPile = new ResourcePile(this);
		
		 try {
			UIManager.setLookAndFeel(
			            UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		thiss = this;
		setLayout(new BorderLayout());
		openButton = new JMenuItem("Open File...");
		openButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Open");   
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				 
				int userSelection = fileChooser.showOpenDialog(thiss);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToOpen = fileChooser.getSelectedFile();
				    
				    try {
				    	System.out.println("Opened folder: " + fileToOpen.getAbsolutePath());
						parse(fileToOpen);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				    
				}
			}
		});
		
		add(openButton, BorderLayout.NORTH);
		
		makeTable(true);
		
		panel = new OutPanel(this);
		makeSquares();
		add(panel, BorderLayout.CENTER);
		repaint();
		
		
		painter = new Thread(new Runnable(){
			@Override
			public void run() {
				synchronized (this) {
					try {
						wait(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					panel.repaint();
				}
			}});
		painter.start();
		
		
	}

	private void makeSquares() {
		try {
			
			wood = new Square(panel,"0", "Wood", ImageIO.read(new File("img/wood.png")), 200, 70);
			metal = new Square(panel,"0", "Metal", ImageIO.read(new File("img/metal.png")), 300, 70);
			plastic = new Square(panel,"0", "Plastic", ImageIO.read(new File("img/plastic.png")), 400, 70);
			
			screwdriver = new Square(panel, "0", "Screwdriver", ImageIO.read(new File("img/screwdriver.png")), 10, 100);
			hammer = new Square(panel, "0", "Hammer", ImageIO.read(new File("img/hammer.png")), 10, 180);
			paintbrush = new Square(panel,"0", "Paintbrush", ImageIO.read(new File("img/paintbrush.png")), 10, 260);
			pliers = new Square(panel,"0", "Pliers", ImageIO.read(new File("img/pliers.png")), 10, 340);
			scissors = new Square(panel,"0", "Scissors", ImageIO.read(new File("img/scissors.png")), 10, 420);
			
			tasks = new Square(panel,"", "", ImageIO.read(new File("img/tasks.png")), 570, 20);
			
			anvil1 = new Worktable(panel, Worktable.ANVIL, 170, 200);
			anvil2 = new Worktable(panel, Worktable.ANVIL, 240, 200);
			wb1 = new Worktable(panel, Worktable.BENCH, 310, 200);
			wb2 = new Worktable(panel, Worktable.BENCH, 380, 200);
			wb3 = new Worktable(panel, Worktable.BENCH, 450 , 200);
			furn1 = new Worktable(panel, Worktable.FURNACE, 170 ,320);
			furn2 = new Worktable(panel, Worktable.FURNACE, 240 ,320);
			ts1 = new Worktable(panel, Worktable.SAW, 310 ,320);
			ts2 = new Worktable(panel, Worktable.SAW, 380 ,320);
			ts3= new Worktable(panel, Worktable.SAW, 450 ,320);
			ps1 = new Worktable(panel, Worktable.PAINTING, 170 ,440);
			ps2 = new Worktable(panel, Worktable.PAINTING, 240 ,440);
			ps3 = new Worktable(panel, Worktable.PAINTING, 310 ,440);
			ps4 = new Worktable(panel, Worktable.PAINTING, 380 ,440);
			press = new Worktable(panel, Worktable.PRESS, 450 ,440);
			
//			final Worker wk = new Worker(panel, 50, 40);
//			panel.add(wk);
			
//			 wk.facneyLarp(new LarpListener(){
//
//					@Override
//					public void reachedLocation() {
//						wk.facneyLarp( new LarpListener(){
//
//							@Override
//							public void reachedLocation() {
//								wk.facneyLarp( new LarpListener(){
//
//									@Override
//									public void reachedLocation() {
//										wk.facneyLarp( new LarpListener(){
//
//											@Override
//											public void reachedLocation() {
//												wk.facneyLarp( new LarpListener(){
//
//													@Override
//													public void reachedLocation() {
//														// TODO Auto-generated method stub
//														
//													}}, tasks);
//												
//											}}, hammer);
//										
//									}}, wood);
//								
//							}}, anvil1);
//						
//					}}, press);
			
			addRest();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addRest() {
		tabels.add(anvil1);
		tabels.add(anvil2);
		tabels.add(wb1);
		tabels.add(wb2);
		tabels.add(wb3);
		tabels.add(furn1);
		tabels.add(furn2);
		tabels.add(ts1);
		tabels.add(ts2);
		tabels.add(ts3);
		tabels.add(ps1);
		tabels.add(ps2);
		tabels.add(ps3);
		tabels.add(ps4);
		tabels.add(press);
		
		panel.add(anvil1);
		panel.add(anvil2);
		panel.add(wb1);
		panel.add(wb2);
		panel.add(wb3);
		panel.add(furn1);
		panel.add(furn2);
		panel.add(ts1);
		panel.add(ts2);
		panel.add(ts3);
		panel.add(ps1);
		panel.add(ps2);
		panel.add(ps3);
		panel.add(ps4);
		panel.add(press);
		
		panel.add(wood);
		panel.add(metal);
		panel.add(plastic);
		
		panel.add(screwdriver);
		panel.add(hammer);
		panel.add(paintbrush);
		panel.add(pliers);
		panel.add(scissors);
		
		panel.add(tasks);
	}

	public void makeTable(boolean first) {
		
		board = new TaskBoard();
//		if(!first)
//			remove(table);
		
		add(board, BorderLayout.EAST);
		revalidate();
	}

	
	protected void parse(File in) throws Exception {
		String[] files = in.list();
		for(String i: files)
		{
			if(i.contains(".rcp"))
				rpcFiles.add(new File(in.getAbsolutePath() + "\\" + i));
			else if(i.contains(".factory"))
				factoryFiles.add(new File(in.getAbsolutePath() + "\\" + i));
		}
		
		File factory = factoryFiles.get(0);
		Scanner sc = new Scanner(factory);
		
		while(sc.hasNext())
		{
			String line = sc.nextLine(),
					item, amount;
			int collenInt = line.indexOf(":");
			int endInt = line.lastIndexOf("]");
			if(collenInt == -1) continue;
			
			item = line.substring(1 , collenInt);
			amount = line.substring(collenInt + 1, endInt);
			int number = Integer.parseInt(amount);
			
			System.out.println(item + " " + amount);
			
			if(item.equalsIgnoreCase("Workers"))
			{
				workers = number;
			}
			else if(item.equalsIgnoreCase("Hammers"))
			{
				toolshead.num_hammert = number;
			}
			else if(item.equalsIgnoreCase("Screwdrivers"))
			{
				toolshead.num_screwt = number;
			}
			else if(item.equalsIgnoreCase("Pliers"))
			{
				toolshead.num_plierst = number;
			}
			else if(item.equalsIgnoreCase("Scissors"))
			{
				toolshead.num_scissorst = number;
			}
			else if(item.equalsIgnoreCase("Paintbrushes"))
			{
				toolshead.num_pbt = number;
			}
		}
		
		toolshead.init();
		resPile.init();
		
		for(File i : rpcFiles)
		{
			sc = new Scanner(i);
			Recipe rpc = new Recipe();
			int numberOfRpcs = 0;
			
			while(sc.hasNext())
			{
				String line = sc.nextLine(),
						item, amount;
				int collenInt = line.indexOf(":");
				int endInt = line.lastIndexOf("]");
				int firstXInt = line.lastIndexOf("x");
				int number = 0;
				
				
				if(collenInt != -1)
				{
					item = line.substring(1 , collenInt);
					amount = line.substring(collenInt + 1, endInt);
					number = Integer.parseInt(amount);
				}
				else
					item = line.substring(1 , endInt);
				
				if(firstXInt > endInt) //FirstItem;
				{
					rpc.name = item;
					amount = line.substring(firstXInt + 1, line.length());
					amount = amount.trim();
					number = Integer.parseInt(amount);
					numberOfRpcs = number;
					continue;
				}
				
				if(collenInt != -1) //Materials Line
				{
					if(item.equalsIgnoreCase("Wood"))
					{
						rpc.wood = number;
					}
					else if(item.equalsIgnoreCase("Metal"))
					{
						rpc.metal = number;
					}
					else if(item.equalsIgnoreCase("Plastic"))
					{
						rpc.plastic = number;
					}
					continue;
				}
				
				//Must be action
				Action act = rpc.new Action();
				int forInt = line.lastIndexOf("for");
				int lastSInt = line.lastIndexOf("s");
				
				amount = line.substring(forInt + 4, lastSInt);
				amount = amount.trim();
				number = Integer.parseInt(amount);
				
				act.time = number;
				
				line = line.substring(forInt);
				
				if(line.contains("Anvil"))
					act.location = Worktable.ANVIL;
				else if(line.contains("Workbench"))
					act.location = Worktable.BENCH;
				else if(line.contains("Saw"))
					act.location = Worktable.SAW;
				else if(line.contains("Press"))
					act.location = Worktable.PRESS;
				else if(line.contains("Painting Station") || line.contains("Paintingstation"))
					act.location = Worktable.PAINTING;
				else if(line.contains("Furnace"))
					act.location = Worktable.FURNACE;
				
				while(line.contains("x"))
				{
					int type = 0;
					
					int firstX = line.indexOf("x");
					String sub = line.substring(firstX);
					int lastSpace = sub.lastIndexOf(" ");
					amount = line.substring(lastSpace + 1, firstX);
					amount = amount.trim();
					number = Integer.parseInt(amount);
					
					sub = line.substring(firstX + 2, line.length());
					int firstSpace = sub.indexOf(" ");
					sub = sub.substring(firstX + 2, firstSpace);
					
					if(sub.contains("Screwdriver"))
						type = ToolShead.SCREWDRIVER;
					else if(sub.contains("Hammer"))
						type = ToolShead.HAMMER;
					else if(sub.contains("Paintbrush"))
						type = ToolShead.PAINTBRUSH;
					else if(sub.contains("Pliers"))
						type = ToolShead.PLIERS;
					else if(sub.contains("Scissors"))
						type = ToolShead.SCISSORS;
					
					line = line.substring(firstSpace, line.length());
					
					for(int ii = 0; ii < number; ii++)
					{
						act.tools.add(type);
					}
				}
				
				rpc.actions.add(act);
				
			}
			
			for(int ii = 0; ii < numberOfRpcs; ii++)
			{
				RecipeManager.getInstance().addRecipe(new Recipe(rpc));
			}
			
			RecipeManager.getInstance().update();
		}
		
		panel.removeAll();
		
		for(int ii = 0; ii < workers; ii++)
		{
			Worker wk = new Worker(panel, 50, 40);
			panel.add(wk);
		}
		
		addRest();
		
	}


	public static void main(String[] args)
	{
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		mainFrame.setSize(800,600);
		mainFrame.setResizable(false);
		mainFrame.setMinimumSize(new Dimension(50*9 + 280,50*9 + 110));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
