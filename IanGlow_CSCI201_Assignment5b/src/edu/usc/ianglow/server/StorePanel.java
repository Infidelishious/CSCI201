package edu.usc.ianglow.server;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StorePanel extends OutPanel{

	public StorePanel(MainFrame parent) {
		super(parent);
		setOpaque(true);
		makeSquares();
		this.setBounds(0, 0, parent.panel.getWidth(), parent.panel.getWidth());
		
	}

	Square wood, metal, plastic, hammer, screwdriver, paintbrush, pliers, scissors, worker;
	BackButton back;
	private StoreButton store;
	
	private void makeSquares() {
		try {
			OutPanel panel = this;
			wood = new Square(panel,"0", "Wood", ImageIO.read(new File("img/wood.png")), 300, 100);
			metal = new Square(panel,"0", "Metal", ImageIO.read(new File("img/metal.png")), 300, 180);
			plastic = new Square(panel,"0", "Plastic", ImageIO.read(new File("img/plastic.png")), 300, 260);

			screwdriver = new Square(panel, "0", "Screwdriver", ImageIO.read(new File("img/screwdriver.png")), 10, 100);
			hammer = new Square(panel, "0", "Hammer", ImageIO.read(new File("img/hammer.png")), 10, 180);
			paintbrush = new Square(panel,"0", "Paintbrush", ImageIO.read(new File("img/paintbrush.png")), 10, 260);
			pliers = new Square(panel,"0", "Pliers", ImageIO.read(new File("img/pliers.png")), 10, 340);
			scissors = new Square(panel,"0", "Scissors", ImageIO.read(new File("img/scissors.png")), 10, 420);

			worker = new Square(panel,"", "0", ImageIO.read(new File("img/worker.png")), 300, 420);
			
			store = new StoreButton(panel);
			back = new BackButton(this);
			
			add(back);
			
			add(scissors);
			add(pliers);
			add(paintbrush);
			add(hammer);
			add(screwdriver);
			
			add(wood);
			add(metal);
			add(plastic);
			
			add(worker);
			
			makeButtons();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void makeButtons() {
		JButton temp;
		temp = new JButton("Buy $1");
		temp.setBounds(400, 120, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 0)
				{
					frame.resPile.addRes(ResourcePile.WOOD, 1);
					frame.money -= 1;
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $1");
		temp.setBounds(400, 140, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.resPile.getResBool(ResourcePile.WOOD))
					frame.money += 1;
			}
		});
		add(temp);
		
		temp = new JButton("Buy $3");
		temp.setBounds(400, 200, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 2)
				{
					frame.resPile.addRes(ResourcePile.METAL, 1);
					frame.money -= 3;
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $2");
		temp.setBounds(400, 220, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.resPile.getResBool(ResourcePile.METAL))
					frame.money += 2;
			}
		});
		add(temp);
		
		temp = new JButton("Buy $2");
		temp.setBounds(400, 280, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 1)
				{
					frame.resPile.addRes(ResourcePile.PLASTIC, 1);
					frame.money -= 2;
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $1");
		temp.setBounds(400, 300, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.resPile.getResBool(ResourcePile.PLASTIC))
					frame.money += 1;
			}
		});
		add(temp);
		
		temp = new JButton("Buy $1");
		temp.setBounds(400, 120, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 0)
				{
					frame.resPile.addRes(ResourcePile.WOOD, 1);
					frame.money -= 1;
				}
			}
		});
		add(temp);
		
		//////////////////////////////////////////////////////
		temp = new JButton("Buy $10");
		temp.setBounds(120, 120, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 9)
				{
					frame.toolshead.createTool(ToolShead.SCREWDRIVER);
					frame.toolshead.num_screwt++;
					frame.toolshead.update();
					frame.money -= 10;
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $7");
		temp.setBounds(120, 140, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.toolshead.deleteTool(ToolShead.SCREWDRIVER))
				{
					frame.money += 7;
					frame.toolshead.update();
				}
			}
		});
		add(temp);
		
		temp = new JButton("Buy $12");
		temp.setBounds(120, 200, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 11)
				{
					frame.toolshead.createTool(ToolShead.HAMMER);
					frame.toolshead.num_hammert++;
					frame.toolshead.update();
					frame.money -= 12;
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $9");
		temp.setBounds(120, 220, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.toolshead.deleteTool(ToolShead.HAMMER))
				{
					frame.money += 9;
					frame.toolshead.update();
				}
			}
		});
		add(temp);
		
		temp = new JButton("Buy $5");
		temp.setBounds(120, 280, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 4)
				{
					frame.toolshead.createTool(ToolShead.PAINTBRUSH);
					frame.toolshead.num_pbt++;
					frame.toolshead.update();
					frame.money -= 5;
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $3");
		temp.setBounds(120, 300, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.toolshead.deleteTool(ToolShead.PAINTBRUSH))
				{
					frame.money += 3;
					frame.toolshead.update();
				}
			}
		});
		add(temp);
		
		temp = new JButton("Buy $11");
		temp.setBounds(120, 360, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 10)
				{
					frame.toolshead.createTool(ToolShead.PLIERS);
					frame.toolshead.num_plierst++;
					frame.toolshead.update();
					frame.money -= 11;
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $9");
		temp.setBounds(120, 380, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.toolshead.deleteTool(ToolShead.PLIERS))
				{
					frame.money += 9;
					frame.toolshead.update();
				}
			}
		});
		add(temp);
		
		temp = new JButton("Buy $9");
		temp.setBounds(120, 440, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 8)
				{
					frame.toolshead.createTool(ToolShead.SCISSORS);
					frame.toolshead.num_scissorst++;
					frame.toolshead.update();
					
					frame.money -= 9;
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $7");
		temp.setBounds(120, 460, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.toolshead.deleteTool(ToolShead.SCISSORS))
				{
					frame.money += 7;
					frame.toolshead.update();
				}
			}
		});
		add(temp);
		/////////////////////////////////////////////
		
		temp = new JButton("Buy $15");
		temp.setBounds(400, 440, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame.money > 14)
				{
					Worker wk = new Worker(frame.panel, 50, 40);
					frame.panel.add(wk);
					frame.workerArray.add(wk);
					frame.workers++;
					frame.money -= 15;
					
					frame.panel.removeAll();
					frame.addWorkers();
					frame.addRest();
				}
			}
		});
		add(temp);
		
		temp = new JButton("Sell $15");
		temp.setBounds(400, 460, 100, 15);
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(Worker i : frame.workerArray)
				{
					if(i.fired == false)
					{
						i.fired = true;
						frame.money += 15;
						return;
					}
				}
			}
		});
		add(temp);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent2(g);
		wood.label = "" + frame.resPile.num_wood;
		metal.label = "" + frame.resPile.num_metal;
		plastic.label = "" + frame.resPile.num_plastic;
		
		screwdriver.label = "" + frame.toolshead.num_screwt;
		paintbrush.label = "" + frame.toolshead.num_pbt;
		pliers.label = "" + frame.toolshead.num_plierst;
		scissors.label = "" + frame.toolshead.num_scissorst;
		hammer.label = "" + frame.toolshead.num_hammert;
		
		worker.top = "" + frame.workers;
	}
}
