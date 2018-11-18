package facadeUI;

public interface DaFacade {
	public void login(String id);
	public void logout();
	public void getMatch();
	public void getMessage(String id);
	public void sendMessage(String id, String msg);
	public void buyPrem(String type, String cardType, String cardNo, String address);
	public void cancelPrem(String type);
	public void uploadImage(String url);
	public void deleteImage(String url);
	public void getImage();
	public void signUp(String email, String name, int pID);
	public void deactivate();
	public void viewPaymentInfo();
	public void showUserStats();
	public void showBuyRecords();
}