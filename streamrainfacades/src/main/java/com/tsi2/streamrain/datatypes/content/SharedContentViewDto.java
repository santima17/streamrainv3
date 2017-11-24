package com.tsi2.streamrain.datatypes.content;

import java.util.Date;

public class SharedContentViewDto {
	
    private Integer id;
    private Integer contentId;
    private Integer usersByDestinationUserId;
    private Integer usersByUserId;
    private Date date;
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
	public Integer getUsersByDestinationUserId() {
		return usersByDestinationUserId;
	}
	public void setUsersByDestinationUserId(Integer usersByDestinationUserId) {
		this.usersByDestinationUserId = usersByDestinationUserId;
	}
	public Integer getUsersByUserId() {
		return usersByUserId;
	}
	public void setUsersByUserId(Integer usersByUserId) {
		this.usersByUserId = usersByUserId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
    
    
    
    

}
