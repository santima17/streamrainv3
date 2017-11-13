package com.tsi2.streamrain.datatypes.janus;

public class JanusBackendTokenDto {
	private String janus;
    private String transaction;
    private String admin_secret;
    private String token;
    
	public String getJanus() {
		return janus;
	}
	public void setJanus(String janus) {
		this.janus = janus;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public String getAdmin_secret() {
		return admin_secret;
	}
	public void setAdmin_secret(String admin_secret) {
		this.admin_secret = admin_secret;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
    
    
  }