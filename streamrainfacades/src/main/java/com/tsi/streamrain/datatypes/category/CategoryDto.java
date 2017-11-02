package com.tsi.streamrain.datatypes.category;

public class CategoryDto {
	
	private Integer id;
    private String name;
    private String description;
    private String coverPictureUrl;
    
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCoverPictureUrl() {
		return coverPictureUrl;
	}
	public void setCoverPictureUrl(String coverPictureUrl) {
		this.coverPictureUrl = coverPictureUrl;
	}
    
    

}
