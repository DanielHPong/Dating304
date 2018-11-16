package pkg304models.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pkg304data.Customer;
import pkg304models.GenericModel;

public class CustomerModel extends GenericModel<Customer> {
	public CustomerModel(Connection con) {
		super(con);
	}

	@Override
	public List<Customer> getAll() throws SQLException {
		ResultSet rs = execQuerySQL("SELECT * FROM Customer");
		List<Customer> result = new ArrayList<Customer>();
		while (rs.next()) {
			int cid = rs.getInt("customerId");
			int pid = rs.getInt("personalityId");
			String email = rs.getString("email");
			String name = rs.getString("name");
			boolean isActive = rs.getBoolean("isActive");
			Customer row = new Customer(cid, email, name, isActive, pid);
			result.add(row);
		}
		return result;
	}
	
	public int getIdFromEmail(String email) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.CHAR);
		values.add((Object) email);
		ResultSet rs = execQuerySQL(
			"SELECT customerId FROM Customer WHERE email = ?",
			types,
			values
		);
		return rs.getInt("customerId");
	}
	
	public List<Customer> getMatchedCustomers(int customerId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.INTEGER));
		values.addAll(Arrays.asList((Object) customerId, (Object) customerId));
		
		String cmd = "SELECT * FROM Customer JOIN Match"
				+ " WHERE ( Match.customerId1 = ? OR Match.customerId2 = ? )"
				+ " AND ( Match.c1Active = '1' AND Match.c2Active = '1' )";
		
		ResultSet rs = execQuerySQL(cmd);
		List<Customer> result = new ArrayList<Customer>();
		while (rs.next()) {
			int cid = rs.getInt("customerId");
			int pid = rs.getInt("personalityId");
			String email = rs.getString("email");
			String name = rs.getString("name");
			boolean isActive = rs.getBoolean("isActive");
			Customer row = new Customer(cid, email, name, isActive, pid);
			result.add(row);
		}
		return result;
	}
	
	public int createCustomer(String email, String name, int pid) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER));
		values.addAll(Arrays.asList((Object) email, (Object) name, (Object) "1", (Object) pid));
		return execUpdateSQL(
			"INSERT INTO Customer (email, name, isActive, personalityId) VALUES (?, ?, ?, ?)",
			types,
			values
		);
	}
	
	public int deactivateCustomer(String email) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.CHAR);
		values.add((Object) email);
		return execUpdateSQL(
			"UPDATE Customer SET isActive = 0 WHERE email = ?",
			types,
			values
		);
	}
}
