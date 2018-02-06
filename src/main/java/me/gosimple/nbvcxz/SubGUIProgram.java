package me.gosimple.nbvcxz;

import java.awt.*;
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.python.util.PythonInterpreter;

import me.gosimple.nbvcxz.resources.Dictionary;
import me.gosimple.nbvcxz.resources.DictionaryUtil;
import me.gosimple.nbvcxz.resources.Generator;

import javafx.beans.value.ChangeListener;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import junit.framework.Test;
import javafx.scene.web.WebEngine;
import static javafx.concurrent.Worker.State;

//An AWT GUI program inherits the top-level container java.awt.Frame
public class SubGUIProgram extends Frame implements ActionListener, WindowListener {
	// This class acts as listener for ActionEvent and WindowEvent
	// A Java class can extend only one superclass, but it can implement multiple
	// interfaces.

	public TextField tfCount1, tfCount2, tfCount3, tfCount4, tfSuggestedPW, tfverifierptweets, tfverifierGmail, tfHanguel, tfEnglish, tfpwMinLen, tfpwMaxLen, tfnumWords, tfdelimeter; // Declare a TextField
																								// component
	private Button btnGenerate, btnTVerifier, btnLocalFile, btnNoLocalFile, btnGetTweets, btnTVerifierPersonal, btnAllowGmail, btnNotAllowGmail,
			btnAllowLikedTweets, btnNotAllowLikedTweets, btnNotAllowTweets, btnAllowTweets, btnTVerifierGmail, btnConvert; // Declare a Button
	
	private Checkbox hanCheckbox;
	
	// component
	public Nbvcxz nbvcxz;
	
	//characters that should not appear in generated password
	private char[] bannedChars = {',', '.', '】','【','','[',']','{','}',';',':','"','<','>','/','?', '\'','\\', '|', '-','_','=','+'}; 

	//number of words to be used in passphrase
	private int numWordsPP;
	
	//minimum length of password required
	private int pwMinLen;
	
	//maximum length of password it can be
	private int pwMaxLen;
	
	//if Hanguel, converted to English, is to be included in the password or not
	boolean includeHan;

	String userdata = "";

	// user data dictionary
	Dictionary userdataDic;

	// Constructor to setup the GUI components and event handlers
	public SubGUIProgram(Nbvcxz nbvcxz2) {
		nbvcxz = nbvcxz2;

		setLayout(new FlowLayout()); // "super" Frame sets to FlowLayout
		//
		// add(new Label("Username ")); // "super" Frame adds an anonymous Label
		// tfCount1 = new TextField("", 10); // Construct the TextField
		// tfCount1.setEditable(true);
		// add(tfCount1); // "super" Frame adds TextField
		//
		// add(new Label("Password")); // "super" Frame adds an anonymous Label
		// tfCount2 = new TextField("", 10); // Construct the TextField
		// tfCount2.setEditable(true);
		// add(tfCount2); // "super" Frame adds TextField
		
		//yahoo
//		try {
//			YahooGetUserData.openBrowser(this);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//
		
		//google
//		try {
//			GoogleGetGmail.openBrowser(this);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//python code example
//		System.setProperty("python.console.encoding", "UTF-8");
//		PythonInterpreter interpreter;
//		interpreter = new PythonInterpreter();
//		//interpreter.execfile("src\\main\\java\\me\\gosimple\\nbvcxz\\pwd_guess.py");
//		//interpreter.execfile("src\\main\\java\\me\\gosimple\\nbvcxz\\example_python_code.py");
//		interpreter.exec("print 'Hello, world!'");
//		interpreter.close();
		//
		
		//javascript example
		// create a script engine manager
//        ScriptEngineManager factory = new ScriptEngineManager();
//        // create a JavaScript engine
//        ScriptEngine engine = factory.getEngineByName("JavaScript");
//        // evaluate JavaScript code from String
//        try {
//			engine.eval("print('Hello, World')");
//		} catch (ScriptException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//
		
		//TODO: chrome user data
		//GoogleChrome.GetHistory();
		//

		add(new Label("Convert Hanguel to English")); 
		tfHanguel = new TextField("", 40); // Construct the TextField
		tfHanguel.setEditable(true);
		add(tfHanguel);
		btnConvert = new Button("Convert"); // Construct the Button
		add(btnConvert); // "super" Frame adds Button
		btnConvert.addActionListener(this);
		tfEnglish = new TextField("", 40); // Construct the TextField
		tfEnglish.setEditable(false);
		add(tfEnglish);
		
		add(new Label("Search Local Files?")); // "super" Frame adds an anonymous Label

		btnLocalFile = new Button("Yes"); // Construct the Button
		add(btnLocalFile); // "super" Frame adds Button

		btnLocalFile.addActionListener(this);

		btnNoLocalFile = new Button("No"); // Construct the Button
		add(btnNoLocalFile); // "super" Frame adds Button

		btnNoLocalFile.addActionListener(this);

		addWindowListener(this);
		// "super" Frame (source object) fires WindowEvent.
		// "super" Frame adds "this" object as a WindowEvent listener.

		setTitle("Collecting User Data"); // "super" Frame sets title
		setSize(350, 400); // "super" Frame sets initial size
		setVisible(true); // "super" Frame shows
	}

