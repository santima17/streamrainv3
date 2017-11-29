package com.tsi2.streamrain.services.content.implementations;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.bussines.content.implementations.BLContentImpl;
import com.tsi2.streamrain.bussines.content.interfaces.IBLContent;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.janus.JanusLiveContentConverter;
import com.tsi2.streamrain.converters.content.ContentConverter;
import com.tsi2.streamrain.converters.content.UserContentViewConverter;
import com.tsi2.streamrain.converters.content.SharedContentConverter;
import com.tsi2.streamrain.converters.content.UserCommentConverter;
import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.datatypes.content.SharedContentViewDto;
import com.tsi2.streamrain.datatypes.content.UserContentCommentDto;
import com.tsi2.streamrain.datatypes.content.UserContentFavDto;
import com.tsi2.streamrain.datatypes.content.UserContentViewDto;
import com.tsi2.streamrain.datatypes.janus.JanusLiveOnlyInfoDto;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.SharedContents;
import com.tsi2.streamrain.model.generator.UserComments;
import com.tsi2.streamrain.model.generator.UserViews;
import com.tsi2.streamrain.services.content.interfaces.IContentService;

public class ContentServiceImpl implements IContentService {

	IBLContent contentBussines = (BLContentImpl) StremRainUserBussinesContextLoader.contextLoader()
			.getBean("contentBussines");
	
	IConverter<ContentDto, Contents> contentConverter = (ContentConverter) StremRainFacadesContextLoader.contextLoader()
			.getBean("contentConverter");

	IConverter<UserContentViewDto, UserViews> userContentViewConverter = (UserContentViewConverter) StremRainFacadesContextLoader
			.contextLoader().getBean("userContentViewConverter");
	
	IConverter<JanusLiveOnlyInfoDto, Contents> janusLiveContentInfoConverter = (JanusLiveContentConverter) StremRainFacadesContextLoader.contextLoader()
			.getBean("janusLiveContentInfoConverter");
	
	IConverter<SharedContentViewDto, SharedContents> sharedContentConverter = (SharedContentConverter) StremRainFacadesContextLoader.contextLoader()
			.getBean("sharedContentConverter"); 
	
	IConverter<UserContentCommentDto, UserComments> userCommentConverter = (UserCommentConverter) StremRainFacadesContextLoader.contextLoader()
			.getBean("userCommentConverter"); 
	
	

	public Integer saveContent(final ContentDto content, final String tenantID) {
		return contentBussines.saveContent((Contents) contentConverter.deConverter(content), content.getIdCategories(), content.getIdSimilarContents(), tenantID);
	}

	public List<ContentDto> getAllContents(final String tenantID) {
		List<Contents> list = contentBussines.getAllContents(tenantID);
		return (List<ContentDto>) contentConverter.convertAll(list);
	}

	public ContentDto getContentById(final Integer contentID, final String tenantID) {
		Contents content = contentBussines.getContentById(contentID, tenantID);
		if (content == null) {
			return null;
		}
		return contentConverter.converter(content);
	}

	@Override
	public void updateContent(final ContentDto contentDto, final String tenantID) {
		contentBussines.updateContent((Contents) contentConverter.deConverter(contentDto), tenantID);

	}

	@Override
	public void deleteContent(final Integer contentID, final String tenantID) {
		contentBussines.deleteContent(contentID, tenantID);
	}

	@Override
	public boolean voteContent(final Integer contentID, final String userNickname, final Integer rank,
			final String tenantID) {
		return contentBussines.voteContent(contentID, userNickname, rank, tenantID);
	}

	@Override
	public Double getContentRaiting(final Integer contentID, final String tenantID) {
		return contentBussines.getContentRaiting(contentID, tenantID);
	}

	@Override
	public boolean addContentToFav(final UserContentFavDto userContentFavDto, final String tenantID) {
		return contentBussines.addContentToFav(userContentFavDto.getContentID(), userContentFavDto.getUserNickname(),
				userContentFavDto.isFav(), tenantID);
	}

