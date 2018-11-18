package pkg304models.models;

import pkg304models.GenericModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pkg304data.Content;

public class ContentModel extends GenericModel<Content> {

	public ContentModel(Connection con) {
		super(con);
	}

	@Override
	public List<Content> getAll() throws SQLException {
		ResultSet rs = execQuerySQL("SELECT * FROM Content");
		List<Content> result = new ArrayList<Content>();
		while (rs.next()) {
			int chatId = rs.getInt("chatId");
			int senderId = rs.getInt("senderId");
			int receiverId = rs.getInt("receiverId");
			String message = rs.getString("message");
			String timestamp = rs.getString("time");
			Content row = new Content(chatId, senderId, receiverId, message, timestamp);
			result.add(row);
		}
		return result;
	}

	public List<Content> getConversation(int cId1, int cId2) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER));
		values.addAll(Arrays.asList((Object) cId1, (Object) cId2, (Object) cId2, (Object) cId1));
		
		String cmd = "SELECT * FROM Content"
				+ " WHERE (senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?)"
				+ " ORDER BY chatId ASC";
		ResultSet rs = execQuerySQL(cmd, types, values);
		
		List<Content> result = new ArrayList<Content>();
		while (rs.next()) {
			int chatId = rs.getInt("chatId");
			int senderId = rs.getInt("senderId");
			int receiverId = rs.getInt("receiverId");
			String message = rs.getString("message");
			String timestamp = rs.getString("time");
			Content row = new Content(chatId, senderId, receiverId, message, timestamp);
			result.add(row);
		}
		return result;
	}
	
	public int createContent(int senderId, int receiverId, String message) throws SQLException {
		List<Integer> types = new ArrayList<Integer>();
		List<Object> values = new ArrayList<Object>();
		types.addAll(Arrays.asList(Types.INTEGER, Types.INTEGER, Types.CHAR));
		values.addAll(Arrays.asList((Object) senderId, (Object) receiverId, (Object) message));
		
		String cmd = "INSERT INTO Content (chatId, senderId, receiverId, message, time) "
				+ "VALUES (incr_chatId.nextval, ?, ?, ?, current_timestamp)";
		return execUpdateSQL(cmd, types, values);
	}
}
