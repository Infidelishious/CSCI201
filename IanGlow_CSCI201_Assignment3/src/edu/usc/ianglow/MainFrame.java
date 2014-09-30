package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame{

	public JPanel panel, outPanel, aboutPanel;
	
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
		
		makeAboutPanel();
		outPanel.add(panel, "month");
		outPanel.add(aboutPanel, "about");
		
		add(outPanel);
		
	}

	private void makeAboutPanel() {
		aboutPanel = new JPanel(new BorderLayout());
		JLabel temp = new JLabel("Ian Glow", SwingConstants.CENTER);
		temp.setFont(new Font("Helvetica", Font.BOLD, 40));
		aboutPanel.add(temp, BorderLayout.NORTH);
		
		
		temp = new JLabel("", SwingConstants.CENTER);
		temp.setIcon(new ImageIcon("pic.png"));
		aboutPanel.add(temp, BorderLayout.CENTER);
		
		JPanel backInfoPanel = new JPanel(new BorderLayout());
		
		temp = new JLabel("<html>Section: Monday/Wednesday Noon<br>Finished: October 2nd</html>", SwingConstants.CENTER);
		temp.setFont(new Font("Helvetica", Font.BOLD, 16));
		backInfoPanel.add(temp, BorderLayout.WEST);
		
		JButton temp1 = new JButton("Back");
		temp1.setFont(new Font("Helvetica", Font.BOLD, 16));
		temp1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)outPanel.getLayout();
				cl.show(outPanel, "month");
				
			}
			
		});
		backInfoPanel.add(temp1, BorderLayout.EAST);
		
		aboutPanel.add(backInfoPanel, BorderLayout.SOUTH);
		
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
	
	public void export()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Export Events");   
		 
		int userSelection = fileChooser.showSaveDialog(this);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    if(!fileToSave.getAbsolutePath().contains(".csv"))
		    	fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
		    
		    try {
				BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave));
				
				for(Event i : events)
					out.append(i.toCSVLine());
				
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
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
