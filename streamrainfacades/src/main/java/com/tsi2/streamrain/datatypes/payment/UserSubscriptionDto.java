package com.tsi2.streamrain.datatypes.payment;

import java.util.Date;

public class UserSubscriptionDto {
	
	private Integer idPaymentMethod;
	private String userNickName;
	private Date datePayment;
    private Date dateStart;
    private Date dateFinish;
        
	public Integer getIdPaymentMethod() {
		return idPaymentMethod;
	}
	public void setIdPaymentMethod(Integer idPaymentMethod) {
		this.idPaymentMethod = idPaymentMethod;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public Date getDatePayment() {
		return datePayment;
	}
	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateFinish() {
		return dateFinish;
	}
	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}   

}
