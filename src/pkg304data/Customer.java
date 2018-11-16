package pkg304data;

public class Customer {
	private int customerId;
	private int personalityId;
	private String email;
	private String name;
	private boolean isActive;
	
	public Customer(int cid, String email, String name, boolean isActive, int pid) {
		this.customerId = cid;
		this.personalityId = pid;
		this.email = email;
		this.name = name;
		this.isActive = isActive;
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getPersonalityId() {
		return personalityId;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return isActive;
	}
}
