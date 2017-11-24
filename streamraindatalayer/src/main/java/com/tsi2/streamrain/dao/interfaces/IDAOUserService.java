package com.tsi2.streamrain.dao.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.UserRatings;
import com.tsi2.streamrain.model.generator.Users;

public interface IDAOUserService extends IDAOService {
	
	Users getUserByNickname(final String userNickname, final String tenantID); 
	
	boolean findByNickname(final String nickname, final String tenantID);

	boolean findByNicknamePassword(final String nickname, final String password, final String tenantID);

	boolean findByTwitterId(final String twitterId, final String tenantID);
	
	List<UserRatings> getRankForUser(Integer contentID, Integer userID, String tenantID);

}
