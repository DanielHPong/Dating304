package pkg304models.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pkg304data.Personality;
import pkg304models.GenericModel;

public class PersonalityModel extends GenericModel<Personality> {
	public PersonalityModel(Connection con) {
		super(con);
	}
	
	public List<Personality> getAll() throws SQLException {
		ResultSet rs = execQuerySQL("SELECT * FROM Personality");
		return parseResultSet(rs);
	}
	
	public List<Personality> parseResultSet(ResultSet rs) throws SQLException {
		List<Personality> result = new ArrayList<Personality>();
		while (rs.next()) {
			int personalityId = rs.getInt("PID");
			String personalityType = rs.getString("Type");
			Personality row = new Personality(personalityId, personalityType);
			result.add(row);
		}
		return result;
	}
}
