package pkg304data;

public class Content {
	private int chatId;
	private int senderId;
	private int receiverId;
	private String message;
	private String time;
	
	public Content(int chatId, int senderId, int receiverId, String message, String time) {
		this.chatId = chatId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.time = time;
	}

	public int getChatId() {
		return chatId;
	}

	public int getSenderId() {
		return senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public String getMessage() {
		return message;
	}

	public String getTime() {
		return time;
	}

	
}
