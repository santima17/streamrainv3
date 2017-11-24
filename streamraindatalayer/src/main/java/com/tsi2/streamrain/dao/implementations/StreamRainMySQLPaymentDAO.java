package com.tsi2.streamrain.dao.implementations;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOPaymentService;
import com.tsi2.streamrain.model.generator.UserSubscriptions; 

public class StreamRainMySQLPaymentDAO extends StreamRainMySQLDAO implements IDAOPaymentService {

	public Integer existeValidSubscription(final Integer userID, final Date dateExpiration,
			final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);

		Query query = dbSession.createSQLQuery(
				"select id from user_subscriptions where user_id = :userID and :nowDate < date_finish order by date_finish")
				.setParameter("userID", userID).setParameter("nowDate", new Date());

		if (!query.list().isEmpty()) {
			return (Integer) query.list().get(0);
		} else {
			return null;
		}
	}

}
