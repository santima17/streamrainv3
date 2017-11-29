package com.tsi2.streamrain.dao.implementations;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOContentService;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.UserViews;
import com.tsi2.streamrain.model.generator.Users;

public class StreamRainMySQLContentDAO extends StreamRainMySQLDAO implements IDAOContentService{

	public int updateContentView(UserViews userViews, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Transaction tx = dbSession.getTransaction();
		try {
			tx.begin();
			SQLQuery  query = dbSession.createSQLQuery("update user_views u set u.second = :second where u.id = :id");
			query.setParameter("second", userViews.getSecond());
			query.setParameter("id", userViews.getId());
			query.addSynchronizedEntityClass(UserViews.class);
			int result = query.executeUpdate();
			tx.commit();
			return result;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			return -1;
		} 
	}
	
	public List<UserViews> findUserViewByContentAndUser(Users user, Contents content, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Transaction tx = dbSession.getTransaction();
		try {
			tx.begin();
			List<UserViews> result = (List<UserViews>) dbSession.createQuery("select u from UserViews u where u.contents = :content and u.users = :user")
					.setEntity("content", content)
					.setEntity("user", user).list();
			tx.commit();
			return result;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			return null;
		}
	}

	public boolean existSpolierMark(Integer commentId, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Query query = dbSession
				.createSQLQuery("select * from spoiler_marks where user_comment_id=:commentId")
				.setParameter("commentId", commentId);
		if (query.list().isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

}
