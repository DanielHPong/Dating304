package facadeUI;

public class Viewer {
	// Attributes
	private static Viewer instance = null;
	
	// Constructor
	private Viewer() {
		
	}
	// GetInstance
	public static Viewer getInstance() {
		if (instance == null) {
			instance = new Viewer();
		}
		return instance;
	}
	// Methods
	public void showUserStats() {
		// TODO
	}
	
	public void showBuyRecords() {
		// TODO
	}
	// Getter
}
