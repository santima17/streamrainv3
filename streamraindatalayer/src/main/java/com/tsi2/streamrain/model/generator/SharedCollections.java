package com.tsi2.streamrain.model.generator;

import java.util.Date;

/**
 * SharedCollections generated by hbm2java
 */
public class SharedCollections  implements java.io.Serializable {


     private Integer id;
     private Collections collections;
     private Users usersByDestinationUserId;
     private Users usersByUserId;
     private Date date;

    public SharedCollections() {
    }

    public SharedCollections(Collections collections, Users usersByDestinationUserId, Users usersByUserId, Date date) {
       this.collections = collections;
       this.usersByDestinationUserId = usersByDestinationUserId;
       this.usersByUserId = usersByUserId;
       this.date = date;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Collections getCollections() {
        return this.collections;
    }
    
    public void setCollections(Collections collections) {
        this.collections = collections;
    }
    public Users getUsersByDestinationUserId() {
        return this.usersByDestinationUserId;
    }
    
    public void setUsersByDestinationUserId(Users usersByDestinationUserId) {
        this.usersByDestinationUserId = usersByDestinationUserId;
    }
    public Users getUsersByUserId() {
        return this.usersByUserId;
    }
    
    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }




}


