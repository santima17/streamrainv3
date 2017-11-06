package com.tsi2.streamrain.datatypes.payment;

import java.util.Date;

public class UserContentPPVDto {

    private Integer idContent;
    private Integer idPaymentMethods;
    private String userNickName;
    private Date datePayment;
    
	public Integer getIdContent() {
		return idContent;
	}
	public void setIdContent(Integer idContent) {
		this.idContent = idContent;
	}
	public Integer getIdPaymentMethods() {
		return idPaymentMethods;
	}
	public void setIdPaymentMethods(Integer idPaymentMethods) {
		this.idPaymentMethods = idPaymentMethods;
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
    
    
}
