package com.tsi2.streamrain.bussines.content.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.Contents;

public interface IBLContent {
	
	boolean saveContent(final Contents contents, final String tenantID);

	List<Contents> getAllContents(final String tenantID);

	Contents getContentById(final Integer contentID, final String tenantID);

	void updateContent(final Contents content, final String tenantID);

	void deleteContent(final Integer contentID, final String tenantID);

	boolean voteContent(final Integer contentID, final String userNickname, final Integer rank, final String tenantID);

	boolean addContentToFav(final Integer contentID, final String userNickname, final boolean isFav, final String tenantID);

	boolean addCommentToContent(Integer contentID, String userNickname, String text, boolean delete, String tenantID);


}
