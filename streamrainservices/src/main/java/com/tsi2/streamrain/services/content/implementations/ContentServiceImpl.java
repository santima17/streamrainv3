package com.tsi2.streamrain.services.content.implementations;

import com.tsi2.streamrain.bussines.content.implementations.BLContentImpl;
import com.tsi2.streamrain.bussines.content.interfaces.IBLContent;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.content.ContentConverter;
import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.services.content.interfaces.IContentService;

public class ContentServiceImpl implements IContentService{

	public boolean saveContent(final ContentDto content, final String tenantID) {
		IBLContent daoService = (BLContentImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("contentBussines");
		IConverter contentConverter = (ContentConverter)StremRainFacadesContextLoader.contextLoader().getBean("contentConverter");
		daoService.saveContent((Contents)contentConverter.deConverter(content), tenantID);
		return true;
	}

}