package com.tsi2.streamrain.datatypes.user;

public class UserDto {
	
    private Integer id;
    private String email;
    private String nickname;
    private String password;
    private String confirmPass;
    private Boolean isBanned;
    private String country;
    private String city;
    private String facebookUserId;
    private String twitterUserId;
    private Integer integratorId;
    private boolean blocked;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIsBanned() {
		return isBanned;
	}
	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFacebookUserId() {
		return facebookUserId;
	}
	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}
	public String getTwitterUserId() {
		return twitterUserId;
	}
	public void setTwitterUserId(String twitterUserId) {
		this.twitterUserId = twitterUserId;
	}
	public Integer getIntegratorId() {
		return integratorId;
	}
	public void setIntegratorId(Integer integratorId) {
		this.integratorId = integratorId;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	} 
}
