package com.tsi2.streamrain.dao.implementations;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOService;

public class StreamRainMySQLMainDAO implements IDAOService {

	public <T> Integer save(final T o, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		Transaction tx = dbSession.beginTransaction();
		dbSession.persist(o);
		tx.commit();
		return 1;
	}

	public void delete(final Object object, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		dbSession.delete(object);
	}

	public <T> T get(final Class<T> type, final Integer id, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		return (T) dbSession.get(type, id);
	}

	public <T> T merge(final T o, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		return (T) dbSession.merge(o);
	}

	public <T> boolean saveOrUpdate(final T o, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		dbSession.saveOrUpdate(o);
		return true;
	}

	public <T> List<T> getAll(final Class<T> type, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryMain();
		final Criteria crit = dbSession.createCriteria(type);
		return crit.list();
	}

}
