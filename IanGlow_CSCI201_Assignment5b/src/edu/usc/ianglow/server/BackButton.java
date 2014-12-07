package edu.usc.ianglow.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BackButton extends Square implements MouseListener{

	public BackButton(OutPanel panel) throws IOException {
		
		super(panel, "", "", ImageIO.read(new File("img/back.png")), 10, 20);
		this.addMouseListener(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
//		System.out.println("Click");
		panel.frame.remove(panel);
		panel.frame.add(panel.frame.panel, BorderLayout.CENTER);
		panel.frame.revalidate();
		panel.frame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if(img != null)
			g.drawImage(img, 20,20, 40, 40, null);

		top = "$" + panel.frame.money;
		int width = g.getFontMetrics().stringWidth(label);
		int width2 = g.getFontMetrics().stringWidth(top);

		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString(label, -width/2 + 40, 45);

		g.drawString(top, -width2/2 + 40, 15);
	}
}
