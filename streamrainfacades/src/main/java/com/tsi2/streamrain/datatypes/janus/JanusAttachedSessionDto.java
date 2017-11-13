package com.tsi2.streamrain.datatypes.janus;

public class JanusAttachedSessionDto {
	
	private String janus;
    private String transaction;
    private String token;
    private String plugin;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPlugin() {
		return plugin;
	}
	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}
    
}
