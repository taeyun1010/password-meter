package me.gosimple.nbvcxz;

import java.awt.*;
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import me.gosimple.nbvcxz.resources.Dictionary;
import me.gosimple.nbvcxz.resources.DictionaryUtil;
import me.gosimple.nbvcxz.resources.Generator;

//An AWT GUI program inherits the top-level container java.awt.Frame
public class GUIProgram extends Frame implements ActionListener, WindowListener {
	// This class acts as listener for ActionEvent and WindowEvent
	// A Java class can extend only one superclass, but it can implement multiple
	// interfaces.

	public TextField tfCount1, tfCount2, tfCount3; // Declare a TextField component
	private Button btnCount; // Declare a Button component
	public Nbvcxz nbvcxz;

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

//		//find all files in myDocuments folder
//		File dir = new File(myDocuments);
//		Collection<File> allfiles = FileUtils.listFiles(dir, null, true);
//		Collection<File> pdffiles = FileUtils.listFiles(dir, new String[] { "pdf" }, true);

//		try {
//			extractpdf(new File("C:\\Users\\User\\Documents\\2017_2_test.pdf"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		pdffiles.forEach(file -> {
//			try {
//				extractpdf(file);
//			}  catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});

		Dictionary dic = new Dictionary("userdata", DictionaryUtil.loadUnrankedDictionary("userdata.txt"), false);
		String revisedpw = Generator.generatePassphrase("l", 3, dic);
		tfCount3.setText(revisedpw);

		// InputStream testInput;
		// try {
		// // Create a stream to hold the output
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// PrintStream ps = new PrintStream(baos);
		// // IMPORTANT: Save the old System.out!
		// PrintStream old = System.out;
		// // Tell Java to use your special stream
		// System.setOut(ps);
		//
		// testInput = new ByteArrayInputStream("g".getBytes("UTF-8"));
		// System.setIn( testInput );
		//
		//
		// // Put things back
		// System.out.flush();
		// System.setOut(old);
		// // Show what happened
		// String revisedpw = baos.toString();
		// tfCount3.setText(revisedpw);
		//
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// InputStream old = System.in;
		//
		// System.setIn( old );

	}

	public void extractpdf(File file) throws IOException {
		
		
		FileInputStream fis = new FileInputStream(file);
		PDFParser parser = new PDFParser(fis);
		
		parser.parse();
		
		COSDocument cosDoc = parser.getDocument();
		
		PDDocument pdDoc = new PDDocument(cosDoc);
		
		PDFTextStripper strip = new PDFTextStripper();
		
		String data = strip.getText(pdDoc);
		
		System.out.println(data);
		
		
	      pdDoc.close();
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