package cs601.sideproject.application;

public class Stock {
	private int id;
	private String userId;
	private String name;
	
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
	
	public String toString() {
		return this.userId + " " + this.userId;
	}
}
