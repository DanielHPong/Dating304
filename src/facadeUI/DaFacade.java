package facadeUI;

public interface DaFacade {
	public void login(String id);
	public void getMatch();
	public void getMessage(String id);
	public void sendMessage(String id, String msg);
	public void buyPrem(String type);
	public void cancelPrem();
	public void uploadImage(String url);
	public void deleteImage(String url);
	public void signUp(String email, String name, int pID);
	public void deactivate();
	public void viewPaymentInfo();
	public void showUserStats();
	public void showBuyRecords();
}
