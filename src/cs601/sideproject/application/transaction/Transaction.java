package cs601.sideproject.application.transaction;

/**
 * Object for transactions
 * @author nkebbas
 *
 */
public class Transaction {
	private int id;
	private String userId;
	private float value;
	private String description;
	private String operation;
	
	public Transaction(String userId, float value, String operation, String description) {
		this.userId = userId;
		this.value = value;
		this.operation = operation;
		this.description = description;
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

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
}
