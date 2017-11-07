package com.tsi2.streamrain.datatypes.content;

import java.util.Date;

public class UserContentViewDto {
	
	private Integer contentID;
	
	private String userNickname;
	
	private Date dateStart;
	
	private Date dateFinish;
	
	private int second;

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

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
}
