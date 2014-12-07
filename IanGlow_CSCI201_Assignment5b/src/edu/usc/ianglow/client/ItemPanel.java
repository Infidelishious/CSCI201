package edu.usc.ianglow.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.usc.ianglow.server.MainFrame;
import edu.usc.ianglow.server.Square;

public class ItemPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("SansSerif", Font.PLAIN, 18);
	private ArrayList<Square> squares;
	public ClientFrame frame;
	public JTextField itemF, costF, woodF, plasticF, metalF;
	public JButton plus, minus;
	
	public ItemPanel(ClientFrame parent){
		this.frame = parent;
		setLayout(null);
		setMinimumSize(new Dimension(300, 150));
		setPreferredSize(new Dimension(300, 150));
		
//		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		itemF = new JTextField();
		itemF.setLocation(82, 30);
		itemF.setSize(new Dimension(100,20));
		add(itemF);
		
		costF = new JTextField();
		costF.setLocation(82, 60);
		costF.setSize(new Dimension(100,20));
		add(costF);
		
		woodF = new JTextField();
		woodF.setLocation(432, 50);
		woodF.setSize(new Dimension(40,20));
		add(woodF);
		
		plasticF = new JTextField();
		plasticF.setLocation(432, 80);
		plasticF.setSize(new Dimension(40,20));
		add(plasticF);
		
		metalF = new JTextField();
		metalF.setLocation(432, 110);
		metalF.setSize(new Dimension(40,20));
		add(metalF);
		
		plus = new JButton("+");
		plus.setLocation(600, 50);
		plus.setSize(new Dimension(50,50));
		plus.addActionListener(this);
		add(plus);
		
		minus = new JButton("-");
		minus.setLocation(660, 50);
		minus.setSize(new Dimension(50,50));
		minus.addActionListener(this);
		add(minus);
	}
	
	
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString("Item:", 40, 45);
		g.drawString("Cost:", 38, 75);
		
		g.drawString("Instructions:", 70, 140);
		g.drawString("Materials", 385, 40);
		
		g.drawString("Wood:", 370, 65);
		g.drawString("Plastic:", 364, 95);
		g.drawString("Metal:", 374, 125);
	}



	public void paintComponent2(Graphics g) {
		super.paintComponent(g);
		
//		g.setColor(Color.BLACK);
//		g.setFont(FONT);
//		g.drawString("Money", 24, 20);
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton btn = (JButton) arg0.getSource();
		if(btn == plus)
		{
			frame.addInt();
		}
		else
		{
			frame.removeInt();
		}
	}

}
