package com.tsi2.streamrain.dao.implementations;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOReportService;

public class StreamRainMySQLReportDAO extends StreamRainMySQLDAO implements IDAOReportService{
 
	  
	public List getFavByMovie(final String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);

		Query query = dbSession
				.createSQLQuery("select  c.name, count(*) as total from user_favs as f join contents as c on c.id = content_id group by name");

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
