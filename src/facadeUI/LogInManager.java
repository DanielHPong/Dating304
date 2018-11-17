package facadeUI;
import pkg304models.ModelManager;
import pkg304models.Table;
import pkg304models.models.CustomerModel;
import pkg304ui.UIUpdater;
import pkg304data.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogInManager {
	// attributes
	private static LogInManager instance = null;
	private int uid;
	private boolean isLoggedOn;
	private ModelManager modelMan;
	private CustomerModel cModel;

	// Constructor
	private LogInManager() {
		uid = 0;
		isLoggedOn = false;
		try {
			modelMan = ModelManager.getInstance();
		} catch (SQLException e) {
			UIUpdater.error("Failed at LogInManager creation: "+ e.getMessage());
		}
		try {
			cModel = (CustomerModel) modelMan.getModel(Table.CUSTOMER);
		} catch (SQLException e) {
			UIUpdater.error("Failed at LogInManager creation: "+ e.getMessage());
		}
	}

	// getInstance
	public static LogInManager getInstance() {
		if (instance == null) {
			instance = new LogInManager();
		}
		return instance;
	}

	// Methods

	// Login Method
	public void login(String id) {
		// TODO
		// if isLoggedOn = true -> error
		if (isLoggedOn) {
			// error handle
			UIUpdater.error("You are already logged on. Logout and try again.");
		} else {
			// verify with DB and get UID
			int userID;
			try {
				userID = cModel.getIdFromEmail(id);
				uid = userID;
				isLoggedOn = true;
				// get the matches
				List<Customer> matched = cModel.getMatchedCustomers(uid);
				ArrayList<String> matches = new ArrayList<String>();
				for (Customer match : matched) {
					String s = "Name: " + match.getName() + "\n" + "uid: " + match.getCustomerId();
					matches.add(s);
				}
				UIUpdater.login(id, matches);
			} catch (SQLException e) {
				UIUpdater.error("Error logging in: "+ e.getMessage());
			}
		}
	}

	// SignUp Method
	public void signUp(String email, String name, int pID) {
		if (isLoggedOn) {
			UIUpdater.error("Please logout before signing up!");
		} else {
			// SignUp Process
			try {
				cModel.createCustomer(email, name, pID);
				// Auto SignOn
				login(email);
			} catch (SQLException e){
				UIUpdater.error("Error occured while creating your account: "+ e.getMessage());
			}
		}
	}

	// Deactivate Method
	public void deactivate() {
		// if not isLoggedOn, error
		if (!isLoggedOn) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			try {
				cModel.deactivateCustomer(uid);
				UIUpdater.error("You are now deactivated.");
			} catch (SQLException e) {
				UIUpdater.error("Error: failed to deactivate:" + e.getMessage());
			}
		}
	}

	public void logout() {
		uid = 0;
		isLoggedOn = false;
		UIUpdater.error("You are now logged out.");
	}

	// Getters

	public int getUID() {
		return uid;
	}

	public boolean isLoggedOn() {
		return isLoggedOn;
	}
}
