package com.tsi2.streamrain.dao.implementations;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOJanusService;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;
import com.tsi2.streamrain.model.generator.UserPpvs;
import com.tsi2.streamrain.model.generator.UserSubscriptions;

public class StreamRainMySQLJanusDAO extends StreamRainMySQLDAO implements IDAOJanusService {

	public JanusCreationTokens getJanusByToken(final String token, final Date dateExpiration, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		JanusCreationTokens janusToken = (JanusCreationTokens) dbSession.createQuery(
				"select j from JanusCreationTokens j where j.janusToken = :janusToken and :dateExpiration < j.dateExpiration")
				.setString("janusToken", token).setDate("dateExpiration", dateExpiration).uniqueResult();
		return janusToken;
	}

	public List<UserSubscriptions> getAllAvailablesUserSubscriptions(final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		List<UserSubscriptions> users = (List<UserSubscriptions>) dbSession
				.createQuery("select u from UserSubscriptions u where :nowDate < u.dateFinish")
				.setDate("nowDate", new Date()).list();
		return users;
	}

	public String getAvailablesUserSubscriptionsForUser(final int idUser, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);

		Query query = dbSession
				.createSQLQuery("select janus_user_token from user_subscriptions where user_id = :userID and :nowDate < date_finish")
				.setParameter("userID", idUser).setParameter("nowDate", new Date());

		return (String)query.uniqueResult();
	}

	public List<UserPpvs> getPpvsForUser(final int userID, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);

		Query query = dbSession.createSQLQuery("select * from user_ppvs where user_id = :userID").setParameter("userID",
				userID);

		return query.list();
	}

	public List<UserSubscriptions> getAllAvailablesUserSubscriptionsByUser(String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		List<UserSubscriptions> users = (List<UserSubscriptions>) dbSession
				.createQuery("select u from UserSubscriptions u where :nowDate < u.dateFinish ")
				.setDate("nowDate", new Date()).list();
		return users;
	}

}
