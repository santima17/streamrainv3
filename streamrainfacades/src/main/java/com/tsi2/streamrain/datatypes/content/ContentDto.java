package com.tsi2.streamrain.datatypes.content;

import java.util.Date;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public class ContentDto {
	
	private Integer id;
    private String name;
    private String description;
    private String type;
    private Boolean alwaysAvailable;
    private Double ranking;
    private String coverPictureUrl;
    private String storageUrl;
    private Boolean isPayPerView;
    private Set<ContentCastDto> actors;
    private Set<ContentCastDto> directors;
    private MultipartFile picture;
    private MultipartFile video;
    private Integer duration;
    private Date dateStart;
    private Integer estimatedDuraction;
    
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getRanking() {
		return ranking;
	}
	public void setRanking(Double ranking) {
		this.ranking = ranking;
	}
	public String getCoverPictureUrl() {
		return coverPictureUrl;
	}
	public void setCoverPictureUrl(String coverPictureUrl) {
		this.coverPictureUrl = coverPictureUrl;
	}
	public String getStorageUrl() {
		return storageUrl;
	}
	public void setStorageUrl(String storageUrl) {
		this.storageUrl = storageUrl;
	}
	public Boolean getIsPayPerView() {
		return isPayPerView;
	}
	public void setIsPayPerView(Boolean isPayPerView) {
		this.isPayPerView = isPayPerView;
	}
	public MultipartFile getPicture() {
		return picture;
	}
	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}
	public MultipartFile getVideo() {
		return video;
	}
	public void setVideo(MultipartFile video) {
		this.video = video;
	}
	
	public Set<ContentCastDto> getActors() {
		return actors;
	}
	public void setActors(Set<ContentCastDto> actors) {
		this.actors = actors;
	}
	public Set<ContentCastDto> getDirectors() {
		return directors;
	}
	public void setDirectors(Set<ContentCastDto> directors) {
		this.directors = directors;
	}

	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Integer getEstimatedDuraction() {
		return estimatedDuraction;
	}
	public void setEstimatedDuraction(Integer estimatedDuraction) {
		this.estimatedDuraction = estimatedDuraction;
	}
	public Boolean getAlwaysAvailable() {
		return alwaysAvailable;
	}
	public void setAlwaysAvailable(Boolean alwaysAvailable) {
		this.alwaysAvailable = alwaysAvailable;
	}
						    
}
