package me.gosimple.nbvcxz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.gosimple.nbvcxz.matching.DateMatcher;
import me.gosimple.nbvcxz.matching.DictionaryMatcher;
import me.gosimple.nbvcxz.matching.PasswordMatcher;
import me.gosimple.nbvcxz.matching.RepeatMatcher;
import me.gosimple.nbvcxz.matching.SeparatorMatcher;
import me.gosimple.nbvcxz.matching.SequenceMatcher;
import me.gosimple.nbvcxz.matching.SpacialMatcher;
import me.gosimple.nbvcxz.matching.YearMatcher;
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
	private Map<String, Integer> sortedDic;

	
	//TODO: check if all pattern arrays are sorted from the most frequent to least frequent
	//list of words that are found to be date pattern
	private ArrayList<String> datePatterns = new ArrayList<String>();
	private ArrayList<String> dictionaryPatterns = new ArrayList<String>();
	private ArrayList<String> repeatPatterns = new ArrayList<String>();
	private ArrayList<String> separatorPatterns = new ArrayList<String>();
	private ArrayList<String> sequencePatterns = new ArrayList<String>();
	private ArrayList<String> spacialPatterns = new ArrayList<String>();
	private ArrayList<String> yearPatterns = new ArrayList<String>();
	
	public FixPasswordGUI(SubGUIProgram subgui, String userdata, Dictionary userdataDic) {
		this.subgui = subgui;
		this.userdata = userdata;
		this.userdataDic = userdataDic;
		// reverse order; most infrequent ones first
		sortedDic = SubGUIProgram.sortByValue(userdataDic.getDictonary());
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
			tfFixedPW1.setText(fixedPW);
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
		ArrayList<String> patterns = new ArrayList<String>();
		ArrayList<String> tokens = new ArrayList<String>();
		for (Match match : result.getMatches())
        {
            System.out.println("-----------------------------------");
            System.out.println(match.getDetails());
            String patternName = match.getClass().getSimpleName();
            String token = match.getToken();
            //System.out.println("patternName = " + patternName);
            
            tokens.add(token);
            patterns.add(patternName);
        }
		
		
		fixedPW = createPWgivenPattern(patterns, tokens);
		
		return fixedPW;
	}
	
	//determine given pattern of words in user data dictionary and store
	private void findWordsWithPattern(String pattern) {
		PasswordMatcher matcher = null;
		
		switch(pattern) {
		case "DateMatch":
			matcher = new DateMatcher();
			break;
		case "DictionaryMatch":
			matcher = new DictionaryMatcher();
			break;
		case "RepeatMatch":
			matcher = new RepeatMatcher();
			break;
		case "SeparatorMatch":
			matcher = new SeparatorMatcher();
			break;
			
			//do not know why this one starts with lower case
		case "sequenceMatch":
			matcher = new SequenceMatcher();
			break;
		case "SpacialMatch":
			matcher = new SpacialMatcher();
			break;
		case "YearMatch":
			matcher = new YearMatcher();
			break;
			
		}
		
		List<Match> matches = new ArrayList<>();
		
		final int high = userdataDic.getDictonary().size();
		Nbvcxz nbvcxz = new Nbvcxz();
		// 
		add(new Label("found " + high + " words in dictionary"));
		setVisible(true);
		Label wordCounter = new Label("finding 0th word's matches...");
		add(wordCounter);
		setVisible(true);
		for (int i = 0; i < high; i++) {
			
			if (i == 0) {
				
			}
			else {
				wordCounter.setText("finding " + i + "th word's matches...");
			}
			int rank = i + 1;
			//currentwordlbl.setText("checking if " + daterank + "th word is date pattern...");
			String word = SubGUIProgram.getKeyByValue(sortedDic, rank);
			
			//only count the matches that are not too long, to save time
			if (word.length() <= 15)
				matches.addAll(matcher.match(nbvcxz.getConfiguration(), word));
		}
		
		// if no date pattern is found, return immediately
		if (matches.size() == 0) {

			return;

		}
		add(new Label("found " + matches.size() + " matches"));
		setVisible(true);
		Label counter;
		counter = new Label("adding 0th match...");

		add(counter);
		setVisible(true);
		for (int i = 0; i < matches.size(); i++) {
			if (i == 0) {
				
			}
			else {
				counter.setText("adding " + i + "th match...");
				setVisible(true);
			}
			String word = matches.get(i).getToken();
			switch(pattern) {
			case "DateMatch":
				datePatterns.add(word);
				break;
			case "DictionaryMatch":
				dictionaryPatterns.add(word);
				break;
			case "RepeatMatch":
				repeatPatterns.add(word);
				break;
			case "SeparatorMatch":
				separatorPatterns.add(word);
				break;
			case "SequenceMatch":
				sequencePatterns.add(word);
				break;
			case "SpacialMatch":
				spacialPatterns.add(word);
				break;
			case "YearMatch":
				yearPatterns.add(word);
				break;
				
			}
			
		}
	}
	
	
	//TODO: if a word with required pattern is not found in user data dic, use word specified by the user
	//REQUIRES:  patterns and tokens must have same number of elements
	private String createPWgivenPattern(ArrayList<String> patterns, ArrayList<String> tokens) {
		String createdpw = "";
		
		//find words with given pattern if they were not found before
		for(int i=0; i<patterns.size(); i++) {
			switch(patterns.get(i)) {
			case "DateMatch":
				if (datePatterns.size() == 0)
					findWordsWithPattern("DateMatch");
				
				break;
			case "DictionaryMatch":
				if (dictionaryPatterns.size() == 0)
					findWordsWithPattern("DictionaryMatch");
				break;
			case "RepeatMatch":
				if (repeatPatterns.size() == 0)
					findWordsWithPattern("repeatMatch");
				break;
			case "SeparatorMatch":
				if (separatorPatterns.size() == 0)
					findWordsWithPattern("separatorMatch");
				break;
			case "SequenceMatch":
				if (sequencePatterns.size() == 0)
					findWordsWithPattern("sequenceMatch");
				break;
			case "SpacialMatch":
				if (spacialPatterns.size() == 0)
					findWordsWithPattern("spacialMatch");
				break;
			case "YearMatch":
				if (yearPatterns.size() == 0)
					findWordsWithPattern("yearMatch");
				break;
				
			}
		}
		
		//fill createdpw with given patterns, fill with the most frequent ones, for now
		for(int i=0; i<patterns.size(); i++) {
			switch(patterns.get(i)) {
			case "DateMatch":
				if (datePatterns.size() != 0)
					createdpw = createdpw + datePatterns.get(0);
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "DictionaryMatch":
				if (dictionaryPatterns.size() != 0)
					createdpw = createdpw + dictionaryPatterns.get(0);
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "RepeatMatch":
				if (repeatPatterns.size() != 0)
					createdpw = createdpw + repeatPatterns.get(0);
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "SeparatorMatch":
				if (separatorPatterns.size() != 0)
					createdpw = createdpw + separatorPatterns.get(0);
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "SequenceMatch":
				if (sequencePatterns.size() != 0)
					createdpw = createdpw + sequencePatterns.get(0);
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "SpacialMatch":
				if (spacialPatterns.size() != 0)
					createdpw = createdpw + spacialPatterns.get(0);
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "YearMatch":
				if (yearPatterns.size() != 0)
					createdpw = createdpw + yearPatterns.get(0);
				else
					createdpw = createdpw + tokens.get(i);
				break;
				
			}
		}
		
		return createdpw;
		
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
