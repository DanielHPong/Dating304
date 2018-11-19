package pkg304models;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import pkg304data.Content;
import pkg304data.Customer;
import pkg304data.Image;
import pkg304data.Personality;
import pkg304models.models.ContentModel;
import pkg304models.models.CustomerModel;
import pkg304models.models.ImageModel;
import pkg304models.models.MatchModel;
import pkg304models.models.PersonalityModel;
import pkg304ui.UIMain;

public class entrypoint {

	public static void main(String[] args) {
		// INSTRUCTIONS: Run create_table.sql script before running this
		try {
			ModelManager mManager = ModelManager.getInstance();

			// PersonalityModel Test
			PersonalityModel pModel = (PersonalityModel) mManager.getModel(Table.PERSONALITY);
			List<Personality> pers = pModel.getAll();
			desc("Available Personalities");
			String pt1 = pers.get(0).getType();
			String pt2 = pers.get(1).getType();
			String pt3 = pers.get(2).getType();
			String pt4 = pers.get(3).getType();
			System.out.println(Arrays.asList(pt1, pt2, pt3, pt4));
			
			// CustomerModel Test
			CustomerModel cModel = (CustomerModel) mManager.getModel(Table.CUSTOMER);
			desc("Creating Taylor Swift customer");
			System.out.println(cModel.createCustomer("ts@gmail.com", "Taylor Swift", "F", 3));
			desc("Getting id by email and displaying Taylor's info by her id - Should be 8");
			int id = cModel.getIdFromEmail("ts@gmail.com");
			System.out.println("id: " + id + ", info from id: " + cModel.getCustomerInfoById(id));
			desc("Getting Taylor's matches - Should be Jason and Ed");
			List<Customer> matched = cModel.getMatchedCustomers(id);
			System.out.println(Arrays.asList(matched.get(0).getName(), matched.get(1).getName()));
			
			// MatchModel Test
			MatchModel mModel = (MatchModel) mManager.getModel(Table.MATCH);
			desc("Unmatching Taylor from Jason and Ed");
			System.out.println(mModel.deactivateMatch(id, 2));
			System.out.println(mModel.deactivateMatch(id, 6));
			desc("Taylor's PersonalityBrokenMatchAnalysis - Should be 2, 5");
			System.out.println(mModel.getPersonalityToBrokenMatchCount(id));
			
			// ImageModel Test
			ImageModel iModel = (ImageModel) mManager.getModel(Table.IMAGE_LOG);
			desc("Giving Taylor some images");
			System.out.println(iModel.createImage(id, "tdabest.com/img1") + iModel.createImage(id, "tdabest.com/img2"));
			desc("Getting Taylor's images: Should be tdabest.com/img1, tdabest.com/img2");
			List<Image> imgs = iModel.getUserImage(id);
			System.out.println(Arrays.asList(imgs.get(0).getUrl(), imgs.get(1).getUrl()));
			desc("Deleting Taylor's images");
			System.out.println(iModel.deleteImage(id, "tdabest.com/img1") + iModel.deleteImage(id, "tdabest.com/img2"));
			
			// ContentModel Test
			ContentModel coModel = (ContentModel) mManager.getModel(Table.CONTENT);
			desc("Creating convo between Taylor and Ellen");
			System.out.println(
				coModel.createContent(id, 3, "Hi Ellen! I'm Taylor.") 
				+ coModel.createContent(3, id, "I like my hamster more than you!" )
				+ coModel.createContent(id, 3, ":( Ellen I'm sad.")
			);
			desc("Getting convo between Taylor and Ellen");
			List<Content> convo = coModel.getConversation(id, 3);
			System.out.println(Arrays.asList(convo.get(0).getMessage(), convo.get(1).getMessage(), convo.get(2).getMessage()));

			// Final
			desc("Deactivating Taylor");
			System.out.println(cModel.deactivateCustomer(id));
			
			mManager.close();
		} catch (SQLException e) {
			System.out.println("ERROR: failed with message: " + e.getMessage());
		}
	}

	public static void desc(String s) {
		System.out.println("\n(T) " + s);
	}
}
