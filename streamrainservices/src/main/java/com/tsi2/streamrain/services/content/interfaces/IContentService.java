package com.tsi2.streamrain.services.content.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.datatypes.content.UserContentCommentDto;
import com.tsi2.streamrain.datatypes.content.UserContentFavDto;
import com.tsi2.streamrain.datatypes.content.UserContentViewDto;
import com.tsi2.streamrain.datatypes.janus.JanusLiveOnlyInfoDto;

public interface IContentService {
	
	Integer saveContent(final ContentDto content, final String tenantID);
	
	List<ContentDto> getAllContents(final String tenantID);
	
	ContentDto getContentById(final Integer contentID, final String tenantID);

	void updateContent(final ContentDto contentDto, final String tenantID);

	void deleteContent(final Integer contentID, final String tenantID);

	boolean voteContent(final Integer contentID, final String userNickname, final Integer rank, final String tenantID);
	
	Double getContentRaiting(final Integer contentID, final String tenantID);

	boolean addContentToFav(final UserContentFavDto userContentFavDto, final String tenantID);
	
	boolean removeContentOfFav(final UserContentFavDto userContentFavDto, final String tenantID);

	boolean addCommentToContent(final UserContentCommentDto userContentCommentDto, final String tenantID);
	
	boolean getCommentsOfContent(final UserContentCommentDto userContentCommentDto, final String tenantID);

	boolean addViewToContent(final UserContentViewDto userContentViewDto, final String tenantID);
	
	UserContentViewDto getLastViewToContent(final UserContentViewDto userContentViewDto, final String tenantID);

	boolean spolierMarkComment(final String userNickName, final Integer userCommentId, final String tenantID);

	List<JanusLiveOnlyInfoDto> getAllLiveOnlyContents(final String tenantID);

}