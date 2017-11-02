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

/**
 *
 * @author santiago
 */
public class StreamRainMySQLDAO implements IDAOService{
    

    public <T> T save(final T o, final String tenantID){
      Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
      return (T) dbSession.save(o);
    }

    public void delete(final Object object, final String tenantID){
       Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
       dbSession.delete(object);
    }

    public <T> T get(final Class<T> type, final Long id, final String tenantID){
       Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
       return (T) dbSession.get(type, id);
    }

    public <T> T merge(final T o, final String tenantID)   {
      Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
      return (T) dbSession.merge(o);
    }

    public <T> boolean saveOrUpdate(final T o, final String tenantID){
      Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
      dbSession.saveOrUpdate(o);
      return true;
    }

    public <T> List<T> getAll(final Class<T> type, final String tenantID) {
      Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
      final Criteria crit = dbSession.createCriteria(type);
      return crit.list();
    }
}
