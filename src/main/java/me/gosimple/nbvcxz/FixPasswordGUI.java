package me.gosimple.nbvcxz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
//	private ArrayList<String> datePatterns = new ArrayList<String>();
//	private ArrayList<String> dictionaryPatterns = new ArrayList<String>();
//	private ArrayList<String> repeatPatterns = new ArrayList<String>();
//	private ArrayList<String> separatorPatterns = new ArrayList<String>();
//	private ArrayList<String> sequencePatterns = new ArrayList<String>();
//	private ArrayList<String> spacialPatterns = new ArrayList<String>();
//	private ArrayList<String> yearPatterns = new ArrayList<String>();
//	//private ArrayList<String> bruteforcePatterns = new ArrayList<String>();
	
	//counts how many times each word occurred, sorted so most frequent ones appear first
//	private Map<String, Integer> dateSortedMap = new LinkedHashMap<String, Integer>();
//	private Map<String, Integer> dictionarySortedMap = new LinkedHashMap<String, Integer>();
//	private Map<String, Integer> repeatSortedMap = new LinkedHashMap<String, Integer>();
//	private Map<String, Integer> separatorSortedMap = new LinkedHashMap<String, Integer>();
//	private Map<String, Integer> sequenceSortedMap = new LinkedHashMap<String, Integer>();
//	private Map<String, Integer> spacialSortedMap = new LinkedHashMap<String, Integer>();
//	private Map<String, Integer> yearSortedMap = new LinkedHashMap<String, Integer>();
//	//private Map<String, Integer> bruteforceSortedMap = new HashMap<String, Integer>();
	
	//most frequent ones first
	private List<String> dateSortedWords = new ArrayList<String>();
	private List<String> dictionarySortedWords = new ArrayList<String>();
	private List<String> repeatSortedWords = new ArrayList<String>();
	private List<String> separatorSortedWords = new ArrayList<String>();
	private List<String> sequenceSortedWords = new ArrayList<String>();
	private List<String> spacialSortedWords = new ArrayList<String>();
	private List<String> yearSortedWords = new ArrayList<String>();
	
	
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
			
		case "SequenceMatch":
			matcher = new SequenceMatcher();
			break;
		case "SpacialMatch":
			matcher = new SpacialMatcher();
			break;
		case "YearMatch":
			matcher = new YearMatcher();
			break;
//		case "BruteForceMatch":
//			matcher = new BruteForceMatcher();
//			break;
			
		}
		
		//List<Match> matches = new ArrayList<>();
		
		final int high = userdataDic.getDictonary().size();
		Nbvcxz nbvcxz = new Nbvcxz();
		// 
		add(new Label("found " + high + " words in dictionary"));
		setVisible(true);
		Label wordCounter = new Label("finding 0th word's matches...");
		add(wordCounter);
		setVisible(true);
		List<String> words = new ArrayList<String>();
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
				//matches.addAll(matcher.match(nbvcxz.getConfiguration(), word));
				
				//add only if given pattern is found in this word
				if (matcher.match(nbvcxz.getConfiguration(), word).size() > 0)
					words.add(word);
		}
		
		
		//old version which adds all matches, instead of whole words
		// if no date pattern is found, return immediately
