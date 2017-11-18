package com.tsi2.streamrain.datatypes.content;

public class ChatMessageDto {
	
	private String textroom;
	
	private Long room;
	
	private String from;
	
	private String date;
	
	private String text;

	public String getTextroom() {
		return textroom;
	}

	public void setTextroom(String textroom) {
		this.textroom = textroom;
	}

	public Long getRoom() {
		return room;
	}

	public void setRoom(Long room) {
		this.room = room;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "{textroom=" + textroom + ", room=" + room + ", from=" + from + ", date=" + date
				+ ", text=" + text + "}";
	}
	
	
}
