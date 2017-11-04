package com.tsi2.streamrain.dao.implementations;

import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOTenantService;
import com.tsi2.streamrain.model.main.Tenants;

public class StreamRainMySQLTenantDAO extends StreamRainMySQLMainDAO implements IDAOTenantService{
	
	public boolean findByNickname(final String nickname) {
		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		Tenants user = (Tenants) dbSession.createQuery("select u from Tenants u where u.generatorUser = :nickname")
        .setString("nickname", nickname)
        .uniqueResult();
		return user != null;
	}

	public boolean findByNicknamePassword(final String nickname, final String password) {
		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		Tenants user = (Tenants) dbSession.createQuery("select u from Tenants u where u.generatorUser = :nickname and u.generatorPassword = :pass")
        .setString("nickname", nickname)
        .setString("pass", password)
        .uniqueResult();
		return user != null;
	}

}
