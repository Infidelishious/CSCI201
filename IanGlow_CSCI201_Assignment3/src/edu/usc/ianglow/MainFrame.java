package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame{

	public JPanel panel, outPanel;
	
	private static final long serialVersionUID = 1L;
	
	private Month currentMonth;
	private ArrayList<Event> events;
	
	
	public MainFrame()
	{
		outPanel = new JPanel(new CardLayout());
		panel = new JPanel(new BorderLayout());
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 0);
		currentMonth = new Month(this, now);
		panel.add(currentMonth, BorderLayout.CENTER);
		outPanel.add(panel, "month");
		add(outPanel);
		
	}

	public void nextMonth() {
		panel.remove(currentMonth);
		Calendar now = (Calendar) currentMonth.start.clone();
		now.add(Calendar.MONTH, 1);
		currentMonth = new Month(this, now);
		panel.add(currentMonth);
		revalidate();
	}
	
	public void lastMonth() {
		panel.remove(currentMonth);
		Calendar now = (Calendar) currentMonth.start.clone();
		now.add(Calendar.MONTH, -1);
		currentMonth = new Month(this, now);
		panel.add(currentMonth);
		revalidate();
	}
	
	public static void main(String[] args)
	{
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		mainFrame.setSize(500,500);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
