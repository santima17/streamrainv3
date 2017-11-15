package com.tsi2.streamrain.converters.janus;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.datatypes.payment.PaymentMethodDto;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;
import com.tsi2.streamrain.model.generator.PaymentMethods;

public class JanusTokenConverter implements IConverter<JanusCreateTokenDto, JanusCreationTokens> {

	public JanusCreateTokenDto converter(JanusCreationTokens source) {
		JanusCreateTokenDto janusCreationTokens = new JanusCreateTokenDto();
		janusCreationTokens.setId(source.getId());
		janusCreationTokens.setJanusToken(source.getJanusToken());
		janusCreationTokens.setStreamrainRestBackend(source.getStreamrainRestBackend());
		janusCreationTokens.setDateCreation(source.getDateCreation());
		janusCreationTokens.setDateExpiration(source.getDateExpiration());
		janusCreationTokens.setDateUsage(source.getDateUsage());
		return janusCreationTokens;
	}

	public JanusCreationTokens deConverter(JanusCreateTokenDto source) {
		JanusCreationTokens janusCreationTokens = new JanusCreationTokens();
		janusCreationTokens.setId(source.getId());
		janusCreationTokens.setJanusToken(source.getJanusToken());
		janusCreationTokens.setStreamrainRestBackend(source.getStreamrainRestBackend());
		janusCreationTokens.setDateCreation(source.getDateCreation());
		janusCreationTokens.setDateExpiration(source.getDateExpiration());
		janusCreationTokens.setDateUsage(source.getDateUsage());
		return janusCreationTokens;
	}

	public List<JanusCreateTokenDto> convertAll(List<JanusCreationTokens> source) {
		List<JanusCreateTokenDto> dtoList = new ArrayList<JanusCreateTokenDto>();
		for(JanusCreationTokens janusCreationTokens : source) {
			dtoList.add(converter(janusCreationTokens));
		}
		return dtoList;
	}

	public List<JanusCreationTokens> deConvertAll(List<JanusCreateTokenDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
