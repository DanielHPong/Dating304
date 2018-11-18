package facadeUI;
import pkg304models.*;
import pkg304models.models.*;

import java.sql.SQLException;
import java.util.List;

import pkg304data.*;
import pkg304ui.UIUpdater;

public class Viewer {
	// Attributes
	private static Viewer instance = null;
	private LogInManager LoginMan;
	private ModelManager modelMan;
	
	// Constructor
	private Viewer() {
		LoginMan = LogInManager.getInstance();
		try {
			modelMan = ModelManager.getInstance();
		} catch (SQLException e) {
			UIUpdater.error("Failed at viewer init: " + e.getMessage());
		}
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
		try {
			PurchaseModel pModel = (PurchaseModel) modelMan.getModel(Table.PURCHASE);
			List<String> toDisplay = pModel.getLedger();
			UIUpdater.getMessages(toDisplay);
		} catch (SQLException e) {
			UIUpdater.error("Failed retrieving purchase records: " + e.getMessage());
		}
	}
	
	// Returns all available premium packages
	public List<PremiumPackage> viewPrem() throws Exception{
		PremiumPackageModel pModel;
		List<PremiumPackage> packages;
		try {
			pModel = (PremiumPackageModel) modelMan.getModel(Table.PREMIUM_PACKAGE);
			packages = pModel.getAll();
		} catch (Exception e) {
			UIUpdater.error("Failed retrieving premium packages: " + e.getMessage());
			throw new Error("Failed retrieving premium packages.");
		}
		
		return packages;
	}
	// Getter
}
