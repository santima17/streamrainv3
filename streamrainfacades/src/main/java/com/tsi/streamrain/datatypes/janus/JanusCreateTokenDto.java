package com.tsi.streamrain.datatypes.janus;

import java.util.Date;

public class JanusCreateTokenDto {
	
	private Integer id;
    private String janusToken;
    private String streamrainRestBackend;
    private Date dateCreation;
    private Date dateExpiration;
    private Date dateUsage;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJanusToken() {
		return janusToken;
	}
	public void setJanusToken(String janusToken) {
		this.janusToken = janusToken;
	}
	public String getStreamrainRestBackend() {
		return streamrainRestBackend;
	}
	public void setStreamrainRestBackend(String streamrainRestBackend) {
		this.streamrainRestBackend = streamrainRestBackend;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Date getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public Date getDateUsage() {
		return dateUsage;
	}
	public void setDateUsage(Date dateUsage) {
		this.dateUsage = dateUsage;
	}
    
    

}
