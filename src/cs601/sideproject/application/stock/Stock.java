package cs601.sideproject.application.stock;

/**
 * Stock object
 * @author nkebbas
 *
 */
public class Stock {
	private int id;
	private String userId;
	private String name;
	private int shares;
	

	public Stock(String userId, String name) {
		this.userId = userId;
		this.setName(name);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}
	
	public String toString() {
		return this.userId + " " + this.userId;
	}
}
