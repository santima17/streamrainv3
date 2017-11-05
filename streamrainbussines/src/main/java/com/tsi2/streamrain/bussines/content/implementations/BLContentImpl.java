package com.tsi2.streamrain.bussines.content.implementations;


import java.util.Date;
import java.util.List;

import com.tsi2.streamrain.bussines.content.interfaces.IBLContent;
import com.tsi2.streamrain.bussines.user.implementations.BLUserImpl;
import com.tsi2.streamrain.bussines.user.interfaces.IBLUser;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLUserDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.UserComments;
import com.tsi2.streamrain.model.generator.UserFavs;
import com.tsi2.streamrain.model.generator.UserRatings;
import com.tsi2.streamrain.model.generator.Users;

public class BLContentImpl implements IBLContent {

	IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");
	
	IDAOUserService daoUserService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
	
	IBLUser userBussines = (BLUserImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("userBussines");
	
	public boolean saveContent(final Contents contents, final String tenantID) {
		daoService.save(contents, tenantID);
		/*for(Iterator it = contents.getContentCastses().iterator(); it.hasNext();) {
		    ContentCasts x = (ContentCasts)it.next();
		    daoService.save(x, tenantID);
		}*/
		return true; 
	}

	public List<Contents> getAllContents(final String tenantID) {
		return daoService.getAll(Contents.class, tenantID);
	}

	public Contents getContentById(final Integer contentID, final String tenantID) {
		return daoService.get(Contents.class, contentID, tenantID);
	}

	public void updateContent(final Contents content, final String tenantID) {
		daoService.saveOrUpdate(content, tenantID);
	}

	public void deleteContent(final Integer contentID, final String tenantID) {
		Contents content = getContentById(contentID, tenantID);
		//cambiar el baja logica a true
		daoService.saveOrUpdate(content, tenantID);
	}

	public boolean voteContent(final Integer contentID, final String userNickname, final Integer rank, final String tenantID) {
		UserRatings userRatings = new UserRatings();
		userRatings.setRate(rank);
		userRatings.setUseForRecommendations(true);
		userRatings.setContents(daoService.get(Contents.class, contentID, tenantID));
		userRatings.setUsers(daoUserService.getUserByNickname(userNickname, tenantID));
		daoService.save(userRatings, tenantID);
		return true;
	}

	public boolean addContentToFav(Integer contentID, String userNickname, boolean isFav, String tenantID) {
		UserFavs userFavs = new UserFavs();
		userFavs.setContents(daoService.get(Contents.class, contentID, tenantID));
		userFavs.setFav(isFav);
		userFavs.setUsers(daoUserService.getUserByNickname(userNickname, tenantID));
		//userFavs.setDate(new Date());
		daoService.save(userFavs, tenantID);
		return true;
	}

	public boolean addCommentToContent(Integer contentID, String userNickname, String text, boolean delete,
			String tenantID) {
		UserComments userComments = new UserComments();
		userComments.setDate(new Date());
		userComments.setIsDeleted(delete);
		userComments.setText(text);
		userComments.setUsers(daoUserService.getUserByNickname(userNickname, tenantID));
		//userComments.setContents(daoService.get(Contents.class, contentID, tenantID));
		daoService.save(userComments, tenantID);
		return true;
		
	}
	

}
