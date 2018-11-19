package cs601.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	/* Sign user in, don't create user. Then we can get all the transactions */
	public boolean authenticate(String accessToken) throws SQLException {
		String selectStmt = "SELECT * FROM spusers";
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		while (result.next()) {
			String dbToken= result.getString("access_token");
			if (accessToken.equals(dbToken) ) {
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
	
	public ArrayList<String> getAllUserTransactions(String userId) throws SQLException {
		System.out.println ("User id:" + userId); 
		String selectStmt = "SELECT * FROM transactions"; 
		PreparedStatement stmt = con.prepareStatement(selectStmt);
		ResultSet result = stmt.executeQuery();
		ArrayList<String> transactions = new ArrayList<String>();
		while (result.next()) {
			String id = result.getString("user_id");
			if (userId.equals(id)) {
				System.out.println(result);
			}
		}
		return null;
	}
	
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
}