	@Override
	public boolean removeContentOfFav(final UserContentFavDto userContentFavDto, final String tenantID) {
		return contentBussines.removeContentOfFav(userContentFavDto.getContentID(), userContentFavDto.getUserNickname(),
				userContentFavDto.isFav(), tenantID);
	}

	@Override
	public UserContentCommentDto addCommentToContent(final UserContentCommentDto userContentCommentDto, final String tenantID) {
		return userCommentConverter.converter(contentBussines.addCommentToContent(userContentCommentDto.getContentID(),
				userContentCommentDto.getUserNickname(), userContentCommentDto.getText(),
				userContentCommentDto.isDelete(), tenantID));
	}

	@Override
	public boolean addViewToContent(final UserContentViewDto userContentViewDto, final String tenantID) {
		return contentBussines.addViewToContent(userContentViewDto.getContentID(), userContentViewDto.getUserNickname(),
				userContentViewDto.getDateStart(), userContentViewDto.getDateFinish(), userContentViewDto.getSecond(),
				tenantID);
	}

	@Override
	public UserContentViewDto getLastViewToContent(final UserContentViewDto userContentViewDto, final String tenantID) {
		UserViews lastUserContentViewDto = contentBussines.getLastViewToContent(userContentViewDto.getContentID(),
				userContentViewDto.getUserNickname(), tenantID);
		if (lastUserContentViewDto == null) {
			return null;
		}
		return userContentViewConverter.converter(lastUserContentViewDto);

	}

	public boolean spolierMarkComment(final String userNickName, final Integer userCommentId, final String tenantID) {
		return contentBussines.spolierMarkComment(userNickName, userCommentId, tenantID);
	}

	@Override
	public List<JanusLiveOnlyInfoDto> getAllLiveOnlyContents(String tenantID) {
		List<Contents> list = contentBussines.getAllContents(tenantID);
		List<Contents> liveOnlyList = new ArrayList<Contents>();
		for(Contents content : list) {
			if(content.getLiveOnlyContents() != null) {
				liveOnlyList.add(content);
			}
		}
		return janusLiveContentInfoConverter.convertAll(liveOnlyList);
	}

	public boolean addChatMessageToContent(final Integer idJanusServer, final String jsonChatMessage, final String tenantID) {
		return contentBussines.addChatMessageToContent(idJanusServer, jsonChatMessage, tenantID);
	}

	@Override
	public Integer getContentRaitingOfUser(final Integer contentID, final String userNickName, final String tenantID) {
		return contentBussines.getContentRaitingOfUser(contentID, userNickName, tenantID);
	}

	@Override 
	public boolean shareContent(final SharedContentViewDto sharedContent, final String tenantID) {
		return contentBussines.shareContent(sharedContent.getContentId(), sharedContent.getDate(), sharedContent.getUsersByDestinationUserId(), sharedContent.getUsersByUserId(), tenantID);
	} 

	@Override
	public List<SharedContentViewDto> getShareContent(final String userNickName, final Integer searchType, final String tenantID) {
		return sharedContentConverter.convertAll(contentBussines.getShareContent(userNickName, searchType, tenantID));
	}


	public boolean updateViewContent(UserContentViewDto lastViewDto, final Integer contentID, final String userNickName, final String tenantID) {
		return contentBussines.updateViewContent(userContentViewConverter.deConverter(lastViewDto), contentID, userNickName, tenantID);
	}
	
	@Override
	public String isFav(final Integer contentID, final String userNickName, final String tenantID) {
		return contentBussines.isFav(contentID, userNickName, tenantID); 
	}

	public List<UserContentCommentDto> getAllCommentOfContent(final Integer contentId, final String tenantID) {
		List<UserComments> list = contentBussines.getCommentsOfContent(contentId, tenantID);
		return userCommentConverter.convertAll(list);
	}

	public boolean isSpoilerComment(final Integer commentId, final String tenantID) {
		return contentBussines.isSpoilerComment(commentId, tenantID);
	}

}