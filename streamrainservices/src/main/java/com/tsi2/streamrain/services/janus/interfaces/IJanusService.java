package com.tsi2.streamrain.services.janus.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusLiveOnlyInfoDto;
import com.tsi2.streamrain.datatypes.janus.JanusServerDto;

public interface IJanusService {

	JanusCreateTokenDto createJanusToken(final JanusCreateTokenDto janusCreateTokenDto, final String tenantID);
	
	boolean updateJanusToken(final JanusCreateTokenDto janusCreateTokenDto, final String tenantID);

	boolean createJanusServer(final JanusServerDto janusServerDto, final String tenantID);

	List<String> getAllJanusAdminUrl(final String tenantID);

	List<String> getAllJanusUrl(final String currentTenant);

	String getJanusAdminUrlByToken(final String backendToken, final String currentTenant);

	String getJanusUrlByToken(final String janusToken, final String currentTenant);

	List<String> getAllAvailablesUserTokens(final String currentTenant);
	
	List<JanusServerDto> getAllJanusServerActive(final String currentTenant);

}
