package me.gosimple.nbvcxz;

import java.awt.*;
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

//An AWT GUI program inherits the top-level container java.awt.Frame
public class GUIProgram extends Frame implements ActionListener, WindowListener {
	// This class acts as listener for ActionEvent and WindowEvent
	// A Java class can extend only one superclass, but it can implement multiple
	// interfaces.

	public TextField tfCount1, tfCount2, tfCount3; // Declare a TextField component
	private Button btnCount; // Declare a Button component
	

	// Constructor to setup the GUI components and event handlers
	public GUIProgram() {
		setLayout(new FlowLayout()); // "super" Frame sets to FlowLayout

		add(new Label("Username  ")); // "super" Frame adds an anonymous Label
		tfCount1 = new TextField("", 10); // Construct the TextField
		tfCount1.setEditable(true); 
		add(tfCount1); // "super" Frame adds TextField
		
		add(new Label("Password")); // "super" Frame adds an anonymous Label
		tfCount2 = new TextField("", 10); // Construct the TextField
		tfCount2.setEditable(true); 
		add(tfCount2); // "super" Frame adds TextField
		
		add(new Label("Revised Password")); // "super" Frame adds an anonymous Label
		tfCount3 = new TextField("", 10); // Construct the TextField
		tfCount3.setEditable(false); // read-only
		add(tfCount3); // "super" Frame adds TextField

		btnCount = new Button("Submit"); // Construct the Button
		add(btnCount); // "super" Frame adds Button

		btnCount.addActionListener(this);
		// btnCount (source object) fires ActionEvent upon clicking
		// btnCount adds "this" object as an ActionEvent listener

		addWindowListener(this);
		// "super" Frame (source object) fires WindowEvent.
		// "super" Frame adds "this" object as a WindowEvent listener.

		setTitle("Password Meter"); // "super" Frame sets title
		setSize(250, 200); // "super" Frame sets initial size
		setVisible(true); // "super" Frame shows
	}

	/* ActionEvent handler */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String originalpw = tfCount2.getText();
		//tfCount3.setText(originalpw);
		
		InputStream testInput;
		try {
			// Create a stream to hold the output
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    PrintStream ps = new PrintStream(baos);
		    // IMPORTANT: Save the old System.out!
		    PrintStream old = System.out;
		    // Tell Java to use your special stream
		    System.setOut(ps);
			
			testInput = new ByteArrayInputStream("g".getBytes("UTF-8"));
			System.setIn( testInput );
			
		   
		    // Put things back
		    System.out.flush();
		    System.setOut(old);
		    // Show what happened
		    String revisedpw = baos.toString();
		    tfCount3.setText(revisedpw);
		    
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream old = System.in;
		
		System.setIn( old );
		
	}

	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) {
		System.exit(0); // Terminate the program
	}

	// Not Used, but need to provide an empty body to compile.
	@Override
	public void windowOpened(WindowEvent evt) {
	}

	@Override
	public void windowClosed(WindowEvent evt) {
	}

	@Override
	public void windowIconified(WindowEvent evt) {
	}

	@Override
	public void windowDeiconified(WindowEvent evt) {
	}

	@Override
	public void windowActivated(WindowEvent evt) {
	}

	@Override
	public void windowDeactivated(WindowEvent evt) {
	}
}