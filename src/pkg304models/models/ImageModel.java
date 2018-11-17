package pkg304models.models;

import pkg304models.GenericModel;
import pkg304data.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageModel extends GenericModel<Image> {
	public ImageModel(Connection con) {
		super(con);
	}

	@Override
	public List<Image> getAll() throws SQLException {
		ResultSet rs = execQuerySQL("SELECT * FROM Image_Log");
		List<Image> result = new ArrayList<Image>();
		while (rs.next()) {
			int customerId = rs.getInt("customerId");
			String url = rs.getString("url");
			Image row = new Image(customerId, url);
			result.add(row);
		}
		return result;
	}
	
	// Get all images that belong to a user
	public List<Image> getUserImage(int customerId) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.add(Types.INTEGER);
		values.add((Object) customerId);
		
		String cmd = "SELECT url FROM Image_Log WHERE customerId = ?";
		ResultSet rs = execQuerySQL(cmd, types, values);
		
		List<Image> result = new ArrayList<Image>();
		while (rs.next()) {
			String url = rs.getString("url");
			Image row = new Image(customerId, url);
			result.add(row);
		}
		return result;
	}
	
	// Create an image row
	public int createImage(int customerId, String url) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.CHAR));
		values.addAll(Arrays.asList((Object) customerId, (Object) url));
		
		String cmd = "INSERT INTO Image_Log (customerId, url) VALUES (?, cast(? as char(255)))";
		return execUpdateSQL(cmd, types, values);
	}
	
	// Delete an image
	public int deleteImage(int customerId, String url) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.CHAR));
		values.addAll(Arrays.asList((Object) customerId, (Object) url));
		
		String cmd = "DELETE FROM Image_Log WHERE customerId = ? AND url = cast(? as char(255))";
		return execUpdateSQL(cmd, types, values);
	}
}
