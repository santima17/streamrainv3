package com.tsi2.streamrain.datatypes.content;

import java.util.Date;

public class SpoilerMarkDto {
	
	private Integer idUserComments;
    private Integer idUsers;
    private Date date;
    
	public Integer getIdUserComments() {
		return idUserComments;
	}
	public void setIdUserComments(Integer idUserComments) {
		this.idUserComments = idUserComments;
	}
	public Integer getIdUsers() {
		return idUsers;
	}
	public void setIdUsers(Integer idUsers) {
		this.idUsers = idUsers;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
