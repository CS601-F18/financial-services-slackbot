package cs601.sideproject.database;

/**
 * Database that can be called by the getInstance method
 * Use the singleton here.
 * 
 * @author nkebbas
 * */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cs601.sideproject.database.DBConstants;

public final class Database {
	private static Database INSTANCE;
	private DBManager dbm;
	private Connection con;

	public Database() {
		dbm = new DBManager();
	}
	
	/* TODO: Make threadsafe without using synchronized */
	public synchronized static Database getInstance() {
		
		if(INSTANCE == null) {
            INSTANCE = new Database();
        }
		
        return INSTANCE;
	}
	
	public void connect() {
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
		String urlString = DBConstants.DATABASE_URL+DBConstants.DB;
		//Must set time zone explicitly in newer versions of mySQL.
		try {
			con = DriverManager.getConnection(urlString+DBConstants.DATABASE_TIMEZONE_SETTINGS,
					DBConstants.USERNAME,
					DBConstants.PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dbm.setCon(con);
	}
	
	public DBManager getDBManager() {
		return this.dbm;
	}
	
	public Connection getConnection() {
		return this.con;
	}
}
