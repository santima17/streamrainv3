package com.tsi2.streamrain.services.janus.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusServerDto;

public interface IJanusService {

	JanusCreateTokenDto createJanusToken(final JanusCreateTokenDto janusCreateTokenDto, final String tenantID);
	
	boolean updateJanusToken(final JanusCreateTokenDto janusCreateTokenDto, final String tenantID);

	boolean createJanusServer(final JanusServerDto janusServerDto, final String tenantID);

	List<String> getAllJanusAdminUrl(final String tenantID);

	List<String> getAllJanusUrl(final String tenantID);

	String getJanusAdminUrlByToken(final String backendToken, final String tenantID);

	String getJanusUrlByToken(final String janusToken, final String tenantID);

	List<String> getAllAvailablesUserTokens(final String tenantID);
	
	List<JanusServerDto> getAllJanusServerActive(final String tenantID);
	
	List<JanusServerDto> getAllJanusServer(final String tenantID);

	JanusServerDto getJanusServer(final Integer janusServerId, final String tenantID);

	List<JanusCreateTokenDto> getAllJanusCreationTokens(final String tenantID);

	Integer getJanusServerIdForJanusCreationToken(final String tokenJanusCreationTokens, final String tenantID);

	JanusCreateTokenDto getJanusCreationToken(final String tokenJanusCreationTokens, final String tenantID);	

}
