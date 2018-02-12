package me.gosimple.nbvcxz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.gosimple.nbvcxz.resources.Dictionary;
import me.gosimple.nbvcxz.resources.DictionaryUtil;
import me.gosimple.nbvcxz.resources.Generator;

public class PassphraseGUI extends Frame implements ActionListener, WindowListener{

	private SubGUIProgram subgui;
	private String userdata;
	private Dictionary userdataDic;
	// characters that should not appear in generated password
	private char[] bannedChars = { ',', '.', '】', '【', '', '[', ']', '{', '}', ';', ':', '"', '<', '>', '/', '?', '\'',
			'\\', '|', '-', '_', '=', '+' };
	private Set<String> generatedPWs = new HashSet<String>();
	private TextField tfpwMinLen = new TextField("", 1);
	private TextField tfpwMaxLen = new TextField("", 2);
	private TextField tfnumWords = new TextField("", 1);
	private TextField tfdelimeter = new TextField("", 1);
	private TextField tfminEntropy = new TextField("", 2);
	private TextField tfSuggestedPW = new TextField("", 40);
	private Checkbox hanCheckbox = new Checkbox("Include Hanguel in passwords?");
	private Button btnGenerate = new Button("Generate");
	private Label numLoopslbl, currentLooplbl, entropylbl, generatelbl, highlbl;
	private Label minPWlenlbl = new Label("Enter Min pw length");
	
	
	private Label maxPWlenlbl = new Label("Enter Max pw length");
	
	private Label numWordspasslbl = new Label("Enter number of words to be used in passphrase");
	
	private Label delpasslbl = new Label("Enter a delimeter to be used in passphrase");
	
	private Label minentropylbl = new Label("Enter minimum zxcvbn entropy password must have");
	
	private boolean numLoopslblset, currentLooplblset, entropylblset, generatelblset, highlblset, btnAbortset = false;
	
	private Button btnAbort;
	
	private volatile Thread t;
	
