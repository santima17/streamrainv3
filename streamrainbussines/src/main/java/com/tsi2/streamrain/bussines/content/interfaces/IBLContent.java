package com.tsi2.streamrain.bussines.content.interfaces;

import java.util.Date;
import java.util.List;

import com.tsi2.streamrain.model.generator.Contents;

public interface IBLContent {

	boolean saveContent(final Contents contents, final String tenantID);

	List<Contents> getAllContents(final String tenantID);

	Contents getContentById(final Integer contentID, final String tenantID);

	void updateContent(final Contents content, final String tenantID);

	void deleteContent(final Integer contentID, final String tenantID);

	boolean voteContent(final Integer contentID, final String userNickname, final Integer rank, final String tenantID);

	boolean addContentToFav(final Integer contentID, final String userNickname, final boolean isFav,
			final String tenantID);

	boolean addCommentToContent(final Integer contentID, final String userNickname, final String text,
			final boolean delete, final String tenantID);

	boolean addViewToContent(final Integer contentID, final String userNickname, final Date dateStart, final Date dateFinish,
			final int second, final String tenantID);

}
