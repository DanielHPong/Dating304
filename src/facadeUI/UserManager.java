package facadeUI;
import pkg304ui.UIUpdater;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pkg304models.*;
import pkg304models.models.*;
import pkg304data.*;

public class UserManager {
	// Attributes
	private static UserManager instance = null;
	private LogInManager LoginMan;
	private ModelManager modelMan;
	private ContentModel cModel;
	private ImageModel iModel;
	
	// Constructor
	private UserManager() {
		LoginMan = LogInManager.getInstance();
		try {
			modelMan = ModelManager.getInstance();
			cModel = (ContentModel) modelMan.getModel(Table.CONTENT);
			iModel = (ImageModel) modelMan.getModel(Table.IMAGE_LOG);
		} catch (SQLException e) {
			UIUpdater.error("UserManager init failed: " + e.getMessage());
		}
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
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			// return array of name and uid and display
			CustomerModel cuModel;
			try {
				cuModel = (CustomerModel) modelMan.getModel(Table.CUSTOMER);
				// get the matches
				List<Customer> matched = cuModel.getMatchedCustomers(uid);
				ArrayList<String> matches = new ArrayList<String>();
				for (Customer match : matched) {
					String s = "Name: " + match.getName() + "\n" + "uid: " + match.getCustomerId();
					matches.add(s);
				}
				UIUpdater.getMatches(matches);
			} catch (SQLException e) {
				UIUpdater.error("Failed at retrieving matches: "+ e.getMessage());
			}
		}
	}
	
	public void getMessage(String id) {
		// id is from user 2
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			try {
				List<Content> contents = cModel.getConversation(uid, Integer.parseInt(id));
				ArrayList<String> messages = new ArrayList<String>();
				for (Content content : contents) {
					String s = "Sender: " + content.getSenderId() + "\n" + content.getMessage();
					messages.add(s);
				}
				UIUpdater.getMessages(messages);
			} catch (SQLException e) {
				UIUpdater.error("Failed at retrieving messages: " + e.getMessage());
			}
		}
	}
	
	public void sendMessage(String id, String msg) {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			try {
				cModel.createContent(uid, Integer.parseInt(id), msg);
				this.getMessage(id);
			} catch (SQLException e) {
				UIUpdater.error("Failed sending message: " + e.getMessage());
			}
		}
	}
	
	public void buyPrem(String pName) {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			try {
				PremiumPackageModel pModel = (PremiumPackageModel) modelMan.getModel(Table.PREMIUM_PACKAGE);
				pModel.createPurchase(uid, pName);
				UIUpdater.setText("You can now enjoy our premium benefits!");
			} catch (Exception e) {
				UIUpdater.error("Failed to buy premium: " + e.getMessage());
			}
		}
	}
	
	// Deprecated
	public void cancelPrem(String type) {
		// TODO
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
		}
	}
	
	public void uploadImage(String url) {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			try {
				iModel.createImage(uid, url);
				UIUpdater.setText("Image uploaded!");
			} catch (SQLException e) {
				UIUpdater.error("Failed to upload image: " + e.getMessage());
			}
		}
	}
	
	public void deleteImage(String url) {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			int i;
			try {
				i = iModel.deleteImage(uid, url);
				if (i == 0) {
					UIUpdater.error("Failed to delete image: image does not exist in database");
				} else {
					UIUpdater.setText("Image deleted!");
				}
			} catch (SQLException e) {
				UIUpdater.error("Failed to delete image: " + e.getMessage());
			}
		}
	}
	
	public ArrayList<String> getImage() {
        ArrayList<String> images = new ArrayList();
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
            return images;
		} else {
			int uid = LoginMan.getUID();
			List<Image> imgs;
			try {
				imgs = iModel.getUserImage(uid);
				for (Image img : imgs) {
					images.add(img.getUrl());
				}
				return images;
			} catch (SQLException e) {
				UIUpdater.error("Failed to retrieve images: " + e.getMessage());
				return images;
			}
		}
	}
        
        public void viewImage() {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			List<Image> imgs;
			ArrayList<String> images = new ArrayList();
			try {
				imgs = iModel.getUserImage(uid);
				for (Image img : imgs) {
					images.add(img.getUrl());
				}
				UIUpdater.getMessages(images);
			} catch (SQLException e) {
				UIUpdater.error("Failed to retrieve images: " + e.getMessage());
			}
		}
	}
	
	// Creates PaymentInfo table for this user
	public void addPaymentInfo(String cardType, String cardNo, String address) {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			try {
				PaymentInfoModel pModel = (PaymentInfoModel) modelMan.getModel(Table.PAYMENT_INFO);
				int n = pModel.createPaymentInfo(uid, cardType, cardNo, address);
				if (n == 3) {
					UIUpdater.setText("Your payment info has been created!");
				} else {
					UIUpdater.error("You can only have one paymentInfo.");
				}
			} catch (SQLException e) {
				UIUpdater.error("Failed creating paymentInfo: " + e.getMessage());
			}
		}
	}
	
	// Deletes paymentInfo table 
	public void deletePaymentInfo() {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			try {
				PaymentInfoModel pModel = (PaymentInfoModel) modelMan.getModel(Table.PAYMENT_INFO);
				int n = pModel.deletePaymentInfo(uid);
				if (n == 0) {
					UIUpdater.error("You do not have paymentInfo!");
				} else {
					UIUpdater.setText("Your payment info has been deleted!");
				}
			} catch (SQLException e) {
				UIUpdater.error("Failed deleting paymentInfo: " + e.getMessage());
			}
		}
	}
	
	// Returns PaymentInfo object for this user.
	public PaymentInfo viewPaymentInfo() throws Exception{
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
			throw new Exception("You are not logged in!");
		}
		int uid = LoginMan.getUID();
		PaymentInfo pInfo = null;
		try {
			PaymentInfoModel pModel = (PaymentInfoModel) modelMan.getModel(Table.PAYMENT_INFO);
			pInfo = pModel.getPaymentInfoById(uid);
		} catch (SQLException e) {
			// UIUpdater.error("Failed to retrieve payment info: " + e.getMessage());
			return pInfo;
		}
		
		return pInfo;
	}
	
	// Returns List of PremiumPackage object for this user.
	public List<PremiumPackage> myPremiums() throws Exception{
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: you are not logged in.");
			throw new Exception("You are not logged in!");
		}
		int uid = LoginMan.getUID();
		List<PremiumPackage> packages;
		try {
			PremiumPackageModel pModel = (PremiumPackageModel) modelMan.getModel(Table.PREMIUM_PACKAGE);
			packages = pModel.getPremByID(uid);
		} catch (SQLException e) {
			UIUpdater.error("Failed to get premium package: " + e.getMessage());
			throw new Exception("Failed to get premium package.");
		}
		
		return packages;
	}
	
	// Displays # broken matches per personality type for this user.
	public void getBrokenMatchesByType() {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: you are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			List<Object> res;
			List<String> toDisplay = new ArrayList();
			try {
				MatchModel mModel = (MatchModel) modelMan.getModel(Table.MATCH);
				res = mModel.getPersonalityToBrokenMatchCount(uid);
				int n = res.size()/2;
				for (int i = 0; i < n; i++) {
					int i1 = i * 2;
					int i2 = i1 + 1;
					String s = "Type: " + res.get(i1) + "    " + "# of Broken Hearts: " + res.get(i2);
					toDisplay.add(s);
				}
				UIUpdater.getMessages(toDisplay);
			} catch (SQLException e) {
				UIUpdater.error("Failed to display broken hearts: " + e.getMessage());
			}
		}
	}
	
	// Deactivates match of given user id.
	public void deactivateMatch(String id) {
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: you are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			try {
				MatchModel mModel = (MatchModel) modelMan.getModel(Table.MATCH);
				int res = mModel.deactivateMatch(uid, Integer.parseInt(id));
				if (res == 1) {
					UIUpdater.setText("You have ghosted user: " + id + ", douche.");
				} else {
					UIUpdater.error("Looks like you've already been ghosted!");
				}
			} catch (SQLException e) {
				UIUpdater.error("Failed ghosting user: " + e.getMessage());
			}
		}
	}
}