	public PassphraseGUI(SubGUIProgram subgui, String userdata, Dictionary userdataDic) {
		this.subgui = subgui;
		this.userdata = userdata;
		this.userdataDic = userdataDic;
		setLayout(new FlowLayout());
		add(minPWlenlbl); // "super" Frame adds an anonymous Label
		tfpwMinLen.setEditable(true);
		add(tfpwMinLen);
		
		add(maxPWlenlbl); // "super" Frame adds an anonymous Label
		tfpwMaxLen.setEditable(true);
		add(tfpwMaxLen);
		
		add(numWordspasslbl); // "super" Frame adds an anonymous Label
		 
		tfnumWords.setEditable(true);
		add(tfnumWords);
		
		add(delpasslbl); // "super" Frame adds an anonymous Label
		tfdelimeter.setEditable(true);
		add(tfdelimeter);
		
		add(minentropylbl); // "super" Frame adds an anonymous Label
		tfminEntropy.setEditable(true);
		add(tfminEntropy);
		
		add(hanCheckbox); // "super" Frame adds an anonymous Label

	
		add(btnGenerate); // "super" Frame adds Button
		btnGenerate.addActionListener(this);
//		add(btnGeneratePWforTD); // "super" Frame adds Button
//		btnGeneratePWforTD.addActionListener(this);
		
		tfSuggestedPW.setEditable(false);
		add(tfSuggestedPW);
		
		addWindowListener(this);
		// "super" Frame (source object) fires WindowEvent.
		// "super" Frame adds "this" object as a WindowEvent listener.

		setTitle("Collecting User Data"); // "super" Frame sets title
		setSize(350, 750); // "super" Frame sets initial size
		setResizable(false);
		setVisible(true); // "super" Frame shows

	}
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnAbort) {
			//mustAbort = true;
			t.interrupt();
			//t = null;
		}
		if (evt.getSource() == btnGenerate) {
		
			
			if(btnAbortset == true) {
				//do nothing
			}
			else {
				btnAbort = new Button("Abort"); // Construct the Button
				add(btnAbort); // "super" Frame adds Button
				btnAbort.setVisible(true);
				btnAbort.addActionListener(this);
				btnAbortset = true;
			}
			
			t = new Thread(new Runnable() {
				
				public void run() {
					// process and get the user data dictionary

					// if userdata was not collected at all, use nbvcxz's default generetor, for
					// now.
					String suggestedPW = "";
					
					//TODO: check that delimeter is in English
					String delimeter = tfdelimeter.getText();
					int numWords = Integer.parseInt(tfnumWords.getText());
					int minLength = Integer.parseInt(tfpwMinLen.getText());
					int maxLength = Integer.parseInt(tfpwMaxLen.getText());
					int minEntropy = Integer.parseInt(tfminEntropy.getText());
					
					if (userdata.equals("")) {
						while(true) {
							suggestedPW = Generator.generatePassphrase(delimeter, numWords);
							if ((suggestedPW.length() >= minLength) && (suggestedPW.length() <= maxLength)) {
								break;
							}
						}
					} 
					else {

						// only if userdataDic was not filled before
						if (userdataDic == null) {
							userdataDic = SubGUIProgram.processUserData(userdata);
							SubGUIProgram.printToUserDatatxt(userdataDic);
							System.out.println("userdata = " + userdata);
							add(new Label("created file userdataForProject11111.txt"));
							add(new Label("in Documents folder"));
							setVisible(true);
						}
						
						//suggestedPW = Generator.generatePassphrase("l", 3, userdataDic);
						
						
						Nbvcxz nbvcxz = new Nbvcxz();
//						Double entropy = nbvcxz.estimate(suggestedPW).getEntropy();
//						//if zxcvbn returns a password strength lower than threshold, generate different password
//						// set threshold to 100 for now
//						while(entropy < 100) {
//							suggestedPW = Generator.generatePassphrase("l", 3, userdataDic);
//							entropy = nbvcxz.estimate(suggestedPW).getEntropy();
//							//System.out.println("generated password = " + suggestedPW);
//							//System.out.println("entropy = " + entropy);
//						}
						String meaningOfHanguel = "";
						Double entropy = 0.000;

						final int high = userdataDic.getDictonary().size();
						
						if(highlblset == true) {
							highlbl.setText("Number of Words in userdata dictionary : " + high);
						}
						else {
							highlbl = new Label("Number of Words in userdata dictionary : " + high);
							add(highlbl);
							setVisible(true);
							highlblset = true;
						}

						//reverse order; most infrequent ones first
						Map<String, Integer> sortedDic = SubGUIProgram.sortByValue(userdataDic.getDictonary());
						//List<String> userdataWords = new ArrayList<String>(sortedDic.value);
						
						
						//if actual numLoops returns greater than 214783647 (max int value possible)
						// it is set to 214783647
						int numLoops = (int) Math.pow(high, numWords);
						
						if (numLoopslblset == true) {
							numLoopslbl.setText("Max Number of loops to be iterated : " + numLoops);
						}

						else {
							numLoopslbl = new Label("Max Number of loops to be iterated : " + numLoops);
							add(numLoopslbl);
							setVisible(true);
							numLoopslblset = true;
						}
						if (currentLooplblset == true) {
							currentLooplbl.setText("currently doing 0th loop");
						} 
						else {
							currentLooplbl = new Label("currently doing 0th loop");
							add(currentLooplbl);
							setVisible(true);
							currentLooplblset = true;
						}
						
						for (int j = 0; j < numLoops; j++) {
//							if (mustAbort == true) {
//								currentLooplbl.setText("Abort button pressed.");
//								return;
//							}
							if (Thread.currentThread().isInterrupted()) {
								currentLooplbl.setText("Abort button pressed.");
								return;
							}
							currentLooplbl.setText("currently doing " + j + "th loop");
							
							suggestedPW = "";
							for (int k=1; k <= numWords; k++) {
								//if k == numWords, need remainder rather than quotient
								if (k == numWords) {
									suggestedPW = suggestedPW + SubGUIProgram.getKeyByValue(sortedDic, (j % k) + 1);
								}
								//else need quotient
								else {
									int expon = numWords - k;
									int rank = j / ((int)Math.pow(numWords, expon));
									//since rank starts counting from 1
									rank =rank +1;
									suggestedPW = suggestedPW + SubGUIProgram.getKeyByValue(sortedDic, rank);
									suggestedPW = suggestedPW + delimeter;
								}
							
							}
							
							// if this pw was already generated before
							// if (!generatedPWs.isEmpty()) {
							if (generatedPWs.contains(suggestedPW)) {
								continue;
							}
							// }
							
							//suggestedPW = Generator.generatePassphrase(delimeter, numWords, userdataDic);
							String originalPass = suggestedPW;
							// if suggestedPW contains Hanguel, convert to English and inform user
							String[] words = suggestedPW.split(delimeter);
							suggestedPW = "";
							meaningOfHanguel = "";
							if (hanCheckbox.getState() == false) {
								// if at least one of the words contain at least one Hanguel char, try different
								// password
								boolean tryDiffpw = false;
								for (String word : words) {
									//

									for (int i = 0; i < word.length(); i++) {
										char letter = word.charAt(i);
										String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
										// System.out.println( "\\u" + unicodeStr);
										int unicode = Integer.parseInt(unicodeStr, 16);
										if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
											tryDiffpw = true;
										}

									}

								}
								if (tryDiffpw == true) {
									continue;
								} else {
									suggestedPW = originalPass;
								}
							} else {

								for (String word : words) {
									// convert only if at least one character in this word is Hanguel
									boolean mustBeConverted = false;
									for (int i = 0; i < word.length(); i++) {
										char letter = word.charAt(i);
										String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
										// System.out.println( "\\u" + unicodeStr);
										int unicode = Integer.parseInt(unicodeStr, 16);
										if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
											mustBeConverted = true;
										}

									}

									// if it has to be converted
									if (mustBeConverted == true) {
										// since convertToEng is defined only for Hanguel, scan every char, and call
										// converToEng only if it is Hanguel
										String originalWord = word;
										String convertedWord = "";
										for (int i = 0; i < word.length(); i++) {
											char letter = word.charAt(i);
											String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
											// System.out.println( "\\u" + unicodeStr);
											int unicode = Integer.parseInt(unicodeStr, 16);
											if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
												convertedWord = convertedWord + SubGUIProgram.convertToEng(Character.toString(letter));
											} else {
												convertedWord = convertedWord + Character.toString(letter);
											}

										}
										// word = convertToEng(word);
										meaningOfHanguel = meaningOfHanguel + convertedWord + " stands for " + originalWord
												+ "    ";

										word = convertedWord;
									}
									suggestedPW = suggestedPW + word + delimeter;

								}

							}

							// if length requirement specified by the user is not met
							// TODO: get rid of infinite loop caused by no password possible meeting the
							// length requirement
							if (!((suggestedPW.length() >= minLength) && (suggestedPW.length() <= maxLength))) {
								continue;
							}

							entropy = nbvcxz.estimate(suggestedPW).getEntropy();
							// if zxcvbn returns a password strength lower than the threshold user specified, generate
							// a different password
							if (entropy >= minEntropy) {
								System.out.println(meaningOfHanguel);
								break;
								// System.out.println("generated password = " + suggestedPW);
								// System.out.println("entropy = " + entropy);
							}
							
							//tried all possible passwords, none passed
							if (j == (numLoops - 1)) {
								tfSuggestedPW.setText("");
								add(new Label("no password possible, try different setting"));
								setVisible(true);
								return;
							}
						}

						//

						System.out.println("generated password = " + suggestedPW);

						System.out.println("entropy = " + entropy);
						//
//						printToUserDatatxt(userdataDic);
						
						if (!(meaningOfHanguel == "")) {
							add(new Label(meaningOfHanguel));
						}
						if (entropylblset == true) {
							entropylbl.setText("zxcvbn entropy = " + entropy);
						}
						else {
							entropylbl = new Label("zxcvbn entropy = " + entropy);
							add(entropylbl);
							setVisible(true);
							entropylblset = true;
						}
						
					}
					
					tfSuggestedPW.setText(suggestedPW);
					generatedPWs.add(suggestedPW);
					if(generatelblset == true) {
						//do nothing
					}
					else {
						generatelbl = new Label("click generate button again to try different password");
						add(generatelbl);
						setVisible(true);
						generatelblset = true;
					}
					//btnAbort.setVisible(false);
					
				}
			});
			t.start();
		}
		
	}
	
