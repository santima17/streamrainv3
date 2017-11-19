package com.tsi2.streamrain.dao.interfaces;

import java.util.Date;

import com.tsi2.streamrain.model.generator.UserSubscriptions;

public interface IDAOPaymentService extends IDAOService {
	
	UserSubscriptions existeValidSubscription(final Integer userID, final Date dateExpiration, final String tenantID);

}
