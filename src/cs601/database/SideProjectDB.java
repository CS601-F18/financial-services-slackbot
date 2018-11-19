package cs601.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SideProjectDB {
	public static void main(String[] args) throws SQLException {
		String username  = "user05";
		String password  = "user05";
		String db  = "user05";

		try {
			// load driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (Exception e) {
			System.err.println("Can't find driver");
			System.exit(1);
		}

		// format "jdbc:mysql://[hostname][:port]/[dbname]"
		//note: if connecting through an ssh tunnel make sure to use 127.0.0.1 and
		//also to that the ports are set up correctly
		String urlString = "jdbc:mysql://127.0.0.1:3306/"+db;
		//Must set time zone explicitly in newer versions of mySQL.
		String timeZoneSettings = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		Connection con = DriverManager.getConnection(urlString+timeZoneSettings,
				username,
				password);
		
		//create a statement object
		String selectStmt = "SELECT * FROM customers"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);


		ResultSet result = stmt.executeQuery();
		//iterate over the ResultSet
		int incrementId = 0;
		while (result.next()) {
			//for each result, get the value of the columns name and id
			String nameres = result.getString("name");
			int idres = result.getInt("id");
			System.out.printf("name: %s id: %d\n", nameres, idres);
			incrementId = idres;
		}
		incrementId +=1;
		String name = "Test E 2";

		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO events (name, id) VALUES (?, ?)");
		updateStmt.setString(1, name);
		updateStmt.setInt(2, incrementId);
		updateStmt.execute();	

		//print the updated table

		System.out.println("\n***\n");

		//print the updated table
		result = stmt.executeQuery (
				"SELECT * " + 
				"FROM events;");
		while (result.next()) {
			String nameres = result.getString("name");
			int idres = result.getInt("id");
			System.out.printf("name: %s id: %d\n", nameres, idres);
		}

		con.close();
	}
}
