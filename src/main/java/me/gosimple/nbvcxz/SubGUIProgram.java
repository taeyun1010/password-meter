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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
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

import me.gosimple.nbvcxz.matching.DateMatcher;
import me.gosimple.nbvcxz.matching.match.Match;
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

	public TextField tfCount1, tfCount2, tfCount3, tfCount4, tfverifierptweets, tfverifierGmail,
			tfHanguel, tfEnglish; // Declare a TextField
	
	private TextField tfpwMinLen = new TextField("", 1);
	private TextField tfpwMaxLen = new TextField("", 2);
	private TextField tfnumWords = new TextField("", 1);
	private TextField tfdelimeter = new TextField("", 1);
	private TextField tfminEntropy = new TextField("", 2);
	private TextField tfSuggestedPW = new TextField("", 40);
	private Checkbox hanCheckbox = new Checkbox("Include Hanguel in passwords?");
	private Button btnGenerate = new Button("Generate");
	private Button btnGenerateTokenDate = new Button("Generate TokenDate pw");
	//private Button btnGeneratePWforTD = new Button("Generate");
	
	// "passphrase", "tokendate"
	private String patternOption;
	// component
	private Button btnTVerifier, btnLocalFile, btnNoLocalFile, btnGetTweets, btnTVerifierPersonal,
			btnAllowGmail, btnNotAllowGmail, btnAllowLikedTweets, btnNotAllowLikedTweets, btnNotAllowTweets,
			btnAllowTweets, btnTVerifierGmail, btnConvert, btnAbort, btnGeneratePass; // Declare a Button

	private Button btnFixPass = new Button("Password Fixer");
	
	private volatile Thread t, gmailThread, TDthread;
	
	private boolean neverSetUp = true;
	
	private Label numLoopslbl, currentLooplbl, entropylbl, generatelbl, highlbl, ptweetlbl, tweetlbl, textfilelbl, pdffilelbl, localfilelbl;
	
	private Label likedTlbl = new Label("Search liked tweets?");
	
	private Label nodatefoundlbl = new Label("no date pattern found, try different pattern, or collect more userdata");
	
	private Label localFileCompllbl = new Label("Local file search complete");
	
	private Label tverifierlbl = new Label("Enter Twitter verifier");
	private Label likedTCompllbl = new Label("Liked Tweet search complete");
	
	private Label verGmaillbl = new Label("Enter verifier for Gmail");

	private Label searchPtweetlbl = new Label("Search written tweets?");
	private Label searchGmaillbl = new Label("Search Gmail?");
	
	private Label tverifierplbl = new Label("Enter Twitter verifier for personal tweets");
	
	private Label minPWlenlbl = new Label("Enter Min pw length");
	
	private Label maxPWlenlbl = new Label("Enter Max pw length");
	
	private Label numWordspasslbl = new Label("Enter number of words to be used in passphrase");
	
	private Label delpasslbl = new Label("Enter a delimeter to be used in passphrase");
	
	private Label minentropylbl = new Label("Enter minimum zxcvbn entropy password must have");
	
	
	private boolean numLoopslblset, currentLooplblset, entropylblset, generatelblset, highlblset, btnAbortset = false;
	
	// component
	public Nbvcxz nbvcxz;
	
	// characters that should not appear in generated password
	private static char[] bannedChars = { ',', '.', '】', '【', '', '[', ']', '{', '}', ';', ':', '"', '<', '>', '/', '?', '\'',
			'\\', '|', '-', '_', '=', '+'};

	
	//these words will not be included in generated password
	private static String[] bannedStr = {"the","a", "an", "and", "or","aboard",
				"about",
				"above",
				"across",
				"after",
				"against",
				"along",
				"amid",
				"among",
				"anti",
				"around",
				"as",
				"at",
				"before",
				"behind",
				"below",
				"beneath",
				"beside",
				"besides",
				"between",
				"beyond",
				"but",
				"by",
				"concerning",
				"considering",
				"despite",
				"down",
				"during",
				"except",
				"excepting",
				"excluding",
				"following",
				"for",
				"from",
				"in",
				"inside",
				"into",
				"like",
				"minus",
				"near",
				"of",
				"off",
				"on",
				"onto",
				"opposite",
				"outside",
				"over",
				"past",
				"per",
				"plus",
				"regarding",
				"round",
				"save",
				"since",
				"than",
				"through",
				"to",
				"toward",
				"towards",
				"under",
				"underneath",
				"unlike",
				"until",
				"up",
				"upon",
				"versus",
				"via",
				"with",
				"within",
				"without",
				"ago",
				"apart",
				"aside",
				"aslant",
				"away",
				"hence",
				"notwithstanding",
				"on",
				"short",
				"through",
				"withal",
				"who",	"whom", "whose",
				"which",
				"that", "not"};
	
	private Set<String> generatedPWs = new HashSet<String>();
	
	//number of words to be used in passphrase
	private int numWordsPP;
	
	//minimum length of password required
	private int pwMinLen;
	
	//maximum length of password it can be
	private int pwMaxLen;
	
	private int textfileCounter;
	private int pdffileCounter;
	
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
		
		localfilelbl = new Label("Search local files?");
		add(localfilelbl); // "super" Frame adds an anonymous Label

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
		setSize(350, 1500); // "super" Frame sets initial size
		setResizable(false);
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
			btnLocalFile.setVisible(false);
			btnNoLocalFile.setVisible(false);
			localfilelbl.setVisible(false);
			
			add(likedTlbl);
			btnAllowLikedTweets = new Button("Yes");
			add(btnAllowLikedTweets);
			btnAllowLikedTweets.addActionListener(this);
			btnNotAllowLikedTweets = new Button("No");
			add(btnNotAllowLikedTweets);
			btnNotAllowLikedTweets.addActionListener(this);
			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnLocalFile) {
			btnLocalFile.setVisible(false);
			btnNoLocalFile.setVisible(false);
			localfilelbl.setVisible(false);
			
			// get userdata, only if userdata was not extracted before
			if (userdata.equals("")) {
				add(new Label("Extracting userdata..."));
				setVisible(true);

				// extract userdata from local files
				userdata = extractUserData();

			}
			
			add(localFileCompllbl);

			add(likedTlbl);
			btnAllowLikedTweets = new Button("Yes");
			add(btnAllowLikedTweets);
			btnAllowLikedTweets.addActionListener(this);
			btnNotAllowLikedTweets = new Button("No");
			add(btnNotAllowLikedTweets);
			btnNotAllowLikedTweets.addActionListener(this);
			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnNotAllowLikedTweets) {
			likedTlbl.setVisible(false);
			localFileCompllbl.setVisible(false);
			btnAllowLikedTweets.setVisible(false);
			btnNotAllowLikedTweets.setVisible(false);
			
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

			searchPtweetlbl.setVisible(false);
			likedTCompllbl.setVisible(false);
			btnAllowTweets.setVisible(false);
			btnNotAllowTweets.setVisible(false);
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
			
			add(tverifierplbl); // "super" Frame adds an anonymous Label
			tfverifierptweets = new TextField("", 20); // Construct the TextField
			tfverifierptweets.setEditable(true);
			add(tfverifierptweets); // "super" Frame adds TextField

			btnTVerifierPersonal = new Button("Submit Twitter verifier for personal tweets"); // Construct the Button
			add(btnTVerifierPersonal); // "super" Frame adds Button
			btnTVerifierPersonal.addActionListener(this);

			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnNotAllowTweets) {
			searchPtweetlbl.setVisible(false);
			likedTCompllbl.setVisible(false);
			btnAllowTweets.setVisible(false);
			btnNotAllowTweets.setVisible(false);
//			add(new Label("Generate?")); // "super" Frame adds an anonymous Label
//
//			btnGenerate = new Button("Generate"); // Construct the Button
//			add(btnGenerate); // "super" Frame adds Button
//			btnGenerate.addActionListener(this);
//			tfSuggestedPW = new TextField("", 40); // Construct the TextField
//			tfSuggestedPW.setEditable(false);
//			add(tfSuggestedPW);
//			setVisible(true); // "super" Frame shows
			
			add(searchGmaillbl);
			btnAllowGmail = new Button("Yes");
			btnAllowGmail.addActionListener(this);
			add(btnAllowGmail);
			btnNotAllowGmail = new Button("No");
			btnNotAllowGmail.addActionListener(this);
			add(btnNotAllowGmail);
			setVisible(true); // "super" Frame shows
		}
		
		if (evt.getSource() == btnNotAllowGmail) {
			btnAllowGmail.setVisible(false);
			btnNotAllowGmail.setVisible(false);
			searchGmaillbl.setVisible(false);
			btnGeneratePass = new Button("Generate passphrase");
			add(btnGeneratePass);
			btnGeneratePass.addActionListener(this);
			
			btnGenerateTokenDate = new Button("Generate token + date");
			add(btnGenerateTokenDate);
			btnGenerateTokenDate.addActionListener(this);
			
			add(btnFixPass);
			btnFixPass.addActionListener(this);
			
			setVisible(true);
//			add(new Label("Enter Min pw length")); // "super" Frame adds an anonymous Label
//			tfpwMinLen = new TextField("", 1); // Construct the TextField
//			tfpwMinLen.setEditable(true);
//			add(tfpwMinLen);
//			
//			add(new Label("Enter Max pw length")); // "super" Frame adds an anonymous Label
//			tfpwMaxLen = new TextField("", 2); // Construct the TextField
//			tfpwMaxLen.setEditable(true);
//			add(tfpwMaxLen);
//			
//			add(new Label("Enter number of words to be used in passphrase")); // "super" Frame adds an anonymous Label
//			tfnumWords = new TextField("", 1); // Construct the TextField
//			tfnumWords.setEditable(true);
//			add(tfnumWords);
//			
//			add(new Label("Enter a delimeter to be used in passphrase")); // "super" Frame adds an anonymous Label
//			tfdelimeter = new TextField("", 1); // Construct the TextField
//			tfdelimeter.setEditable(true);
//			add(tfdelimeter);
//			
//			add(new Label("Enter minimum zxcvbn entropy password must have")); // "super" Frame adds an anonymous Label
//			tfminEntropy = new TextField("", 2); // Construct the TextField
//			tfminEntropy.setEditable(true);
//			add(tfminEntropy);
//			
//			hanCheckbox = new Checkbox("Include Hanguel in passwords?");
//			add(hanCheckbox); // "super" Frame adds an anonymous Label
//		
//			
//			
//			btnGenerate = new Button("Generate"); // Construct the Button
//			add(btnGenerate); // "super" Frame adds Button
//			btnGenerate.addActionListener(this);
//			tfSuggestedPW = new TextField("", 40); // Construct the TextField
//			tfSuggestedPW.setEditable(false);
//			add(tfSuggestedPW);
//			setVisible(true); // "super" Frame shows
		}
		
		if (evt.getSource() == btnAllowGmail) {
			btnAllowGmail.setVisible(false);
			btnNotAllowGmail.setVisible(false);
			searchGmaillbl.setVisible(false);
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
			add(verGmaillbl); // "super" Frame adds an anonymous Label
			tfverifierGmail = new TextField("", 20); // Construct the TextField
			tfverifierGmail.setEditable(true);
			add(tfverifierGmail); // "super" Frame adds TextField

			btnTVerifierGmail = new Button("Submit verifier for Gmail"); // Construct the Button
			add(btnTVerifierGmail); // "super" Frame adds Button
			btnTVerifierGmail.addActionListener(this);

			setVisible(true); // "super" Frame shows
		}

		
		if(evt.getSource() == btnTVerifierGmail) {
			btnTVerifierGmail.setVisible(false);
			tfverifierGmail.setVisible(false);
			verGmaillbl.setVisible(false);
			
			add(new Label("Extracting Gmail data..."));
			setVisible(true);

			String gverifier = tfverifierGmail.getText();
			String gmailcontent = "";
			try {
				gmailcontent = GoogleGetGmail.getGmail(gverifier, this);
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
			userdata = userdata + "\n";
			
			add(new Label("Gmail search complete"));
			
			//
			btnGeneratePass = new Button("Generate passphrase");
			add(btnGeneratePass);
			btnGeneratePass.addActionListener(this);
			
			btnGenerateTokenDate = new Button("Generate token + date");
			add(btnGenerateTokenDate);
			btnGenerateTokenDate.addActionListener(this);
			
			add(btnFixPass);
			btnFixPass.addActionListener(this);
			
			setVisible(true);
			
//			add(new Label("Enter Min pw length")); // "super" Frame adds an anonymous Label
//			tfpwMinLen = new TextField("", 1); // Construct the TextField
//			tfpwMinLen.setEditable(true);
//			add(tfpwMinLen);
//			
//			add(new Label("Enter Max pw length")); // "super" Frame adds an anonymous Label
//			tfpwMaxLen = new TextField("", 2); // Construct the TextField
//			tfpwMaxLen.setEditable(true);
//			add(tfpwMaxLen);
//			
//			add(new Label("Enter number of words to be used in passphrase")); // "super" Frame adds an anonymous Label
//			tfnumWords = new TextField("", 1); // Construct the TextField
//			tfnumWords.setEditable(true);
//			add(tfnumWords);
//			
//			add(new Label("Enter a delimeter to be used in passphrase")); // "super" Frame adds an anonymous Label
//			tfdelimeter = new TextField("", 1); // Construct the TextField
//			tfdelimeter.setEditable(true);
//			add(tfdelimeter);
//			
//			add(new Label("Enter minimum zxcvbn entropy password must have")); // "super" Frame adds an anonymous Label
//			tfminEntropy = new TextField("", 2); // Construct the TextField
//			tfminEntropy.setEditable(true);
//			add(tfminEntropy);
//			
//			hanCheckbox = new Checkbox("Include Hanguel in passwords?");
//			add(hanCheckbox); // "super" Frame adds an anonymous Label
//		
//			
//			btnGenerate = new Button("Generate"); // Construct the Button
//			add(btnGenerate); // "super" Frame adds Button
//			btnGenerate.addActionListener(this);
//			tfSuggestedPW = new TextField("", 40); // Construct the TextField
//			tfSuggestedPW.setEditable(false);
//			add(tfSuggestedPW);
//			setVisible(true); // "super" Frame shows

		}
		
		if (evt.getSource() == btnAllowLikedTweets) {
			likedTlbl.setVisible(false);
			localFileCompllbl.setVisible(false);
			btnAllowLikedTweets.setVisible(false);
			btnNotAllowLikedTweets.setVisible(false);
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
			
			add(tverifierlbl); // "super" Frame adds an anonymous Label
			tfCount4 = new TextField("", 20); // Construct the TextField
			tfCount4.setEditable(true);
			add(tfCount4); // "super" Frame adds TextField

			btnTVerifier = new Button("Submit Twitter verifier"); // Construct the Button
			add(btnTVerifier); // "super" Frame adds Button
			btnTVerifier.addActionListener(this);

			setVisible(true); // "super" Frame shows
		}

		if (evt.getSource() == btnTVerifier) {
			tverifierlbl.setVisible(false);
			tfCount4.setVisible(false);
			btnTVerifier.setVisible(false);
			add(new Label("Extracting Twitter data..."));
			setVisible(true);

			String tverifier = tfCount4.getText();
			try {

				// gets all liked tweets by the user
				String bodyOfResponse = TwitterExample.getLikedTweets(tverifier);
				JSONArray responseArray = new JSONArray(bodyOfResponse);
				// System.out.println(responseArray);
				final int n = responseArray.length();
				add(new Label("found a total of " + n + " liked Tweets"));
				setVisible(true);
				tweetlbl = new Label("processing 0th liked Tweet...");
				add(tweetlbl);
				setVisible(true);
				for (int i = 0; i < n; ++i) {
					tweetlbl.setText("processing " + (i+1) + "th liked Tweet...");
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
						userdata = userdata + "\n";
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
							userdata = userdata + "\n";
						}
					}
					// add to userdata
					
					//extra info available from Twitter
//
//					userdata = userdata + responseObj.getJSONObject("user").getString("name");
//					userdata = userdata + responseObj.getJSONObject("user").getString("screen_name");
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

			add(likedTCompllbl);
		
			add(searchPtweetlbl);
			btnAllowTweets = new Button("Yes");
			btnAllowTweets.addActionListener(this);
			add(btnAllowTweets);
			btnNotAllowTweets = new Button("No");
			btnNotAllowTweets.addActionListener(this);
			add(btnNotAllowTweets);
			setVisible(true); // "super" Frame shows

		}

		if (evt.getSource() == btnTVerifierPersonal) {
			btnTVerifierPersonal.setVisible(false);
			tfverifierptweets.setVisible(false);
			tverifierplbl.setVisible(false);
			String tverifierPersonal = tfverifierptweets.getText();

			try {

				String bodyOfResponse = TwitterGetAllTweets.getAllTweets(tverifierPersonal);
				JSONArray responseArray = new JSONArray(bodyOfResponse);
				System.out.println(responseArray);
				final int n = responseArray.length();
				add(new Label("found a total of " + n + " personal Tweets"));
				setVisible(true);
				ptweetlbl = new Label("processing 0th personal Tweet...");
				add(ptweetlbl);
				setVisible(true);
				for (int i = 0; i < n; ++i) {
					ptweetlbl.setText("processing " + (i+1) + "th personal Tweet...");
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
						userdata = userdata + "\n";

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
//							userdata = userdata + "\n";
							userdata = userdata + tweetText.text();
							userdata = userdata + "\n";
//							System.out.println("tweetText = " + tweetText.text());
//							userdata = userdata + "\n";
						}
					}
					// add to userdata
//
					
					//extra information available from Twitter
//					userdata = userdata + responseObj.getJSONObject("user").getString("name");
//					userdata = userdata + responseObj.getJSONObject("user").getString("screen_name");
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
//
//		if (evt.getSource() == btnAbort) {
//			//mustAbort = true;
//			t.interrupt();
//			//t = null;
//		}
		
		if (evt.getSource() == btnGenerateTokenDate) {

			patternOption = "tokendate";
			
			// only if userdataDic was not filled before
			if (userdataDic == null) {
				
				userdataDic = processUserData(userdata);
				printToUserDatatxt(userdataDic);
				System.out.println("userdata = " + userdata);
				add(new Label("created file userdataForProject11111.txt"));
				add(new Label("in Documents folder"));
				setVisible(true);
			}
			
			TokenDateGUI tdgui = new TokenDateGUI(this, userdata, userdataDic);
			
			//if never set up
//			if(neverSetUp == true) {
//				setupPWrequirement();
//				neverSetUp = false;
//			}
//			createPWrequirementTokenDate();
		}
		
		if (evt.getSource() == btnGeneratePass) {

			patternOption = "passphrase";
			
			// only if userdataDic was not filled before
			if (userdataDic == null) {
				
				userdataDic = processUserData(userdata);
				printToUserDatatxt(userdataDic);
				System.out.println("userdata = " + userdata);
				add(new Label("created file userdataForProject11111.txt"));
				add(new Label("in Documents folder"));
				setVisible(true);
			}
			
			PassphraseGUI passgui =  new PassphraseGUI(this, userdata, userdataDic);
			
//			
//			if(neverSetUp == true) {
//				setupPWrequirement();
//				neverSetUp = false;
//			}
//			createPWrequirement();
		}
		if (evt.getSource() == btnFixPass) {

			//patternOption = "fixpass";
			
			// only if userdataDic was not filled before
			if (userdataDic == null) {
				
				userdataDic = processUserData(userdata);
				printToUserDatatxt(userdataDic);
				System.out.println("userdata = " + userdata);
				add(new Label("created file userdataForProject11111.txt"));
				add(new Label("in Documents folder"));
				setVisible(true);
			}
			
			FixPasswordGUI fpgui =  new FixPasswordGUI(this, userdata, userdataDic);

		}
		
//		if (evt.getSource() == btnGenerate) {
//			nodatefoundlbl.setVisible(false);
//			// token date pattern
//			if (patternOption == "tokendate") {
//				generateTDpw();
//				return;
//			}
//			
//			if(btnAbortset == true) {
//				//do nothing
//			}
//			else {
//				btnAbort = new Button("Abort"); // Construct the Button
//				add(btnAbort); // "super" Frame adds Button
//				btnAbort.setVisible(true);
//				btnAbort.addActionListener(this);
//				btnAbortset = true;
//			}
//			
//			t = new Thread(new Runnable() {
//				
//				public void run() {
//					// process and get the user data dictionary
//
//					// if userdata was not collected at all, use nbvcxz's default generetor, for
//					// now.
//					String suggestedPW = "";
//					
//					//TODO: check that delimeter is in English
//					String delimeter = tfdelimeter.getText();
//					int numWords = Integer.parseInt(tfnumWords.getText());
//					int minLength = Integer.parseInt(tfpwMinLen.getText());
//					int maxLength = Integer.parseInt(tfpwMaxLen.getText());
//					int minEntropy = Integer.parseInt(tfminEntropy.getText());
//					
//					if (userdata.equals("")) {
//						while(true) {
//							suggestedPW = Generator.generatePassphrase(delimeter, numWords);
//							if ((suggestedPW.length() >= minLength) && (suggestedPW.length() <= maxLength)) {
//								break;
//							}
//						}
//					} 
//					else {
//
//						// only if userdataDic was not filled before
//						if (userdataDic == null) {
//							userdataDic = processUserData(userdata);
//							printToUserDatatxt(userdataDic);
//							System.out.println("userdata = " + userdata);
//							add(new Label("created file userdataForProject11111.txt"));
//							add(new Label("in Documents folder"));
//							setVisible(true);
//						}
//						
//						//suggestedPW = Generator.generatePassphrase("l", 3, userdataDic);
//						
//						
//						Nbvcxz nbvcxz = new Nbvcxz();
////						Double entropy = nbvcxz.estimate(suggestedPW).getEntropy();
////						//if zxcvbn returns a password strength lower than threshold, generate different password
////						// set threshold to 100 for now
////						while(entropy < 100) {
////							suggestedPW = Generator.generatePassphrase("l", 3, userdataDic);
////							entropy = nbvcxz.estimate(suggestedPW).getEntropy();
////							//System.out.println("generated password = " + suggestedPW);
////							//System.out.println("entropy = " + entropy);
////						}
//						String meaningOfHanguel = "";
//						Double entropy = 0.000;
//
//						final int high = userdataDic.getDictonary().size();
//						
//						if(highlblset == true) {
//							highlbl.setText("Number of Words in userdata dictionary : " + high);
//						}
//						else {
//							highlbl = new Label("Number of Words in userdata dictionary : " + high);
//							add(highlbl);
//							setVisible(true);
//							highlblset = true;
//						}
//
//						//reverse order; most infrequent ones first
//						Map<String, Integer> sortedDic = sortByValue(userdataDic.getDictonary());
//						//List<String> userdataWords = new ArrayList<String>(sortedDic.value);
//						
//						
//						//if actual numLoops returns greater than 214783647 (max int value possible)
//						// it is set to 214783647
//						int numLoops = (int) Math.pow(high, numWords);
//						
//						if (numLoopslblset == true) {
//							numLoopslbl.setText("Max Number of loops to be iterated : " + numLoops);
//						}
//
//						else {
//							numLoopslbl = new Label("Max Number of loops to be iterated : " + numLoops);
//							add(numLoopslbl);
//							setVisible(true);
//							numLoopslblset = true;
//						}
//						if (currentLooplblset == true) {
//							currentLooplbl.setText("currently doing 0th loop");
//						} 
//						else {
//							currentLooplbl = new Label("currently doing 0th loop");
//							add(currentLooplbl);
//							setVisible(true);
//							currentLooplblset = true;
//						}
//						
//						for (int j = 0; j < numLoops; j++) {
////							if (mustAbort == true) {
////								currentLooplbl.setText("Abort button pressed.");
////								return;
////							}
//							if (Thread.currentThread().isInterrupted()) {
//								currentLooplbl.setText("Abort button pressed.");
//								return;
//							}
//							currentLooplbl.setText("currently doing " + j + "th loop");
//							
//							suggestedPW = "";
//							for (int k=1; k <= numWords; k++) {
//								//if k == numWords, need remainder rather than quotient
//								if (k == numWords) {
//									suggestedPW = suggestedPW + getKeyByValue(sortedDic, (j % k) + 1);
//								}
//								//else need quotient
//								else {
//									int expon = numWords - k;
//									int rank = j / ((int)Math.pow(numWords, expon));
//									//since rank starts counting from 1
//									rank =rank +1;
//									suggestedPW = suggestedPW + getKeyByValue(sortedDic, rank);
//									suggestedPW = suggestedPW + delimeter;
//								}
//							
//							}
//							
//							// if this pw was already generated before
//							// if (!generatedPWs.isEmpty()) {
//							if (generatedPWs.contains(suggestedPW)) {
//								continue;
//							}
//							// }
//							
//							//suggestedPW = Generator.generatePassphrase(delimeter, numWords, userdataDic);
//							String originalPass = suggestedPW;
//							// if suggestedPW contains Hanguel, convert to English and inform user
//							String[] words = suggestedPW.split(delimeter);
//							suggestedPW = "";
//							meaningOfHanguel = "";
//							if (hanCheckbox.getState() == false) {
//								// if at least one of the words contain at least one Hanguel char, try different
//								// password
//								boolean tryDiffpw = false;
//								for (String word : words) {
//									//
//
//									for (int i = 0; i < word.length(); i++) {
//										char letter = word.charAt(i);
//										String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
//										// System.out.println( "\\u" + unicodeStr);
//										int unicode = Integer.parseInt(unicodeStr, 16);
//										if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
//											tryDiffpw = true;
//										}
//
//									}
//
//								}
//								if (tryDiffpw == true) {
//									continue;
//								} else {
//									suggestedPW = originalPass;
//								}
//							} else {
//
//								for (String word : words) {
//									// convert only if at least one character in this word is Hanguel
//									boolean mustBeConverted = false;
//									for (int i = 0; i < word.length(); i++) {
//										char letter = word.charAt(i);
//										String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
//										// System.out.println( "\\u" + unicodeStr);
//										int unicode = Integer.parseInt(unicodeStr, 16);
//										if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
//											mustBeConverted = true;
//										}
//
//									}
//
//									// if it has to be converted
//									if (mustBeConverted == true) {
//										// since convertToEng is defined only for Hanguel, scan every char, and call
//										// converToEng only if it is Hanguel
//										String originalWord = word;
//										String convertedWord = "";
//										for (int i = 0; i < word.length(); i++) {
//											char letter = word.charAt(i);
//											String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
//											// System.out.println( "\\u" + unicodeStr);
//											int unicode = Integer.parseInt(unicodeStr, 16);
//											if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
//												convertedWord = convertedWord + convertToEng(Character.toString(letter));
//											} else {
//												convertedWord = convertedWord + Character.toString(letter);
//											}
//
//										}
//										// word = convertToEng(word);
//										meaningOfHanguel = meaningOfHanguel + convertedWord + " stands for " + originalWord
//												+ "    ";
//
//										word = convertedWord;
//									}
//									suggestedPW = suggestedPW + word + delimeter;
//
//								}
//
//							}
//
//							// if length requirement specified by the user is not met
//							// TODO: get rid of infinite loop caused by no password possible meeting the
//							// length requirement
//							if (!((suggestedPW.length() >= minLength) && (suggestedPW.length() <= maxLength))) {
//								continue;
//							}
//
//							entropy = nbvcxz.estimate(suggestedPW).getEntropy();
//							// if zxcvbn returns a password strength lower than the threshold user specified, generate
//							// a different password
//							if (entropy >= minEntropy) {
//								System.out.println(meaningOfHanguel);
//								break;
//								// System.out.println("generated password = " + suggestedPW);
//								// System.out.println("entropy = " + entropy);
//							}
//							
//							//tried all possible passwords, none passed
//							if (j == (numLoops - 1)) {
//								tfSuggestedPW.setText("");
//								add(new Label("no password possible, try different setting"));
//								setVisible(true);
//								return;
//							}
//						}
//
//						//
//
//						System.out.println("generated password = " + suggestedPW);
//
//						System.out.println("entropy = " + entropy);
//						//
////						printToUserDatatxt(userdataDic);
//						
//						if (!(meaningOfHanguel == "")) {
//							add(new Label(meaningOfHanguel));
//						}
//						if (entropylblset == true) {
//							entropylbl.setText("zxcvbn entropy = " + entropy);
//						}
//						else {
//							entropylbl = new Label("zxcvbn entropy = " + entropy);
//							add(entropylbl);
//							setVisible(true);
//							entropylblset = true;
//						}
//						
//					}
//					
//					tfSuggestedPW.setText(suggestedPW);
//					generatedPWs.add(suggestedPW);
//					if(generatelblset == true) {
//						//do nothing
//					}
//					else {
//						generatelbl = new Label("click generate button again to try different password");
//						add(generatelbl);
//						setVisible(true);
//						generatelblset = true;
//					}
//					//btnAbort.setVisible(false);
//					
//				}
//			});
//			t.start();
//		}

	}


//	private void generateTDpw() {
//		TDthread = new Thread(new Runnable() {
//			
//			public void run() {
//		
//				// if userdata was not collected at all, use nbvcxz's default generetor, for
//				// now.
//				String suggestedPW = "";
//				
//				
//				int minLength = Integer.parseInt(tfpwMinLen.getText());
//				int maxLength = Integer.parseInt(tfpwMaxLen.getText());
//				int minEntropy = Integer.parseInt(tfminEntropy.getText());
//				
//				if (userdata.equals("")) {
//					while(true) {
//						suggestedPW = Generator.generatePassphrase("l", 3);
//						if ((suggestedPW.length() >= minLength) && (suggestedPW.length() <= maxLength)) {
//							break;
//						}
//					}
//				} 
//				else {
//
//					// only if userdataDic was not filled before
//					if (userdataDic == null) {
//						userdataDic = processUserData(userdata);
//						printToUserDatatxt(userdataDic);
//						System.out.println("userdata = " + userdata);
//						add(new Label("created file userdataForProject11111.txt"));
//						add(new Label("in Documents folder"));
//						setVisible(true);
//					}
//					
//					//suggestedPW = Generator.generatePassphrase("l", 3, userdataDic);
//					
//					
//					Nbvcxz nbvcxz = new Nbvcxz();
////					Double entropy = nbvcxz.estimate(suggestedPW).getEntropy();
////					//if zxcvbn returns a password strength lower than threshold, generate different password
////					// set threshold to 100 for now
////					while(entropy < 100) {
////						suggestedPW = Generator.generatePassphrase("l", 3, userdataDic);
////						entropy = nbvcxz.estimate(suggestedPW).getEntropy();
////						//System.out.println("generated password = " + suggestedPW);
////						//System.out.println("entropy = " + entropy);
////					}
//					String meaningOfHanguel = "";
//					Double entropy = 0.000;
//
//					final int high = userdataDic.getDictonary().size();
//					
//					if(highlblset == true) {
//						highlbl.setText("Number of Words in userdata dictionary : " + high);
//					}
//					else {
//						highlbl = new Label("Number of Words in userdata dictionary : " + high);
//						add(highlbl);
//						setVisible(true);
//						highlblset = true;
//					}
//
//					//reverse order; most infrequent ones first
//					Map<String, Integer> sortedDic = sortByValue(userdataDic.getDictonary());
//					//List<String> userdataWords = new ArrayList<String>(sortedDic.value);
//					
//					 List<Match> matches = new ArrayList<>();
//					 DateMatcher datematcher = new DateMatcher();
//					 
//					 for (int i=0; i < high; i++) {
//						 int rank = i+1;
//						 String word = getKeyByValue(sortedDic, rank);
//						 matches.addAll(datematcher.match(nbvcxz.getConfiguration(), word));
//						 if (matches.size() != 0) {
//							 break;
//						 }
//					 }
//					 	 
//					 
////					 for (Map.Entry<String, Integer> entry : sortedDic.entrySet())
////					 {
////						 matches.addAll(datematcher.match(nbvcxz.getConfiguration(), entry.getKey()));
////					 }
////					 
////					 for (int i=0; i< matches.size(); i++) {
////						 System.out.println("token : " + matches.get(i).getToken());
////						 System.out.println(matches.get(i).getDetails());
////						 System.out.println("\n");
////					 }
//					 
//					 //if no date pattern is found, return immediately
//					 if(matches.size() == 0) {
//						 System.out.println("no date pattern found");
//						 tfSuggestedPW.setText("");
//						 
//						 add(nodatefoundlbl);
//						 setVisible(true);
//						 return;
//						 
//					 }
//					 
//					 String date = matches.get(0).getToken();
////					//if actual numLoops returns greater than 214783647 (max int value possible)
////					// it is set to 214783647
////					int numLoops = (int) Math.pow(high, numWords);
////					
////					if (numLoopslblset == true) {
////						numLoopslbl.setText("Max Number of loops to be iterated : " + numLoops);
////					}
////
////					else {
////						numLoopslbl = new Label("Max Number of loops to be iterated : " + numLoops);
////						add(numLoopslbl);
////						setVisible(true);
////						numLoopslblset = true;
////					}
////					if (currentLooplblset == true) {
////						currentLooplbl.setText("currently doing 0th loop");
////					} 
////					else {
////						currentLooplbl = new Label("currently doing 0th loop");
////						add(currentLooplbl);
////						setVisible(true);
////						currentLooplblset = true;
////					}
////					
////					for (int j = 0; j < numLoops; j++) {
//////						if (mustAbort == true) {
//////							currentLooplbl.setText("Abort button pressed.");
//////							return;
//////						}
////						if (Thread.currentThread().isInterrupted()) {
////							currentLooplbl.setText("Abort button pressed.");
////							return;
////						}
////						currentLooplbl.setText("currently doing " + j + "th loop");
////						
////						suggestedPW = "";
////						for (int k=1; k <= numWords; k++) {
////							//if k == numWords, need remainder rather than quotient
////							if (k == numWords) {
////								suggestedPW = suggestedPW + getKeyByValue(sortedDic, (j % k) + 1);
////							}
////							//else need quotient
////							else {
////								int expon = numWords - k;
////								int rank = j / ((int)Math.pow(numWords, expon));
////								//since rank starts counting from 1
////								rank =rank +1;
////								suggestedPW = suggestedPW + getKeyByValue(sortedDic, rank);
////								suggestedPW = suggestedPW + delimeter;
////							}
////						
////						}
////						
////						// if this pw was already generated before
////						// if (!generatedPWs.isEmpty()) {
////						if (generatedPWs.contains(suggestedPW)) {
////							continue;
////						}
////						// }
////						
////						//suggestedPW = Generator.generatePassphrase(delimeter, numWords, userdataDic);
////						String originalPass = suggestedPW;
////						// if suggestedPW contains Hanguel, convert to English and inform user
////						String[] words = suggestedPW.split(delimeter);
////						suggestedPW = "";
////						meaningOfHanguel = "";
////						if (hanCheckbox.getState() == false) {
////							// if at least one of the words contain at least one Hanguel char, try different
////							// password
////							boolean tryDiffpw = false;
////							for (String word : words) {
////								//
////
////								for (int i = 0; i < word.length(); i++) {
////									char letter = word.charAt(i);
////									String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
////									// System.out.println( "\\u" + unicodeStr);
////									int unicode = Integer.parseInt(unicodeStr, 16);
////									if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
////										tryDiffpw = true;
////									}
////
////								}
////
////							}
////							if (tryDiffpw == true) {
////								continue;
////							} else {
////								suggestedPW = originalPass;
////							}
////						} else {
////
////							for (String word : words) {
////								// convert only if at least one character in this word is Hanguel
////								boolean mustBeConverted = false;
////								for (int i = 0; i < word.length(); i++) {
////									char letter = word.charAt(i);
////									String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
////									// System.out.println( "\\u" + unicodeStr);
////									int unicode = Integer.parseInt(unicodeStr, 16);
////									if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
////										mustBeConverted = true;
////									}
////
////								}
////
////								// if it has to be converted
////								if (mustBeConverted == true) {
////									// since convertToEng is defined only for Hanguel, scan every char, and call
////									// converToEng only if it is Hanguel
////									String originalWord = word;
////									String convertedWord = "";
////									for (int i = 0; i < word.length(); i++) {
////										char letter = word.charAt(i);
////										String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
////										// System.out.println( "\\u" + unicodeStr);
////										int unicode = Integer.parseInt(unicodeStr, 16);
////										if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
////											convertedWord = convertedWord + convertToEng(Character.toString(letter));
////										} else {
////											convertedWord = convertedWord + Character.toString(letter);
////										}
////
////									}
////									// word = convertToEng(word);
////									meaningOfHanguel = meaningOfHanguel + convertedWord + " stands for " + originalWord
////											+ "    ";
////
////									word = convertedWord;
////								}
////								suggestedPW = suggestedPW + word + delimeter;
////
////							}
////
////						}
////
////						// if length requirement specified by the user is not met
////						// TODO: get rid of infinite loop caused by no password possible meeting the
////						// length requirement
////						if (!((suggestedPW.length() >= minLength) && (suggestedPW.length() <= maxLength))) {
////							continue;
////						}
////
////						entropy = nbvcxz.estimate(suggestedPW).getEntropy();
////						// if zxcvbn returns a password strength lower than the threshold user specified, generate
////						// a different password
////						if (entropy >= minEntropy) {
////							System.out.println(meaningOfHanguel);
////							break;
////							// System.out.println("generated password = " + suggestedPW);
////							// System.out.println("entropy = " + entropy);
////						}
////						
////						//tried all possible passwords, none passed
////						if (j == (numLoops - 1)) {
////							tfSuggestedPW.setText("");
////							add(new Label("no password possible, try different setting"));
////							setVisible(true);
////							return;
////						}
////					}
//
//					//
//
//					System.out.println("generated password = " + suggestedPW);
//
//					System.out.println("entropy = " + entropy);
//					//
////					printToUserDatatxt(userdataDic);
//					
//					if (!(meaningOfHanguel == "")) {
//						add(new Label(meaningOfHanguel));
//					}
//					if (entropylblset == true) {
//						entropylbl.setText("zxcvbn entropy = " + entropy);
//					}
//					else {
//						entropylbl = new Label("zxcvbn entropy = " + entropy);
//						add(entropylbl);
//						setVisible(true);
//						entropylblset = true;
//					}
//					
//				}
//				
//				tfSuggestedPW.setText(suggestedPW);
//				generatedPWs.add(suggestedPW);
//				if(generatelblset == true) {
//					//do nothing
//				}
//				else {
//					generatelbl = new Label("click generate button again to try different password");
//					add(generatelbl);
//					setVisible(true);
//					generatelblset = true;
//				}
//				//btnAbort.setVisible(false);
//				
//			}	
//		});
//		TDthread.start();
//	}

//	private void setupPWrequirement() {
//		add(minPWlenlbl); // "super" Frame adds an anonymous Label
//		tfpwMinLen.setEditable(true);
//		add(tfpwMinLen);
//		
//		add(maxPWlenlbl); // "super" Frame adds an anonymous Label
//		tfpwMaxLen.setEditable(true);
//		add(tfpwMaxLen);
//		
//		add(numWordspasslbl); // "super" Frame adds an anonymous Label
//		 
//		tfnumWords.setEditable(true);
//		add(tfnumWords);
//		
//		add(delpasslbl); // "super" Frame adds an anonymous Label
//		tfdelimeter.setEditable(true);
//		add(tfdelimeter);
//		
//		add(minentropylbl); // "super" Frame adds an anonymous Label
//		tfminEntropy.setEditable(true);
//		add(tfminEntropy);
//		
//		add(hanCheckbox); // "super" Frame adds an anonymous Label
//
//	
//		add(btnGenerate); // "super" Frame adds Button
//		btnGenerate.addActionListener(this);
////		add(btnGeneratePWforTD); // "super" Frame adds Button
////		btnGeneratePWforTD.addActionListener(this);
//		
//		tfSuggestedPW.setEditable(false);
//		add(tfSuggestedPW);
//		setVisible(true); // "super" Frame shows
//		
//	}

//	private void createPWrequirementTokenDate() {
//		btnGenerateTokenDate.setVisible(false);
//		btnGeneratePass.setVisible(true);
//		//btnGeneratePWforTD.setVisible(true);
//		minPWlenlbl.setVisible(true);
//		maxPWlenlbl.setVisible(true);
//		numWordspasslbl.setVisible(false);
//		delpasslbl.setVisible(false);
//		minentropylbl.setVisible(true);
//		tfpwMinLen.setVisible(true);
//		tfpwMaxLen.setVisible(true);
//		tfnumWords.setVisible(false);
//		tfdelimeter.setVisible(false);
//		tfminEntropy.setVisible(true);
//		hanCheckbox.setVisible(true);
//		btnGenerate.setVisible(true);
//		tfSuggestedPW.setVisible(true);
//		
//	}
//
//	private void createPWrequirement() {
////		
//		btnGeneratePass.setVisible(false);
//		btnGenerateTokenDate.setVisible(true);
//		//btnGeneratePWforTD.setVisible(false);
//		minPWlenlbl.setVisible(true);
//		maxPWlenlbl.setVisible(true);
//		numWordspasslbl.setVisible(true);
//		delpasslbl.setVisible(true);
//		minentropylbl.setVisible(true);
//		tfpwMinLen.setVisible(true);
//		tfpwMaxLen.setVisible(true);
//		tfnumWords.setVisible(true);
//		tfdelimeter.setVisible(true);
//		tfminEntropy.setVisible(true);
//		hanCheckbox.setVisible(true);
//		btnGenerate.setVisible(true);
//		tfSuggestedPW.setVisible(true);
//		
//		
//	}

	
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	static boolean isHanguel(char letter) {
		boolean result = false;
		String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
		// System.out.println( "\\u" + unicodeStr);
		int unicode = Integer.parseInt(unicodeStr, 16);
		if (((unicode >= 0xAC00) && (unicode <= 0xD7A3))) {
			result = true;
		}
		return result;

	}
	
	static String convertToEng(String hanguel) {
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
	
	private static String convertToEngHelper(char hanguelLetter) {
		String partialResult = "";

		if(!isHanguel(hanguelLetter)) {
			partialResult = partialResult + hanguelLetter;
			return partialResult;
		}
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
	
	static void printToUserDatatxt(Dictionary userdataDic2) {
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
		//Map<String, Integer> sortedmap = sortByValue(map);
		int numOfWords = map.size();
		try {
			PrintWriter writer = new PrintWriter(myDocuments, "UTF-8");
			
			for (int i=1; i <= numOfWords; i++) {
				writer.println(getKeyByValue(map, i));
			}
			
//			//loop a Map
//			for (Map.Entry<String, Integer> entry : map.entrySet()) {
//				writer.println(entry.getKey());
//			
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	// TODO: process user data in extractUserData() to speed up?
	static Dictionary processUserData(String userdata) {

		// key value
		Map<String, Integer> map = new HashMap<String, Integer>();

		// read from userdata.txt, split on space

		// System.out.println(line);
		String[] splited = userdata.split("\\s+");
		for (String str : splited) {
			boolean containsBanned = false;
			
			boolean unsupportedChar = false;
			//only add to map if all characters are within this unicode range (English, Korean, Symbol)
			for (int i = 0; i < str.length(); i++) {
				char letter = str.charAt(i);
				String unicodeStr = Integer.toHexString(letter | 0x10000).substring(1);
				// System.out.println( "\\u" + unicodeStr);
				int unicode = Integer.parseInt(unicodeStr, 16);
				
				
				if (unicode < 0x0021) {
					unsupportedChar = true;
					break;
				}
				
				else if ((unicode > 0x007a) && (unicode < 0xAC00)) {
					unsupportedChar = true;
					break;
				}
				
				else if (unicode > 0xD7A3) {
					unsupportedChar = true;
					break;
				}

			}
			
			if(unsupportedChar)
				continue;
			
			
			//do not add to map if this str contains forbidden characters
			for (char fbiddenChar : bannedChars) {
				
				if (str.indexOf(fbiddenChar) >= 0) {
					containsBanned = true;
				}
			}
			
		
			if (containsBanned == true) {
				continue;
			}
			
			//do not add to map if this str.toLowerCase() is one of bannedStr
			if ((Arrays.asList(bannedStr).contains(str.toLowerCase()))) {
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

		add(new Label("found a total of " + textfiles.size() + " text files"));
		add(new Label("found a total of " + pdffiles.size() + " pdf files"));
		textfileCounter = 0;
		pdffileCounter =0;
		textfilelbl = new Label("processing 0th text file...");
		pdffilelbl = new Label("processing 0th pdf file...");
		
		add(textfilelbl);
		setVisible(true);
		textfiles.forEach(file -> {

			textfileCounter++;
			textfilelbl.setText("processing " + textfileCounter + "th text file...");
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

		add(pdffilelbl);
		setVisible(true);
		pdffiles.forEach(file -> {
			pdffileCounter++;
			pdffilelbl.setText("processing " + pdffileCounter + "th pdf file...");
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
		userdata = userdata + "\n";

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