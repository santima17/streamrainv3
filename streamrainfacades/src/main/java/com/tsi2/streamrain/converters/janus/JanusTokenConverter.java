package com.tsi2.streamrain.converters.janus;

import java.util.List;

import com.tsi.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;

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
		// TODO Auto-generated method stub
		return null;
	}

	public List<JanusCreationTokens> deConvertAll(List<JanusCreateTokenDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
