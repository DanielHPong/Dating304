package facadeUI;
import facadeUI.LogInManager;

public class ViFacade implements DaFacade {
	private LogInManager LogInMan;
	
	ViFacade() {
		// TODO
		LogInMan = LogInManager.getInstance();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void getMessage(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String id, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buyPrem(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelPrem() {
		// TODO Auto-generated method stub

	}

	@Override
	public void uploadImage(String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteImage(String url) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void showUserStats() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showBuyRecords() {
		// TODO Auto-generated method stub

	}

}
