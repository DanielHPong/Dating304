package pkg304models;

import java.sql.SQLException;
import java.util.List;

import pkg304data.Personality;
import pkg304models.models.CustomerModel;
import pkg304models.models.ImageModel;
import pkg304models.models.PersonalityModel;

public class entrypoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ModelManager mManager = ModelManager.getInstance();

			ImageModel iModel = (ImageModel) mManager.getModel(Table.IMAGE_LOG);
			System.out.println("creating image: " + iModel.createImage(11, "yolo.com"));
			System.out.println("creating image: " + iModel.createImage(11, "woohoo.com"));
			System.out.println("11's images: " + iModel.getUserImage(11));
			System.out.println("Deleting image: " + iModel.deleteImage(11, "woohoo.com"));
			
			mManager.close();
		} catch (SQLException e) {
			System.out.println("ERROR: failed with message: " + e.getMessage());
		}
	}

}
