package com.tsi2.streamrain.datatypes.content;

public class UserContentFavDto {
	
	private Integer contentID;
	
	private String userNickname;
	
	private boolean isFav;

	
	public Integer getContentID() {
		return contentID;
	}
	public void setContentID(Integer contentID) {
		this.contentID = contentID;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public boolean isFav() {
		return isFav;
	}
	public void setFav(boolean isFav) {
		this.isFav = isFav;
	}
	

}
