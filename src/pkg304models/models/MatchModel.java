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
	
	// Obtains a number of users whose personality fits best with the user with customerId cId
	// for aggregation purpose
	public int getNumBestMatches(int cId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.INTEGER);
		values.add((Object) cId);
		
		String cmd = "SELECT COUNT(gender) FROM Customer JOIN Personality JOIN Personality_Match"
				+ " ON customerId = ? AND personalityId = pId AND personalityId = p1Id"
				+ " GROUP BY gender";
		ResultSet rs = execQuerySQL(cmd, types, values);
		if (!rs.next()) {
			throw new SQLException("error: no response was returned for getNumBestMatches(cId)");
		}
		return rs.getInt("COUNT(gender)");
	}

	// Create matches between customer with id cId and all other users whose personalityId
	// has rank < 3 relative to the customer's personalityId
	public int createMatches(int cId, int pId, String gender) throws SQLException {	
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.CHAR, Types.INTEGER, Types.INTEGER));
		values.addAll(Arrays.asList((Object) cId, (Object) gender, (Object) pId, (Object) pId));
		
		String cmd = "INSERT INTO Match (customer1Id, customer2Id, c1Active, c2Active)"
				+ " SELECT ?, customerId, '1', '1'"
				+ " FROM Customer"
				+ " WHERE gender <> cast(? as char(10)) AND personalityId IN"
				+ " (SELECT p2Id FROM Personality_Match WHERE p1Id = ? AND rank < 3)";
		return execUpdateSQL(cmd, types, values);
	}
}
