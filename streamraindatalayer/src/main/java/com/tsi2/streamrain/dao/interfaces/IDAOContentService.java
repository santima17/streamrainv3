package com.tsi2.streamrain.dao.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.UserViews;
import com.tsi2.streamrain.model.generator.Users;

public interface IDAOContentService extends IDAOService{
	
	int updateContentView(final UserViews userViews, final String tenantID);
	
	List<UserViews> findUserViewByContentAndUser(Users user, Contents content, String tenantID);
	
	boolean existSpolierMark(final Integer commentId, final String tenantID);

}
