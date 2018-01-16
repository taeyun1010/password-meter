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

	public TextField tfCount1, tfCount2, tfCount3; // Declare a TextField component
	private Button btnCount; // Declare a Button component
	public Nbvcxz nbvcxz;

	private PrintWriter writer = null;

	// Constructor to setup the GUI components and event handlers
	public GUIProgram(Nbvcxz nbvcxz2) {
		nbvcxz = nbvcxz2;

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
		tfCount3 = new TextField("", 30); // Construct the TextField
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
		// tfCount3.setText(originalpw);

		// if userdata.txt file does not exist, extract user data
		File f = new File("src/main/resources/dictionaries/userdata.txt");
		if (!(f.exists() && !f.isDirectory())) {

			// extract facebook data
			// String[] args = null;
			// FxWebViewExample1.main_webView(args);

			// extract local data
			extractUserData();

			// process userdata.txt
			processUserData();

		}

		Dictionary dic = new Dictionary("sorteduserdata", DictionaryUtil.loadUnrankedDictionary("sorteduserdata.txt"), false);
		String revisedpw = Generator.generatePassphrase("l", 3, dic);

		// String decodedPath = null;
		// try {
		// decodedPath =
		// GUIProgram.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		// } catch (URISyntaxException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// tfCount3.setText(decodedPath);

		tfCount3.setText(revisedpw);

	}

	// TODO: process user data in extractUserData() to speed up?
	private void processUserData() {

		// key value
		Map<String, Integer> map = new HashMap<String, Integer>();

		try {
			writer = new PrintWriter("src/main/resources/dictionaries/processeduserdata.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader br = null;

		// read from userdata.txt, split on space
		try {
			br = new BufferedReader(new FileReader("src/main/resources/dictionaries/userdata.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] splited = line.split("\\s+");
				for (String str : splited) {

					// if this word is already added to map before
					if (map.containsKey(str)) {
						map.put(str, map.get(str) + 1);
					}
					// if this word is encountered for the first time
					else {
						map.put(str, 1);
					}
					writer.println(str);
				}
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

		// add map to sorteduserdata.txt
		try {
			writer = new PrintWriter("src/main/resources/dictionaries/sorteduserdata.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Integer> sortedmap = sortByValue(map);
		// loop a Map
		for (Map.Entry<String, Integer> entry : sortedmap.entrySet()) {
			writer.println(entry.getKey());
		}

	}

	//sorts map, most frequent ones first
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

	private void extractUserData() {
		// get path to documents folder
		String myDocuments = null;

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

		try {
			writer = new PrintWriter("src/main/resources/dictionaries/userdata.txt", "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		textfiles.forEach(file -> {

			try {
				readtxtfile(file, writer);
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
				extractpdf(file, writer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		writer.close();
	}

	public void extractpdf(File file, PrintWriter writer) throws IOException {

		PDDocument document = PDDocument.load(file);

		// Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		// Retrieving text from PDF document
		String text = pdfStripper.getText(document);

		// System.out.println(text);
		writer.println(text);

		// Closing the document
		document.close();
	}

	public void readtxtfile(File txtfile, PrintWriter writer)
			throws FileNotFoundException, UnsupportedEncodingException {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(txtfile));
			String line;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				writer.println(line);
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