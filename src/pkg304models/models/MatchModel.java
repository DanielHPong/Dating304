package pkg304models.models;

import pkg304models.GenericModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pkg304data.Match;

public class MatchModel extends GenericModel<Match> {

	public MatchModel(Connection con) {
		super(con);
	}

	@Override
	public List<Match> getAll() throws SQLException {
		ResultSet rs = execQuerySQL("SELECT * FROM Match");
		List<Match> result = new ArrayList<Match>();
		while (rs.next()) {
			int customer1Id = rs.getInt("customer1Id");
			int customer2Id = rs.getInt("customer2Id");
			boolean c1Active = rs.getBoolean("c1Active");
			boolean c2Active = rs.getBoolean("c2Active");
			Match row = new Match(customer1Id, customer2Id, c1Active, c2Active);
			result.add(row);
		}
		return result;
	}
	
	// Returns [personality type, #broken matches] for a user with customerId cId
	// Fulfills aggregation portion of project + reveals insight on what kind of personality is less likely to be
	// compatible with the user
	public List<Object> getPersonalityToBrokenMatchCount(int cId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.INTEGER));
		values.addAll(Arrays.asList((Object) cId, (Object) cId));
		
		String cmd = "SELECT type, COUNT(*)"
				+ " FROM Customer JOIN Personality ON personalityId = pId"
				+ " WHERE customerId IN"
				+ " (SELECT customer1Id FROM Match WHERE (c1Active = 0 OR c2Active = 0) AND customer2Id = ?"
				+ " UNION"
				+ " SELECT customer2Id FROM Match WHERE (c1Active = 0 OR c2Active = 0) AND customer1Id = ?)"
				+ " GROUP BY type";
		ResultSet rs = execQuerySQL(cmd, types, values);
		
		List<Object> result = new ArrayList<Object>();
		if (rs.next()) {
			result.add(rs.getString("type"));
			result.add(rs.getInt("COUNT(*)"));
		}
		return result;
	}

	// Create matches between customer with id cId and all other users whose personalityId
	// has rank < 3 relative to the customer's personalityId
	public int createMatches(int cId, int pId, String gender) throws SQLException {	
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.CHAR, Types.INTEGER));
		values.addAll(Arrays.asList((Object) cId, (Object) gender, (Object) pId));
		
		String cmd = "INSERT INTO Match (customer1Id, customer2Id, c1Active, c2Active)"
				+ " SELECT ?, customerId, '1', '1'"
				+ " FROM Customer"
				+ " WHERE gender <> cast(? as char(10)) AND personalityId IN"
				+ " (SELECT p2Id FROM Personality_Match WHERE p1Id = ? AND rank < 3)";
		return execUpdateSQL(cmd, types, values);
	}
	
	// Deactivates a match given c1Id and c2Id (marked disabled by c1)
	public int deactivateMatch(int c1Id, int c2Id) throws SQLException {	
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.INTEGER));
		values.addAll(Arrays.asList((Object) c1Id, (Object) c2Id));
		
		String cmd1 = "UPDATE Match SET c1Active = 0 WHERE customer1Id = ? AND customer2Id = ?";
		int res1 = execUpdateSQL(cmd1, types, values);
		String cmd2 = "UPDATE Match SET c2Active = 0 WHERE customer2Id = ? AND customer1Id = ?";
		int res2 = execUpdateSQL(cmd2, types, values);
		
		return res1 + res2;
	}
}
