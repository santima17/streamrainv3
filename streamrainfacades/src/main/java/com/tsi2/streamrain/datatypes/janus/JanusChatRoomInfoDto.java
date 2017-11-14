package com.tsi2.streamrain.datatypes.janus;

public class JanusChatRoomInfoDto{
	
	
    private String request;
    private int room; 
    private String description; 
    private String post;
    private String pin;
    private boolean permanent;
    private String admin_key;
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public boolean isPermanent() {
		return permanent;
	}
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
	public String getAdmin_key() {
		return admin_key;
	}
	public void setAdmin_key(String admin_key) {
		this.admin_key = admin_key;
	}

    
    

}
