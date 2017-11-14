package com.tsi2.streamrain.services.janus.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.janus.implementations.BLJanusImpl;
import com.tsi2.streamrain.bussines.janus.interfaces.IBLJanus;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.janus.JanusServerConverter;
import com.tsi2.streamrain.converters.janus.JanusTokenConverter;
import com.tsi2.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusServerDto;
import com.tsi2.streamrain.model.generator.JanusCreationTokens;
import com.tsi2.streamrain.model.generator.JanusServers;
import com.tsi2.streamrain.services.janus.interfaces.IJanusService;

public class JanusServiceImpl implements IJanusService{
	
	IBLJanus janusBussines = (BLJanusImpl) StremRainUserBussinesContextLoader.contextLoader()
			.getBean("janusBussines");
	IConverter<JanusCreateTokenDto, JanusCreationTokens> janusTokentConverter = (JanusTokenConverter) StremRainFacadesContextLoader.contextLoader()
			.getBean("janusTokenConverter");
	IConverter<JanusServerDto, JanusServers> janusServerConverter = (JanusServerConverter) StremRainFacadesContextLoader.contextLoader()
			.getBean("janusServerConverter");

	

	public JanusCreateTokenDto createJanusToken(JanusCreateTokenDto janusCreateTokenDto, String tenantID) {
		Integer idToken = janusBussines.saveJanusToken((JanusCreationTokens) janusTokentConverter.deConverter(janusCreateTokenDto), tenantID);
		janusCreateTokenDto.setId(idToken);
		return janusCreateTokenDto;
	}

	public boolean updateJanusToken(JanusCreateTokenDto janusCreateTokenDto, String tenantID) {
		return janusBussines.updateJanusToken((JanusCreationTokens) janusTokentConverter.deConverter(janusCreateTokenDto), tenantID);
	}

	public boolean createJanusServer(JanusServerDto janusServerDto, String tenantID) {
		JanusServers janusServers = janusServerConverter.deConverter(janusServerDto);
		Integer idJanusServer = janusBussines.saveJanusServer(janusServers, janusServerDto.getTokenJanusCreationTokens(), tenantID);
		return idJanusServer != null;
	}
	
	public List<String> getAllJanusAdminUrl(final String tenantID){
		return janusBussines.getAllJanusAdminUrl(tenantID);
	}
	
	public List<String> getAllJanusUrl(final String tenantID){
		return janusBussines.getAllJanusUrl(tenantID);
	}

	@Override
	public String getJanusAdminUrlByToken(final String backendToken, final String currentTenant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJanusUrlByToken(String janusToken, String currentTenant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllAvailablesUserTokens(final String tenantID) {
		return janusBussines.getAllAvailablesUserTokens(tenantID);
	}

	public List<JanusServerDto> getAllJanusServerActive(final String tenantID) {
		return janusServerConverter.convertAll(janusBussines.getAllJanusServerActive(tenantID));
	}

}
