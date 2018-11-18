package pkg304data;

public class PaymentInfo {
	private int infoId;
	private String cardNo;
	private String cardType;
	private String address;
	
	public PaymentInfo(int infoId, String cardNo, String cardType, String address) {
		this.infoId = infoId;
		this.cardNo = cardNo;
		this.cardType = cardType;
		this.address = address;
	}

	public int getInfoId() {
		return infoId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public String getAddress() {
		return address;
	}
	
}