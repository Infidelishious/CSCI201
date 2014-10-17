package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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

	String[] columnNames = {"Car #","X","Y"};
	ArrayList<String[]> data;
	ArrayList<Car> cars;
	
	
	public DrawingPanel drawPanel;
	public JTable table;
	public JMenuItem openButton;
	public MainFrame thiss;
	
	public MainFrame()
	{
		super("Roadway Simulator");
		
		cars = new ArrayList<Car>();
		
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
				 
				int userSelection = fileChooser.showOpenDialog(thiss);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToOpen = fileChooser.getSelectedFile();
				    
				    try {
						
						parse(fileToOpen);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				    
				    System.out.println("Opened  file: " + fileToOpen.getAbsolutePath());
				}
			}
		});
		add(openButton, BorderLayout.NORTH);
		
		data = new ArrayList<String[]>();
		data.add(columnNames);
		
		makeTable(true);
		
		drawPanel = new DrawingPanel();
		OutPanel panel = new OutPanel();
		panel.add(drawPanel);
		
		add(panel, BorderLayout.CENTER);
	}

	public void makeTable(boolean first) {
		if(!first)
			remove(table);
		
		TableModel dataModel = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;
			public int getColumnCount() { return 3; }
			public int getRowCount() { return data.size();}
			public Object getValueAt(int row, int col) { 
				if(data.size() > row){
					return data.get(row)[col];
				}
				else
					return "";
			}
		};

		table = new JTable(dataModel);
		table.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		table.setPreferredSize(new Dimension(200,600));
		table.setBackground(SWING_GRAY);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        c.setBackground(row == 0 ? SWING_GRAY : Color.WHITE);
		        return c;
		    }
		};
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, renderer);
		table.setDefaultRenderer(Object.class, renderer);
		add(table, BorderLayout.EAST);
		revalidate();
	}

	
	protected void parse(File in) throws Exception {
		
		boolean[][][] directions = new boolean[9][9][4];
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(in);
		NodeList tileList = doc.getDocumentElement().getElementsByTagName("tile");
		NodeList carList = doc.getDocumentElement().getElementsByTagName("car");
		NodeList locList = doc.getDocumentElement().getElementsByTagName("location");
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				Element n = (Element) tileList.item(i*9 + j);
				String t = n.getAttribute("type");
				String d = n.getAttribute("degree");
				boolean[] passible = createNodeArray(t,d);
				directions[i][j] = passible;
			}
		}
		drawPanel.update(directions);
		
		for(int i = 0; i < carList.getLength(); i++)
		{
			Element n = (Element) carList.item(i),
					m = (Element) locList.item(i);
			
			int ai = Integer.parseInt(n.getAttribute("ai"));
			if(ai == 1){
				cars.add(new Car1(this, i + 1, 
						Integer.parseInt(n.getAttribute("ai")), 
						Integer.parseInt(m.getAttribute("x")) - 1, 
						m.getAttribute("y").charAt(0) - 'A', 
						n.getAttribute("color"),
						Float.parseFloat(n.getAttribute("speed"))));	
			}
			else if(ai == 2){
				cars.add(new Car2(this, i + 1, 
						Integer.parseInt(n.getAttribute("ai")), 
						Integer.parseInt(m.getAttribute("x")) - 1, 
						m.getAttribute("y").charAt(0) - 'A', 
						n.getAttribute("color"),
						Float.parseFloat(n.getAttribute("speed"))));	
			}
			else if(ai == 3){
				cars.add(new Car3(this, i + 1, 
						Integer.parseInt(n.getAttribute("ai")), 
						Integer.parseInt(m.getAttribute("x")) - 1, 
						m.getAttribute("y").charAt(0) - 'A', 
						n.getAttribute("color"),
						Float.parseFloat(n.getAttribute("speed"))));	
			}
			else if(ai == 4){
				cars.add(new Car4(this, i + 1, 
						Integer.parseInt(n.getAttribute("ai")), 
						Integer.parseInt(m.getAttribute("x")) - 1, 
						m.getAttribute("y").charAt(0) - 'A', 
						n.getAttribute("color"),
						Float.parseFloat(n.getAttribute("speed"))));	
			}
		}
		
		for(Car i: cars)
		{
			String[] names = {"" + i.num,
		            "" + (i.x + 1),
		            "" + new String(Character.toChars(i.y + 'A'))};
			data.add(names);
			makeTable(false);
			
			System.out.println(i.toString());
			new Thread(i).start();
		}
	}


	private boolean[] createNodeArray(String t, String d) {
		boolean[] array = new boolean[4];
		if(t.equalsIgnoreCase("blank"))
		{
			array[Land.UP]    = false;
			array[Land.RIGHT] = false;
			array[Land.DOWN]  = false;
			array[Land.LEFT]  = false;
		}
		else if(t.equalsIgnoreCase("i"))
		{
			array[Land.UP]    = true;
			array[Land.RIGHT] = false;
			array[Land.DOWN]  = true;
			array[Land.LEFT]  = false;
		}
		else if(t.equalsIgnoreCase("l"))
		{
			array[Land.UP]    = true;
			array[Land.RIGHT] = true;
			array[Land.DOWN]  = false;
			array[Land.LEFT]  = false;
		}
		else if(t.equalsIgnoreCase("t"))
		{
			array[Land.UP]    = false;
			array[Land.RIGHT] = true;
			array[Land.DOWN]  = true;
			array[Land.LEFT]  = true;
		}
		else{
			array[Land.UP]    = true;
			array[Land.RIGHT] = true;
			array[Land.DOWN]  = true;
			array[Land.LEFT]  = true;
		}
		
		return rotate(array, Integer.parseInt(d));
	}

	private boolean[] rotate(boolean[] array, int d) {
		if(d == 0) return array;
		
		boolean temp = array[0];
		array[0] = array[1];
		array[1] = array[2];
		array[2] = array[3];
		array[3] = temp;

		return rotate(array, d - 90);
	}

	public static void main(String[] args)
	{
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		mainFrame.setSize(800,600);
		mainFrame.setMinimumSize(new Dimension(50*9 + 280,50*9 + 110));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
