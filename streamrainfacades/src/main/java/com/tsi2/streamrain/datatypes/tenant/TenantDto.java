package com.tsi2.streamrain.datatypes.tenant;

public class TenantDto {

	private Integer id;
	private String name;
	private String dbIp;
	private int dbPort;
	private String dbUser;
	private String dbPassword;
	private String generatorUser;
	private String generatorPassword;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDbIp() {
		return dbIp;
	}
	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}
	public int getDbPort() {
		return dbPort;
	}
	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getGeneratorUser() {
		return generatorUser;
	}
	public void setGeneratorUser(String generatorUser) {
		this.generatorUser = generatorUser;
	}
	public String getGeneratorPassword() {
		return generatorPassword;
	}
	public void setGeneratorPassword(String generatorPassword) {
		this.generatorPassword = generatorPassword;
	}

	
	
}
