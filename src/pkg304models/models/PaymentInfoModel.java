package pkg304models.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pkg304data.PaymentInfo;
import pkg304models.GenericModel;
import pkg304models.ModelManager;
import pkg304models.Table;

public class PaymentInfoModel extends GenericModel<PaymentInfo> {

	public PaymentInfoModel(Connection con) {
		super(con);
	}

	@Override
	public List<PaymentInfo> getAll() throws SQLException {
		ResultSet rs = execQuerySQL(
			"SELECT * FROM Payment_Info JOIN Payment_Info_Card_Type ON Payment_Info.cardNo = Payment_Info_Card_Type.cardNo"
		);
		List<PaymentInfo> result = new ArrayList<PaymentInfo>();
		while (rs.next()) {
			int infoId = rs.getInt("infoId");
			String cardNo = rs.getString("cardNo");
			String cardType = rs.getString("cardType");
			String address = rs.getString("address");
			PaymentInfo row = new PaymentInfo(infoId, cardNo, cardType, address);
			result.add(row);
		}
		return result;
	}
	
	// Get infoId, cardNo, cardType, and address by customerId
	public PaymentInfo getPaymentInfoById(int customerId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.INTEGER);
		values.add((Object) customerId);
		
		String cmd = "SELECT Payment_Info.infoId, Payment_Info.cardNo, cardType, address"
				+ " FROM Payment_Info"
				+ " JOIN Payment_Info_Card_Type ON Payment_Info.cardNo = Payment_Info_Card_type.cardNo"
				+ " JOIN Premium_Customer ON Premium_Customer.customerId = ? AND Payment_Info.infoId = Premium_Customer.infoId";
		ResultSet rs = execQuerySQL(cmd, types, values);
		if (!rs.next()) {
			throw new SQLException("error: no paymentInfo associated with customerId - " + customerId);
		}
		return new PaymentInfo(rs.getInt("infoId"), rs.getString("cardNo"), rs.getString("cardType"), rs.getString("address"));
	}

	// This actually creates the premium user too
	public int createPaymentInfo(int customerId, String cardType, String cardNo, String address) throws SQLException {
		List<Integer> types1 = new ArrayList<Integer>();
		List<Object> values1 = new ArrayList<Object>();
		types1.addAll(Arrays.asList(Types.CHAR, Types.CHAR));
		values1.addAll(Arrays.asList((Object) cardNo, (Object) address));
		String cmd1 = "INSERT INTO Payment_Info (infoId, cardNo, address)"
				+ " VALUES (incr_infoId.nextval, ?, ?)";
		int res1 = execUpdateSQL(cmd1, types1, values1);
		
		List<Integer> types2 = new ArrayList<Integer>();
		List<Object> values2 = new ArrayList<Object>();
		types2.addAll(Arrays.asList(Types.CHAR, Types.CHAR));
		values2.addAll(Arrays.asList((Object) cardNo, (Object) cardType));
		String cmd2 = "INSERT INTO Payment_Info_Card_Type (cardNo, cardType)"
				+ " VALUES (?, ?)";
		int res2 = execUpdateSQL(cmd2, types2, values2);
		
		List<Integer> types3 = new ArrayList<Integer>();
		List<Object> values3 = new ArrayList<Object>();
		types3.add(Types.CHAR);
		values3.add((Object) cardNo);
		String cmd3 = "SELECT infoId FROM Payment_Info WHERE cardNo = cast(? as char(50))";
		ResultSet rs = execQuerySQL(cmd3, types3, values3);
		if (!rs.next()) {
			throw new SQLException("error: infoId not found during createPaymentInfo");
		}
		int infoId = rs.getInt("infoId");
		
		PremiumCustomerModel pcModel = (PremiumCustomerModel) ModelManager.getInstance().getModel(Table.PREMIUM_CUSTOMER);
		int res3 = pcModel.createPremiumCustomer(customerId, infoId);
		
		return res1 + res2 + res3;
	}
	
	// Delete payment info, premium user
	public int deletePaymentInfo(int customerId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.INTEGER);
		values.add((Object) customerId);
		String cmd = "DELETE FROM Payment_Info WHERE infoId IN (SELECT infoId FROM Premium_Customer WHERE customerId = ?)";
		return execUpdateSQL(cmd, types, values);
	}
}
