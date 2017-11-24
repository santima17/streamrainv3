package com.tsi2.streamrain.datatypes.user;

import java.util.Date;

public class UserSuscriptionDto {

	private Integer id;
    private Integer paymentMethodId;
    private Integer userId;
    private Date datePayment;
    private Date dateStart;
    private Date dateFinish;
    private String janusUserToken;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(Integer paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getJanusUserToken() {
		return janusUserToken;
	}
	public void setJanusUserToken(String janusUserToken) {
		this.janusUserToken = janusUserToken;
	}
    
    
}
