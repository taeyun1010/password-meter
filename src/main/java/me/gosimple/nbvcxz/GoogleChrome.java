package me.gosimple.nbvcxz;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.omg.CORBA.Environment;
import org.sqlite.SQLiteConnection;

public class GoogleChrome {
	// public List<URL> URLs = new List<URL>();
	public static void GetHistory() {
		// // Get Current Users App Data
		// String documentsFolder = Environment.GetFolderPath
		// (Environment.SpecialFolder.ApplicationData);
		// String[] tempstr = documentsFolder.Split('\\');
		// String tempstr1 = "";
		// documentsFolder += "\\Google\\Chrome\\User Data\\Default";
		// if (tempstr[tempstr.Length - 1] != "Local")
		// {
		// for (int i = 0; i < tempstr.Length - 1; i++)
		// {
		// tempstr1 += tempstr[i] + "\\";
		// }
		// documentsFolder = tempstr1 + "Local\\Google\\Chrome\\User Data\\Default";
		// }

		String path = System.getProperty("user.home");
		path = path + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default";

		//System.out.println("path = " + path);

		// Check if directory exists
		if (Files.exists(Paths.get(path))) {
			try {
				SQLiteConnection connection = new SQLiteConnection(path, "History");
				System.out.println(connection.getSchema());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// System.out.println("directory does not exist");
		}

	}

	// IEnumerable<URL> ExtractUserHistory(String folder)
	// {
	// // Get User history info
	// DataTable historyDT = ExtractFromTable("urls", folder);
	//
	// // Get visit Time/Data info
	// DataTable visitsDT = ExtractFromTable("visits",
	// folder);
	//
	// // Loop each history entry
	// for (DataRow row : historyDT.Rows)
	// {
	//
	// // Obtain URL and Title strings
	// String url = row["url"].ToString();
	// String title = row["title"].ToString();
	//
	// // Create new Entry
	// URL u = new URL(url.Replace('\'', ' '),
	// title.Replace('\'', ' '),
	// "Google Chrome");
	//
	// // Add entry to list
	// URLs.Add(u);
	// }
	// // Clear URL History
	// DeleteFromTable("urls", folder);
	// DeleteFromTable("visits", folder);
	//
	// return URLs;
	// }
	// void DeleteFromTable(String table, String folder)
	// {
	// SQLiteConnection sql_con;
	// SQLiteCommand sql_cmd;
	//
	// // FireFox database file
	// String dbPath = folder + "\\History";
	//
	// // If file exists
	// if (File.Exists(dbPath))
	// {
	// // Data connection
	// sql_con = new SQLiteConnection("Data Source=" + dbPath +
	// ";Version=3;New=False;Compress=True;");
	//
	// // Open the Conn
	// sql_con.Open();
	//
	// // Delete Query
	// String CommandText = "delete from " + table;
	//
	// // Create command
	// sql_cmd = new SQLiteCommand(CommandText, sql_con);
	//
	// sql_cmd.ExecuteNonQuery();
	//
	// // Clean up
	// sql_con.Close();
	// }
	// }
	//
	// DataTable ExtractFromTable(String table, String folder)
	// {
	// SQLiteConnection sql_con;
	// SQLiteCommand sql_cmd;
	// SQLiteDataAdapter DB;
	// DataTable DT = new DataTable();
	//
	// // FireFox database file
	// String dbPath = folder + "\\History";
	//
	// // If file exists
	// if (File.Exists(dbPath))
	// {
	// // Data connection
	// sql_con = new SQLiteConnection("Data Source=" + dbPath +
	// ";Version=3;New=False;Compress=True;");
	//
	// // Open the Connection
	// sql_con.Open();
	// sql_cmd = sql_con.CreateCommand();
	//
	// // Select Query
	// String CommandText = "select * from " + table;
	//
	// // Populate Data Table
	// DB = new SQLiteDataAdapter(CommandText, sql_con);
	// DB.Fill(DT);
	//
	// // Clean up
	// sql_con.Close();
	// }
	// return DT;
	// }
}