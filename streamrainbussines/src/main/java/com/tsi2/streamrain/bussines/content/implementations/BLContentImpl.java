package com.tsi2.streamrain.bussines.content.implementations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tsi2.streamrain.bussines.content.interfaces.IBLContent;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMongoDbDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLContentDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLUserDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOContentService;
import com.tsi2.streamrain.dao.interfaces.IDAOMongoDBService;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.model.generator.AlwaysAvailableContents;
import com.tsi2.streamrain.model.generator.Categories;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.SharedContents;
import com.tsi2.streamrain.model.generator.SpoilerMarks;
import com.tsi2.streamrain.model.generator.SpoilerMarksId;
import com.tsi2.streamrain.model.generator.UserComments;
import com.tsi2.streamrain.model.generator.UserFavs;
import com.tsi2.streamrain.model.generator.UserFavsId;
import com.tsi2.streamrain.model.generator.UserRatings;
import com.tsi2.streamrain.model.generator.UserRatingsId;
import com.tsi2.streamrain.model.generator.UserViews;
import com.tsi2.streamrain.model.generator.Users;

public class BLContentImpl implements IBLContent {

	IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");

	IDAOContentService daoContentService = (StreamRainMySQLContentDAO) StremRainDataContextLoader.contextLoader()
			.getBean("daoContentService");

	IDAOUserService daoUserService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader()
			.getBean("daoUserService");

	IDAOMongoDBService daoMongoDbService = (StreamRainMongoDbDAO) StremRainDataContextLoader.contextLoader()
			.getBean("daoMongoDBService");

