package com.tsi2.streamrain.datatypes.content;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    private Date dateStart;
    private Integer estimatedDuraction;
    private List<Integer> idCategories;
    private List<Integer> idSimilarContents;
    private Boolean featured;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    private Date featuredDateStart;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    private Date featuredDateFinish;
    private String tenantId;
    private String pathTokenVOD;
    private Integer janus_audio_pt;
    private String janus_audio_rtp_map;
    private Integer janus_video_pt;
    private String janus_video_rtp_map;
    private String janus_pin;
    private Integer janus_audio_port;
    private Integer janus_video_port;
    private Boolean blocked;
    
    private String actorsName;
    private String directorsName;
    private String categoriesName;
    private String similarContentsName;
    private String dateStartLiveOnly;
    
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
	public List<Integer> getIdCategories() {
		return idCategories;
	}
	public void setIdCategories(List<Integer> idCategories) {
		this.idCategories = idCategories;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public Boolean getFeatured() {
		return featured;
	}
	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}
	public Date getFeaturedDateStart() {
		return featuredDateStart;
	}
	public void setFeaturedDateStart(Date featuredDateStart) {
		this.featuredDateStart = featuredDateStart;
	}
	public Date getFeaturedDateFinish() {
		return featuredDateFinish;
	}
	public void setFeaturedDateFinish(Date featuredDateFinish) {
		this.featuredDateFinish = featuredDateFinish;
	}
	public List<Integer> getIdSimilarContents() {
		return idSimilarContents;
	}
	public void setIdSimilarContents(List<Integer> idSimilarContents) {
		this.idSimilarContents = idSimilarContents;
	}
	public String getPathTokenVOD() {
		return pathTokenVOD;
	}
	public void setPathTokenVOD(String pathTokenVOD) {
		this.pathTokenVOD = pathTokenVOD;
	}
	public Integer getJanus_audio_pt() {
		return janus_audio_pt;
	}
	public void setJanus_audio_pt(Integer janus_audio_pt) {
		this.janus_audio_pt = janus_audio_pt;
	}
	public String getJanus_audio_rtp_map() {
		return janus_audio_rtp_map;
	}
	public void setJanus_audio_rtp_map(String janus_audio_rtp_map) {
		this.janus_audio_rtp_map = janus_audio_rtp_map;
	}
	public Integer getJanus_video_pt() {
		return janus_video_pt;
	}
	public void setJanus_video_pt(Integer janus_video_pt) {
		this.janus_video_pt = janus_video_pt;
	}
	public String getJanus_video_rtp_map() {
		return janus_video_rtp_map;
	}
	public void setJanus_video_rtp_map(String janus_video_rtp_map) {
		this.janus_video_rtp_map = janus_video_rtp_map;
	}
	public String getJanus_pin() {
		return janus_pin;
	}
	public void setJanus_pin(String janus_pin) {
		this.janus_pin = janus_pin;
	}
	public Integer getJanus_audio_port() {
		return janus_audio_port;
	}
	public void setJanus_audio_port(Integer janus_audio_port) {
		this.janus_audio_port = janus_audio_port;
	}
	public Integer getJanus_video_port() {
		return janus_video_port;
	}
	public void setJanus_video_port(Integer janus_video_port) {
		this.janus_video_port = janus_video_port;
	}
	public String getActorsName() {
		return actorsName;
	}
	public void setActorsName(String actorsName) {
		this.actorsName = actorsName;
	}
	public String getDirectorsName() {
		return directorsName;
	}
	public void setDirectorsName(String directorsName) {
		this.directorsName = directorsName;
	}
	public String getCategoriesName() {
		return categoriesName;
	}
	public void setCategoriesName(String categoriesName) {
		this.categoriesName = categoriesName;
	}
	public String getSimilarContentsName() {
		return similarContentsName;
	}
	public void setSimilarContentsName(String similarContentsName) {
		this.similarContentsName = similarContentsName;
	}
	public String getDateStartLiveOnly() {
		return dateStartLiveOnly;
	}
	public void setDateStartLiveOnly(String dateStartLiveOnly) {
		this.dateStartLiveOnly = dateStartLiveOnly;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
			
}
