package com.tsi2.streamrain.model.generator;
// Generated 04/10/2017 11:03:07 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Collections generated by hbm2java
 */
public class Collections  implements java.io.Serializable {


     private Integer id;
     private String name;
     private String description;
     private String type;
     private Double ranking;
     private String coverPictureUrl;
     private Set contentCollectionses = new HashSet(0);
     private Set sharedCollectionses = new HashSet(0);
     private Set featuredCollectionses = new HashSet(0);

    public Collections() {
    }

	
    public Collections(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public Collections(String name, String description, String type, Double ranking, String coverPictureUrl, Set contentCollectionses, Set sharedCollectionses, Set featuredCollectionses) {
       this.name = name;
       this.description = description;
       this.type = type;
       this.ranking = ranking;
       this.coverPictureUrl = coverPictureUrl;
       this.contentCollectionses = contentCollectionses;
       this.sharedCollectionses = sharedCollectionses;
       this.featuredCollectionses = featuredCollectionses;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public Double getRanking() {
        return this.ranking;
    }
    
    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }
    public String getCoverPictureUrl() {
        return this.coverPictureUrl;
    }
    
    public void setCoverPictureUrl(String coverPictureUrl) {
        this.coverPictureUrl = coverPictureUrl;
    }
    public Set getContentCollectionses() {
        return this.contentCollectionses;
    }
    
    public void setContentCollectionses(Set contentCollectionses) {
        this.contentCollectionses = contentCollectionses;
    }
    public Set getSharedCollectionses() {
        return this.sharedCollectionses;
    }
    
    public void setSharedCollectionses(Set sharedCollectionses) {
        this.sharedCollectionses = sharedCollectionses;
    }
    public Set getFeaturedCollectionses() {
        return this.featuredCollectionses;
    }
    
    public void setFeaturedCollectionses(Set featuredCollectionses) {
        this.featuredCollectionses = featuredCollectionses;
    }




}


