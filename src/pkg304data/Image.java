package pkg304data;

public class Image {
	private int customerId;
	private String url;
	
	public Image(int customerId, String url) {
		this.customerId = customerId;
		this.url = url;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public String getUrl() {
		return url;
	}
}
