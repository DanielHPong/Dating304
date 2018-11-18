package facadeUI;

public class LogInManager {
	// attributes
	private static LogInManager instance = null;
	private int uid;
	private boolean isLoggedOn;

	// Constructor
	private LogInManager() {
		uid = 0;
		isLoggedOn = false;
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
		} else {
			// verify with DB and get UID
			// uid = DBUID
			// on error = display error to UI
			uid = 0;
			isLoggedOn = true;
		}
	}

	// SignUp Method
	public void signUp(String email, String name, int pid) {
		// TODO
		if (isLoggedOn) {
			// Error: please logout before you sign up
		}

		// SignUp Process - this should return uid

		// Auto SignIn After signing up
		login("id");
	}

	// Deactivate Method
	public void deactivate() {
		// TODO
		// if not isLoggedOn, error
		if (!isLoggedOn) {
			// Error: you are not loged in.
		}
		
		// set this user's isActive in database to false (from uid)
		// and display that it's done
	}

	public void logout() {
		uid = 0;
		isLoggedOn = false;
		// update UI to display "You are now loged out."
		// TODO
	}

	// Getters

	public int getUID() {
		return uid;
	}

	public boolean isLoggedOn() {
		return isLoggedOn;
	}
}
