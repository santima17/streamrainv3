package com.tsi2.streamrain.services.janus.implementations;

import com.tsi.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.bussines.janus.implementations.BLJanusImpl;
import com.tsi2.streamrain.bussines.janus.interfaces.IBLJanus;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.janus.JanusTokenConverter;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;
import com.tsi2.streamrain.services.janus.interfaces.IJanusService;

public class JanusServiceImpl implements IJanusService{
	
	IBLJanus janusBussines = (BLJanusImpl) StremRainUserBussinesContextLoader.contextLoader()
			.getBean("janusBussines");
	IConverter<JanusCreateTokenDto, JanusCreationTokens> janusTokentConverter = (JanusTokenConverter) StremRainFacadesContextLoader.contextLoader()
			.getBean("janusTokenConverter");

	public JanusCreateTokenDto createJanusToken(JanusCreateTokenDto janusCreateTokenDto, String tenantID) {
		Integer idToken = janusBussines.saveJanusToken((JanusCreationTokens) janusTokentConverter.deConverter(janusCreateTokenDto), tenantID);
		janusCreateTokenDto.setId(idToken);
		return janusCreateTokenDto;
	}

	public boolean updateJanusToken(JanusCreateTokenDto janusCreateTokenDto, String tenantID) {
		return janusBussines.updateJanusToken((JanusCreationTokens) janusTokentConverter.deConverter(janusCreateTokenDto), tenantID);
	}

}
