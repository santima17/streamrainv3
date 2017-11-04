package com.tsi2.streamrain.bussines.content.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.Contents;

public interface IBLContent {
	
	boolean saveContent(final Contents contents, final String tenantID);

	List<Contents> getAllContents(String tenantID);

}
