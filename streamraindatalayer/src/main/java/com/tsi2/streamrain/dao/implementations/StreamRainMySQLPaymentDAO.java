package com.tsi2.streamrain.dao.implementations;

import java.util.Date;

import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOPaymentService;
import com.tsi2.streamrain.model.generator.UserSubscriptions;

public class StreamRainMySQLPaymentDAO extends StreamRainMySQLDAO implements IDAOPaymentService{

	public UserSubscriptions existeValidSubscription(final Integer userID, final Date dateExpiration, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		UserSubscriptions userSubscription = (UserSubscriptions) dbSession.createQuery(
				"select us from UserSubscriptions us where :dateExpiration < us.dateFinish")
				.setDate("dateExpiration", dateExpiration).uniqueResult();
		return userSubscription;
	}

}
