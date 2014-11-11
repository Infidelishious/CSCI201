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

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public static Color SWING_GRAY = UIManager.getColor ("Panel.background");

	ArrayList<String[]> data;
	ArrayList<Worktable> tabels;
	ArrayList<File> rpcFiles;
	ArrayList<File> factoryFiles;
	OutPanel panel;
	
	public JTable table;
	public JMenuItem openButton;
	public MainFrame thiss;
	public Thread painter;
	
	Square wood, metal, plastic;
	
	Square screwdriver, hammer, paintbrush, pliers, scissors;
	
	Worktable anvil1, anvil2, wb1, wb2, wb3, furn1, furn2, ts1, ts2, ts3, ps1, ps2, ps3, ps4, press;
	
	public MainFrame()
	{
		super("Factory");
		rpcFiles = new ArrayList<File>();
		factoryFiles = new ArrayList<File>();
		tabels = new ArrayList<Worktable>();
		
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
		
		panel = new OutPanel();
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
			
			wood = new Square(panel,"0", "Wood", ImageIO.read(new File("img/wood.png")), 150, 10);
			metal = new Square(panel,"0", "Metal", ImageIO.read(new File("img/metal.png")), 300, 10);
			plastic = new Square(panel,"0", "Plastic", ImageIO.read(new File("img/plastic.png")), 450, 10);
			
			screwdriver = new Square(panel, "0", "Screwdriver", ImageIO.read(new File("img/screwdriver.png")), 10, 100);
			hammer = new Square(panel, "0", "Hammer", ImageIO.read(new File("img/hammer.png")), 10, 180);
			paintbrush = new Square(panel,"0", "Paintbrush", ImageIO.read(new File("img/paintbrush.png")), 10, 260);
			pliers = new Square(panel,"0", "Pliers", ImageIO.read(new File("img/pliers.png")), 10, 340);
			scissors = new Square(panel,"0", "Scissors", ImageIO.read(new File("img/scissors.png")), 10, 420);
			
			anvil1 = new Worktable(panel, Worktable.ANVIL, 170, 120);
			anvil2 = new Worktable(panel, Worktable.ANVIL, 240, 120);
			wb1 = new Worktable(panel, Worktable.BENCH, 310, 120);
			wb2 = new Worktable(panel, Worktable.BENCH, 380, 120);
			wb3 = new Worktable(panel, Worktable.BENCH, 450 , 120);
			furn1 = new Worktable(panel, Worktable.FURNACE, 170 ,240);
			furn2 = new Worktable(panel, Worktable.FURNACE, 240 ,240);
			ts1 = new Worktable(panel, Worktable.SAW, 310 ,240);
			ts2 = new Worktable(panel, Worktable.SAW, 380 ,240);
			ts3= new Worktable(panel, Worktable.SAW, 450 ,240);
			ps1 = new Worktable(panel, Worktable.PAINTING, 170 ,360);
			ps2 = new Worktable(panel, Worktable.PAINTING, 240 ,360);
			ps3 = new Worktable(panel, Worktable.PAINTING, 310 ,360);
			ps4 = new Worktable(panel, Worktable.PAINTING, 380 ,360);
			press = new Worktable(panel, Worktable.PRESS, 450 ,360);
			
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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void makeTable(boolean first) {
		
		TaskBoard board = new TaskBoard();
//		if(!first)
//			remove(table);
		
		add(board, BorderLayout.EAST);
		revalidate();
	}

	
	protected void parse(File in) throws Exception {
		String[] files = in.list();
		for(String i: files)
		{
			if(i.contains(".rpc"))
				rpcFiles.add(new File(in.getAbsolutePath() + i));
			else if(i.contains(".factory"))
				rpcFiles.add(new File(in.getAbsolutePath() + i));
		}
		
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
