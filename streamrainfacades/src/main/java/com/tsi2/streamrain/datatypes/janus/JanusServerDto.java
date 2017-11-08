package com.tsi2.streamrain.datatypes.janus;

import java.util.Date;

public class JanusServerDto {

	private Integer id;
    private String tokenJanusCreationTokens;
    private String name;
    private boolean isEnable;
    private String janusUrl;
    private String adminUrl;
    private String stunServer;
    private String adminSecret;
    private String adminKey;
    private String streamrainRestToken;
    private Date dateLastUpdateRequest;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTokenJanusCreationTokens() {
		return tokenJanusCreationTokens;
	}
	public void setTokenJanusCreationTokens(String tokenJanusCreationTokens) {
		this.tokenJanusCreationTokens = tokenJanusCreationTokens;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	public String getJanusUrl() {
		return janusUrl;
	}
	public void setJanusUrl(String janusUrl) {
		this.janusUrl = janusUrl;
	}
	public String getAdminUrl() {
		return adminUrl;
	}
	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}
	public String getStunServer() {
		return stunServer;
	}
	public void setStunServer(String stunServer) {
		this.stunServer = stunServer;
	}
	public String getAdminSecret() {
		return adminSecret;
	}
	public void setAdminSecret(String adminSecret) {
		this.adminSecret = adminSecret;
	}
	public String getAdminKey() {
		return adminKey;
	}
	public void setAdminKey(String adminKey) {
		this.adminKey = adminKey;
	}
	public String getStreamrainRestToken() {
		return streamrainRestToken;
	}
	public void setStreamrainRestToken(String streamrainRestToken) {
		this.streamrainRestToken = streamrainRestToken;
	}
	public Date getDateLastUpdateRequest() {
		return dateLastUpdateRequest;
	}
	public void setDateLastUpdateRequest(Date dateLastUpdateRequest) {
		this.dateLastUpdateRequest = dateLastUpdateRequest;
	}
    
}
