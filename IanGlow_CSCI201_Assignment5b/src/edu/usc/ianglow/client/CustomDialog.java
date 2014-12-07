package edu.usc.ianglow.client;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class CustomDialog extends JDialog
implements ActionListener,
PropertyChangeListener {
	private String typedText = null;
	private JTextField textField;
	private  ClientFrame dd;

	private String magicWord;
	private JOptionPane optionPane;

	private String btnString1 = "Connect";
	private String btnString2 = "Cancel";

	/**
	 * Returns null if the typed string was invalid;
	 * otherwise, returns the string as the user entered it.
	 */
	public String getValidatedText() {
		return typedText;
	}

	/** Creates the reusable dialog. */
	public CustomDialog(Frame aFrame, ClientFrame parent) {
		super(aFrame, true);
		dd = parent;
		setMinimumSize(new Dimension(300,180));
		setTitle("Quiz");

		textField = new JTextField(10);
		textField.setText("127.0.0.1");
		//		setMinimumSize(new Dimension(50*9 + 280,50*9 + 110));

		//Create an array of the text and components to be displayed.
		String msgString1 = "What is the Host?";
		String msgString2 = "";
		Object[] array = {msgString1, msgString2, textField};

		//Create an array specifying the number of dialog buttons
		//and their text.
		Object[] options = {btnString1, btnString2};

		//Create the JOptionPane.
		optionPane = new JOptionPane(array,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION,
				null,
				options,
				options[0]);

		//Make this dialog display it.
		setContentPane(optionPane);

		//Handle window closing correctly.
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				/*
				 * Instead of directly closing the window,
				 * we're going to change the JOptionPane's
				 * value property.
				 */
				optionPane.setValue(new Integer(
						JOptionPane.CLOSED_OPTION));
			}
		});

		//Ensure the text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textField.requestFocusInWindow();
			}
		});

		//Register an event handler that puts the text into the option pane.
		textField.addActionListener(this);

		//Register an event handler that reacts to option pane state changes.
		optionPane.addPropertyChangeListener(this);
	}

	/** This method handles events for the text field. */
	public void actionPerformed(ActionEvent e) {
		optionPane.setValue(btnString1);
	}

	/** This method reacts to state changes in the option pane. */
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();

		if (isVisible()
				&& (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop) ||
						JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				//ignore reset
				return;
			}

			//Reset the JOptionPane's value.
			//If you don't do this, then if the user
			//presses the same button next time, no
			//property change event will be fired.
			optionPane.setValue(
					JOptionPane.UNINITIALIZED_VALUE);

			if (btnString1.equals(value)) {

				Socket sock;
				try {
					sock = new Socket(textField.getText(), 10000);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(
							CustomDialog.this,
							"Failed to connect",
							"Try again",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					return;
				}
				//				
				dd.socket = sock;
				clearAndHide();
				textField.requestFocusInWindow();
			}	
			else { //user closed dialog or clicked cancel
				//				dd.setLabel("It's OK.  "
				//						+ "We won't force you to type "
				//						+ magicWord + ".");
				//				typedText = null;
				System.out.println("Close");
				dd.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				clearAndHide();
			}
		}

	}

	/** This method clears the dialog and hides it. */
	public void clearAndHide() {
		textField.setText(null);
		setVisible(false);
	}
}