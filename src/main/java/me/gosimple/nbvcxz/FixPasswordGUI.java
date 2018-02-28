package me.gosimple.nbvcxz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Dictionary;
import me.gosimple.nbvcxz.scoring.Result;

public class FixPasswordGUI extends Frame implements ActionListener, WindowListener{
	
	private SubGUIProgram subgui;
	private String userdata;
	private Dictionary userdataDic;
	private TextField tfFixedPW1 = new TextField("", 40);
	private TextField tfFixedPW2 = new TextField("", 40);
	private TextField tfFixedPW3 = new TextField("", 40);
	private TextField userInputPW = new TextField("", 40);
	private Button btnSubmit = new Button("Submit");
	private Label fixedpwlbl1 = new Label("Fixed PW 1");
	private Label fixedpwlbl2 = new Label("Fixed PW 2");
	private Label fixedpwlbl3 = new Label("Fixed PW 3");
	private Label entropyBeforelbl;
	
	public FixPasswordGUI(SubGUIProgram subgui, String userdata, Dictionary userdataDic) {
		this.subgui = subgui;
		this.userdata = userdata;
		this.userdataDic = userdataDic;
		setLayout(new FlowLayout());

		userInputPW.setEditable(true);
		add(userInputPW);
	
		add(btnSubmit); // "super" Frame adds Button
		btnSubmit.addActionListener(this);
		
		add(fixedpwlbl1);
		tfFixedPW1.setEditable(false);
		add(tfFixedPW1);
		
		add(fixedpwlbl2);
		tfFixedPW2.setEditable(false);
		add(tfFixedPW2);
		
		add(fixedpwlbl3);
		tfFixedPW3.setEditable(false);
		add(tfFixedPW3);
		
		addWindowListener(this);
		// "super" Frame (source object) fires WindowEvent.
		// "super" Frame adds "this" object as a WindowEvent listener.

		setTitle("Password Fixer"); // "super" Frame sets title
		setSize(350, 750); // "super" Frame sets initial size
		setResizable(false);
		setVisible(true); // "super" Frame shows

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnSubmit) {
			if(entropyBeforelbl != null)
				entropyBeforelbl.setVisible(false);
			String inputPW = userInputPW.getText();
			String fixedPW = fixPassword(inputPW);
		}
	}
	
	private String fixPassword(String inputPW) {
		String fixedPW;
		
		Result result = subgui.nbvcxz.estimate(inputPW);
		Double entropyBefore = result.getEntropy();
		entropyBeforelbl = new Label("Password you entered has an entropy of " + entropyBefore);
		
		addElement(entropyBeforelbl);
		//add(entropyBeforelbl);
		//setVisible(true);
		for (Match match : result.getMatches())
        {
            System.out.println("-----------------------------------");
            System.out.println(match.getDetails());
        }
		
		return "";
	}

	// adds component to GUI and makes it visible
	private void addElement(Component cmp) {
		add(cmp);
		setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
