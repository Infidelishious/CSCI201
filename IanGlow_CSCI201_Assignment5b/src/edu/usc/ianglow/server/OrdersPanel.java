package edu.usc.ianglow.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class OrdersPanel extends OutPanel{

	Vector<Order> orders;
	JPanel mainPanel;
	private ScrollPane scroll;

	public OrdersPanel(MainFrame parent) {
		super(parent);
		setOpaque(true);
		makeSquares();
		
		setLayout(null);

		frame = parent;
		this.setPreferredSize(new Dimension(3000, 5000));
		this.setSize(new Dimension(3000, 5000));
		this.setBounds(0, 0, 3000, 5000);

		mainPanel = new JPanel();
		orders = new Vector<Order>();

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBounds(0, 100, 650, 500);
		
		scroll = new ScrollPane();
//		scroll.setBorder(null);
		
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//		scroll.setBounds(0,100,getWidth(), getHeight()-200);
//		scroll.setSize()
		
//		scroll = new JScrollPane();
		scroll.add(mainPanel);
		panel.add(scroll, BorderLayout.CENTER);
		add(panel);

	}

	Square wood, metal, plastic, hammer;
	BackButton back;
	//	private StoreButton store;

	private void makeSquares() {
		try {
			OutPanel panel = this;

			back = new BackButton(this);
			
			add(back);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void paintComponent(Graphics g)
	{
		super.paintComponent2(g);
		
		

		mainPanel.removeAll();
		orders.removeAllElements();

		synchronized(frame.server)
		{
			for(ServerThread i : frame.server.serverThreads)
			{
				if(i.recipe != null)
					orders.add(new Order(this, i.recipe));
			}
		}
//		System.out.println(orders.size() + " orders");
		
		for(Order i : orders)
		{
			mainPanel.add(i);
		}
		
		mainPanel.revalidate();
		
		if(orders.size() == 0)
			mainPanel.repaint();


	}
}