//	private void printToUserDatatxt(Dictionary userdataDic2) {
//		 // get path to documents folder
//		String myDocuments = null;
//
//		try {
//		    Process p =  Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
//		    p.waitFor();
//
//		    InputStream in = p.getInputStream();
//		    byte[] b = new byte[in.available()];
//		    in.read(b);
//		    in.close();
//
//		    myDocuments = new String(b);
//		    myDocuments = myDocuments.split("\\s\\s+")[4];
//
//		} catch(Throwable t) {
//		    t.printStackTrace();
//		}
//	
//		
//		myDocuments = myDocuments + "\\userdataForProject11111.txt";
//		
//		Map<String, Integer> map = userdataDic2.getDictonary();
//		//Map<String, Integer> sortedmap = sortByValue(map);
//		int numOfWords = map.size();
//		try {
//			PrintWriter writer = new PrintWriter(myDocuments, "UTF-8");
//			
//			for (int i=1; i <= numOfWords; i++) {
//				writer.println(SubGUIProgram.getKeyByValue(map, i));
//			}
//			
////			//loop a Map
////			for (Map.Entry<String, Integer> entry : map.entrySet()) {
////				writer.println(entry.getKey());
////			
////			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		
//	}
//
//	// TODO: process user data in extractUserData() to speed up?
//	private Dictionary processUserData(String userdata) {
//
//		// key value
//		Map<String, Integer> map = new HashMap<String, Integer>();
//
//		// read from userdata.txt, split on space
//
//		// System.out.println(line);
//		String[] splited = userdata.split("\\s+");
//		for (String str : splited) {
//			boolean containsBanned = false;
//			
//			//do not add to map if this str contains forbidden characters
//			for (char fbiddenChar : bannedChars) {
//				
//				if (str.indexOf(fbiddenChar) >= 0) {
//					containsBanned = true;
//				}
//			}
//			
//			if (containsBanned == true) {
//				continue;
//			}
//			
//			// if this word is already added to map before
//			if (map.containsKey(str)) {
//				map.put(str, map.get(str) + 1);
//			}
//			// if this word is encountered for the first time
//			else {
//				map.put(str, 1);
//			}
//		}
//
//		// add map to sorteduserdata.txt
//		Map<String, Integer> sortedmap = SubGUIProgram.sortByValue(map);
//		// // loop a Map
//		// for (Map.Entry<String, Integer> entry : sortedmap.entrySet()) {
//		// writer.println(entry.getKey());
//		// }
//
//		Dictionary dic = new Dictionary("sorteduserdata", DictionaryUtil.loadRankedDictionaryGivenMap(sortedmap),
//				false);
//
//		return dic;
//
//	}

	
	/* WindowEvent handlers */
	// Called back upon clicking close-window button
	@Override
	public void windowClosing(WindowEvent evt) {
		// hide this sub window
		setVisible(false);
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
