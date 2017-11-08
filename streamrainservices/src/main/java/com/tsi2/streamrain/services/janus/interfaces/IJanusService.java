package com.tsi2.streamrain.services.janus.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.janus.JanusCreateTokenDto;
import com.tsi2.streamrain.datatypes.janus.JanusServerDto;

public interface IJanusService {

	JanusCreateTokenDto createJanusToken(JanusCreateTokenDto janusCreateTokenDto, final String tenantID);
	
	boolean updateJanusToken(JanusCreateTokenDto janusCreateTokenDto, final String tenantID);

	boolean createJanusServer(JanusServerDto janusServerDto, final String tenantID);

	List<String> getAllJanusAdminUrl(final String tenantID);

	List<String> getAllJanusUrl(final String currentTenant);

}
