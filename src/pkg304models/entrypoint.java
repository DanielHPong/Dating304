package pkg304models;

import java.sql.SQLException;
import pkg304models.models.CustomerModel;
import pkg304models.models.MatchModel;
import pkg304ui.UIMain;

public class entrypoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ModelManager mManager = ModelManager.getInstance();

			CustomerModel iModel = (CustomerModel) mManager.getModel(Table.CUSTOMER);
			// System.out.println("creating customer: " + iModel.createCustomer("ts@gmail.com", "Taylor Swift", "F", 3));
			int id = iModel.getIdFromEmail("ts@gmail.com");
			System.out.println("id from email: " + id);
			System.out.println("info from id: " + iModel.getCustomerInfoById(id));
			
			MatchModel mModel = (MatchModel) mManager.getModel(Table.MATCH);
			// System.out.println("createMatches returned: " + mModel.createMatches(id, 3, "F"));
			// System.out.println("getNumBestMatches returned: " + mModel.getNumBestMatches(id));
			mManager.close();
                        UIMain.main(args);
		} catch (SQLException e) {
			System.out.println("ERROR: failed with message: " + e.getMessage());
		}
	}

}
