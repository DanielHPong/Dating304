package pkg304models;

import java.sql.SQLException;
import java.util.List;

import pkg304data.Personality;
import pkg304models.models.CustomerModel;
import pkg304models.models.PersonalityModel;

public class entrypoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ModelManager mManager = ModelManager.getInstance();

			CustomerModel cModel = (CustomerModel) mManager.getModel(Table.CUSTOMER);
			System.out.println("creation code: " + cModel.createCustomer("Ed Knorr", "edk@gmail.com", 3));
			System.out.println("id from mail: " + cModel.getIdFromEmail("edk@gmail.com"));
			
			mManager.close();
		} catch (SQLException e) {
			System.out.println("ERROR: failed with message: " + e.getMessage());
		}
	}

}
