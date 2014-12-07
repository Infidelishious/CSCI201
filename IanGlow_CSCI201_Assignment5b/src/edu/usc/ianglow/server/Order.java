package edu.usc.ianglow.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.usc.ianglow.client.ClientFrame;

public class Order extends OutPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("SansSerif", Font.PLAIN, 18);
	private ArrayList<Square> squares;
	public OrdersPanel frame;
	public JButton accept, decline;
	public Recipe rcp;
	public int totalTime = 0;
	
	Square wood, metal, plastic;
	
	public Order(OrdersPanel parent, Recipe rcp){
		super(null);
		this.frame = parent;
		this.rcp = rcp;
		setLayout(null);
		setMinimumSize(new Dimension(600, 100));
		setPreferredSize(new Dimension(600, 100));
		setBounds(0, 0, 600, 100);
		
		try {
			wood = new Square(this,"" + rcp.wood, "Wood", ImageIO.read(new File("img/wood.png")), 150, 0);
			metal = new Square(this,"" + rcp.metal, "Metal", ImageIO.read(new File("img/metal.png")), 200, 0);
			plastic = new Square(this,"" + rcp.plastic, "Plastic", ImageIO.read(new File("img/plastic.png")), 250, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add(wood);
		add(metal);
		add(plastic);
		
		accept = new JButton("Accept");
		accept.setLocation(400, 15);
		accept.setSize(new Dimension(100,30));
		accept.addActionListener(this);
		add(accept);
	
		decline = new JButton("Decline");
		decline.setLocation(520, 15);
		decline.setSize(new Dimension(100,30));
		decline.addActionListener(this);
		add(decline);
		
		for(Action i : rcp.actions)
		{			
			totalTime += i.time;
		}
	}
	
	
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent3(g);

		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString(rcp.name + " - $" + rcp.cost, 10, 40);
		g.drawString("" + totalTime + "s", 350, 40);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton btn = (JButton) arg0.getSource();
		Message msg = new Message();
		
		if(btn == accept)
		{
			msg.type = Message.ACCEPTED;
			rcp.st.SendMessage(msg);
			RecipeManager.getInstance().addRecipe(rcp);
			RecipeManager.getInstance().update();
		}
		else
		{
			msg.type = Message.DENIED;
			rcp.st.SendMessage(msg);
		}
		
		rcp.st.recipe = null;
	}
}