//		if (matches.size() == 0) {
//
//			return;
//
//		}
//		add(new Label("found " + matches.size() + " matches"));
//		setVisible(true);
//		Label counter;
//		counter = new Label("adding 0th match...");
//
//		add(counter);
//		setVisible(true);
//		for (int i = 0; i < matches.size(); i++) {
//			if (i == 0) {
//				
//			}
//			else {
//				counter.setText("adding " + i + "th match...");
//				setVisible(true);
//			}
//			String word = matches.get(i).getToken();
//			switch(pattern) {
//			case "DateMatch":
//				// if this word is already added to map before
//				if (dateSortedMap.containsKey(word)) {
//					dateSortedMap.put(word, dateSortedMap.get(word) + 1);
//				}
//				// if this word is encountered for the first time
//				else {
//					dateSortedMap.put(word, 1);
//				}
//				break;
//			case "DictionaryMatch":
//				// if this word is already added to map before
//				if (dictionarySortedMap.containsKey(word)) {
//					dictionarySortedMap.put(word, dictionarySortedMap.get(word) + 1);
//				}
//				// if this word is encountered for the first time
//				else {
//					dictionarySortedMap.put(word, 1);
//				}
//				break;
//			case "RepeatMatch":
//				// if this word is already added to map before
//				if (repeatSortedMap.containsKey(word)) {
//					repeatSortedMap.put(word, repeatSortedMap.get(word) + 1);
//				}
//				// if this word is encountered for the first time
//				else {
//					repeatSortedMap.put(word, 1);
//				}
//				break;
//			case "SeparatorMatch":
//				// if this word is already added to map before
//				if (separatorSortedMap.containsKey(word)) {
//					separatorSortedMap.put(word, separatorSortedMap.get(word) + 1);
//				}
//				// if this word is encountered for the first time
//				else {
//					separatorSortedMap.put(word, 1);
//				}
//				break;
//			case "SequenceMatch":
//				// if this word is already added to map before
//				if (sequenceSortedMap.containsKey(word)) {
//					sequenceSortedMap.put(word, sequenceSortedMap.get(word) + 1);
//				}
//				// if this word is encountered for the first time
//				else {
//					sequenceSortedMap.put(word, 1);
//				}
//				break;
//			case "SpacialMatch":
//				// if this word is already added to map before
//				if (spacialSortedMap.containsKey(word)) {
//					spacialSortedMap.put(word, spacialSortedMap.get(word) + 1);
//				}
//				// if this word is encountered for the first time
//				else {
//					spacialSortedMap.put(word, 1);
//				}
//				break;
//			case "YearMatch":
//				// if this word is already added to map before
//				if (yearSortedMap.containsKey(word)) {
//					yearSortedMap.put(word, yearSortedMap.get(word) + 1);
//				}
//				// if this word is encountered for the first time
//				else {
//					yearSortedMap.put(word, 1);
//				}
//				break;
////			case "BruteForceMatch":
////				bruteforcePatterns.add(word);
////				break;
//				
//			}
		
		if (words.isEmpty()) {
			return;
		}
		add(new Label("found " + words.size() + " words"));
		setVisible(true);
		Label counter;
		counter = new Label("adding 0th word...");

		add(counter);
		setVisible(true);

		switch (pattern) {
		case "DateMatch":
			dateSortedWords = words;
			break;
		case "DictionaryMatch":
			dictionarySortedWords = words;
			break;
		case "RepeatMatch":
			repeatSortedWords = words;
			break;
		case "SeparatorMatch":
			separatorSortedWords = words;
			break;
		case "SequenceMatch":
			sequenceSortedWords = words;
			break;
		case "SpacialMatch":
			spacialSortedWords = words;
			break;
		case "YearMatch":
			yearSortedWords = words;
			break;
		// case "BruteForceMatch":
		// bruteforcePatterns.add(word);
		// break;

		}

