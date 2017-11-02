package com.tsi2.streamrain.datatypes.content;

public class ContentCastDto {
	
    private String firstName;
    private String lastName;
    private Boolean isActor;
    private Boolean isDirector;
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Boolean getIsActor() {
		return isActor;
	}
	public void setIsActor(Boolean isActor) {
		this.isActor = isActor;
	}
	public Boolean getIsDirector() {
		return isDirector;
	}
	public void setIsDirector(Boolean isDirector) {
		this.isDirector = isDirector;
	}
    
}
