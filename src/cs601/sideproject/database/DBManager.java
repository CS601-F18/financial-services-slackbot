package cs601.sideproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs601.sideproject.application.Stock;



/**
 * Use the singleton here.
 * 
 * Must ensure isolation.
 * 	- Easy (inefficient) way is to make all methods synchronized
 *	- Alternatively, Transactions can be interleaved because they're 
 *	not touching any of the same data.
 * @author nkebbas
 *
 */
public class DBManager {
	Connection con;

	public User getUser(int id) {
		return null;	
	}
	
	/**
	 * Create account and give password.
	 * Generate a salt. Hash password and store it.
	 * When you log in, give user and password. 
	 * Get username, get salt out of row, add it to username,
	 * then hash that. If they're the same, allow access. If not, deny.
	 * Implement something like https://www.baeldung.com/java-password-hashing
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public void createUser(User user, String tableName) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (name, slack_id, access_token) VALUES (?, ?, ?)");
		updateStmt.setString(1, user.getName());
		updateStmt.setString(2, user.getId());
		updateStmt.setString(3, user.getAccessToken());
		updateStmt.execute();	
	}
	
	public void createTransaction(Transaction transaction, String tableName) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (value, operation, user_id, description) VALUES (?, ?, ?, ?)");
		updateStmt.setFloat(1, transaction.getValue());
		updateStmt.setString(2, transaction.getOperation());
		updateStmt.setString(3, transaction.getUserId());
		updateStmt.setString(4, transaction.getDescription());
		updateStmt.execute();	
	}
	
	public void createStock(Stock stock, String tableName) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (user_id, name) VALUES (?, ?)");
		updateStmt.setString(1, stock.getUserId());
		updateStmt.setString(2, stock.getName());
		updateStmt.execute();	
	}
	
	public String returnSuggestions(String userId, String tableName) throws SQLException {
		String results = "";
		PreparedStatement updateStmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE user_id=" + "\'" + userId +"\'");
		ResultSet result = updateStmt.executeQuery();
		while (result.next()) {
			results += result.getString("name");
			results += "\n";
		}
		return results;
	}
	
	public void testJoin(String field1, String field2, String tableName, String command) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement("SELECT " + field1 + ", " + field2 + " FROM " + tableName + " " + command);
		//print the updated table
		ResultSet result = updateStmt.executeQuery();
		
		while (result.next()) {
			String nameres = result.getString("name");
			String emailres = result.getString("email");
			//int idres = result.getInt("id");
			System.out.printf("name: %s email: %s \n" , nameres, emailres);
		}
	}
	
	public Event getEvent() {
		return null;
	}
	
/**
 * Take as input username and password, return User object data
 * @param username
 * @param password
 * @return
 */
	public User authenticate(String username, String password) {
		return null;
	}
	
	/* Sign user in, don't create user. Then we can get all the transactions
	 * This is broken because i shouldnt use accesstoken as cookie because
	 * that expires quickly
	 * 
	 *  */
	
	public boolean authenticate(String slackId) throws SQLException {
		String selectStmt = "SELECT * FROM spusers";
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String dbSlackId= result.getString("slack_id");
			System.out.println("db slack id: " + dbSlackId);
			System.out.println("slackId" + slackId);
			if (slackId.equals(dbSlackId) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean signIn(String userId) throws SQLException {
		String selectStmt = "SELECT * FROM spusers";
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String dbUserId= result.getString("slack_id");
			if (userId.equals(dbUserId) ) {
				return true;
			}
		}
		return false;
	}
	
	public String getAllUserTransactions(String userId) throws SQLException {
		System.out.println ("User id:" + userId); 
		String output ="";
		String selectStmt = "SELECT * FROM transactions"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String id = result.getString("user_id");
			if (userId.equals(id)) {
				String operation = result.getString("operation");
				String value = result.getString("value");
				String description = result.getString("description");
			
				if (operation.contains("charge") || operation.contains("buy")) {
					output += "<p>Charge $";
				} else if (operation.contains("credit") || operation.contains("sell")) {
					output += "Credit $";
				}
				output+= value;
				output+= " for " + description; 
				output += "</p>";
			}
		}
		if (output.equals("")) {
			output += "<p>No transactions recorded. Please record some in slack.</p>";
		}
		return output;
	}
	
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
}