	public Integer saveContent(Contents contents, final List<Integer> idCategories,
			final List<Integer> idSimilarContent, final String tenantID) {
		try {
			Set<Categories> categories = new HashSet<Categories>();
			if (idCategories != null) {
				for (Integer idCategory : idCategories) {
					Categories cat = daoService.get(Categories.class, idCategory, tenantID);
					categories.add(cat);
				}
			}
			contents.setCategorieses(categories);
			Set<Contents> similarContents = new HashSet<Contents>();
			if (idSimilarContent != null) {
				for (Integer idSimilarCont : idSimilarContent) {
					Contents content = daoService.get(Contents.class, idSimilarCont, tenantID);
					similarContents.add(content);
				}
			}
			contents.setContentsesForIdContent1(similarContents);
			return daoService.save(contents, tenantID);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

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
		// cambiar el baja logica a true
		daoService.saveOrUpdate(content, tenantID);
	}

	public boolean voteContent(final Integer contentID, final String userNickname, final Integer rank,
			final String tenantID) {
		UserRatings userRatings = new UserRatings();
		userRatings.setRate(rank);
		userRatings.setUseForRecommendations(true);
		userRatings.setContents(daoService.get(Contents.class, contentID, tenantID));
		Users user = daoUserService.getUserByNickname(userNickname, tenantID);
		userRatings.setUsers(user);
		UserRatingsId uID = new UserRatingsId();
		uID.setContentId(contentID);
		uID.setUserId(user.getId());
		uID.setDate(new Date());
		userRatings.setId(uID);
		daoService.save(userRatings, tenantID);
		return true;
	}

	public Double getContentRaiting(Integer contentID, String tenantID) {
		Contents content = daoService.get(Contents.class, contentID, tenantID);
		return content.getRanking();
	}

	public boolean addContentToFav(Integer contentID, String userNickname, boolean isFav, String tenantID) {
		UserFavs userFavs = new UserFavs();
		userFavs.setContents(daoService.get(Contents.class, contentID, tenantID));
		userFavs.setFav(isFav);
		Users user = daoUserService.getUserByNickname(userNickname, tenantID);
		userFavs.setUsers(user);
		UserFavsId uID = new UserFavsId();
		uID.setContentId(contentID);
		uID.setDate(new Date());
		uID.setUserId(user.getId());
		userFavs.setId(uID);
		daoService.save(userFavs, tenantID);
		return true;
	}

	public boolean removeContentOfFav(Integer contentID, String userNickname, boolean isFav, String tenantID) {
		UserFavs userFavs = new UserFavs();
		userFavs.setContents(daoService.get(Contents.class, contentID, tenantID));
		userFavs.setFav(isFav);
		Users user = daoUserService.getUserByNickname(userNickname, tenantID);
		userFavs.setUsers(user);
		UserFavsId uID = new UserFavsId();
		uID.setContentId(contentID);
		uID.setDate(new Date());
		uID.setUserId(user.getId());
		userFavs.setId(uID);
		daoService.save(userFavs, tenantID);
		return true;
	}

	public UserComments addCommentToContent(Integer contentID, String userNickname, String text, boolean delete,
			String tenantID) {
		UserComments userComments = new UserComments();
		userComments.setDate(new Date());
		userComments.setIsDeleted(delete);
		userComments.setText(text);
		userComments.setUsers(daoUserService.getUserByNickname(userNickname, tenantID));
		userComments.setAlwaysAvailableContents(daoService.get(AlwaysAvailableContents.class, contentID, tenantID));
		daoService.save(userComments, tenantID);
		return userComments;
	}

	public List<UserComments> getCommentsOfContent(final Integer contentID, final String tenantID) {
		AlwaysAvailableContents content = daoService.get(AlwaysAvailableContents.class, contentID, tenantID);
		UserComments userComments = new UserComments();
		userComments.setAlwaysAvailableContents(content);
		return daoService.getAllByExample(UserComments.class, userComments, tenantID);
	}

	public boolean addViewToContent(Integer contentID, String userNickname, Date dateStart, Date dateFinish, int second,
			String tenantID) {
		UserViews userViews = new UserViews();
		userViews.setDateStart(dateStart);
		userViews.setDateFinish(dateFinish);
		userViews.setSecond(second);
		userViews.setUsers(daoUserService.getUserByNickname(userNickname, tenantID));
		userViews.setContents(daoService.get(Contents.class, contentID, tenantID));
		daoService.save(userViews, tenantID);
		return true;
	}

	public UserViews getLastViewToContent(Integer contentID, String userNickname, String tenantID) {
		Contents contents = daoService.get(Contents.class, contentID, tenantID);
		Users user = daoUserService.getUserByNickname(userNickname, tenantID);
		List<UserViews> views = daoContentService.findUserViewByContentAndUser(user, contents, tenantID);
		Integer id = 0;
		UserViews result = null;
		if (views != null && !views.isEmpty()) {
			Iterator<UserViews> it = views.iterator();
			while (it.hasNext()) {
				UserViews userView = it.next();
				if (userView.getUsers().getNickname().equals(userNickname) && userView.getId() > id) {
					result = userView;
					id = userView.getId();
				}
			}
		}
		return result;
	}

	public boolean spolierMarkComment(final String userNickName, final Integer userCommentId, final String tenantID) {
		try {
			Users user = daoUserService.getUserByNickname(userNickName, tenantID);
			UserComments userComments = daoService.get(UserComments.class, userCommentId, tenantID);
			SpoilerMarks spoilerMarks = new SpoilerMarks();
			SpoilerMarksId id = new SpoilerMarksId();
			id.setUserId(user.getId());
			id.setUserCommentId(userComments.getId());
			spoilerMarks.setUserComments(userComments);
			spoilerMarks.setUsers(user);
			spoilerMarks.setDate(new Date());
			spoilerMarks.setId(id);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean addChatMessageToContent(final Integer idJanusServer, final String jsonChatMessage, String tenantID) {
		daoMongoDbService.saveChatMessageToContent(idJanusServer, jsonChatMessage, tenantID);
		return true;
	}

	public Integer getContentRaitingOfUser(Integer contentID, String userNickName, String tenantID) {
		Users user = daoUserService.getUserByNickname(userNickName, tenantID);
		List<Object[]> urList = daoUserService.getRankForUser(contentID, user.getId(), tenantID);
		if (urList.isEmpty())
			return 0;
		return (Integer)urList.get(0)[3];
	}

	public boolean shareContent(final Integer contentId, final Date date, final Integer usersByDestinationUserId,
			final Integer usersByUserId, final String tenantID) {
		SharedContents sharedContent = new SharedContents();
		sharedContent.setDate(date);
		sharedContent.setContents(daoService.get(Contents.class, contentId, tenantID));
		sharedContent.setUsersByDestinationUserId(daoUserService.get(Users.class, usersByDestinationUserId, tenantID));
		sharedContent.setUsersByUserId(daoUserService.get(Users.class, usersByUserId, tenantID));
		daoService.save(sharedContent, tenantID);
		return true;
	}

	public List<SharedContents> getShareContent(final String userNickName, final Integer searchType,
			final String tenantID) {
		Users user = daoUserService.getUserByNickname(userNickName, tenantID);
		List<Object[]> list = daoUserService.getShareContent(user.getId(), searchType, tenantID);
		List<SharedContents> newList = new ArrayList<SharedContents>();
		for (Object[] ur : list) {
			SharedContents sharedContent = new SharedContents();
			sharedContent.setId((Integer) ur[0]);
			sharedContent.setContents(daoService.get(Contents.class, (Integer) ur[1], tenantID));
			sharedContent.setUsersByUserId(daoService.get(Users.class, (Integer) ur[2], tenantID));
			sharedContent.setUsersByDestinationUserId(daoService.get(Users.class, (Integer) ur[3], tenantID));
			sharedContent.setDate((Date) ur[4]);
			newList.add(sharedContent);
		}
		return newList;
	}

	public boolean updateViewContent(final UserViews userView, final Integer contentID, final String userNickName,
			final String tenantID) {
		Contents contents = daoService.get(Contents.class, contentID, tenantID);
		Users user = daoUserService.getUserByNickname(userNickName, tenantID);
		userView.setContents(contents);
		userView.setUsers(user);
		int result = daoContentService.updateContentView(userView, tenantID);
		return result != 0;
	}

	public String isFav(Integer contentID, String userNickName, String tenantID) {
		Users user = daoUserService.getUserByNickname(userNickName, tenantID);
		UserFavsId fID = new UserFavsId();
		fID.setUserId(user.getId());
		fID.setContentId(contentID);
		Boolean fav = daoUserService.getFav(fID, tenantID);
		if (fav != null && fav) {
			return "true";
		} else {
			return "false";
		}
	}

}
