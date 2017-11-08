package com.tsi2.streamrain.dao.implementations;

import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOJanusService;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;

public class StreamRainMySQLJanusDAO extends StreamRainMySQLDAO implements IDAOJanusService {

	public JanusCreationTokens getJanusByToken(String token, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		JanusCreationTokens janusToken = (JanusCreationTokens) dbSession.createQuery("select j from JanusCreationTokens j where j.janusToken = :janusToken")
        .setString("janusToken", token)
        .uniqueResult();
		return janusToken;
	}

}
