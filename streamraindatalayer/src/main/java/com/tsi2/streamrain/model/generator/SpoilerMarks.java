package com.tsi2.streamrain.model.generator;

import java.util.Date;

/**
 * SpoilerMarks generated by hbm2java
 */
public class SpoilerMarks  implements java.io.Serializable {


     private SpoilerMarksId id;
     private UserComments userComments;
     private Users users;
     private Date date;

    public SpoilerMarks() {
    }

    public SpoilerMarks(SpoilerMarksId id, UserComments userComments, Users users, Date date) {
       this.id = id;
       this.userComments = userComments;
       this.users = users;
       this.date = date;
    }
   
    public SpoilerMarksId getId() {
        return this.id;
    }
    
    public void setId(SpoilerMarksId id) {
        this.id = id;
    }
    public UserComments getUserComments() {
        return this.userComments;
    }
    
    public void setUserComments(UserComments userComments) {
        this.userComments = userComments;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }




}


