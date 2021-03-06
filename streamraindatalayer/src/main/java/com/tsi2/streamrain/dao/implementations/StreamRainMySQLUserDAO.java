package com.tsi2.streamrain.dao.implementations;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tsi2.streamrain.context.DBHibernateUtil;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.model.generator.SharedContents;
import com.tsi2.streamrain.model.generator.UserFavs;
import com.tsi2.streamrain.model.generator.UserFavsId;
import com.tsi2.streamrain.model.generator.UserPpvs;
import com.tsi2.streamrain.model.generator.UserRatings;
import com.tsi2.streamrain.model.generator.Users;

public class StreamRainMySQLUserDAO extends StreamRainMySQLDAO implements IDAOUserService {
 
	public boolean findByNickname(String nickname, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Users user = (Users) dbSession.createQuery("select u from Users u where u.nickname = :nickname")
				.setString("nickname", nickname).uniqueResult();
		return user != null;
	}

	public boolean findByNicknamePassword(String nickname, String password, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Users user = (Users) dbSession
				.createQuery("select u from Users u where u.nickname = :nickname and u.password = :pass")
				.setString("nickname", nickname).setString("pass", password).uniqueResult();
		return user != null;
	}

	public boolean findByTwitterId(String twitterId, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Users user = (Users) dbSession.createQuery("select u from Users u where u.twitterUserId = :twitterUserId")
				.setString("twitterUserId", twitterId).uniqueResult();
		return user != null;
	}

	public Users getUserByNickname(String userNickname, String tenantID) {
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Users user = (Users) dbSession.createQuery("select u from Users u where u.nickname = :nickname")
				.setString("nickname", userNickname).uniqueResult();
		return user;
	}

	public List<Object[]> getRankForUser(Integer contentID, Integer userID, String tenantID) {

		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);

		Query query = dbSession
				.createSQLQuery("select * from user_ratings where user_id = :userID and content_id = :contentID order by date desc")
				.setParameter("userID", userID).setParameter("contentID", contentID);

		return query.list();
	}
	
	public Boolean getFav(UserFavsId id, String tenantID) {
 
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Query query = dbSession
				.createSQLQuery("select fav from user_favs where user_id=:userID and content_id=:contentID order by date desc")
				.setParameter("userID", id.getUserId()).setParameter("contentID", id.getContentId());
		if (query.list().isEmpty()) {
			return false;
		}else {
			return (Boolean)query.list().get(0);
		}
	}

	public List<Object[]> getShareContent(Integer userID, Integer searchType, String tenantID) {
		String query = "select * from shared_contents where user_id=:userID ";
		if(searchType == 1) {
			query = "select * from shared_contents where destination_user_id=:userID ";
		}
		Session dbSession = DBHibernateUtil.getSessionFactoryGenerator(tenantID);
		Query querydb = dbSession.createSQLQuery(query).setParameter("userID", userID);
		return querydb.list();
	}

}
