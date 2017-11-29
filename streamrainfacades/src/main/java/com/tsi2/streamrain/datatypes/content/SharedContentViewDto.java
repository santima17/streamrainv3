package com.tsi2.streamrain.datatypes.content;

import java.util.Date;

public class SharedContentViewDto {
	
    private Integer id;
    private Integer contentId;
    private String usersByDestinationUserId;
    private String usersByUserId;
    private Date date;
    private String dateShared;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getUsersByDestinationUserId() {
		return usersByDestinationUserId;
	}
	public void setUsersByDestinationUserId(String usersByDestinationUserId) {
		this.usersByDestinationUserId = usersByDestinationUserId;
	}
	public String getUsersByUserId() {
		return usersByUserId;
	}
	public void setUsersByUserId(String usersByUserId) {
		this.usersByUserId = usersByUserId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDateShared() {
		return dateShared;
	}
	public void setDateShared(String dateShared) {
		this.dateShared = dateShared;
	}
	 

}
