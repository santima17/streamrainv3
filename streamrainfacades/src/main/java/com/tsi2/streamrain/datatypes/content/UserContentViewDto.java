package com.tsi2.streamrain.datatypes.content;

import java.util.Date;

import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.Users;

public class UserContentViewDto {
	
	private Integer contentID;
	private String userNickname;
    private Integer id;
    private Date dateStart;
    private Date dateFinish;
    private int second;
    //private Contents contents;
    //private Users users;
    
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
//	public Contents getContents() {
//		return contents;
//	}
//	public void setContents(Contents contents) {
//		this.contents = contents;
//	}
//	public Users getUsers() {
//		return users;
//	}
//	public void setUsers(Users users) {
//		this.users = users;
//	}
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
