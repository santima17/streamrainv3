package com.tsi2.streamrain.dao.interfaces;

import java.util.Date;
import java.util.List;

import com.tsi2.streamrain.model.generator.JanusCreationTokens;
import com.tsi2.streamrain.model.generator.UserPpvs;
import com.tsi2.streamrain.model.generator.UserSubscriptions;

public interface IDAOJanusService extends IDAOService {

	JanusCreationTokens getJanusByToken(final String token, final Date dateExpiration, final String tenantID);

	List<UserSubscriptions> getAllAvailablesUserSubscriptions(final String tenantID);
	
	List<UserSubscriptions> getAvailablesUserSubscriptionsForUser(final int idUser, final String tenantID);
	
	List<UserPpvs> getPpvsForUser(final int userID, final String tenantID);
}
