package edu.usc.ianglow.server;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class OrdersPanel extends OutPanel{

	public OrdersPanel(MainFrame parent) {
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