//		//done with addition, sort
//		switch(pattern) {
//		case "DateMatch":
//			dateSortedMap = SubGUIProgram.sortByValue(dateSortedMap);
//			break;
//		case "DictionaryMatch":
//			dictionarySortedMap = SubGUIProgram.sortByValue(dictionarySortedMap);
//			break;
//		case "RepeatMatch":
//			repeatSortedMap = SubGUIProgram.sortByValue(repeatSortedMap);
//			break;
//		case "SeparatorMatch":
//			separatorSortedMap = SubGUIProgram.sortByValue(separatorSortedMap);
//			break;
//		case "SequenceMatch":
//			sequenceSortedMap = SubGUIProgram.sortByValue(sequenceSortedMap);
//			break;
//		case "SpacialMatch":
//			spacialSortedMap = SubGUIProgram.sortByValue(spacialSortedMap);
//			break;
//		case "YearMatch":
//			yearSortedMap = SubGUIProgram.sortByValue(yearSortedMap);
//			break;
////		case "BruteForceMatch":
////			bruteforcePatterns.add(word);
////			break;
//			
//		}
	}
	
	
	//TODO: if a word with required pattern is not found in user data dic, use word specified by the user
	//REQUIRES:  patterns and tokens must have same number of elements
	private String createPWgivenPattern(ArrayList<String> patterns, ArrayList<String> tokens) {
		String createdpw = "";
		
		//find words with given pattern if they were not found before
		for(int i=0; i<patterns.size(); i++) {
			switch(patterns.get(i)) {
			case "DateMatch":
//				if (datePatterns.size() == 0)
				if (dateSortedWords.isEmpty())
					findWordsWithPattern("DateMatch");	
				break;
			case "DictionaryMatch":
				//if (dictionaryPatterns.size() == 0)
				if (dictionarySortedWords.isEmpty())
					findWordsWithPattern("DictionaryMatch");
				break;
			case "RepeatMatch":
				//if (repeatPatterns.size() == 0)
				if (repeatSortedWords.isEmpty())	
					findWordsWithPattern("RepeatMatch");
				break;
			case "SeparatorMatch":
				///if (separatorPatterns.size() == 0)
				if (separatorSortedWords.isEmpty())
					findWordsWithPattern("SeparatorMatch");
				break;
			case "SequenceMatch":
				//if (sequencePatterns.size() == 0)
				if (sequenceSortedWords.isEmpty())
					findWordsWithPattern("SequenceMatch");
				break;
			case "SpacialMatch":
				//if (spacialPatterns.size() == 0)
				if (spacialSortedWords.isEmpty())
					findWordsWithPattern("SpacialMatch");
				break;
			case "YearMatch":
				//if (yearPatterns.size() == 0)
				if (yearSortedWords.isEmpty())
					findWordsWithPattern("YearMatch");
				break;
//			case "BruteForceMatch":
//				if (bruteforcePatterns.size() == 0)
//					findWordsWithPattern("BruteForceMatch");
//				break;
				
			}
		}
		
		//fill createdpw with given patterns, fill with the most frequent ones, for now
		for(int i=0; i<patterns.size(); i++) {
			//tells if no word satisfying the requirement is found
			//boolean notFound = false;
			//int patternsize;
			switch (patterns.get(i)) {
			
			case "DateMatch":
//				patternsize = datePatterns.size();
//				for (int j = 0; j < patternsize; j++) {
//					String thisword = datePatterns.get(j);
//					// add only if the word is not one of bannedStr
//					if (!(Arrays.asList(bannedStr).contains(thisword.toLowerCase()))) {
//						createdpw = createdpw + thisword;
//						break;
//					}
//					if (j == (patternsize -1))
//						notFound = true;
//				}
//
//				if (notFound)
//					createdpw = createdpw + tokens.get(i);
//				
//				break;
				
				//fill with the most frequent ones, for now
				if (!dateSortedWords.isEmpty()){
					String word = dateSortedWords.get(0);
					createdpw = createdpw + word;
				}
				 else
					createdpw = createdpw + tokens.get(i);
				break;
			case "DictionaryMatch":
//				patternsize = dictionaryPatterns.size();
//				for (int j = 0; j < patternsize; j++) {
//					String thisword = dictionaryPatterns.get(j);
//					// add only if the word is not one of bannedStr
//					if (!(Arrays.asList(bannedStr).contains(thisword.toLowerCase()))) {
//						createdpw = createdpw + thisword;
//						break;
//					}
//					if (j == (patternsize -1))
//						notFound = true;
//				}
//
//				if (notFound)
//					createdpw = createdpw + tokens.get(i);
//				
//				break;
				if (!dictionarySortedWords.isEmpty()){
					String word = dictionarySortedWords.get(0);
					createdpw = createdpw + word;
				}
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "RepeatMatch":
//				patternsize = repeatPatterns.size();
//				for (int j = 0; j < patternsize; j++) {
//					String thisword = repeatPatterns.get(j);
//					// add only if the word is not one of bannedStr
//					if (!(Arrays.asList(bannedStr).contains(thisword.toLowerCase()))) {
//						createdpw = createdpw + thisword;
//						break;
//					}
//					if (j == (patternsize -1))
//						notFound = true;
//				}
//
//				if (notFound)
//					createdpw = createdpw + tokens.get(i);
//				
//				break;
				if (!repeatSortedWords.isEmpty()){
					String word = repeatSortedWords.get(0);
					createdpw = createdpw + word;
				}
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "SeparatorMatch":
//				patternsize = separatorPatterns.size();
//				for (int j = 0; j < patternsize; j++) {
//					String thisword = separatorPatterns.get(j);
//					// add only if the word is not one of bannedStr
//					if (!(Arrays.asList(bannedStr).contains(thisword.toLowerCase()))) {
//						createdpw = createdpw + thisword;
//						break;
//					}
//					if (j == (patternsize -1))
//						notFound = true;
//				}
//
//				if (notFound)
//					createdpw = createdpw + tokens.get(i);
//				
//				break;
				if (!separatorSortedWords.isEmpty()){
					String word = separatorSortedWords.get(0);
					createdpw = createdpw + word;
				}
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "SequenceMatch":
//				patternsize = sequencePatterns.size();
//				for (int j = 0; j < patternsize; j++) {
//					String thisword = sequencePatterns.get(j);
//					// add only if the word is not one of bannedStr
//					if (!(Arrays.asList(bannedStr).contains(thisword.toLowerCase()))) {
//						createdpw = createdpw + thisword;
//						break;
//					}
//					if (j == (patternsize -1))
//						notFound = true;
//				}
//
//				if (notFound)
//					createdpw = createdpw + tokens.get(i);
//				
//				break;
				if (!sequenceSortedWords.isEmpty()){
					String word = sequenceSortedWords.get(0);
					createdpw = createdpw + word;
				}
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "SpacialMatch":
//				patternsize = spacialPatterns.size();
//				for (int j = 0; j < patternsize; j++) {
//					String thisword = spacialPatterns.get(j);
//					// add only if the word is not one of bannedStr
//					if (!(Arrays.asList(bannedStr).contains(thisword.toLowerCase()))) {
//						createdpw = createdpw + thisword;
//						break;
//					}
//					if (j == (patternsize -1))
//						notFound = true;
//				}
//
//				if (notFound)
//					createdpw = createdpw + tokens.get(i);
//				
//				break;
				if (!spacialSortedWords.isEmpty()){
					String word = spacialSortedWords.get(0);
					createdpw = createdpw + word;
				}
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "YearMatch":
//				patternsize = yearPatterns.size();
//				for (int j = 0; j < patternsize; j++) {
//					String thisword = yearPatterns.get(j);
//					// add only if the word is not one of bannedStr
//					if (!(Arrays.asList(bannedStr).contains(thisword.toLowerCase()))) {
//						createdpw = createdpw + thisword;
//						break;
//					}
//					if (j == (patternsize -1))
//						notFound = true;
//				}
//
//				if (notFound)
//					createdpw = createdpw + tokens.get(i);
//				
//				break;
				if (!yearSortedWords.isEmpty()){
					String word = yearSortedWords.get(0);
					createdpw = createdpw + word;
				}
				else
					createdpw = createdpw + tokens.get(i);
				break;
			case "BruteForceMatch":
//				patternsize = yearPatterns.size();
//				for (int j = 0; j < patternsize; j++) {
//					String thisword = yearPatterns.get(j);
//					// add only if the word is not one of bannedStr
//					if (!(Arrays.asList(bannedStr).contains(thisword.toLowerCase()))) {
//						createdpw = createdpw + thisword;
//						break;
//					}
//					if (j == (patternsize -1))
//						notFound = true;
//				}
//
//				if (notFound)
//					createdpw = createdpw + tokens.get(i);
//				
//				break;
				
				
//				if (bruteforcePatterns.size() != 0)
//					createdpw = createdpw + bruteforcePatterns.get(0);
//				else
				
				
				//if bruteforcematch just add what user input
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
