package com.tsi2.streamrain.datatypes.janus;

import java.util.List;

public class JanusAccessInformationDto {
	
	private boolean isBaned;
	private String janusToken;
	private List<String> janusPins;
	
	public boolean isBaned() {
		return isBaned;
	}
	public void setBaned(boolean isBaned) {
		this.isBaned = isBaned;
	}
	public String getJanusToken() {
		return janusToken;
	}
	public void setJanusToken(String janusToken) {
		this.janusToken = janusToken;
	}
	public List<String> getJanusPins() {
		return janusPins;
	}
	public void setJanusPins(List<String> janusPins) {
		this.janusPins = janusPins;
	}
	
	
	
	
}
