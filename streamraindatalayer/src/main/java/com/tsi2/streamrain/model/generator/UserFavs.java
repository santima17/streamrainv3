package com.tsi2.streamrain.model.generator;

/**
 * UserFavs generated by hbm2java
 */
public class UserFavs  implements java.io.Serializable {


     private UserFavsId id;
     private Contents contents;
     private Users users;
     private boolean fav;

    public UserFavs() {
    }

    public UserFavs(UserFavsId id, Contents contents, Users users, boolean fav) {
       this.id = id;
       this.contents = contents;
       this.users = users;
       this.fav = fav;
    }
   
    public UserFavsId getId() {
        return this.id;
    }
    
    public void setId(UserFavsId id) {
        this.id = id;
    }
    public Contents getContents() {
        return this.contents;
    }
    
    public void setContents(Contents contents) {
        this.contents = contents;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public boolean isFav() {
        return this.fav;
    }
    
    public void setFav(boolean fav) {
        this.fav = fav;
    }




}


