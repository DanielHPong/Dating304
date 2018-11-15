package pkg304models;

import java.sql.SQLException;
import java.util.List;

import pkg304data.Personality;
import pkg304models.models.PersonalityModel;

public class entrypoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ModelManager mManager = ModelManager.getInstance();
			PersonalityModel pModel = (PersonalityModel) mManager.getModel(Table.PERSONALITY);
			List<Personality> result = pModel.getAll();
			
			System.out.println("Done!");
			System.out.println("Length of result is: " + result.size());
			System.out.println("First element is: " + result.get(0).getType());
			
			List<Personality> rtwo = pModel.getAll();
			
			System.out.println("Done2!");
			System.out.println("Length of rtwo is: " + rtwo.size());
			System.out.println("First element rtwo is: " + rtwo.get(0).getType());
			
			mManager.close();
		} catch (SQLException e) {
			System.out.println("ERROR: failed with message: " + e.getMessage());
		}
	}

}
