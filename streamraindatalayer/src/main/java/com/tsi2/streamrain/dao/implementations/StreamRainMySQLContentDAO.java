package com.tsi2.streamrain.dao.implementations;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOContentService;
import com.tsi2.streamrain.model.generator.UserViews;
import com.tsi2.streamrain.model.generator.Users;

public class StreamRainMySQLContentDAO extends StreamRainMySQLDAO implements IDAOContentService{

	public int updateContentView(UserViews userViews, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Query query = dbSession.createQuery("update from UserViews u set u.second = :second where u.id = :id");
		query.setParameter("second", userViews.getSecond());
		query.setParameter("id", userViews.getId());
		return query.executeUpdate();
	}

}
