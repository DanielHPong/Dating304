package pkg304models.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pkg304data.Benefit;
import pkg304data.PremiumPackage;
import pkg304models.GenericModel;
import pkg304models.ModelManager;
import pkg304models.Table;

public class PremiumPackageModel extends GenericModel<PremiumPackage> {

	protected PremiumPackageModel(Connection con) {
		super(con);
	}

	@Override
	public List<PremiumPackage> getAll() throws SQLException {
		ResultSet rs = execQuerySQL("SELECT * FROM Premium_Package");
		List<PremiumPackage> result = new ArrayList<PremiumPackage>();
		while (rs.next()) {
			String pName = rs.getString("pName");
			float price = rs.getFloat("price");
			PremiumPackage row = new PremiumPackage(pName, price);
			result.add(row);
		}
		return result;
	}
	
	// Get all the benefits that come with a single package
	public List<Benefit> getBenefitsByPackage(String pName) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		ResultSet rs = execQuerySQL("SELECT bName, bInfo FROM Premium_To_Benefit JOIN Benefit"
				+ " ON Premium_To_Benefit.bName = Benefit.bName AND pName = cast(? as char(50))");
		List<Benefit> result = new ArrayList<Benefit>();
		while (rs.next()) {
			String bName = rs.getString("bName");
			String bInfo = rs.getString("bInfo");
			Benefit row = new Benefit(bName, bInfo);
			result.add(row);
		}
		return result;
	}
	
	// Get all premium packages for a user
	public List<PremiumPackage> getPremByID(int customerId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.INTEGER);
		values.add(customerId);
		ResultSet rs = execQuerySQL("SELECT pName, price FROM Purchase JOIN Premium_Package ON pName = packageName AND customerId = ?");
		List<PremiumPackage> result = new ArrayList();
		while (rs.next()) {
			String pName = rs.getString("pName");
			float price = rs.getFloat("price");
			PremiumPackage pack = new PremiumPackage(pName, price);
			result.add(pack);
		}
		return result;
	}
	
	// A user buys a package
	public int createPurchase(int customerId, String packageName) throws SQLException {
		PaymentInfoModel pModel = (PaymentInfoModel) ModelManager.getInstance().getModel(Table.PAYMENT_INFO);
		int infoId = pModel.getPaymentInfoById(customerId).getInfoId();
		
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.INTEGER, Types.CHAR));
		values.addAll(Arrays.asList(customerId, infoId, packageName));
		
		String cmd = "INSERT INTO Purchase (customerId, infoId, packageName, purchaseTime)"
				+ " VALUES (?, ?, ?, current_timestamp)";
		return execUpdateSQL(cmd, types, values);
	}

}
