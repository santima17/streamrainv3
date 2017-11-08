package com.tsi2.streamrain.converters.janus;

import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.janus.JanusServerDto;
import com.tsi2.streamrain.model.generator.JanusServers;

public class JanusServerConverter implements IConverter<JanusServerDto, JanusServers> {

	public JanusServerDto converter(JanusServers source) {
		// TODO Auto-generated method stub
		return null;
	}

	public JanusServers deConverter(JanusServerDto source) {
		JanusServers janusServers = new JanusServers();
		janusServers.setId(source.getId());
		janusServers.setAdminKey(source.getAdminKey());
		janusServers.setAdminSecret(source.getAdminSecret());
		janusServers.setAdminUrl(source.getAdminUrl());
		janusServers.setDateLastUpdateRequest(source.getDateLastUpdateRequest());
		janusServers.setIsEnable(source.isEnable());
		janusServers.setJanusUrl(source.getJanusUrl());
		janusServers.setName(source.getName());
		janusServers.setStreamrainRestToken(source.getStreamrainRestToken());
		janusServers.setStunServer(source.getStunServer());
		return janusServers;
	}

	public List<JanusServerDto> convertAll(List<JanusServers> source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<JanusServers> deConvertAll(List<JanusServerDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
