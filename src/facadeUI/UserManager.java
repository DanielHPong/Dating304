package facadeUI;
import facadeUI.LogInManager;

public class UserManager {
	// Attributes
	private static UserManager instance = null;
	private LogInManager LoginMan;
	
	// Constructor
	private UserManager() {
		LoginMan = LogInManager.getInstance();
	}
	
	// GetInstance
	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	// Methods
	
	public void getMatch() {
		// TODO
		if (!LoginMan.isLoggedOn()) {
			// Error you are not logged in
		} else {
			int uid = LoginMan.getUID();
			// return array of name and uid and display
		}
	}
	
	public void getMessage(String id) {
		// TODO
		// id is from user 2
		if (!LoginMan.isLoggedOn()) {
			// Error you are not logged in
		} else {
			int uid = LoginMan.getUID();
		}
	}
	
	public void sendMessage(String id, String msg) {
		// TODO
		if (!LoginMan.isLoggedOn()) {
			// Error you are not logged in
		} else {
			int uid = LoginMan.getUID();
		}
	}
	
	public void buyPrem(String type) {
		//TODO
		if (!LoginMan.isLoggedOn()) {
			// Error you are not logged in
		} else {
			int uid = LoginMan.getUID();
		}
	}
	
	public void cancelPrem(String type) {
		// TODO
		if (!LoginMan.isLoggedOn()) {
			// Error you are not logged in
		} else {
			int uid = LoginMan.getUID();
		}
	}
	
	public void uploadImage(String url) {
		// TODO
		if (!LoginMan.isLoggedOn()) {
			// Error you are not logged in
		} else {
			int uid = LoginMan.getUID();
		}
	}
	
	public void deleteImage(String url) {
		// TODO
		if (!LoginMan.isLoggedOn()) {
			// Error you are not logged in
		} else {
			int uid = LoginMan.getUID();
		}
	}
	
	public void viewPaymentInfo() {
		// TODO
		if (!LoginMan.isLoggedOn()) {
			// Error you are not logged in
		} else {
			int uid = LoginMan.getUID();
		}
	}
	
	public void myPremiums() {
		// TODO
		// Return all premium this user has
	}
	
	
	// Getter
}
