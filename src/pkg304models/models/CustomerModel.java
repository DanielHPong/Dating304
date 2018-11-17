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
			String gender = rs.getString("gender");
			boolean isActive = rs.getBoolean("isActive");
			Customer row = new Customer(cid, email, name, gender, isActive, pid);
			result.add(row);
		}
		return result;
	}
	
	// Returns customer row by his/her customerId
	public List<Customer> getCustomerInfoById(int cId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.INTEGER);
		values.add((Object) cId);
		
		String cmd = "SELECT * FROM Customer WHERE customerId = ?";
		ResultSet rs = execQuerySQL(cmd, types, values);
		if (!rs.next()) {
			throw new SQLException("error: customer with cId - " + cId + " does not exist in the database");
		}
		List<Customer> result = new ArrayList<Customer>();
		String email = rs.getString("email");
		String name = rs.getString("name");
		String gender = rs.getString("gender");
		boolean isActive = rs.getBoolean("isActive");
		int pId = rs.getInt("personalityId");
		result.add(new Customer(cId, email, name, gender, isActive, pId));
		return result;
	}
	
	// Returns customerId of user by his/her email
	public int getIdFromEmail(String email) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.CHAR);
		values.add((Object) email);
		
		String cmd = "SELECT customerId FROM Customer WHERE email = cast(? as char(50))";
		ResultSet rs = execQuerySQL(cmd, types, values);
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
			String gender = rs.getString("gender");
			boolean isActive = rs.getBoolean("isActive");
			Customer row = new Customer(cid, email, name, gender, isActive, pid);
			result.add(row);
		}
		return result;
	}
	
	// Insert a customer row into database
	public int createCustomer(String email, String name, String gender, int pid) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER));
		values.addAll(Arrays.asList((Object) email, (Object) name, (Object) gender, (Object) "1", (Object) pid));
		
		String cmd = "INSERT INTO Customer (customerId, email, name, gender, isActive, personalityId) "
				+ "VALUES (incr_customerId.nextval, ?, ?, ?, ?, ?)";
		return execUpdateSQL(cmd, types, values);
	}
	
	// Set isActive field of customer with a specific email address
	public int deactivateCustomer(String email) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.CHAR);
		values.add((Object) email);
		return execUpdateSQL(
			"UPDATE Customer SET isActive = 0 WHERE email = cast(? as char(50))",
			types,
			values
		);
	}
}
