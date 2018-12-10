package cs601.sideproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs601.sideproject.application.events.Event;
import cs601.sideproject.application.stock.Stock;
import cs601.sideproject.application.transaction.Transaction;



/**
 *
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
		if (transaction.getOperation().contains("charge") || transaction.getOperation().contains("buy")) {
			updateStmt.setString(2, "charge");
		} else if (transaction.getOperation().contains("credit") || transaction.getOperation().contains("sell")) {
			updateStmt.setString(2, "credit");
		}
		updateStmt.setString(3, transaction.getUserId());
		updateStmt.setString(4, transaction.getDescription());
		updateStmt.execute();
	}
	
	public void createStockSearch(Stock stock, String tableName) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (user_id, name) VALUES (?, ?)");
		updateStmt.setString(1, stock.getUserId());
		updateStmt.setString(2, stock.getName());
		updateStmt.execute();	
	}
	
	public boolean checkForStock(String tableName, String name) throws SQLException {
		PreparedStatement userStmt;
		userStmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE name=" + "\'" + name + "\'");
		ResultSet userRs = userStmt.executeQuery();
		
		/* Check for no reults */
		userRs.beforeFirst();
		if (!userRs.next()) {
			return false;
		}
		return true;
	}
	
	public void updateStockTransaction(String tableName, String name, String operation, int shares) throws SQLException {
		PreparedStatement userStmt;
		PreparedStatement updateStmt;
		if (operation.equals("sell")) {
			updateStmt = con.prepareStatement("UPDATE " + tableName + " SET shares = shares - " + shares + " WHERE name=" + "\'" + name +"\'");
		} else {
			updateStmt = con.prepareStatement("UPDATE " + tableName + " SET shares = shares + " + shares + " WHERE name=" + "\'" + name +"\'");
		}
		userStmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE name=" + "\'" + name +"\'");
		updateStmt.execute();
	}
	
	public void createStockTransaction(Stock stock, String tableName) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement("INSERT INTO " + tableName + " (user_id, name, shares) VALUES (?, ?, ?)");
		updateStmt.setString(1, stock.getUserId());
		updateStmt.setString(2, stock.getName());
		updateStmt.setInt(3, stock.getShares());
		updateStmt.execute();	
	}
	
	
	public String getAllStockSuggestions(String userId, String tableName) throws SQLException {
		String results = "";
		PreparedStatement updateStmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE user_id=" + "\'" + userId +"\'");
		ResultSet result = updateStmt.executeQuery();
		while (result.next()) {
			results += result.getString("name");
			results += "\n";
		}
		return results;
	}
	
	public String getAllStocksOwned(String userId, String tableName) throws SQLException {
		String results = "";
		PreparedStatement updateStmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE user_id=" + "\'" + userId +"\'");
		ResultSet result = updateStmt.executeQuery();
		while (result.next()) {
			results += result.getString("name");
			results += ": ";
			results += result.getInt("shares");
			results += " shares";
			results += "\n";
		}
		return results;
	}
	
	public String getAllUserTransactions(String userId) throws SQLException {
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
				output+= " " + description; 
				output += "</p>";
			}
		}
		if (output.equals("")) {
			output += "<p>No transactions recorded. Please record some in slack.</p>";
		}
		return output;
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
	
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
}
