package pkg304models;

import java.sql.*;
import java.util.List;

public abstract class GenericModel<T> {
	protected Connection con;
	protected GenericModel(Connection con) {
		this.con = con;
	}
	
	public abstract List<T> getAll() throws SQLException;
	
	public abstract List<T> parseResultSet(ResultSet rs) throws SQLException;
	
	protected int execUpdateSQL(String cmd) throws SQLException {
		Statement stmt = this.con.createStatement();
		int rowCount = stmt.executeUpdate(cmd);
		this.con.commit();
		stmt.close();
		if (rowCount == 0) {
			throw new SQLException("error: no resource was updated");
		}
		return rowCount;
	}
	
	protected int execUpdateSQL(String cmd, List<Integer> types, List<Object> values) throws SQLException {
		PreparedStatement ps = this.con.prepareStatement(cmd);
		setupPS(ps, types, values);
		int rowCount = ps.executeUpdate();
		this.con.commit();
		ps.close();
		if (rowCount == 0) {
			throw new SQLException("error: no resource was updated");
		}
		return rowCount;
	}
	
	protected ResultSet execQuerySQL(String cmd) throws SQLException {
		Statement stmt = this.con.createStatement();
		ResultSet rs = stmt.executeQuery(cmd);
		stmt.close();
		return rs;
	}
	
	protected ResultSet execQuerySQL(String cmd, List<Integer> types, List<Object> values) throws SQLException {
		PreparedStatement ps = this.con.prepareStatement(cmd);
		setupPS(ps, types, values);
		ResultSet rs = ps.executeQuery();
		ps.close();
		return rs;
	}
	
	protected void setupPS(PreparedStatement ps, List<Integer> types, List<Object> values) throws SQLException {
		if (types.size() != values.size()) {
			throw new SQLException("error: length mismatch between types and values array");
		}
		for (int i=0; i<types.size(); i++) {
			int t = types.get(i);
			Object v = values.get(i);
			switch(t) {
			case Types.INTEGER:
				if (v == null) {
					ps.setNull(i+1, Types.INTEGER);
				} else {
					ps.setInt(i+1, (int) v);
				}
				break;
			case Types.CHAR:
				if (v == null) {
					ps.setNull(i+1, Types.CHAR);
				} else {
					ps.setString(i+1, (String) v);
				}
			case Types.TIME:
				if (v == null) {
					ps.setNull(i+1, Types.TIME);
				} else {
					// TODO: Initialize date as right now and update ps accordingly
				}
			default:
				throw new SQLException("error: unknown type - " + t + " passed to setupPS");
			}
		}
	}
}
