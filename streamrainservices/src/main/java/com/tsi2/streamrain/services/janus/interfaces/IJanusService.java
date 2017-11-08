package com.tsi2.streamrain.services.janus.interfaces;

import com.tsi.streamrain.datatypes.janus.JanusCreateTokenDto;

public interface IJanusService {

	JanusCreateTokenDto createJanusToken(JanusCreateTokenDto janusCreateTokenDto, final String currentTenant);
	
	void updateJanusToken(JanusCreateTokenDto janusCreateTokenDto, final String tenantID);

}
