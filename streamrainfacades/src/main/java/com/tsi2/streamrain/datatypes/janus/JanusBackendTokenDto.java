package com.tsi2.streamrain.datatypes.janus;

public class JanusBackendTokenDto extends AbstractJanusDto {

    private String admin_secret;

	public String getAdmin_secret() {
		return admin_secret;
	}
	public void setAdmin_secret(String admin_secret) {
		this.admin_secret = admin_secret;
	}
 
  }