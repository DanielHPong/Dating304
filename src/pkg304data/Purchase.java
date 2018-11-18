package pkg304data;

public class Purchase {
	private int customerId;
	private int infoId;
	private String packageName;
	private String purchaseTime;
	
	public Purchase(int customerId, int infoId, String packageName, String purchaseTime) {
		this.customerId = customerId;
		this.infoId = infoId;
		this.packageName = packageName;
		this.purchaseTime = purchaseTime;
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getInfoId() {
		return infoId;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getPurchaseTime() {
		return purchaseTime;
	}
}
