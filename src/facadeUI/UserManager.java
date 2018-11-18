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
		//TODO
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
		} else {
			int uid = LoginMan.getUID();
			// 1. retrieve paymentinfo
			// 2. make purchase after
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
			} catch (SQLException e) {
				UIUpdater.error("Failed to upload image: " + e.getMessage());
			} finally {
				UIUpdater.setText("Image uploaded!");
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
				UIUpdater.error("Failed to upload image: " + e.getMessage());
			}
		}
	}
	
	public void getImage() {
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
				UIUpdater.login("Your images", images);
			} catch (SQLException e) {
				UIUpdater.error("Failed to retrieve images: " + e.getMessage());
			}
		}
	}
	
	public void viewPaymentInfo() {
		// TODO
		if (!LoginMan.isLoggedOn()) {
			UIUpdater.error("Error: You are not logged in.");
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
