/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsi2.streamrain.dao.implementations;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOService;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 *
 * @author santiago
 */
public class StreamRainMySQLDAO implements IDAOService {

	public <T> Integer save(final T o, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Transaction tx = dbSession.getTransaction();
		Object id = "";
		try {
			tx.begin();
			id = dbSession.save(o);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			return null;
		} 
		if (id instanceof Integer) {
			return (Integer) id;
		} else {
			return 0;
		}
	}

	public void delete(final Object object, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		dbSession.delete(object);
	}

	public <T> T get(final Class<T> type, final Integer id, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Transaction tx = dbSession.getTransaction();
		try {
			tx.begin();
			T result = dbSession.get(type, id);
			tx.commit();
			return result;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			return null;
		} 
	}

	public <T> T merge(final T o, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		return (T) dbSession.merge(o);
	}

	public <T> boolean saveOrUpdate(final T o, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Transaction tx = dbSession.getTransaction();
		try {
			tx.begin();
			dbSession.update(o);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			return false;
		} 
		return true;
	}

	public <T> List<T> getAll(final Class<T> type, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		final Criteria crit = dbSession.createCriteria(type);
		return crit.list();
	}

	public <T> List<T> getAllByExample(final Class<T> type, final T objectQuery, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Example objectExample = Example.create(objectQuery);
		final Criteria crit = dbSession.createCriteria(type).add(objectExample);
		return crit.list();
	}
}
