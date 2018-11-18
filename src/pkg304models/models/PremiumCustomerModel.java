package pkg304models.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pkg304data.PremiumCustomer;
import pkg304models.GenericModel;

public class PremiumCustomerModel extends GenericModel<PremiumCustomer> {

	public PremiumCustomerModel(Connection con) {
		super(con);
	}

	@Override
	public List<PremiumCustomer> getAll() throws SQLException {
		ResultSet rs = execQuerySQL("SELECT * FROM PremiumCustomer");
		List<PremiumCustomer> result = new ArrayList<PremiumCustomer>();
		while (rs.next()) {
			int customerId = rs.getInt("customerId");
			int infoId = rs.getInt("infoId");
			PremiumCustomer row = new PremiumCustomer(customerId, infoId);
			result.add(row);
		}
		return result;
	}
	
	// Create a PremiumCustomer row based on customerId and infoId
	public int createPremiumCustomer(int customerId, int infoId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.INTEGER));
		values.addAll(Arrays.asList((Object) customerId, (Object) infoId));
		
		String cmd = "INSERT INTO Premium_Customer (customerId, infoId)"
				+ " VALUES (?, ?)";
		return execUpdateSQL(cmd, types, values);
	}
	
	// To delete, remove PaymentInfo
}
