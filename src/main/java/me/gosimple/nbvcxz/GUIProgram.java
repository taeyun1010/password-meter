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

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

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
public class GUIProgram extends Frame implements ActionListener, WindowListener {
	// This class acts as listener for ActionEvent and WindowEvent
	// A Java class can extend only one superclass, but it can implement multiple
	// interfaces.

	public TextField tfCount1, tfCount2, tfCount3, tfCount4; // Declare a TextField component
	private Button btnStart; // Declare a Button component
	public Nbvcxz nbvcxz;

	// private PrintWriter writer = null;

	//String userdata = "";

	// user data dictionary
	Dictionary userdataDic;

	// Constructor to setup the GUI components and event handlers
	public GUIProgram(Nbvcxz nbvcxz2) {
		nbvcxz = nbvcxz2;

		setLayout(new FlowLayout()); // "super" Frame sets to FlowLayout
//
//		add(new Label("Username  ")); // "super" Frame adds an anonymous Label
//		tfCount1 = new TextField("", 10); // Construct the TextField
//		tfCount1.setEditable(true);
//		add(tfCount1); // "super" Frame adds TextField
//
//		add(new Label("Password")); // "super" Frame adds an anonymous Label
//		tfCount2 = new TextField("", 10); // Construct the TextField
//		tfCount2.setEditable(true);
//		add(tfCount2); // "super" Frame adds TextField


		btnStart = new Button("Start"); // Construct the Button
		add(btnStart); // "super" Frame adds Button

		btnStart.addActionListener(this);
		// btnCount (source object) fires ActionEvent upon clicking
		// btnCount adds "this" object as an ActionEvent listener

		

		addWindowListener(this);
		// "super" Frame (source object) fires WindowEvent.
		// "super" Frame adds "this" object as a WindowEvent listener.

		setTitle("Password Meter"); // "super" Frame sets title
		setSize(250, 250); // "super" Frame sets initial size
		setVisible(true); // "super" Frame shows
	}

	/* ActionEvent handler */
	@Override
	public void actionPerformed(ActionEvent evt) {
		// String originalpw = tfCount2.getText();
		if (evt.getSource() == btnStart) {

			SubGUIProgram gui =  new SubGUIProgram(nbvcxz);
			
//			// get userdata, only if userdata was not extracted before
//			if (userdata.equals("")) {
//
//				// extract userdata from local files
//				userdata = extractUserData();
//
//				
//				
//				
//				// get userdata from twitter
//				try {
//					TwitterExample.openBrowser(this);
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				// process and get the user data dictionary
//				userdataDic = processUserData(userdata);
//			}

//			String revisedpw = Generator.generatePassphrase("l", 3, userdataDic);
//
//			tfCount3.setText(revisedpw);
		}
		
		
		
	}

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
//		Map<String, Integer> sortedmap = sortByValue(map);
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

//	// sorts map, most frequent ones first
//	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
//		LinkedList<Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
//		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
//			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
//				return (o2.getValue()).compareTo(o1.getValue());
//			}
//		});
//
//		Map<K, V> result = new LinkedHashMap<K, V>();
//		for (Map.Entry<K, V> entry : list) {
//			result.put(entry.getKey(), entry.getValue());
//		}
//		return result;
//	}

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

//	private String extractUserData() {
//		// get path to documents folder
//		String myDocuments = null;
//		// String userdata = "";
//
//		try {
//			Process p = Runtime.getRuntime().exec(
//					"reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
//			p.waitFor();
//
//			InputStream in = p.getInputStream();
//			byte[] b = new byte[in.available()];
//			in.read(b);
//			in.close();
//
//			myDocuments = new String(b);
//			myDocuments = myDocuments.split("\\s\\s+")[4];
//
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}
//
//		// find all files in myDocuments folder
//		File dir = new File(myDocuments);
//		Collection<File> allfiles = FileUtils.listFiles(dir, null, true);
//		Collection<File> pdffiles = FileUtils.listFiles(dir, new String[] { "pdf" }, true);
//		Collection<File> textfiles = FileUtils.listFiles(dir, new String[] { "txt" }, true);
//
//		textfiles.forEach(file -> {
//
//			try {
//				userdata = readtxtfile(file, userdata);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		});
//
//		pdffiles.forEach(file -> {
//			try {
//				userdata = extractpdf(file, userdata);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//
//		return userdata;
//	}

//	public String extractpdf(File file, String userdata) throws IOException {
//
//		PDDocument document = PDDocument.load(file);
//
//		// Instantiate PDFTextStripper class
//		PDFTextStripper pdfStripper = new PDFTextStripper();
//
//		// Retrieving text from PDF document
//		String text = pdfStripper.getText(document);
//
//		// System.out.println(text);
//		userdata = userdata + text;
//
//		// Closing the document
//		document.close();
//
//		return userdata;
//	}
//
//	public String readtxtfile(File txtfile, String userdata)
//			throws FileNotFoundException, UnsupportedEncodingException {
//		BufferedReader br = null;
//
//		try {
//			br = new BufferedReader(new FileReader(txtfile));
//			String line;
//			while ((line = br.readLine()) != null) {
//				userdata = userdata + line + "\n";
//			}
//			// writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (br != null) {
//					br.close();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//
//		return userdata;
//
//	}

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