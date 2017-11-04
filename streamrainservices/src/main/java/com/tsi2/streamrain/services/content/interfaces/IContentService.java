package com.tsi2.streamrain.services.content.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.content.ContentDto;

public interface IContentService {
	
	boolean saveContent(final ContentDto content, final String tenantID);
	
	List<ContentDto> getAllContents(final String tenantID);

}