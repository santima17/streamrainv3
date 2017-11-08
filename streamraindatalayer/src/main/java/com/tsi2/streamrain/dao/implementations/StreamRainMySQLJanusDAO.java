package com.tsi2.streamrain.dao.implementations;

import java.util.Date;

import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOJanusService;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;

public class StreamRainMySQLJanusDAO extends StreamRainMySQLDAO implements IDAOJanusService {

	public JanusCreationTokens getJanusByToken(String token, Date dateExpiration, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		JanusCreationTokens janusToken = (JanusCreationTokens) dbSession.createQuery("select j from JanusCreationTokens j where j.janusToken = :janusToken and j.dateExpiration < :dateExpiration")
        .setString("janusToken", token)
        .setDate("dateExpiration", dateExpiration)
        .uniqueResult();
		return janusToken;
	}

}
