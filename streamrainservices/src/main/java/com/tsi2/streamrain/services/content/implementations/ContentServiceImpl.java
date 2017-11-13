package com.tsi2.streamrain.services.content.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.content.implementations.BLContentImpl;
import com.tsi2.streamrain.bussines.content.interfaces.IBLContent;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.content.ContentConverter;
import com.tsi2.streamrain.converters.content.UserContentViewConverter;
import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.datatypes.content.UserContentCommentDto;
import com.tsi2.streamrain.datatypes.content.UserContentFavDto;
import com.tsi2.streamrain.datatypes.content.UserContentViewDto;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.UserViews;
import com.tsi2.streamrain.services.content.interfaces.IContentService;

public class ContentServiceImpl implements IContentService {

	IBLContent contentBussines = (BLContentImpl) StremRainUserBussinesContextLoader.contextLoader()
			.getBean("contentBussines");
	
	IConverter<ContentDto, Contents> contentConverter = (ContentConverter) StremRainFacadesContextLoader.contextLoader()
			.getBean("contentConverter");

	IConverter<UserContentViewDto, UserViews> userContentViewConverter = (UserContentViewConverter) StremRainFacadesContextLoader
			.contextLoader().getBean("userContentViewConverter");

	public boolean saveContent(final ContentDto content, final String tenantID) {
		contentBussines.saveContent((Contents) contentConverter.deConverter(content), content.getIdCategories(), content.getIdSimilarContents(), tenantID);
		return true;
	}

	public List<ContentDto> getAllContents(final String tenantID) {
		List<Contents> list = contentBussines.getAllContents(tenantID);
		return (List<ContentDto>) contentConverter.convertAll(list);
	}

	public ContentDto getContentById(final Integer contentID, final String tenantID) {
		return contentConverter.converter(contentBussines.getContentById(contentID, tenantID));
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
	public boolean addCommentToContent(final UserContentCommentDto userContentCommentDto, final String tenantID) {
		return contentBussines.addCommentToContent(userContentCommentDto.getContentID(),
				userContentCommentDto.getUserNickname(), userContentCommentDto.getText(),
				userContentCommentDto.isDelete(), tenantID);
	}

	@Override
	public boolean getCommentsOfContent(final UserContentCommentDto userContentCommentDto, final String tenantID) {
		return contentBussines.getCommentsOfContent(userContentCommentDto.getContentID(),
				userContentCommentDto.getUserNickname(), tenantID);
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
		return userContentViewConverter.converter(lastUserContentViewDto);

	}

}