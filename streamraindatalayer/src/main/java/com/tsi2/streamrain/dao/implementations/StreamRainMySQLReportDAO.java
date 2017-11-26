package com.tsi2.streamrain.dao.implementations;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOReportService;

public class StreamRainMySQLReportDAO extends StreamRainMySQLDAO implements IDAOReportService{

	
	public List getRankByDate(final Integer contentID, final Date dateFrom, final Date dateTo, final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);

		Query query = dbSession
				.createSQLQuery("select * from user_ratings where content_id = :contentId and date <= :dateTo and date >= :dateFrom order by date")
				.setParameter("contentId", contentID)
				.setParameter("dateTo",dateTo)
				.setParameter("dateFrom", dateFrom);

		return query.list();
	}
	
	public List getTopFive(final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);

		Query query = dbSession
				.createSQLQuery("select * from contents order by ranking desc");

		return query.list();
	}
	
	public List getUsersByCountry(final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);

		Query query = dbSession
				.createSQLQuery("select count(*), country from users group by country");

		return query.list();
	}
	
	
	

}
