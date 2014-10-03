package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class Month extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	
	public MainFrame parent;
	public Day[][] days;
	public Day currentDay;
	public Calendar start;
	public JPanel centerP, northP, dayBar;
	private JPanel scrollPanel;
	private JScrollPane scrollPane;
	
	public Month (MainFrame mainFrame, Calendar s)
	{
		parent = mainFrame;
		start = s;
		
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(Color.WHITE);
		
		centerP = new JPanel();
		northP = new JPanel(new BorderLayout());
		northP.setBackground(new Color(238,238,238));
		northP.setOpaque(true);
		
		dayBar = new JPanel(new GridLayout(1,7));
		
		createDayBar();
		
		centerP.setLayout(new GridLayout(6,7));
		
		createDays();
		createMonthTitle();
		createButtons();
		
		add(centerP, BorderLayout.CENTER);
		northP.add(dayBar, BorderLayout.SOUTH);
		add(northP, BorderLayout.NORTH);
		addMenu();
		
		selectCurrentDay();
		updateEventArea();
		addMarkers();
		
		Calendar now = Calendar.getInstance();
				
		if(now.get(Calendar.YEAR) == start.get(Calendar.YEAR) &&
				now.get(Calendar.MONTH) == start.get(Calendar.MONTH))
		yellowCurrentDay();
	}

	public void addMarkers() {
		
		for(int j = 1; j <= 31; j++)
		{
			for(Event i : parent.events)
			{
				if(i.start.get(Calendar.YEAR) == start.get(Calendar.YEAR)
						&& i.start.get(Calendar.MONTH) == start.get(Calendar.MONTH)
						&& i.start.get(Calendar.DAY_OF_MONTH) == j)
				{
					getDay(j).setEventMarker(true);
				}
			}
		}
		
	}

	public void updateEventArea() {
		
		if(scrollPanel != null || scrollPane != null)
		{
			remove(scrollPane);
		}
			
		ArrayList<Event> eventsOnSelectedDay = new ArrayList<Event>();
		
		if(currentDay != null)
		{
			for(Event i : parent.events)
			{
				if(i.start.get(Calendar.YEAR) == start.get(Calendar.YEAR)
						&& i.start.get(Calendar.MONTH) == start.get(Calendar.MONTH)
						&& i.start.get(Calendar.DAY_OF_MONTH) == currentDay.day)
				{
					eventsOnSelectedDay.add(i);
				}
			}
		}
		
		scrollPanel = new JPanel(new GridLayout(eventsOnSelectedDay.size(), 1));
		scrollPanel.setBackground(Color.WHITE);
		scrollPanel.setOpaque(true);
	    scrollPane = new JScrollPane(scrollPanel);
	    scrollPane.setPreferredSize(new Dimension(500, 200));
	    
	    for(Event i : eventsOnSelectedDay)
		{
			scrollPanel.add(new EventLabel(this, i));
		}
	    
	    currentDay.setEventMarker(eventsOnSelectedDay.size() > 0);

	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.getViewport().add(scrollPanel, null);

	    add(scrollPane, BorderLayout.SOUTH);
	    
	    revalidate();
	}

	private void createButtons() {
		
//		northP.setBackground(new Color(138,157,180));
		
		JLabel temp = new JLabel("", SwingConstants.RIGHT);
		temp.setIcon(new ImageIcon("left.png"));
		temp.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.lastMonth();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			}
		);
		northP.add(temp, BorderLayout.WEST);
		
		temp = new JLabel("", SwingConstants.LEFT);
		temp.setIcon(new ImageIcon("right.png"));
		temp.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.nextMonth();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			}
		);
		northP.add(temp, BorderLayout.EAST);
	}

	private void createMonthTitle() {
		String month = new SimpleDateFormat("MMMM").format(start.getTime());
		String year = "" + start.get(Calendar.YEAR);
		JLabel temp = new JLabel(month + " " + year, SwingConstants.CENTER);
		temp.setFont(new Font("Helvetica", Font.BOLD, 40));
		northP.add(temp, BorderLayout.CENTER);
	}
	
	private void addMenu() {
		//Adds the menu and menu items to panel
		final MainFrame mainFrame = this.parent;
		menuBar = new JMenuBar();

		final JMenuItem emItem = new JMenuItem("Event Manager");
		final JMenuItem eItem = new JMenuItem("Export");
		final JMenuItem aItem = new JMenuItem("About");
		
		emItem.setBackground(new Color(138,157,180));
		eItem.setBackground(new Color(138,157,180));
		aItem.setBackground(new Color(138,157,180));
		
		emItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)mainFrame.outPanel.getLayout();
				cl.show(mainFrame.outPanel,"manager");
			}
		});
		
		eItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.export();
			}
		});
		
		aItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)mainFrame.outPanel.getLayout();
				cl.show(mainFrame.outPanel, "about");
			}
		});
		
		menuBar.add(emItem);
		menuBar.add(eItem);
		menuBar.add(aItem);
		
		northP.add(menuBar, BorderLayout.NORTH);
	}


	private void createDayBar() {
		String font = "Helvetica";
		int size = 14;
		
		JLabel temp = new JLabel("Sun", SwingConstants.CENTER);
		temp.setFont(new Font(font, Font.BOLD, size));
		dayBar.add(temp);
		
		temp = new JLabel("Mon", SwingConstants.CENTER);
		temp.setFont(new Font(font, Font.BOLD, size));
		dayBar.add(temp);
		
		temp = new JLabel("Tue", SwingConstants.CENTER);
		temp.setFont(new Font(font, Font.BOLD, size));
		dayBar.add(temp);
		
		temp = new JLabel("Wed", SwingConstants.CENTER);
		temp.setFont(new Font(font, Font.BOLD, size));
		dayBar.add(temp);
		
		temp = new JLabel("Thu", SwingConstants.CENTER);
		temp.setFont(new Font(font, Font.BOLD, size));
		dayBar.add(temp);
		
		temp = new JLabel("Fri", SwingConstants.CENTER);
		temp.setFont(new Font(font, Font.BOLD, size));
		dayBar.add(temp);
		
		temp = new JLabel("Sat", SwingConstants.CENTER);
		temp.setFont(new Font(font, Font.BOLD, size));
		dayBar.add(temp);

		
	}

	private void createDays() {
		days = new Day[6][7];
		start = new GregorianCalendar(start.get(Calendar.YEAR), start.get(Calendar.MONTH), 1);
		
		int firstDay = start.get(Calendar.DAY_OF_WEEK) - 2;
				
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				if (i == 0)
				{
					Calendar last = new GregorianCalendar(start.get(Calendar.YEAR), start.get(Calendar.MONTH) - 1, 1);
					int lastDay = last.getActualMaximum(Calendar.DAY_OF_MONTH);
					if(j <= firstDay)
						days[i][j] = new Day(this, lastDay - (firstDay - j), false, false);
					else
						days[i][j] = new Day(this, (i * 7 + j) - firstDay, true, false);
				}
				else if (i > 3)
				{
					int lastDay = start.getActualMaximum(Calendar.DAY_OF_MONTH);
					if((i * 7 + j - firstDay) <= lastDay)
						days[i][j] = new Day(this, (i * 7 + j) - firstDay, true, false);
					else
						days[i][j] = new Day(this, (i * 7 + j) - firstDay - lastDay, false, false);
				}
				else
				{
					days[i][j] = new Day(this, (i * 7 + j) - firstDay, true, false);
				}
				
				centerP.add(days[i][j]);
			}
		}	
	}

	public void createEvent() {
		CardLayout cl = (CardLayout)parent.outPanel.getLayout();
		cl.show(parent.outPanel,"add");
	}

	public void editEvent(EventLabel eventLabel) {
		
	}
	
	public void selectCurrentDay() {
		Calendar now = Calendar.getInstance();
		int day = 1;
		
		if(now.get(Calendar.MONTH) == start.get(Calendar.MONTH))
			day = now.get(Calendar.DAY_OF_MONTH);
		
		Day temp = getDay(day);
		temp.mouseClicked(null);
	}
	
	public void yellowCurrentDay() {
		Calendar now = Calendar.getInstance();
		
		if(now.get(Calendar.MONTH) == start.get(Calendar.MONTH))
		{
			int day = now.get(Calendar.DAY_OF_MONTH);
			Day temp = getDay(day);
			temp.selected = true;
		}
	}
	
	public Day getDay(int day)
	{
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				Day temp = days[i][j];
				if(temp.inMonth && temp.day == day)
					return temp;
			}
		}
		return null;
	}

}
