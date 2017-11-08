package com.tsi2.streamrain.bussines.janus.interfaces;

import com.tsi2.streamrain.model.generator.JanusCreationTokens;

public interface IBLJanus {

	Integer saveJanusToken(JanusCreationTokens janusToken, String tenantID);

	void updateJanusToken(JanusCreationTokens deConverter, String tenantID);

}
