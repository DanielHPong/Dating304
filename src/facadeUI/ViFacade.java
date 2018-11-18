package facadeUI;

public class ViFacade implements DaFacade {
	private LogInManager LogInMan;
	private UserManager UserMan;
	private Viewer viewer;
	
	ViFacade() {
		LogInMan = LogInManager.getInstance();
		UserMan = UserManager.getInstance();
		viewer = Viewer.getInstance();
	}

	@Override
	public void login(String id) {
		LogInMan.login(id);
	}
	
	@Override
	public void logout() {
		LogInMan.logout();
	}

	@Override
	public void getMatch() {
		UserMan.getMatch();
	}

	@Override
	public void getMessage(String id) {
		UserMan.getMessage(id);
	}

	@Override
	public void sendMessage(String id, String msg) {
		UserMan.sendMessage(id, msg);
	}

	@Override
	public void buyPrem(String type, String cardType, String cardNo, String address) {
		UserMan.buyPrem(type, cardType, cardNo, address);
	}

	@Override
	public void cancelPrem(String type) {
		UserMan.cancelPrem(type);
	}

	@Override
	public void uploadImage(String url) {
		UserMan.uploadImage(url);
	}

	@Override
	public void deleteImage(String url) {
		UserMan.deleteImage(url);
	}
	
	@Override
	public void getImage() {
		UserMan.getImage();
	}

	@Override
	public void signUp(String email, String name, int pID) {
		LogInMan.signUp(email, name, pID);
	}

	@Override
	public void deactivate() {
		LogInMan.deactivate();
	}

	@Override
	public void viewPaymentInfo() {
		UserMan.viewPaymentInfo();
	}

	@Override
	public void showUserStats() {
		viewer.showUserStats();
	}

	@Override
	public void showBuyRecords() {
		viewer.showBuyRecords();
	}
	
	@Override
	// Display all available premium options
	public void viewPrem() {
		viewer.viewPrem();
	}
	
	@Override
	// Display all premium this user has
	public void myPremiums() {
		UserMan.myPremiums();
	}


}
