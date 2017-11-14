package com.tsi2.streamrain.converters.janus;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.category.CategoryDto;
import com.tsi2.streamrain.datatypes.janus.JanusServerDto;
import com.tsi2.streamrain.model.generator.Categories;
import com.tsi2.streamrain.model.generator.JanusServers;

public class JanusServerConverter implements IConverter<JanusServerDto, JanusServers> {

	public JanusServerDto converter(JanusServers source) {
		JanusServerDto janusServers = new JanusServerDto();
		janusServers.setId(source.getId());
		janusServers.setAdminKey(source.getAdminKey());
		janusServers.setAdminSecret(source.getAdminSecret());
		janusServers.setAdminUrl(source.getAdminUrl());
		janusServers.setDateLastUpdateRequest(source.getDateLastUpdateRequest());
		janusServers.setEnable((source.isIsEnable()));
		janusServers.setJanusUrl(source.getJanusUrl());
		janusServers.setName(source.getName());
		janusServers.setStreamrainRestToken(source.getStreamrainRestToken());
		janusServers.setStunServer(source.getStunServer());
		return janusServers;
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
		List<JanusServerDto> dtoList = new ArrayList<JanusServerDto>();
		for(JanusServers jan : source) {
			dtoList.add(converter(jan));
		}
		return dtoList;
	}

	public List<JanusServers> deConvertAll(List<JanusServerDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
