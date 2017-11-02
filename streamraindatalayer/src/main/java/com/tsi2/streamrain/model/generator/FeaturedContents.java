package com.tsi2.streamrain.model.generator;
// Generated 04/10/2017 11:03:07 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * FeaturedContents generated by hbm2java
 */
public class FeaturedContents  implements java.io.Serializable {


     private Integer id;
     private Contents contents;
     private Date dateStart;
     private Date dateFinish;

    public FeaturedContents() {
    }

    public FeaturedContents(Contents contents, Date dateStart, Date dateFinish) {
       this.contents = contents;
       this.dateStart = dateStart;
       this.dateFinish = dateFinish;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Contents getContents() {
        return this.contents;
    }
    
    public void setContents(Contents contents) {
        this.contents = contents;
    }
    public Date getDateStart() {
        return this.dateStart;
    }
    
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    public Date getDateFinish() {
        return this.dateFinish;
    }
    
    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }




}


