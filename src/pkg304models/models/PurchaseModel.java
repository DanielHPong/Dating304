package pkg304models.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pkg304data.PaymentInfo;
import pkg304data.Purchase;
import pkg304models.GenericModel;

public class PurchaseModel extends GenericModel<Purchase>{
	public PurchaseModel(Connection con) {
		super(con);
	}
	
	@Override
	public List<Purchase> getAll() throws SQLException {
		ResultSet rs = execQuerySQL(
			"SELECT * FROM Purchase"
		);
		List<Purchase> result = new ArrayList<Purchase>();
		while (rs.next()) {
			int cId = rs.getInt("customerId");
			int infoId = rs.getInt("infoId");
			String pName = rs.getString("packageName");
			Date pTime = rs.getDate("purchaseTime");
			Purchase row = new Purchase(cId, infoId, pName, pTime.toString());
			result.add(row);
		}
		return result;
	}
	
	// Returns list of String that represents purchase records
	public List<String> getLedger() throws SQLException {
		int res1 = execUpdateSQL("CREATE VIEW purchaseview AS SELECT customerId,packageName FROM Purchase");
		ResultSet rs = execQuerySQL(
			"SELECT * FROM purchaseview"
		);
		List<String> result = new ArrayList();
		while (rs.next()) {
			int cId = rs.getInt("customerId");
			String pName = rs.getString("packageName");
			String row = "CustomerID: " + cId + "    " + "Package Name: " + pName;
			result.add(row);
		}
		return result;
	}
}
