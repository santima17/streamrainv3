package com.tsi2.streamrain.datatypes.janus;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.tsi2.streamrain.model.generator.Contents;

public class JanusLiveOnlyInfoDto {
	
    private String request;
    private int id;         
    private String  type;         
    private String  description;     
    private boolean  audio;         
    private boolean  video;        
    private int  audioport;        
    private int  audiopt;        
    private String  audiortpmap;     
    private int  videoport;     
    private int  videopt;        
    private String  videortpmap;     
    private String pin; 
    private boolean permanent;
    private String adminkey;
    private boolean ppv;
    
    public String getRequest() {	
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAudioport() {
		return audioport;
	}
	public void setAudioport(int audioport) {
		this.audioport = audioport;
	}
	public String getAudiortpmap() {
		return audiortpmap;
	}
	public void setAudiortpmap(String audiortpmap) {
		this.audiortpmap = audiortpmap;
	}
	public int getVideoport() {
		return videoport;
	}
	public void setVideoport(int videoport) {
		this.videoport = videoport;
	}

	public String getVideortpmap() {
		return videortpmap;
	}
	public void setVideortpmap(String videortpmap) {
		this.videortpmap = videortpmap;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public boolean isPermanent() {
		return permanent;
	}
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
	public String getAdminkey() {
		return adminkey;
	}
	public void setAdminkey(String adminkey) {
		this.adminkey = adminkey;
	}
	public boolean isPpv() {
		return ppv;
	}
	public void setPpv(boolean ppv) {
		this.ppv = ppv;
	}
	public boolean isAudio() {
		return audio;
	}
	public void setAudio(boolean audio) {
		this.audio = audio;
	}
	public boolean isVideo() {
		return video;
	}
	public void setVideo(boolean video) {
		this.video = video;
	}
	public int getAudiopt() {
		return audiopt;
	}
	public void setAudiopt(int audiopt) {
		this.audiopt = audiopt;
	}
	public int getVideopt() {
		return videopt;
	}
	public void setVideopt(int videopt) {
		this.videopt = videopt;
	}
	
	
	

    
    

}