	/* ActionEvent handler */
	@Override
	public void actionPerformed(ActionEvent evt) {
		// String originalpw = tfCount2.getText();
		
		if (evt.getSource() == btnConvert) {
			String hanguel = tfHanguel.getText();
			String converted = convertToEng(hanguel);
			tfEnglish.setText(converted);
			setVisible(true);
			
		}


		if (evt.getSource() == btnNoLocalFile) {
			add(new Label("Search liked tweets?"));
			btnAllowLikedTweets = new Button("Yes");
			add(btnAllowLikedTweets);
			btnAllowLikedTweets.addActionListener(this);
			btnNotAllowLikedTweets = new Button("No");
			add(btnNotAllowLikedTweets);
			btnNotAllowLikedTweets.addActionListener(this);
			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnLocalFile) {

			// get userdata, only if userdata was not extracted before
			if (userdata.equals("")) {
				add(new Label("Extracting userdata..."));
				setVisible(true);

				// extract userdata from local files
				userdata = extractUserData();

			}
			add(new Label("Local File Search Complete"));

			add(new Label("Search liked tweets?"));
			btnAllowLikedTweets = new Button("Yes");
			add(btnAllowLikedTweets);
			btnAllowLikedTweets.addActionListener(this);
			btnNotAllowLikedTweets = new Button("No");
			add(btnNotAllowLikedTweets);
			btnNotAllowLikedTweets.addActionListener(this);
			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnNotAllowLikedTweets) {
			add(new Label("Search written tweets?"));
			btnAllowTweets = new Button("Yes");
			btnAllowTweets.addActionListener(this);
			add(btnAllowTweets);
			btnNotAllowTweets = new Button("No");
			btnNotAllowTweets.addActionListener(this);
			add(btnNotAllowTweets);
			setVisible(true); // "super" Frame shows

		}

		if (evt.getSource() == btnAllowTweets) {
			try {
				TwitterGetAllTweets.openBrowser(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			add(new Label("Enter Twitter verifier for personal tweets")); // "super" Frame adds an anonymous Label
			tfverifierptweets = new TextField("", 20); // Construct the TextField
			tfverifierptweets.setEditable(true);
			add(tfverifierptweets); // "super" Frame adds TextField

			btnTVerifierPersonal = new Button("Submit Twitter verifier for personal tweets"); // Construct the Button
			add(btnTVerifierPersonal); // "super" Frame adds Button
			btnTVerifierPersonal.addActionListener(this);

			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnNotAllowTweets) {
//			add(new Label("Generate?")); // "super" Frame adds an anonymous Label
//
//			btnGenerate = new Button("Generate"); // Construct the Button
//			add(btnGenerate); // "super" Frame adds Button
//			btnGenerate.addActionListener(this);
//			tfSuggestedPW = new TextField("", 40); // Construct the TextField
//			tfSuggestedPW.setEditable(false);
//			add(tfSuggestedPW);
//			setVisible(true); // "super" Frame shows
			add(new Label("Search Gmail?"));
			btnAllowGmail = new Button("Yes");
			btnAllowGmail.addActionListener(this);
			add(btnAllowGmail);
			btnNotAllowGmail = new Button("No");
			btnNotAllowGmail.addActionListener(this);
			add(btnNotAllowGmail);
			setVisible(true); // "super" Frame shows
		}
		
		if (evt.getSource() == btnNotAllowGmail) {
			add(new Label("Enter Min pw length")); // "super" Frame adds an anonymous Label
			tfpwMinLen = new TextField("", 1); // Construct the TextField
			tfpwMinLen.setEditable(true);
			add(tfpwMinLen);
			
			add(new Label("Enter Max pw length")); // "super" Frame adds an anonymous Label
			tfpwMaxLen = new TextField("", 2); // Construct the TextField
			tfpwMaxLen.setEditable(true);
			add(tfpwMaxLen);
			
			add(new Label("Enter number of words to be used in passphrase")); // "super" Frame adds an anonymous Label
			tfnumWords = new TextField("", 1); // Construct the TextField
			tfnumWords.setEditable(true);
			add(tfnumWords);
			
			add(new Label("Enter a delimeter to be used in passphrase")); // "super" Frame adds an anonymous Label
			tfdelimeter = new TextField("", 1); // Construct the TextField
			tfdelimeter.setEditable(true);
			add(tfdelimeter);
			
			hanCheckbox = new Checkbox("Include Hanguel in passwords?");
			add(hanCheckbox); // "super" Frame adds an anonymous Label
		
			
			
			btnGenerate = new Button("Generate"); // Construct the Button
			add(btnGenerate); // "super" Frame adds Button
			btnGenerate.addActionListener(this);
			tfSuggestedPW = new TextField("", 40); // Construct the TextField
			tfSuggestedPW.setEditable(false);
			add(tfSuggestedPW);
			setVisible(true); // "super" Frame shows
		}
		
		if (evt.getSource() == btnAllowGmail) {
			try {
				GoogleGetGmail.openBrowser(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			add(new Label("Enter verifier for Gmail")); // "super" Frame adds an anonymous Label
			tfverifierGmail = new TextField("", 20); // Construct the TextField
			tfverifierGmail.setEditable(true);
			add(tfverifierGmail); // "super" Frame adds TextField

			btnTVerifierGmail = new Button("Submit verifier for Gmail"); // Construct the Button
			add(btnTVerifierGmail); // "super" Frame adds Button
			btnTVerifierGmail.addActionListener(this);

			setVisible(true); // "super" Frame shows
		}

		
		if(evt.getSource() == btnTVerifierGmail) {
			add(new Label("Extracting Gmail data..."));
			setVisible(true);

			String gverifier = tfverifierGmail.getText();
			String gmailcontent = "";
			try {
				gmailcontent = GoogleGetGmail.getGmail(gverifier);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userdata = userdata + gmailcontent;
			
			add(new Label("Gmail search complete"));
			
			add(new Label("Enter Min pw length")); // "super" Frame adds an anonymous Label
			tfpwMinLen = new TextField("", 1); // Construct the TextField
			tfpwMinLen.setEditable(true);
			add(tfpwMinLen);
			
			add(new Label("Enter Max pw length")); // "super" Frame adds an anonymous Label
			tfpwMaxLen = new TextField("", 2); // Construct the TextField
			tfpwMaxLen.setEditable(true);
			add(tfpwMaxLen);
			
			add(new Label("Enter number of words to be used in passphrase")); // "super" Frame adds an anonymous Label
			tfnumWords = new TextField("", 1); // Construct the TextField
			tfnumWords.setEditable(true);
			add(tfnumWords);
			
			add(new Label("Enter a delimeter to be used in passphrase")); // "super" Frame adds an anonymous Label
			tfdelimeter = new TextField("", 1); // Construct the TextField
			tfdelimeter.setEditable(true);
			add(tfdelimeter);
			
			hanCheckbox = new Checkbox("Include Hanguel in passwords?");
			add(hanCheckbox); // "super" Frame adds an anonymous Label
		
			
			btnGenerate = new Button("Generate"); // Construct the Button
			add(btnGenerate); // "super" Frame adds Button
			btnGenerate.addActionListener(this);
			tfSuggestedPW = new TextField("", 40); // Construct the TextField
			tfSuggestedPW.setEditable(false);
			add(tfSuggestedPW);
			setVisible(true); // "super" Frame shows

		}
		
		if (evt.getSource() == btnAllowLikedTweets) {
			try {
				TwitterExample.openBrowser(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			add(new Label("Enter Twitter verifier")); // "super" Frame adds an anonymous Label
			tfCount4 = new TextField("", 20); // Construct the TextField
			tfCount4.setEditable(true);
			add(tfCount4); // "super" Frame adds TextField

			btnTVerifier = new Button("Submit Twitter verifier"); // Construct the Button
			add(btnTVerifier); // "super" Frame adds Button
			btnTVerifier.addActionListener(this);

			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnTVerifier) {
			add(new Label("Extracting Twitter data..."));
			setVisible(true);

			String tverifier = tfCount4.getText();
			try {

				// gets all liked tweets by the user
				String bodyOfResponse = TwitterExample.getLikedTweets(tverifier);
				JSONArray responseArray = new JSONArray(bodyOfResponse);
				// System.out.println(responseArray);
				final int n = responseArray.length();
				for (int i = 0; i < n; ++i) {
					final JSONObject responseObj = responseArray.getJSONObject(i);

					// print the written tweet itself, the name of the person who wrote it, and
					// screen-name of the writer
					System.out.println(responseObj.getString("text"));
					// System.out.println(responseObj.getJSONObject("entities").getJSONArray("urls").getJSONObject(0)
					// .getString("expanded_url"));
					String url = "";

					// add to url only if defined
					if (responseObj.getJSONObject("entities").getJSONArray("urls").length() != 0) {
						if (responseObj.getJSONObject("entities").getJSONArray("urls").getJSONObject(0) != null) {
							url = responseObj.getJSONObject("entities").getJSONArray("urls").getJSONObject(0)
									.getString("expanded_url");
						}
					}
					// System.out.println(responseObj.getJSONObject("user").getString("screen_name"));

					// this case is when tweet is short enough that it could be fully included in
					// "text"
					if ((url == null) || (url.isEmpty())) {
						userdata = userdata + responseObj.getString("text");

					}
					// else we need to connect to expanded_url to get full tweet
					else {
						// String url = "https://twitter.com/i/web/status/953000902331453442";
						Document doc = Jsoup.connect(url).get();
						Element tweetText = doc.select("p.js-tweet-text.tweet-text").first();
						// System.out.println(tweetText.text());

						// add only if text is not null (sometimes expanded_url does not point to tweet
						// so tweetText.text() is undefined
						if (tweetText != null) {
							userdata = userdata + tweetText.text();
						}
					}
					// add to userdata

					userdata = userdata + responseObj.getJSONObject("user").getString("name");
					userdata = userdata + responseObj.getJSONObject("user").getString("screen_name");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			add(new Label("Liked Tweet search complete"));
		
			add(new Label("Search written tweets?"));
			btnAllowTweets = new Button("Yes");
			btnAllowTweets.addActionListener(this);
			add(btnAllowTweets);
			btnNotAllowTweets = new Button("No");
			btnNotAllowTweets.addActionListener(this);
			add(btnNotAllowTweets);
			setVisible(true); // "super" Frame shows

		}

		if (evt.getSource() == btnTVerifierPersonal) {
			String tverifierPersonal = tfverifierptweets.getText();

			try {

				// gets all liked tweets by the user
				String bodyOfResponse = TwitterGetAllTweets.getAllTweets(tverifierPersonal);
				JSONArray responseArray = new JSONArray(bodyOfResponse);
				System.out.println(responseArray);
				final int n = responseArray.length();
				for (int i = 0; i < n; ++i) {
					final JSONObject responseObj = responseArray.getJSONObject(i);

					// print the written tweet itself, the name of the person who wrote it, and
					// screen-name of the writer
					System.out.println(responseObj.getString("text"));
					// System.out.println(responseObj.getJSONObject("entities").getJSONArray("urls").getJSONObject(0)
					// .getString("expanded_url"));
					String url = "";

					// add to url only if defined
					if (responseObj.getJSONObject("entities").getJSONArray("urls").length() != 0) {
						if (responseObj.getJSONObject("entities").getJSONArray("urls").getJSONObject(0) != null) {
							url = responseObj.getJSONObject("entities").getJSONArray("urls").getJSONObject(0)
									.getString("expanded_url");
						}
					}
					// System.out.println(responseObj.getJSONObject("user").getString("screen_name"));

					// this case is when tweet is short enough that it could be fully included in
					// "text"
					if ((url == null) || (url.isEmpty())) {
						userdata = userdata + responseObj.getString("text");

					}
					// else we need to connect to expanded_url to get full tweet
					else {
						// String url = "https://twitter.com/i/web/status/953000902331453442";
						Document doc = Jsoup.connect(url).get();
						Element tweetText = doc.select("p.js-tweet-text.tweet-text").first();
						// System.out.println(tweetText.text());

						// add only if text is not null (sometimes expanded_url does not point to tweet
						// so tweetText.text() is undefined
						if (tweetText != null) {
							userdata = userdata + tweetText.text();
						}
					}
					// add to userdata

					userdata = userdata + responseObj.getJSONObject("user").getString("name");
					userdata = userdata + responseObj.getJSONObject("user").getString("screen_name");
				}
				// final int n = responseArray.length();
				// for (int i = 0; i < n; ++i) {
				// final JSONObject responseObj = responseArray.getJSONObject(i);
				//
				// // print the written tweet itself, the name of the person who wrote it, and
				// // screen-name of the writer
				// // System.out.println(responseObj.getString("text"));
				// //
				// System.out.println(responseObj.getJSONObject("entities").getJSONArray("urls").getJSONObject(0).getString("expanded_url"));
				// String url =
				// responseObj.getJSONObject("entities").getJSONArray("urls").getJSONObject(0)
				// .getString("expanded_url");
				// //
				// System.out.println(responseObj.getJSONObject("user").getString("screen_name"));
				//
				// // String url = "https://twitter.com/i/web/status/953000902331453442";
				// Document doc = Jsoup.connect(url).get();
				// Element tweetText = doc.select("p.js-tweet-text.tweet-text").first();
				// // System.out.println(tweetText.text());
				//
				// // add to userdata
				// userdata = userdata + tweetText.text();
				// userdata = userdata + responseObj.getJSONObject("user").getString("name");
				// userdata = userdata +
				// responseObj.getJSONObject("user").getString("screen_name");
				// }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			add(new Label("personal tweet search complete"));
//			add(new Label("Generate?")); // "super" Frame adds an anonymous Label
//
//			btnGenerate = new Button("Generate"); // Construct the Button
//			add(btnGenerate); // "super" Frame adds Button
//			btnGenerate.addActionListener(this);
//			tfSuggestedPW = new TextField("", 40); // Construct the TextField
//			tfSuggestedPW.setEditable(false);
//			add(tfSuggestedPW);
//			setVisible(true); // "super" Frame shows
			add(new Label("Search Gmail?"));
			btnAllowGmail = new Button("Yes");
			btnAllowGmail.addActionListener(this);
			add(btnAllowGmail);
			btnNotAllowGmail = new Button("No");
			btnNotAllowGmail.addActionListener(this);
			add(btnNotAllowGmail);
			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnGenerate) {
			// process and get the user data dictionary

			// if userdata was not collected at all, use nbvcxz's default generetor, for
			// now.
			String suggestedPW = "";
			
			//TODO: check that delimeter is in English
			String delimeter = tfdelimeter.getText();
			int numWords = Integer.parseInt(tfnumWords.getText());
			int minLength = Integer.parseInt(tfpwMinLen.getText());
			int maxLength = Integer.parseInt(tfpwMaxLen.getText());
			
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
					userdataDic = processUserData(userdata);
					printToUserDatatxt(userdataDic);
					add(new Label("created file userdataForProject11111.txt"));
					add(new Label("in Documents folder"));
				}
				
				//suggestedPW = Generator.generatePassphrase("l", 3, userdataDic);
				
				
				Nbvcxz nbvcxz = new Nbvcxz();
//				Double entropy = nbvcxz.estimate(suggestedPW).getEntropy();
//				//if zxcvbn returns a password strength lower than threshold, generate different password
//				// set threshold to 100 for now
//				while(entropy < 100) {
//					suggestedPW = Generator.generatePassphrase("l", 3, userdataDic);
//					entropy = nbvcxz.estimate(suggestedPW).getEntropy();
//					//System.out.println("generated password = " + suggestedPW);
//					//System.out.println("entropy = " + entropy);
//				}
				String meaningOfHanguel = "";
				Double entropy;
				
				
				while (true) {
					suggestedPW = Generator.generatePassphrase(delimeter, numWords, userdataDic);
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
						if(tryDiffpw == false) {
							continue;
						}
						else {
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
										convertedWord = convertedWord + convertToEng(Character.toString(letter));
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
					// TODO: get rid of infinite loop caused by no password possible meeting the length requirement
					if (!((suggestedPW.length() >= minLength) && (suggestedPW.length() <= maxLength))) {
						continue;
					}
					
					entropy = nbvcxz.estimate(suggestedPW).getEntropy();
					// if zxcvbn returns a password strength lower than threshold, generate
					// a different password
					// set threshold to 100 for now
					if (entropy >= 100) {
						System.out.println(meaningOfHanguel);
						break;
						// System.out.println("generated password = " + suggestedPW);
						// System.out.println("entropy = " + entropy);
					}
				}
				//
				
				System.out.println("generated password = " + suggestedPW);
				
				System.out.println("entropy = " + entropy);
				//
//				printToUserDatatxt(userdataDic);
				
				if (!(meaningOfHanguel == "")) {
					add(new Label(meaningOfHanguel));
				}
				add(new Label("entropy = " + entropy));
				
				
//				add(new Label("created file userdataForProject11111.txt"));
//				add(new Label("in Documents folder"));
				setVisible(true);
			}
			tfSuggestedPW.setText(suggestedPW);
		}

	}

	
	private String convertToEng(String hanguel) {
		String result = "";
        //System.out.println(hanguel);
        //String hanguelLetters = Normalizer.normalize(hanguel, Normalizer.Form.NFD);
//        for (int i = 0; i < string.length(); i++) {
//            System.out.print(String.format("U+%04X ", string.codePointAt(i)));
//        }
        for (int i=0; i<hanguel.length(); i++) {
        	char thisChar = hanguel.charAt(i);
        	String partialResult = convertToEngHelper(thisChar);
        	result = result + partialResult;
        }
        //System.out.println(result);
        return result;
    }
	
	private String convertToEngHelper(char hanguelLetter) {
		String partialResult = "";
		
		//
		//int tes = 9/10;
		//System.out.println(" 9/10 =   " + tes);
		String unicodeStr = Integer.toHexString(hanguelLetter | 0x10000).substring(1);
		//System.out.println( "\\u" + unicodeStr);
		int unicode = Integer.parseInt(unicodeStr, 16);
		unicode = unicode - 0xAC00;
		
		// 
		int firstletter = unicode /(21*28);
		
		//
		int thirdletter = (unicode % 28);
		
		//
		int secondletter = (unicode - thirdletter - (firstletter*21*28))/28;
		
		//System.out.println(firstletter);
		//System.out.println(secondletter);
		//System.out.println(thirdletter);
		
		String letterToBeAdded = "";
		switch(firstletter) {
		case 0:
			letterToBeAdded = "r";
			break;
		case 1:
			letterToBeAdded = "R";
			break;
		case 2:
			letterToBeAdded = "s";
			break;
		case 3:
			letterToBeAdded = "e";
			break;
		case 4:
			letterToBeAdded = "E";
			break;
		case 5:
			letterToBeAdded = "f";
			break;
		case 6:
			letterToBeAdded = "a";
			break;
		case 7:
			letterToBeAdded = "q";
			break;
		case 8:
			letterToBeAdded = "Q";
			break;
		case 9:
			letterToBeAdded = "t";
			break;
		case 10:
			letterToBeAdded = "T";
			break;
		case 11:
			letterToBeAdded = "d";
			break;
		case 12:
			letterToBeAdded = "w";
			break;
		case 13:
			letterToBeAdded = "W";
			break;
		case 14:
			letterToBeAdded = "c";
			break;
		case 15:
			letterToBeAdded = "z";
			break;
		case 16:
			letterToBeAdded = "x";
			break;
		case 17:
			letterToBeAdded = "v";
			break;
		case 18:
			letterToBeAdded = "g";
			break;
			
		}
		partialResult = partialResult + letterToBeAdded;
		
		switch(secondletter) {
		case 0:
			letterToBeAdded = "k";
			break;
		case 1:
			letterToBeAdded = "o";
			break;
		case 2:
			letterToBeAdded = "i";
			break;
		case 3:
			letterToBeAdded = "O";
			break;
		case 4:
			letterToBeAdded = "j";
			break;
		case 5:
			letterToBeAdded = "p";
			break;
		case 6:
			letterToBeAdded = "u";
			break;
		case 7:
			letterToBeAdded = "P";
			break;
		case 8:
			letterToBeAdded = "h";
			break;
		case 9:
			letterToBeAdded = "hk";
			break;
		case 10:
			letterToBeAdded = "ho";
			break;
		case 11:
			letterToBeAdded = "hl";
			break;
		case 12:
			letterToBeAdded = "y";
			break;
		case 13:
			letterToBeAdded = "n";
			break;
		case 14:
			letterToBeAdded = "nj";
			break;
		case 15:
			letterToBeAdded = "np";
			break;
		case 16:
			letterToBeAdded = "nl";
			break;
		case 17:
			letterToBeAdded = "b";
			break;
		case 18:
			letterToBeAdded = "m";
			break;
		case 19:
			letterToBeAdded = "ml";
			break;
		case 20:
			letterToBeAdded = "l";
			break;
			
		}
		partialResult = partialResult + letterToBeAdded;
		
		switch(thirdletter) {
		case 0:
			letterToBeAdded = "";
			break;
		case 1:
			letterToBeAdded = "r";
			break;
		case 2:
			letterToBeAdded = "R";
			break;
		case 3:
			letterToBeAdded = "rt";
			break;
		case 4:
			letterToBeAdded = "s";
			break;
		case 5:
			letterToBeAdded = "sw";
			break;
		case 6:
			letterToBeAdded = "sg";
			break;
		case 7:
			letterToBeAdded = "e";
			break;
		case 8:
			letterToBeAdded = "f";
			break;
		case 9:
			letterToBeAdded = "fr";
			break;
		case 10:
			letterToBeAdded = "fa";
			break;
		case 11:
			letterToBeAdded = "fq";
			break;
		case 12:
			letterToBeAdded = "ft";
			break;
		case 13:
			letterToBeAdded = "fx";
			break;
		case 14:
			letterToBeAdded = "fv";
			break;
		case 15:
			letterToBeAdded = "fg";
			break;
		case 16:
			letterToBeAdded = "a";
			break;
		case 17:
			letterToBeAdded = "q";
			break;
		case 18:
			letterToBeAdded = "qt";
			break;
		case 19:
			letterToBeAdded = "t";
			break;
		case 20:
			letterToBeAdded = "T";
			break;
		case 21:
			letterToBeAdded = "d";
			break;
		case 22:
			letterToBeAdded = "w";
			break;
		case 23:
			letterToBeAdded = "c";
			break;
		case 24:
			letterToBeAdded = "z";
			break;
		case 25:
			letterToBeAdded = "x";
			break;
		case 26:
			letterToBeAdded = "v";
			break;
		case 27:
			letterToBeAdded = "g";
			break;
			
		}
		partialResult = partialResult + letterToBeAdded;
		
		//
		
		

		return partialResult;
	}
	
	private void printToUserDatatxt(Dictionary userdataDic2) {
		 // get path to documents folder
		String myDocuments = null;

		try {
		    Process p =  Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
		    p.waitFor();

		    InputStream in = p.getInputStream();
		    byte[] b = new byte[in.available()];
		    in.read(b);
		    in.close();

		    myDocuments = new String(b);
		    myDocuments = myDocuments.split("\\s\\s+")[4];

		} catch(Throwable t) {
		    t.printStackTrace();
		}
	
		
		myDocuments = myDocuments + "\\userdataForProject11111.txt";
		
		Map<String, Integer> map = userdataDic2.getDictonary();
		
		try {
			PrintWriter writer = new PrintWriter(myDocuments, "UTF-8");
			
			//loop a Map
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				writer.println(entry.getKey());
			
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	// TODO: process user data in extractUserData() to speed up?
	private Dictionary processUserData(String userdata) {

		// key value
		Map<String, Integer> map = new HashMap<String, Integer>();

		// read from userdata.txt, split on space

		// System.out.println(line);
		String[] splited = userdata.split("\\s+");
		for (String str : splited) {
			boolean containsBanned = false;
			
			//do not add to map if this str contains forbidden characters
			for (char fbiddenChar : bannedChars) {
				
				if (str.indexOf(fbiddenChar) >= 0) {
					containsBanned = true;
				}
			}
			
			if (containsBanned == true) {
				continue;
			}
			
			// if this word is already added to map before
			if (map.containsKey(str)) {
				map.put(str, map.get(str) + 1);
			}
			// if this word is encountered for the first time
			else {
				map.put(str, 1);
			}
		}

		// add map to sorteduserdata.txt
		Map<String, Integer> sortedmap = sortByValue(map);
		// // loop a Map
		// for (Map.Entry<String, Integer> entry : sortedmap.entrySet()) {
		// writer.println(entry.getKey());
		// }

		Dictionary dic = new Dictionary("sorteduserdata", DictionaryUtil.loadRankedDictionaryGivenMap(sortedmap),
				false);

		return dic;

	}

	// sorts map, most frequent ones first
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		LinkedList<Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	// old version that extracts to userdata.txt file
	// private void extractUserData() {
	// // get path to documents folder
	// String myDocuments = null;
	//
	// try {
	// Process p = Runtime.getRuntime().exec(
	// "reg query
	// \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell
	// Folders\" /v personal");
	// p.waitFor();
	//
	// InputStream in = p.getInputStream();
	// byte[] b = new byte[in.available()];
	// in.read(b);
	// in.close();
	//
	// myDocuments = new String(b);
	// myDocuments = myDocuments.split("\\s\\s+")[4];
	//
	// } catch (Throwable t) {
	// t.printStackTrace();
	// }
	//
	// // find all files in myDocuments folder
	// File dir = new File(myDocuments);
	// Collection<File> allfiles = FileUtils.listFiles(dir, null, true);
	// Collection<File> pdffiles = FileUtils.listFiles(dir, new String[] { "pdf" },
	// true);
	// Collection<File> textfiles = FileUtils.listFiles(dir, new String[] { "txt" },
	// true);
	//
	// try {
	// writer = new PrintWriter("src/main/resources/dictionaries/userdata.txt",
	// "UTF-8");
	// } catch (FileNotFoundException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (UnsupportedEncodingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// textfiles.forEach(file -> {
	//
	// try {
	// readtxtfile(file, writer);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// });
	//
	// pdffiles.forEach(file -> {
	// try {
	// extractpdf(file, writer);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// });
	//
	// writer.close();
	// }

	private String extractUserData() {
		// get path to documents folder
		String myDocuments = null;
		// String userdata = "";

		try {
			Process p = Runtime.getRuntime().exec(
					"reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
			p.waitFor();

			InputStream in = p.getInputStream();
			byte[] b = new byte[in.available()];
			in.read(b);
			in.close();

			myDocuments = new String(b);
			myDocuments = myDocuments.split("\\s\\s+")[4];

		} catch (Throwable t) {
			t.printStackTrace();
		}

		// find all files in myDocuments folder
		File dir = new File(myDocuments);
		Collection<File> allfiles = FileUtils.listFiles(dir, null, true);
		Collection<File> pdffiles = FileUtils.listFiles(dir, new String[] { "pdf" }, true);
		Collection<File> textfiles = FileUtils.listFiles(dir, new String[] { "txt" }, true);

		textfiles.forEach(file -> {

			try {
				userdata = readtxtfile(file, userdata);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		pdffiles.forEach(file -> {
			try {
				userdata = extractpdf(file, userdata);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return userdata;
	}

	public String extractpdf(File file, String userdata) throws IOException {

		PDDocument document = PDDocument.load(file);

		// Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		// Retrieving text from PDF document
		String text = pdfStripper.getText(document);

		// System.out.println(text);
		userdata = userdata + text;

		// Closing the document
		document.close();

		return userdata;
	}

	public String readtxtfile(File txtfile, String userdata)
			throws FileNotFoundException, UnsupportedEncodingException {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(txtfile));
			String line;
			while ((line = br.readLine()) != null) {
				userdata = userdata + line + "\n";
			}
			// writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return userdata;

	}

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