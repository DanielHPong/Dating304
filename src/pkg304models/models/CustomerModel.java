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

	// Get all users
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
	
	// Returns customerId of user by his/her email
	public int getIdFromEmail(String email) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.CHAR);
		values.add((Object) email);
		
		String cmd = "SELECT customerId FROM Customer WHERE email = cast(? as char(50))";
		ResultSet rs = execQuerySQL(
		 	cmd,
			types,
			values
		);
		if (!rs.next()) {
			throw new SQLException("error: email - " + email + " does not exist in the database");
		}
		return rs.getInt("customerId");
	}
	
	// Returns a list of users who have been matched with a user who has customerId as id
	public List<Customer> getMatchedCustomers(int customerId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.INTEGER));
		values.addAll(Arrays.asList((Object) customerId, (Object) customerId));
		
		String cmd = "SELECT customerId, personalityId, email, name, isActive FROM Customer JOIN Match"
				+ " ON (customer1Id = ? OR customer2Id = ?)"
				+ " AND (c1Active = '1' AND c2Active = '1')";
		
		ResultSet rs = execQuerySQL(cmd, types, values);
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
	
	// Insert a customer row into database
	public int createCustomer(String email, String name, int pid) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER));
		values.addAll(Arrays.asList((Object) email, (Object) name, (Object) "1", (Object) pid));
		return execUpdateSQL(
			"INSERT INTO Customer (customerId, email, name, isActive, personalityId) VALUES (incr_customerId.nextval, ?, ?, ?, ?)",
			types,
			values
		);
	}
	
	// Set isActive field of customer with their uid
	public int deactivateCustomer(int uid) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.CHAR);
		values.add((Object) uid);
		return execUpdateSQL(
			"UPDATE Customer SET isActive = 0 WHERE customerId = cast(? as integer)",
			types,
			values
		);
	}
}
